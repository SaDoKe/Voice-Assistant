package org.sadoke.responses;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
@JsonNaming
public class ExpressionResponse {

	@XmlElement(name = "status")
	private String status;

	@XmlElement(name = "speech")
	private List<String> speech = null;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getSpeech() {
		return speech;
	}

	public void setSpeech(List<String> speech) {
		this.speech = speech;
	}

}