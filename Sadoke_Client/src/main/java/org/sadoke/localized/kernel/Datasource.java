package org.sadoke.localized.kernel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has all information about the currently running sadoke. This class
 * should not be accessed without the provided utils.
 * 
 * @author Saied Sadegh
 *
 */
public class Datasource {

	public static List<String> speech = new ArrayList<>();;

	public static String name = "jarvis";

	public List<String> getSpeech() {
		return Datasource.speech;
	}

	public void setSpeech(List<String> speech) {
		Datasource.speech = speech;
	}

}
