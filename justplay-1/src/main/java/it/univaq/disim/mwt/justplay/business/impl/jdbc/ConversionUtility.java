package it.univaq.disim.mwt.justplay.business.impl.jdbc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversionUtility {
	public static String addPercentSuffix(String s) {
		if (s == null || "".equals(s)) {
			return "%";
		}
		return s + "%";
	}
	
	public static Date string2Date(String date, String dateFormat) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
