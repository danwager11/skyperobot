package com.paulhimes.skylon.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.paulhimes.skylon.RegexBuilder;

public class Rule {
	// <rule negativeFlag=false type=sender match=contains>paul</rule>
	private boolean negativeFlag = false;
	private RuleType type = RuleType.CONTENT;
	private RuleMatch match = RuleMatch.CONTAINS;
	private String value;

	private Pattern pattern;

	public Rule(RuleType type, RuleMatch match, String value,
			boolean negativeFlag) {
		this.type = type;
		this.match = match;
		this.value = value;
		this.negativeFlag = negativeFlag;

		String regexString = "";
		switch (match) {
		case CONTAINS:
			regexString = RegexBuilder.contains(value);
			break;
		case ENDS_WITH:
			regexString = RegexBuilder.endsWith(value);
			break;
		default:
			break;
		}

		pattern = Pattern.compile(regexString, Pattern.DOTALL);
	}

	public RuleMatch getMatch() {
		return match;
	}

	public RuleType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public boolean isNegativeFlag() {
		return negativeFlag;
	}

	public static enum RuleType {
		SENDER, CONTENT;
	}

	public static enum RuleMatch {
		CONTAINS, ENDS_WITH;
	}

	public boolean matches(String senderId, String content) {
		String target;

		switch (type) {
		case CONTENT:
			target = content;
			break;
		case SENDER:
			target = senderId;
		default:
			target = senderId;
			break;
		}

		Matcher matcher = pattern.matcher(target);

		System.out.println("Looking for: " + pattern.toString());
		return matcher.matches() ^ negativeFlag;
	}
}
