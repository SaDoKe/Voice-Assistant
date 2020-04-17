package org.sadoke.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sadoke.expression.Expression;
import org.sadoke.expression.Regex;
import org.springframework.beans.factory.annotation.Value;
/**
 * This class has all information about the currently running sadoke. This class
 * should not be accessed without the provided utils.
 * 
 * @author Saied Sadegh
 *
 */
public class Datasource {

	private Map<Regex, Class<? extends Expression>> commands = new HashMap<>();
	public static List<String> speech;
	@Value("${spring.application.name}")
	public static String name = "jarvis";
	public Datasource() {
		Datasource.speech = new ArrayList<>();
	}
	public Datasource(Map<Regex, Class<? extends Expression>> commands) {
		this.commands = commands;
		Datasource.speech = new ArrayList<>();

	}

	public List<String> getSpeech() {
		return Datasource.speech;
	}

	public void setSpeech(List<String> speech) {
		Datasource.speech = speech;
	}

	/**
	 * This returns the list of currently saved commands.
	 * 
	 * @return commandMap
	 */
	public Map<Regex, Class<? extends Expression>> getCommands() {
		return commands;
	}
	public void setCommands(Map<Regex, Class<? extends Expression>> commands) {
		this.commands = commands;
	}
	public static void setName(String name) {
		Datasource.name = name;
	}

}
