package com.vlocity.exam.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
}
