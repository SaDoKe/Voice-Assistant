package org.sadoke.main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerSadoke implements SadokeInterface {
	private final String url;
	private final int port;
	private final HttpClient client;
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public ServerSadoke(Config config) {
		port = config.getPort();
		final StringBuilder urlBuilder = new StringBuilder();
		if (!config.getUrl().contains("http"))
			urlBuilder.append("http://");
		urlBuilder.append(config.getUrl());
		urlBuilder.append(":" + port);
		urlBuilder.append("/recieve");
		url = urlBuilder.toString();
		client = HttpClientBuilder.create().build();
	}
	@Override
	public String recieveHypothesis(List<String> commands) {
		if (commands.isEmpty())
			return "";
		try {
			final HttpResponse response = client
					.execute(new HttpGet(buildURI(commands)));
			return EntityUtils.toString(response.getEntity(),
					StandardCharsets.UTF_8);
		} catch (final IOException e) {
			log.error(e.getLocalizedMessage());
		}
		return "";
	}
	private String buildURI(List<String> commands) {
		final StringBuilder sb = new StringBuilder(url);
		sb.append("?hypothesis=");
		for (final String command : commands)
			sb.append(cleanXML(command).trim().replaceAll(" ", "%20") + ",");
		return sb.toString();
	}
	private String cleanXML(String command) {
		if (!command.isEmpty() && command.contains("<"))
			return command.substring(4, command.indexOf('<', 4));
		return command;
	}
	@Override
	public String getReinitialization(List<String> commands) {

		return null;
	}

}
