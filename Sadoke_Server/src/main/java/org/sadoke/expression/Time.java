package org.sadoke.expression;

import java.util.Calendar;
import java.util.Locale;

import org.sadoke.util.general.VoiceUtil;

public class Time implements Expression {

	@Override
	public void run(Object[] args) {
		final Calendar calendar = Calendar.getInstance();
		final int day = calendar.get(Calendar.DAY_OF_MONTH);
		final String dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK,
				Calendar.LONG_FORMAT, Locale.ENGLISH);
		final String monthName = calendar.getDisplayName(Calendar.MONTH,
				Calendar.LONG_FORMAT, Locale.ENGLISH);
		final int minutes = calendar.get(Calendar.MINUTE);
		final int hour = calendar.get(Calendar.HOUR_OF_DAY);
		final StringBuilder s = new StringBuilder();
		s.append("It is ");
		s.append(dayName);
		s.append(" ");
		s.append(day + ". of ");
		s.append(monthName + "");
		s.append(hour + "hours and " + minutes + "minutes");
		VoiceUtil.say(s.toString());;
	}

}
