package org.sadoke.expression;
import org.sadoke.util.annotations.Express;
import org.sadoke.util.general.VoiceUtil;
//Haha
@Express(express = {"Hello", "Greetings", "Greeting"}, type = Greeting.class)
public class Greeting implements Expression {

	@Override
	public void run(Object[] args) {
		VoiceUtil.say(
				"Hello guests. Welcome to the presentation about my favorite topic. Mee. I hope my three makers do not screw up so you can enjoy the show.");
	}
}
