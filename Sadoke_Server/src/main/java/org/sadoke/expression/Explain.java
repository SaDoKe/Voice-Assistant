package org.sadoke.expression;

import org.sadoke.util.general.VoiceUtil;

public class Explain implements Expression {

	@Override
	public void run(Object[] args) {
		VoiceUtil.say(
				"I am made from two parts and their connection. The first is a client which represents my senses. The second part the server, my heart and brain. Restful Services the blood in between.");
	}

}
