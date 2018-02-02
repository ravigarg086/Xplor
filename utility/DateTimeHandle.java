package com.utility;

import java.util.Calendar;
import java.util.Date;

public class DateTimeHandle {

	public static String getTimestamp() {

		String[] s = null;

		try {

			Calendar calendar = Calendar.getInstance();

			Date date = calendar.getTime();

			String test = date.toString();

			s = test.split(" ");

		} catch (Exception e) {

			e.printStackTrace();
			ErrorLogger.logError(e.getClass().getName(), "getTimestamp",
					e.getMessage());
		}
		return s[2] + "/" + s[1] + "/" + s[5] + " " + s[3];
	}
}
