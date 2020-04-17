import pyaudio
import requests
from pocketsphinx.pocketsphinx import *
import pygame
from io import BytesIO
from gtts import gTTS
from pygame import mixer
import configparser
import pyttsx3


config = Decoder.default_config()
config.set_string('-hmm', './en-us')
config.set_string('-lm', './main.lm')
config.set_string('-dict',  './main.dic')
decoder = Decoder(config)


p = pyaudio.PyAudio()
stream = p.open(format=pyaudio.paInt16, channels=1, rate=16000, input=True, frames_per_buffer=1024)
stream.start_stream()
in_speech_bf = False

configFile = configparser.ConfigParser()
configFile.read('config.ini')
ip = configFile['DEFAULT']['IP_ADDRESS']
port = configFile['DEFAULT']['PORT']
rest_url = ip+':'+port+'/recieve'
voice = configFile['DEFAULT']['VOICE']
if voice =='google':
    mixer.init(frequency=24000) #32000 cute little mouse. Green Titan 16000
else:
    engine = pyttsx3.init()

decoder.start_utt()

while True:
    try:
        hypothesis = []
        buf = stream.read(256,exception_on_overflow = False)
        if buf:
            decoder.process_raw(buf, False, False)
            if decoder.get_in_speech() != in_speech_bf:
                in_speech_bf = decoder.get_in_speech()
                if not in_speech_bf:
                    decoder.end_utt()
                    i = 0
                    iter = decoder.nbest().__iter__()
                    while i < 5:
                        hyp = iter.next()
                        if hyp is not None and hyp.hypstr is not None:
                            print('Result', i, " :", hyp.hypstr)
                            hypothesis.append(hyp.hypstr)
                        hyp = iter.next()
                        i = i + 1
                    response = requests.get(rest_url, params={'hypothesis': ','.join(hypothesis)})
                    hypothesis.clear()
                    speech = response.json()['speech']

                    if speech is not None:
                        if voice == 'google':
                            tts = gTTS(speech[0], 'en')
                            fp = BytesIO()
                            tts.write_to_fp(fp)
                            fp.seek(0)
                            mixer.music.load(fp)
                            mixer.music.play()
                            while mixer.music.get_busy():
                                pygame.time.Clock().tick(10)
                        else:
                            engine.say(speech[0])
                            engine.runAndWait()

                    decoder.start_utt()
    except Exception as e:
        print("Something went wrong... Tries to continue")
        print(e);

decoder.end_utt()