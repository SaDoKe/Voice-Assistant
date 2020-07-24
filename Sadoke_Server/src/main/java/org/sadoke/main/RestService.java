package org.sadoke.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.sadoke.responses.ExpressionResponse;
import org.sadoke.responses.GetTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestService {

	private final Logger log = LoggerFactory.getLogger(RestService.class);

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/recieve")
	@ResponseBody
	public ExpressionResponse recieve(@RequestParam List<String> hypothesis) {
		Launcher.evaluationEngine.recieve(hypothesis);
		final ExpressionResponse re = new ExpressionResponse();
		log.info("Speech to say {}", Datasource.speech);
		if (!Datasource.speech.isEmpty()) {
			re.setSpeech(new ArrayList<String>(Datasource.speech));
			re.setStatus("200");
			Datasource.speech.clear();
		}
		return re;
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/tokens")
	@ResponseBody
	public GetTokenResponse getToken() {
		final GetTokenResponse response = new GetTokenResponse();
		log.info("GET Token");
		log.info("I log in {}", getPropertyPath());
		final Properties props = new Properties();
		try {
			props.load(new FileInputStream(getPropertyPath()));
			final Enumeration<?> emu = props.propertyNames();
			while (emu.hasMoreElements()) {
				final String element = emu.nextElement().toString();
				if (element.contains("token."))
					response.addToken(element, props.getProperty(element));
			}
		} catch (final IOException ex) {
			log.error("GetToken cant handle the data", ex);
		}
		return response;
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/handshake")
	@ResponseBody
	public ResponseEntity<Boolean> getResponse() {
		log.info("Called REST Service");
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	private String getPropertyPath() {
		return System.getProperty("config");
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/token/update")
	@ResponseBody
	public ResponseEntity<String> updateToken(@RequestParam String name,
			@RequestParam String token) {
		final String propertyPath = getPropertyPath();
		log.info("I log in {}", propertyPath);
		final Properties props = new Properties();
		try (InputStream input = new FileInputStream(propertyPath)) {
			props.load(input);
			if (props.containsKey(name)) {
				props.replace(name, token);
				props.store(new FileOutputStream(propertyPath), null);
				return new ResponseEntity<>("OK", HttpStatus.OK);
			}
			return new ResponseEntity<>("Token not found",
					HttpStatus.BAD_REQUEST);
		} catch (final IOException ex) {
			log.error("UpdateToken cant handle the data", ex);
			return new ResponseEntity<>("UpdateToken cant handle the data",
					HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/token/add")
	@ResponseBody
	public ResponseEntity<String> addToken(@RequestParam String name,
			@RequestParam String token) {
		final String propertyPath = getPropertyPath();
		log.info("I log in {}", propertyPath);
		final Properties props = new Properties();
		try {
			props.load(new FileInputStream(propertyPath));
			if (props.containsKey(name))
				return new ResponseEntity<>("Token already exist",
						HttpStatus.BAD_REQUEST);
			props.setProperty(name, token);
			props.store(new FileOutputStream(propertyPath), null);
			return new ResponseEntity<>("OK", HttpStatus.OK);

		} catch (final IOException ex) {
			log.error("addToken cant handle the data", ex);
			return new ResponseEntity<>("addToken cant handle the data",
					HttpStatus.BAD_REQUEST);
		}
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/token")
	@ResponseBody
	public ResponseEntity<String> deleteToken(@RequestParam String name) {
		final String propertyPath = getPropertyPath();
		final Properties props = new Properties();
		try {
			props.load(new FileInputStream(propertyPath));
			log.info(name);
			if (name.contains("token.custom.")) {
				props.remove(name);
				props.store(new FileOutputStream(propertyPath), null);
				return new ResponseEntity<>("Token is deleted", HttpStatus.OK);
			} else {
				props.setProperty(name, "null");
				props.store(new FileOutputStream(propertyPath), null);
				return new ResponseEntity<>("Token set to null", HttpStatus.OK);
			}
		} catch (final IOException ex) {
			log.error("deleteToken cant handle the data.", ex);
			return new ResponseEntity<>("delteToken cant handle the data",
					HttpStatus.BAD_REQUEST);
		}
	}
}