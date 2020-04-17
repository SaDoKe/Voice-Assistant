package org.sadoke.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.sadoke.expression.Expression;
import org.sadoke.expression.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This class recieves all sound input and evaluates if it is a valid command.
 * If it is valid one it is saved in a HashMap together with its arguments.
 * 
 * @author Saied Sadegh
 *
 */
public class EvaluationEngine {

	private final Datasource source;
	Logger log = LoggerFactory.getLogger(this.getClass());
	private ExpressionRunner expressionRunner;
	private final LinkedHashMap<Class<? extends Expression>, Object[]> savedCommands = new LinkedHashMap<>();

	public EvaluationEngine(Datasource source) {
		this.source = source;
	}

	/**
	 * Looks into the list of valid commands and gives the expression all
	 * remaining arguments.
	 *
	 * @param actualValid
	 * 
	 */
	public void evaluteResponse(List<String> actualValid) {
		final Map<Integer, Regex> possibleCommands = new HashMap<>();
		final Map<Integer, String> possibleHypothesis = new HashMap<>();
		int i = 4;
		for (final String hypothesis : actualValid) {
			for (final Regex command : source.getCommands().keySet())
				evaluatePossibility(command, possibleCommands,
						possibleHypothesis, hypothesis, i);
			i--;
		}
		if (possibleCommands.isEmpty())
			log.warn("No valid commands found");
		else {
			executeHighestPriority(possibleCommands, possibleHypothesis);
			log.info("There are still {} commands in the queue to be executed.",
					savedCommands.size());

		}
	}

	private void executeHighestPriority(Map<Integer, Regex> possibleCommands,
			Map<Integer, String> possibleHypothesis) {
		Regex re = null;
		int i = 0;
		for (; i < 10; i++) {
			re = possibleCommands.get(i);
			if (re != null)
				break;
		}
		final String hyp = possibleHypothesis.get(i);
		if (re != null) {
			final String input = re.getBase().matcher(hyp).replaceAll("").trim()
					.toLowerCase();
			log.info("arguments are: {}", input);
			expressionRunner.run(source.getCommands().get(re),
					Arrays.asList(input).toArray());
		}
	}

	private void evaluatePossibility(Regex command,
			Map<Integer, Regex> possibleCommands,
			Map<Integer, String> possibleHypothesis, String input, int prio) {
		if (command.getRegex().matcher(input).matches()) {
			log.info("{} has {} prio", input, prio);
			possibleCommands.put(prio, command);
			possibleHypothesis.put(prio, input);
		} else if (command.getBase().matcher(input.toLowerCase()).matches()
				&& Math.abs(command.getBase().matcher(input).replaceAll("")
						.trim().split(" ").length
						- command.getArgAmount()) < 2) {
			log.info("{} has {} prio", input, prio + 5);
			possibleCommands.put(prio + 5, command);
			possibleHypothesis.put(prio + 5, input);
		}

	}

	/**
	 * Checks if input contains the name;
	 *
	 * @param input
	 *            to validate
	 * @return if the string was valid to be processed
	 *
	 */
	private boolean isValidInput(String input) {
		return input.toLowerCase().contains(Datasource.name);
	}

	/**
	 * Normalizes a command call by removing the name and putting it into
	 * lowercase;
	 *
	 * @param command
	 *            in its raw state
	 * @return a normalized String
	 *
	 */
	private String normalizeCommand(String command) {

		return cleanXML(command.toLowerCase().replaceAll(Datasource.name, ""))
				.trim();
	}

	private String cleanXML(String command) {
		if (command.contains("<"))
			return command.substring(4, command.indexOf('<', 5));
		return command.trim();
	}

	/**
	 * Takes msgs and validates them. If they are valid commands they will be
	 * put into the evaluatedCommands List
	 * 
	 * @param command
	 */
	public void recieve(List<String> commands) {
		log.debug("Hypothesis arrived");
		final List<String> actualValid = new ArrayList<>();
		for (final String command : commands)
			if (isValidInput(command))
				actualValid.add(normalizeCommand(command));
		if (!actualValid.isEmpty())
			log.info("Those were valid {}", actualValid);
		evaluteResponse(actualValid);

	}

	public void setRunner(ExpressionRunner expressionRunner) {
		this.expressionRunner = expressionRunner;
	}

}
