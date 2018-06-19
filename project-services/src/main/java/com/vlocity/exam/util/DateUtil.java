package com.vlocity.exam.util;

import java.time.LocalDate;
import java.util.Date;

public class DateUtil {

	public static LocalDate getEndDate(LocalDate startDate, long duration) {
		return startDate.plusDays(duration);
	}

	public static boolean hasOverlapped(Date prevEndDate, Date currStartDate) {
		return (prevEndDate.compareTo(currStartDate) >= 0);
	}
}
