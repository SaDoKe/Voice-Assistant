package org.sadoke.responses;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming
public class GetTokenResponse {
	
	@XmlElement(name="tokens")
	private List<Token> tokens ;
	
	
	public void addToken( String name, String token) {
		if(tokens==null)
			tokens= new ArrayList<>();
		tokens.add(new Token(name,token));
	}
	
	
	public List<Token> getTokens() {
		return tokens;
	}


	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}


	class Token{
		public Token(String name, String token) {
			this.name=name;
			this.token=token;
		}
		@XmlElement(name="name")
		private String name;
		@XmlElement(name="token")
		private String token;
		
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		
	}
}
