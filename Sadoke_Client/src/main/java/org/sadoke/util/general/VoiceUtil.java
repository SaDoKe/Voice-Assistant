package org.sadoke.util.general;

import marytts.modules.synthesis.Voice;
/**
 * EN: Makes your programm speak.
 * DE: Ist die Schnittstelle um Text in MarryTTS(TextToSpeech) auszugeben.
 * 
 * @author Kevin Kupsch
 *
 */
public class VoiceUtil {

	private static MarryTTS mTTS = null;
	/**
	 * EN: Function returns the String with default values.
	 * DE: Funktion gibt den String mit den default werten gesprochen aus.
	 * 
	 * @param spoken
	 *            String that being spoken
	 */
	public static final void say(String spoken) {
		if (VoiceUtil.mTTS == null)
			VoiceUtil.mTTS = new MarryTTS();
		VoiceUtil.mTTS.setVoice("cmu-rms-hsmm");
		VoiceUtil.mTTS.speak(spoken, 1.0f, true, true);

	}

	/**
	 * EN: Function returns String with self-selected voice.
	 * DE: Funktion gibt den String mit eine gewälten Stimme gesprochen aus. Die vorhandenen Stimmen kann mit mit
	 * avibleVoices() abrufen.
	 * 
	 * @param spoken
	 *            String that being spoken
	 * @param voice
	 *            the chosen Voice
	 */
	public static final void sayWith(String spoken, String voice) {
		if (VoiceUtil.mTTS == null)
			VoiceUtil.mTTS = new MarryTTS();
		VoiceUtil.mTTS.setVoice("cmu-rms-hsmm");
		VoiceUtil.mTTS.speak(spoken, 1.0f, true, true);
	}
	/**
	 * EN: Function returns String with self-selected voice and start a daemon.
	 * DE: Funktion bei der man entscheide kann welcher Text und welche Stimme gesprochen wird und ob es als demeaon
	 * gestartet werden soll
	 * 
	 * @param spoken
	 *            String that being spoken
	 * @param voice
	 *            the chosen Voice
	 * @param daemon
	 *            True: The thread that will start the text to speech Player
	 *            will be a daemon Thread False: The thread that will start the
	 *            text to speech Player will be a normal non daemon Thread
	 */
	public static final void sayControled(String spoken, String voice,
			boolean daemon) {
		if (VoiceUtil.mTTS == null)
			VoiceUtil.mTTS = new MarryTTS();
		VoiceUtil.mTTS.setVoice(voice);
		VoiceUtil.mTTS.speak(spoken, 2.0f, daemon, false);
	}
	/**
	 * DE: Funktion die das Gesprochene mit allen fasseten einstellbar ausgibt
	 * 
	 * @param spoken
	 *            String that being spoken
	 * @param voice
	 *            the chosen Voice
	 * @param daemon
	 *            True: The thread that will start the text to speech Player
	 *            will be a daemon Thread False: The thread that will start the
	 *            text to speech Player will be a normal non daemon Thread
	 * @param join
	 *            True: The current Thread calling this method will
	 *            wait(blocked) until the Thread which is playing the Speech
	 *            finish False: The current Thread calling this method will
	 *            continue freely after calling this method
	 */
	public static final void sayFullControled(String spoken, String voice,
			boolean daemon, boolean join) {
		if (VoiceUtil.mTTS == null)
			VoiceUtil.mTTS = new MarryTTS();
		VoiceUtil.mTTS.setVoice(voice);
		VoiceUtil.mTTS.speak(spoken, 2.0f, daemon, join);
	}
	/**
	 * EN: Returns voices in console
	 * DE: Gibt die Stimmen in der Console aus
	 */
	public static final void availableVoices() {
		Voice.getAvailableVoices().stream().forEach(System.out::println);
	}
}