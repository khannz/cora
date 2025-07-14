package com.epam.cora.entity.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public final class DateUtils {

    /**
     * @deprecated Use static methods on the class itself rather than its instance.
     */
    @Deprecated
    public DateUtils() { }

    public static Date now() {
        return Date.from(nowUTC().toInstant(ZoneOffset.UTC));
    }

    public static LocalDateTime nowUTC() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    public static LocalDateTime convertDateToLocalDateTime(final Date date) {
        return date.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public static Date convertLocalDateTimeToDate(final LocalDateTime dateTime) {
        return new Date(dateTime.toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    public static LocalDateTime parse(final DateFormat format, final String dateString) {
        try {
            return convertDateToLocalDateTime(format.parse(dateString));
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    String.format("Filed to parse date: %s with format: %s", dateString, format), e);
        }
    }
}
