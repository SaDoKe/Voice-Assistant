package org.sadoke.main;

import org.springframework.stereotype.Component;

@Component
public class Config {

	private boolean usage;

	private int port;

	private String url;

	public Config(String server, String port, String url) {
		super();
		usage = "true".equalsIgnoreCase(server);
		this.port = Integer.parseInt(port);
		this.url = url;
	}

	public boolean isServer() {
		return usage;
	}

	public void setServer(boolean server) {
		usage = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
