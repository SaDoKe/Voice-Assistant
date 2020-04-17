package org.sadoke.expression;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Regex {
	private final Pattern regexPattern;
	private final Pattern base;
	private final Pattern wordPattern;
	private final int argAmount;
	public Regex(String base, int argAmount) {
		this(base, argAmount, false, false);
	}
	public Regex(String base, int argAmount, boolean front) {
		this(base, argAmount, front, false);
	}
	public Regex(String base, int argAmount, boolean front, boolean back) {
		this.base = Pattern.compile(base, 2);
		this.argAmount = argAmount;
		wordPattern = Pattern.compile(Regex.getWordPattern(argAmount), 2);
		regexPattern = Pattern
				.compile(this.base.pattern() + wordPattern.pattern(), 2);
	}

	public Regex(int argAmount, String... base) {
		this(argAmount, false, false, base);
	}
	public Regex(int argAmount, boolean front, String... base) {
		this(argAmount, front, false, base);
	}

	public Regex(int argAmount, boolean front, boolean back, String... base) {
		this("(" + Arrays.asList(base).stream().distinct()
				.reduce((s, e) -> s.concat("|" + e)).get() + ")", argAmount,
				front, back);

	}
	public static String getWordPattern(int amount) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < amount; i++)
			sb.append("\\s\\w+");
		return sb.toString();
	}
	public Pattern getRegex() {
		return regexPattern;
	}

	public Pattern getBase() {
		return base;
	}
	public Pattern getWordPattern() {
		return wordPattern;
	}
	public int getArgAmount() {
		return argAmount;
	}

}
