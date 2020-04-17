package org.sadoke.expression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.tika.metadata.Metadata;
import org.sadoke.util.general.Util;
import org.sadoke.util.general.VoiceUtil;
import org.xml.sax.helpers.DefaultHandler;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Play extends Application implements Expression {

	@Override
	public void run(Object[] args) {
		Application.launch((String[]) args);
	}

	@Override
	public void start(Stage primaryStage) {

		final List<String> list = getParameters().getRaw();

		// z.B. path = "C:\\Users\\Your User\\Music\\sample"
		String path = "path\\to\\file\\";

		final List<File> files = Util.trycast(
				FileUtils.listFiles(new File(path), null, false), List.class);
		final List<String> songsList = new ArrayList<>();
		final Iterator<File> it = files.iterator();
		while (it.hasNext()) {
			final File i = it.next();

			InputStream input;
			Metadata metadata = null;

			try {
				input = new FileInputStream(new File(path + i.getName()));
				new DefaultHandler();
				metadata = new Metadata();
				input.close();
			} catch (final FileNotFoundException e) {

			} catch (final IOException e) {

			}

			songsList.add(metadata.get("Title"));
		}

		for (int j = 0; j < songsList.size(); j++)
			if (list.get(0).trim() == songsList.get(j))
				path += files.get(j).getName();

		Media media = null;

		try {
			media = new Media(new File(path).toURI().toString());
			final MediaPlayer mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setAutoPlay(true);
		} catch (final Exception e) {

			VoiceUtil.say("Song not Found");
		}

	}

}
