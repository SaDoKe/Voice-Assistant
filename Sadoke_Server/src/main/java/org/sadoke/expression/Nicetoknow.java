package org.sadoke.expression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import org.sadoke.util.general.VoiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Nicetoknow implements Expression {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void run(Object[] args) {

		String fact = "I love you";

		try (final BufferedReader br = new BufferedReader(
				new FileReader("facts.txt"))) {
			final int l = (int) Files.lines(Paths.get("facts.txt")).count();

			final int w = new Random().nextInt(l) + 1;

			for (int i = 0; i < w; i++)
				fact = br.readLine();

		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}

		VoiceUtil.say("Here is a fact: " + fact);
	}

}
