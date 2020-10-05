package io.github.bapadua.demo.partner.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author ice_bpadua on 26/09/2020
 */
public class DateUtils {

    /**
     * Retorna um objeto LocalDate a partir de uma string
     * @since 26/09/2020
     * @param date formato(dd/MM/aaaa)
     * @return LocalDate
     */
    public static LocalDate localDateFrom(final String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    public static String fromLocalDate(final LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}
