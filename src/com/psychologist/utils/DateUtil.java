package com.psychologist.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public final class DateUtil {

	// Init
	// ---------------------------------------------------------------------------------------

	private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("^\\d{8}$", "yyyyMMdd");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
			put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy;dd/MM/yyyy");
			put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
			put("^\\d{1,2}-[a-z]{3}-\\d{4}$", "dd-MMM-yyyy");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
			put("^\\d{12}$", "yyyyMMddHHmm");
			put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
			put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
			put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
			put("^\\d{14}$", "yyyyMMddHHmmss");
			put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
			put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
			put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
			put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
			put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
			put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
			put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
		}
	};

	private DateUtil() {
		// Utility class, hide the constructor.
	}

	// Converters
	// ---------------------------------------------------------------------------------

	/**
	 * Convert the given date to a Calendar object. The TimeZone will be derived
	 * from the local operating system's timezone.
	 * 
	 * @param date
	 *            The date to be converted to Calendar.
	 * @return The Calendar object set to the given date and using the local
	 *         timezone.
	 */
	public static Calendar toCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Convert the given date to a Calendar object with the given timezone.
	 * 
	 * @param date
	 *            The date to be converted to Calendar.
	 * @param timeZone
	 *            The timezone to be set in the Calendar.
	 * @return The Calendar object set to the given date and timezone.
	 */
	public static Calendar toCalendar(Date date, TimeZone timeZone) {
		Calendar calendar = toCalendar(date);
		calendar.setTimeZone(timeZone);
		return calendar;
	}

	/**
	 * Parse the given date string to date object and return a date instance
	 * based on the given date string. This makes use of the
	 * {@link DateUtil#determineDateFormat(String)} to determine the
	 * SimpleDateFormat pattern to be used for parsing.
	 * 
	 * @param dateString
	 *            The date string to be parsed to date object.
	 * @return The parsed date object.
	 * @throws ParseException
	 *             If the date format pattern of the given date string is
	 *             unknown, or if the given date string or its actual date is
	 *             invalid based on the date format pattern.
	 */
	public static Date parse(String dateString) throws ParseException {
		String dateFormat = determineDateFormat(dateString);
		if (dateFormat == null) {
			throw new ParseException("Unknown date format.", 0);
		}
		return parse(dateString, dateFormat);
	}

	/**
	 * Validate the actual date of the given date string based on the given date
	 * format pattern and return a date instance based on the given date string.
	 * 
	 * @param dateString
	 *            The date string.
	 * @param dateFormat
	 *            The date format pattern which should respect the
	 *            SimpleDateFormat rules.
	 * @return The parsed date object.
	 * @throws ParseException
	 *             If the given date string or its actual date is invalid based
	 *             on the given date format pattern.
	 * @see SimpleDateFormat
	 */
	public static Date parse(String dateString, String dateFormat) throws ParseException {
		String[] dateFmts = dateFormat.split(";");
		for (String dateFmt : dateFmts) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFmt);
			// Don't automatically convert
			simpleDateFormat.setLenient(false);
			try {
				return simpleDateFormat.parse(dateString);
			}
			catch (Exception e) {
			}
		}
		throw new ParseException("Unknown date format.", 0);
	}

	// Validators
	// ---------------------------------------------------------------------------------

	/**
	 * Checks whether the actual date of the given date string is valid This
	 * makes use of the {@link DateUtil#determineDateFormat(String)} to
	 * determine the SimpleDateFormat pattern to be used for parsing.
	 * 
	 * @param dateString
	 *            The date string.
	 * @return True if the actual date of the given date string is valid.
	 */
	public static boolean isValidDate(String dateString) {
		try {
			parse(dateString);
			return true;
		}
		catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Checks whether the actual date of the given date string is valid based on
	 * the given date format pattern.
	 * 
	 * @param dateString
	 *            The date string.
	 * @param dateFormat
	 *            The date format pattern which should respect the
	 *            SimpleDateFormat rules.
	 * @return True if the actual date of the given date string is valid based
	 *         on the given date format pattern.
	 * @see SimpleDateFormat
	 */
	public static boolean isValidDate(String dateString, String dateFormat) {
		try {
			parse(dateString, dateFormat);
			return true;
		}
		catch (ParseException e) {
			return false;
		}
	}

	// Checkers
	// -----------------------------------------------------------------------------------

	/**
	 * Determine SimpleDateFormat pattern matching with the given date string.
	 * Returns null if format is unknown. You can simply extend DateUtil with
	 * more formats if needed.
	 * 
	 * @param dateString
	 *            The date string to determine the SimpleDateFormat pattern for.
	 * @return The matching SimpleDateFormat pattern, or null if format is
	 *         unknown.
	 * @see SimpleDateFormat
	 */
	public static String determineDateFormat(String dateString) {
		for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
			if (dateString.toLowerCase().matches(regexp)) {
				return DATE_FORMAT_REGEXPS.get(regexp);
			}
		}
		return null; // Unknown format.
	}

	// Changers
	// -----------------------------------------------------------------------------------

	/**
	 * Add the given amount of years to the given date. It actually converts the
	 * date to Calendar and calls {@link CalendarUtil#addYears(Calendar, int)}
	 * and then converts back to date.
	 * 
	 * @param date
	 *            The date to add the given amount of years to.
	 * @param years
	 *            The amount of years to be added to the given date. Negative
	 *            values are also allowed, it will just go back in time.
	 */
	public static Date addYears(Date date, int years) {
		Calendar calendar = toCalendar(date);
		CalendarUtil.addYears(calendar, years);
		return calendar.getTime();
	}

	/**
	 * Add the given amount of months to the given date. It actually converts
	 * the date to Calendar and calls
	 * {@link CalendarUtil#addMonths(Calendar, int)} and then converts back to
	 * date.
	 * 
	 * @param date
	 *            The date to add the given amount of months to.
	 * @param months
	 *            The amount of months to be added to the given date. Negative
	 *            values are also allowed, it will just go back in time.
	 */
	public static Date addMonths(Date date, int months) {
		Calendar calendar = toCalendar(date);
		CalendarUtil.addMonths(calendar, months);
		return calendar.getTime();
	}

	/**
	 * Add the given amount of days to the given date. It actually converts the
	 * date to Calendar and calls {@link CalendarUtil#addDays(Calendar, int)}
	 * and then converts back to date.
	 * 
	 * @param date
	 *            The date to add the given amount of days to.
	 * @param days
	 *            The amount of days to be added to the given date. Negative
	 *            values are also allowed, it will just go back in time.
	 */
	public static Date addDays(Date date, int days) {
		Calendar calendar = toCalendar(date);
		CalendarUtil.addDays(calendar, days);
		return calendar.getTime();
	}

	/**
	 * Add the given amount of hours to the given date. It actually converts the
	 * date to Calendar and calls {@link CalendarUtil#addHours(Calendar, int)}
	 * and then converts back to date.
	 * 
	 * @param date
	 *            The date to add the given amount of hours to.
	 * @param hours
	 *            The amount of hours to be added to the given date. Negative
	 *            values are also allowed, it will just go back in time.
	 */
	public static Date addHours(Date date, int hours) {
		Calendar calendar = toCalendar(date);
		CalendarUtil.addHours(calendar, hours);
		return calendar.getTime();
	}

	/**
	 * Add the given amount of minutes to the given date. It actually converts
	 * the date to Calendar and calls
	 * {@link CalendarUtil#addMinutes(Calendar, int)} and then converts back to
	 * date.
	 * 
	 * @param date
	 *            The date to add the given amount of minutes to.
	 * @param minutes
	 *            The amount of minutes to be added to the given date. Negative
	 *            values are also allowed, it will just go back in time.
	 */
	public static Date addMinutes(Date date, int minutes) {
		Calendar calendar = toCalendar(date);
		CalendarUtil.addMinutes(calendar, minutes);
		return calendar.getTime();
	}

	/**
	 * Add the given amount of seconds to the given date. It actually converts
	 * the date to Calendar and calls
	 * {@link CalendarUtil#addSeconds(Calendar, int)} and then converts back to
	 * date.
	 * 
	 * @param date
	 *            The date to add the given amount of seconds to.
	 * @param seconds
	 *            The amount of seconds to be added to the given date. Negative
	 *            values are also allowed, it will just go back in time.
	 */
	public static Date addSeconds(Date date, int seconds) {
		Calendar calendar = toCalendar(date);
		CalendarUtil.addSeconds(calendar, seconds);
		return calendar.getTime();
	}

	/**
	 * Add the given amount of millis to the given date. It actually converts
	 * the date to Calendar and calls
	 * {@link CalendarUtil#addMillis(Calendar, int)} and then converts back to
	 * date.
	 * 
	 * @param date
	 *            The date to add the given amount of millis to.
	 * @param millis
	 *            The amount of millis to be added to the given date. Negative
	 *            values are also allowed, it will just go back in time.
	 */
	public static Date addMillis(Date date, int millis) {
		Calendar calendar = toCalendar(date);
		CalendarUtil.addMillis(calendar, millis);
		return calendar.getTime();
	}

	// Comparators
	// --------------------------------------------------------------------------------

	/**
	 * Returns <tt>true</tt> if the two given dates are dated on the same
	 * year. It actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#sameYear(Calendar, Calendar)}.
	 * 
	 * @param one
	 *            The one date.
	 * @param two
	 *            The other date.
	 * @return True if the two given dates are dated on the same year.
	 * @see CalendarUtil#sameYear(Calendar, Calendar)
	 */
	public static boolean sameYear(Date one, Date two) {
		return CalendarUtil.sameYear(toCalendar(one), toCalendar(two));
	}

	/**
	 * Returns <tt>true</tt> if the two given dates are dated on the same year
	 * and month. It actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#sameMonth(Calendar, Calendar)}.
	 * 
	 * @param one
	 *            The one date.
	 * @param two
	 *            The other date.
	 * @return True if the two given dates are dated on the same year and month.
	 * @see CalendarUtil#sameMonth(Calendar, Calendar)
	 */
	public static boolean sameMonth(Date one, Date two) {
		return CalendarUtil.sameMonth(toCalendar(one), toCalendar(two));
	}

	/**
	 * Returns <tt>true</tt> if the two given dates are dated on the same
	 * year, month and day. It actually converts the both dates to Calendar and
	 * calls {@link CalendarUtil#sameDay(Calendar, Calendar)}.
	 * 
	 * @param one
	 *            The one date.
	 * @param two
	 *            The other date.
	 * @return True if the two given dates are dated on the same year, month and
	 *         day.
	 * @see CalendarUtil#sameDay(Calendar, Calendar)
	 */
	public static boolean sameDay(Date one, Date two) {
		return CalendarUtil.sameDay(toCalendar(one), toCalendar(two));
	}

	/**
	 * Returns <tt>true</tt> if the two given dates are dated on the same
	 * year, month, day and hour. It actually converts the both dates to
	 * Calendar and calls {@link CalendarUtil#sameHour(Calendar, Calendar)}.
	 * 
	 * @param one
	 *            The one date.
	 * @param two
	 *            The other date.
	 * @return True if the two given dates are dated on the same year, month,
	 *         day and hour.
	 * @see CalendarUtil#sameHour(Calendar, Calendar)
	 */
	public static boolean sameHour(Date one, Date two) {
		return CalendarUtil.sameHour(toCalendar(one), toCalendar(two));
	}

	/**
	 * Returns <tt>true</tt> if the two given dates are dated on the same
	 * year, month, day, hour and minute. It actually converts the both dates to
	 * Calendar and calls {@link CalendarUtil#sameMinute(Calendar, Calendar)}.
	 * 
	 * @param one
	 *            The one date.
	 * @param two
	 *            The other date.
	 * @return True if the two given dates are dated on the same year, month,
	 *         day, hour and minute.
	 * @see CalendarUtil#sameMinute(Calendar, Calendar)
	 */
	public static boolean sameMinute(Date one, Date two) {
		return CalendarUtil.sameMinute(toCalendar(one), toCalendar(two));
	}

	/**
	 * Returns <tt>true</tt> if the two given dates are dated on the same
	 * year, month, day, hour, minute and second. It actually converts the both
	 * dates to Calendar and calls
	 * {@link CalendarUtil#sameSecond(Calendar, Calendar)} .
	 * 
	 * @param one
	 *            The one date.
	 * @param two
	 *            The other date.
	 * @return True if the two given dates are dated on the same year, month,
	 *         day, hour, minute and second.
	 * @see CalendarUtil#sameSecond(Calendar, Calendar)
	 */
	public static boolean sameSecond(Date one, Date two) {
		return CalendarUtil.sameSecond(toCalendar(one), toCalendar(two));
	}

	// Calculators
	// --------------------------------------------------------------------------------

	/**
	 * Retrieve the amount of elapsed years between the two given dates. It
	 * actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#elapsedYears(Calendar, Calendar)}.
	 * 
	 * @param before
	 *            The first date with expected date before the second date.
	 * @param after
	 *            The second date with expected date after the first date.
	 * @return The amount of elapsed years between the two given dates
	 * @throws IllegalArgumentException
	 *             If the first date is dated after the second date.
	 * @see CalendarUtil#elapsedYears(Calendar, Calendar)
	 */
	public static int elapsedYears(Date before, Date after) {
		return CalendarUtil.elapsedYears(toCalendar(before), toCalendar(after));
	}

	/**
	 * Retrieve the amount of elapsed months between the two given dates. It
	 * actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#elapsedMonths(Calendar, Calendar)}.
	 * 
	 * @param before
	 *            The first date with expected date before the second date.
	 * @param after
	 *            The second date with expected date after the first date.
	 * @return The amount of elapsed months between the two given dates.
	 * @throws IllegalArgumentException
	 *             If the first date is dated after the second date.
	 * @see CalendarUtil#elapsedMonths(Calendar, Calendar)
	 */
	public static int elapsedMonths(Date before, Date after) {
		return CalendarUtil.elapsedMonths(toCalendar(before), toCalendar(after));
	}

	public static List<Date> elapsedMonthsList(Date before, Date after) {
		return CalendarUtil.elapsedMonthsList(toCalendar(before), toCalendar(after));
	}

	/**
	 * Retrieve the amount of difference in days between the two given dates. It
	 * actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#elapsedDays(Calendar, Calendar)}.
	 * 
	 * @param before
	 *            The first date with expected date before the second date.
	 * @param after
	 *            The second date with expected date after the first date.
	 * @return The amount of elapsed days between the two given dates.
	 * @throws IllegalArgumentException
	 *             If the first date is dated after the second date.
	 * @see CalendarUtil#elapsedDays(Calendar, Calendar)
	 */
	public static int diffDays(Date before, Date after) {
		return diffDaysCleared(before, after, false);
	}

	/**
	 * Retrieve the amount of difference in days between the two given dates. It
	 * actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#elapsedDays(Calendar, Calendar)}.
	 * 
	 * @param before
	 *            The first date with expected date before the second date.
	 * @param after
	 *            The second date with expected date after the first date.
	 * @param clearTime
	 *            If time component of the date needs to be cleared.
	 * @return The amount of difference in days between the two given dates.
	 * @throws IllegalArgumentException
	 *             If the first date is dated after the second date.
	 * @see CalendarUtil#elapsedDays(Calendar, Calendar)
	 */
	public static int diffDaysCleared(Date before, Date after, boolean clearTime) {
		if (clearTime) {
			before = clearTime(before);
			after = clearTime(after);
		}
		if (before.after(after)) {
			Date temp = before;
			before = after;
			after = temp;
		}
		return CalendarUtil.elapsedDays(toCalendar(before), toCalendar(after)) + 1;
	}

	/**
	 * Retrieve the amount of elapsed days between the two given dates. It
	 * actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#elapsedDays(Calendar, Calendar)}.
	 * 
	 * @param before
	 *            The first date with expected date before the second date.
	 * @param after
	 *            The second date with expected date after the first date.
	 * @return The amount of elapsed days between the two given dates.
	 * @throws IllegalArgumentException
	 *             If the first date is dated after the second date.
	 * @see CalendarUtil#elapsedDays(Calendar, Calendar)
	 */
	public static int elapsedDays(Date before, Date after) {
		return CalendarUtil.elapsedDays(toCalendar(before), toCalendar(after));
	}

	/**
	 * Retrieve the amount of elapsed hours between the two given dates. It
	 * actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#elapsedHours(Calendar, Calendar)}.
	 * 
	 * @param before
	 *            The first date with expected date before the second date.
	 * @param after
	 *            The second date with expected date after the first date.
	 * @return The amount of elapsed hours between the two given dates.
	 * @throws IllegalArgumentException
	 *             If the first date is dated after the second date.
	 * @see CalendarUtil#elapsedHours(Calendar, Calendar)
	 */
	public static int elapsedHours(Date before, Date after) {
		return CalendarUtil.elapsedHours(toCalendar(before), toCalendar(after));
	}

	/**
	 * Retrieve the amount of elapsed minutes between the two given dates. It
	 * actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#elapsedMinutes(Calendar, Calendar)}.
	 * 
	 * @param before
	 *            The first date with expected date before the second date.
	 * @param after
	 *            The second date with expected date after the first date.
	 * @return The amount of elapsed minutes between the two given dates.
	 * @throws IllegalArgumentException
	 *             If the first date is dated after the second date.
	 * @see CalendarUtil#elapsedMinutes(Calendar, Calendar)
	 */
	public static int elapsedMinutes(Date before, Date after) {
		return CalendarUtil.elapsedMinutes(toCalendar(before), toCalendar(after));
	}

	/**
	 * Retrieve the amount of elapsed seconds between the two given dates. It
	 * actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#elapsedSeconds(Calendar, Calendar)}.
	 * 
	 * @param before
	 *            The first date with expected date before the second date.
	 * @param after
	 *            The second date with expected date after the first date.
	 * @return The amount of elapsed seconds between the two given dates.
	 * @throws IllegalArgumentException
	 *             If the first date is dated after the second date.
	 * @see CalendarUtil#elapsedSeconds(Calendar, Calendar)
	 */
	public static int elapsedSeconds(Date before, Date after) {
		return CalendarUtil.elapsedSeconds(toCalendar(before), toCalendar(after));
	}

	/**
	 * Retrieve the amount of elapsed milliseconds between the two given dates.
	 * It actually converts the both dates to Calendar and calls
	 * {@link CalendarUtil#elapsedMillis(Calendar, Calendar)}.
	 * 
	 * @param before
	 *            The first date with expected date before the second date.
	 * @param after
	 *            The second date with expected date after the first date.
	 * @return The amount of elapsed milliseconds between the two given dates.
	 * @throws IllegalArgumentException
	 *             If the first date is dated after the second date.
	 * @see CalendarUtil#elapsedMillis(Calendar, Calendar)
	 */
	public static long elapsedMillis(Date before, Date after) {
		return CalendarUtil.elapsedMillis(toCalendar(before), toCalendar(after));
	}

	/**
	 * Calculate the total of elapsed time from years up to seconds between the
	 * two given dates. It Returns an int array with the elapsed years, months,
	 * days, hours, minutes and seconds respectively. It actually converts the
	 * both dates to Calendar and calls
	 * {@link CalendarUtil#elapsedTime(Calendar, Calendar)}.
	 * 
	 * @param before
	 *            The first date with expected date before the second date.
	 * @param after
	 *            The second date with expected date after the first date.
	 * @return The elapsed time between the two given dates in years, months,
	 *         days, hours, minutes and seconds.
	 * @throws IllegalArgumentException
	 *             If the first date is dated after the second date.
	 * @see CalendarUtil#elapsedTime(Calendar, Calendar)
	 */
	public static int[] elapsedTime(Date before, Date after) {
		return CalendarUtil.elapsedTime(toCalendar(before), toCalendar(after));
	}

	/**
	 * Used to extract a List of java.util.Date objects corresponding to a
	 * certain day of the month for all months between a specified range.<br>
	 * One important thing that needs to be noted here is that if for the last
	 * month in the date range specified the day of month is beyond what is
	 * present in the java.util.Date instance after, then that last date will
	 * not be included in the list of Date objects returned.<br>
	 * <br>
	 * For example if <br>
	 * <code>before = new Date();</code> and <br>
	 * <br>
	 * <code>after = new SimpleDateFormat("yyyy/MM/dd").parse("2009/09/08");</code>
	 * <br>
	 * then calling this method like this <br>
	 * <br>
	 * <code>dayOfEveryMonth(before, after, 15);</code> <br>
	 * will return the following list of Dates. <br>
	 * <br>
	 * Sun Feb 01 10:21:44 IST 2009 <br>
	 * Sun Mar 01 10:21:44 IST 2009 <br>
	 * Wed Apr 01 10:21:44 IST 2009 <br>
	 * Fri May 01 10:21:44 IST 2009 <br>
	 * Mon Jun 01 10:21:44 IST 2009 <br>
	 * Wed Jul 01 10:21:44 IST 2009 <br>
	 * Sat Aug 01 10:21:44 IST 2009 <br>
	 * Tue Sep 01 10:21:44 IST 2009 <br>
	 * <br>
	 * 
	 * Whereas calling the same method like this: <br>
	 * <br>
	 * dayOfEveryMonth(before, after, 5); will return the following list of
	 * Dates.<br>
	 * Thu Feb 05 10:37:53 IST 2009<br>
	 * Thu Mar 05 10:37:53 IST 2009<br>
	 * Sun Apr 05 10:37:53 IST 2009<br>
	 * Tue May 05 10:37:53 IST 2009<br>
	 * Fri Jun 05 10:37:53 IST 2009<br>
	 * Sun Jul 05 10:37:53 IST 2009<br>
	 * Wed Aug 05 10:37:53 IST 2009<br>
	 * Sat Sep 05 10:37:53 IST 2009<br>
	 * <br>
	 * 
	 * To avoid this behavior you need to call this method with the
	 * strictCheckLastMonth passed with a value of false.<br>
	 * Analogous behavior is exhibited in the case of the first month.
	 * 
	 * @param before
	 *            The start of the date range
	 * @param after
	 *            The last day of the date range.
	 * @param day
	 *            The day of month that needs to be extracted.
	 * @param strictCheckFirstMonth
	 *            If the day of month specified by <code>day</code> is before
	 *            teh java.util.Date represented by <code>before</code> and
	 *            this parameter is passed a value of true, then the Date
	 *            representing the day of month in the first month will not be
	 *            included in the List of Dates returned as the output.
	 * @param strictCheckLastMonth
	 *            If the day of month specified by <code>day</code> is going
	 *            beyond the java.util.Date represented by <code>after</code>
	 *            and this parameter is passed a value of true, then the Date
	 *            representing the day of month in the last month will not be
	 *            included in the List of Dates returned as the output.
	 * @return A List of java.util.Date instances corresponding to the mentioned
	 *         day of month for every month in the range.
	 */
	public static List<Date> dayOfEveryMonth(Date before, Date after, int day, boolean strictCheckFirstMonth, boolean strictCheckLastMonth) {
		List<Date> r = new ArrayList<Date>();
		Calendar beforeCal = toCalendar(before);
		while (beforeCal.getTime().before(after)) {
			beforeCal.set(Calendar.DAY_OF_MONTH, day);
			Date temp = beforeCal.getTime();
			beforeCal.add(Calendar.MONTH, 1);
			if (strictCheckFirstMonth && temp.before(before)) {
				continue;
			}
			if (strictCheckLastMonth && temp.after(after)) {
				break;
			}
			r.add(temp);
		}
		return r;
	}

	/**
	 * Used to extract a List of java.util.Date objects corresponding to a
	 * certain day of the month for all months between a specified range.<br>
	 * One important thing that needs to be noted here is that if for the last
	 * month in the date range specified the day of month is beyond what is
	 * present in the java.util.Date instance after, then that last date will
	 * not be included in the list of Date objects returned.<br>
	 * <br>
	 * For example if <br>
	 * <code>before = new Date();</code> and <br>
	 * <br>
	 * <code>after = new SimpleDateFormat("yyyy/MM/dd").parse("2009/09/08");</code>
	 * <br>
	 * then calling this method like this <br>
	 * <br>
	 * <code>dayOfEveryMonth(before, after, 15);</code> <br>
	 * will return the following list of Dates. <br>
	 * <br>
	 * Sun Feb 01 10:21:44 IST 2009 <br>
	 * Sun Mar 01 10:21:44 IST 2009 <br>
	 * Wed Apr 01 10:21:44 IST 2009 <br>
	 * Fri May 01 10:21:44 IST 2009 <br>
	 * Mon Jun 01 10:21:44 IST 2009 <br>
	 * Wed Jul 01 10:21:44 IST 2009 <br>
	 * Sat Aug 01 10:21:44 IST 2009 <br>
	 * Tue Sep 01 10:21:44 IST 2009 <br>
	 * <br>
	 * 
	 * Whereas calling the same method like this: <br>
	 * <br>
	 * dayOfEveryMonth(before, after, 5); will return the following list of
	 * Dates.<br>
	 * Thu Feb 05 10:37:53 IST 2009<br>
	 * Thu Mar 05 10:37:53 IST 2009<br>
	 * Sun Apr 05 10:37:53 IST 2009<br>
	 * Tue May 05 10:37:53 IST 2009<br>
	 * Fri Jun 05 10:37:53 IST 2009<br>
	 * Sun Jul 05 10:37:53 IST 2009<br>
	 * Wed Aug 05 10:37:53 IST 2009<br>
	 * Sat Sep 05 10:37:53 IST 2009<br>
	 * <br>
	 * <br>
	 * Analogous behavior is exhibited in the case of the first month.
	 * 
	 * @param before
	 *            The start of the date range
	 * @param after
	 *            The last day of the date range.
	 * @param day
	 *            The day of month that needs to be extracted.
	 * @return A List of java.util.Date instances corresponding to the mentioned
	 *         day of month for every month in the range.
	 */
	public static List<Date> dayOfEveryMonth(Date before, Date after, int day) {
		return dayOfEveryMonth(before, after, day, true, true);
	}

	/**
	 * Gets a list of Date objects representing all the days between
	 * <code>before</code> and <code>after</code>
	 * 
	 * @param before
	 *            The Date from where you need to start counting.
	 * @param after
	 *            The Date till when you need to count.
	 * @return The List<Date> instance representing all Date's between before
	 *         and after
	 */
	public static List<Date> days(Date before, Date after) {
		List<Date> r = new ArrayList<Date>();
		Calendar c = DateUtil.toCalendar(before);
		Date temp = c.getTime();
		while (temp.before(after)) {
			r.add(temp);
			c.add(Calendar.DATE, 1);
			temp = c.getTime();
		}
		r.add(after);
		return r;
	}

	/**
	 * Internally uses<br>
	 * <code>dayOfEveryMonth(Date before, Date after, int day)</code><br>
	 * <br>
	 * to fetch a list of java.util.Date instances representing the first day of
	 * every month in the range specified by the two arguments. The same nuances
	 * as are present in the<br>
	 * <code>dayOfEveryMonth(Date before, Date after, int day)</code> are also
	 * applicable here.
	 * 
	 * @param before
	 *            The start of the date range
	 * @param after
	 *            The last day of the date range.
	 * @return A List of java.util.Date instances corresponding to the first day
	 *         of month for every month in the range.
	 */
	public static List<Date> firstDayOfEveryMonth(Date before, Date after) {
		return dayOfEveryMonth(before, after, 1, true, true);
	}

	/**
	 * Creates a wrapper that can be used in schedulers that support cron style
	 * scheduling. The java.util.Date passed as the argument is simply converted
	 * to a Calendar and the second, mnute, hour, dayOfMnth, month and year
	 * information is extracted and wrapped in an instance of CronEntry.
	 * 
	 * @param date
	 * @return
	 */
	public static CronEntry cronEntry(Date date) {
		CronEntry e = new CronEntry();

		// Get the calendar for that Date.
		Calendar calendar = DateUtil.toCalendar(date);

		e.setSecond(calendar.get(Calendar.SECOND));
		e.setMinute(calendar.get(Calendar.MINUTE));
		e.setHour(calendar.get(Calendar.HOUR_OF_DAY));
		e.setDayOfMnth(calendar.get(Calendar.DAY_OF_MONTH));
		// 1. The tutorials page here http://www.opensymphony.com/quartz/wikidocs/TutorialLesson6.html 
		// says that months for Cron are 0 centric.
		// 2. However the Java Doc page here says that for Cron months are 1 centric. 
		// http://quartz.sourceforge.net/javadoc/
		e.setMonth(calendar.get(Calendar.MONTH) + 1);
		e.setYear(calendar.get(Calendar.YEAR));

		return e;
	}

	/**
	 * Gets the Month of the Date represented by Date.
	 * 
	 * @param date
	 * @return
	 */
	public static int month(Date date) {
		Calendar c = DateUtil.toCalendar(date);
		return c.get(Calendar.MONTH);
	}

	public static int dayOfWeek(Date date) {
		return DateUtil.toCalendar(date).get(Calendar.DAY_OF_WEEK);
	}

	public static boolean isSunday(Date date) {
		Calendar c = DateUtil.toCalendar(date);
		return Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK) ? true : false;
	}

	public static int dayOfMonth(Date date) {
		Calendar c = DateUtil.toCalendar(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getYear(Date date) {
		Calendar c = DateUtil.toCalendar(date);
		return c.get(Calendar.YEAR);
	}

	public static String getMonthAsString(Date date, boolean fullname) {
		int mnth = month(date);
		switch (mnth) {
		case Calendar.JANUARY:
			return fullname ? "January" : "Jan";
		case Calendar.FEBRUARY:
			return fullname ? "February" : "Feb";
		case Calendar.MARCH:
			return fullname ? "March" : "Mar";
		case Calendar.APRIL:
			return fullname ? "April" : "Apr";
		case Calendar.MAY:
			return fullname ? "May" : "May";
		case Calendar.JUNE:
			return fullname ? "June" : "Jun";
		case Calendar.JULY:
			return fullname ? "July" : "Jul";
		case Calendar.AUGUST:
			return fullname ? "August" : "Aug";
		case Calendar.SEPTEMBER:
			return fullname ? "September" : "Sept";
		case Calendar.OCTOBER:
			return fullname ? "October" : "Oct";
		case Calendar.NOVEMBER:
			return fullname ? "November" : "Nov";
		case Calendar.DECEMBER:
			return fullname ? "December" : "Dec";
		}
		return "";
	}

	/**
	 * Class used to represent a Cron entry. This is used to make life easier
	 * when working with Quartz based CronTriggers.
	 * 
	 * @author Harish Patel
	 * 
	 */
	public static class CronEntry {
		public String second;
		public String minute;
		public String hour;
		public String dayOfMnth;
		public String month;
		public String year;

		public CronEntry() {
			super();
		}

		public CronEntry(String second, String minute, String hour, String dayOfMnth, String month, String year) {
			super();
			this.second = second;
			this.minute = minute;
			this.hour = hour;
			this.dayOfMnth = dayOfMnth;
			this.month = month;
			this.year = year;
		}

		public CronEntry(int second, int minute, int hour, int dayOfMnth, int month, int year) {
			super();
			this.second = new Integer(second).toString();
			this.minute = new Integer(minute).toString();
			this.hour = new Integer(hour).toString();
			this.dayOfMnth = new Integer(dayOfMnth).toString();
			this.month = new Integer(month).toString();
			this.year = new Integer(year).toString();
		}

		public String toQuartzString() {
			StringBuilder b = new StringBuilder();
			b.append(second).append(" ");
			b.append(minute).append(" ");
			b.append(hour).append(" ");
			b.append(dayOfMnth).append(" ");
			b.append(month).append(" ");
			b.append("? ");
			b.append(year);
			return b.toString();
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append(second).append(" ");
			b.append(minute).append(" ");
			b.append(hour).append(" ");
			b.append(dayOfMnth).append(" ");
			b.append(month).append(" ");
			b.append(year);
			return b.toString();
		}

		public void setSecond(int second) {
			this.second = new Integer(second).toString();
		}

		public void setMinute(int minute) {
			this.minute = new Integer(minute).toString();
		}

		public void setHour(int hour) {
			this.hour = new Integer(hour).toString();
		}

		public void setDayOfMnth(int dayOfMnth) {
			this.dayOfMnth = new Integer(dayOfMnth).toString();
		}

		public void setMonth(int month) {
			this.month = new Integer(month).toString();
		}

		public void setYear(int year) {
			this.year = new Integer(year).toString();
		}
	}

	/**
	 * Gets us the number of sundays between two dates.
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static int sundays(Date before, Date after) {
		int r = 0;
		List<Date> days = DateUtil.days(before, after);
		for (Date day : days) {
			if (isSunday(day)) {
				r++;
			}
		}
		return r;
	}

	/**
	 * We are trying to get a Date object representing the day of month passed
	 * in as argument for the month present in the current date passed in as the
	 * first argument.
	 * 
	 * @param date
	 * @param dayOfMonth
	 * @return
	 */
	public static Date dayOfMonthAsDate(Date date, int dayOfMonth) {
		Calendar currCal = toCalendar(date);
		currCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return currCal.getTime();
	}

	/**
	 * Same as above method, except that it gets Date representation of
	 * "dayOfMonth" by default for the current date.
	 * 
	 * @param dayOfMonth
	 * @return
	 */
	public static Date dayOfMonthAsDate(int dayOfMonth) {
		return dayOfMonthAsDate(new Date(), dayOfMonth);
	}

	public static Date clearTime(Date date) {
		Calendar cal = toCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		return cal.getTime();
	}
}
