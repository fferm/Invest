package se.fermitet.invest.converter;

import org.joda.time.LocalDate;

public class DateConverter {
	public LocalDate dateFromString(String string) {
		if (string == null || string.length() == 0) return null;
		
		LocalDate ret = new LocalDate();
		
		String[] splits = string.split("-");
		
		int yearInt = Integer.parseInt(splits[0]) + 2000;
		int monthInt = Integer.parseInt(splits[1]);
		int dayInt = Integer.parseInt(splits[2]);

		ret = ret.withYear(yearInt);
		ret = ret.withMonthOfYear(monthInt);
		ret = ret.withDayOfMonth(dayInt);
		
		return ret;
		
	}
}
