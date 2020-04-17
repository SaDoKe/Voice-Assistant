package org.sadoke.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.sadoke.expression.Expression;
import org.sadoke.expression.Regex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the starter for the whole software and initializes the whole software
 * which means that the Datasource, EvaluationEngine, ExpressionRunner and
 * Listener are created and started
 * 
 * @author Saied Sadegh
 *
 */
@SpringBootApplication
public class Launcher {

	private static final String PACKAGE_EXPRESSION = "org.sadoke.expression.";
	private static final Logger log = LoggerFactory.getLogger(Launcher.class);
	static final Datasource source = new Datasource();
	static final EvaluationEngine evaluationEngine = new EvaluationEngine(Launcher.source);

	public static void main(String[] args) throws IOException {
		Launcher.init();
		SpringApplication.run(Launcher.class, args);
	}

	private static void init() {
		final Map<Regex, Class<? extends Expression>> commands = new HashMap<>();
		System.setProperty("log.server", System.getProperty("user.home").concat("/sadoke/server.log"));
		final String path = Launcher.getApplicationProperties();
		System.setProperty("config", path);
		Launcher.fillCommandsFromFile(commands);
		Launcher.source.setCommands(commands);
		Launcher.evaluationEngine.setRunner(new ExpressionRunner());

	}

	private static void fillCommandsFromFile(
			final Map<Regex, Class<? extends Expression>> commands) {
		try (BufferedReader bR = new BufferedReader(
				new FileReader("command.config"))) {
			String configurationLine = null;
			while ((configurationLine = bR.readLine()) != null) {
				final String[] parts = configurationLine.split(";");
				final Class<?> expression = Class
						.forName(Launcher.PACKAGE_EXPRESSION.concat(parts[0]));
				final int argsNum = Integer.parseInt(parts[1]);
				String[] reg=Arrays.copyOfRange(parts, 2, parts.length + 1);
				for(int i=0;i<reg.length;i++)
					if(reg[i]!=null)
					reg[i]=reg[i].toLowerCase().trim();
				commands.put(
						new Regex(argsNum,reg),
							(Class<? extends Expression>) expression);
			}
			Launcher.log.debug("Found commands= {}", commands);

		} catch (final Exception e) {
			Launcher.log.error(e.getMessage());
		}
	}

	private static String getApplicationProperties() {
		try {
			File f = new File(
					Thread.currentThread().getContextClassLoader().getResource("application.properties").getPath());
			if (!f.exists())
				f = new File("./application.properties");
			if (!f.exists())
				f = new File("./src/main/resources/application.properties");

			return f.getAbsolutePath();

		} catch (final Exception e) {

		}

		return "";

	}

}
