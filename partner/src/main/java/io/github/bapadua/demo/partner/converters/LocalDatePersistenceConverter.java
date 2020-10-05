package io.github.bapadua.demo.partner.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author ice_bpadua on 26/09/2020
 */
@Converter
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        if(Objects.nonNull(localDate)) {
            return Date.valueOf(localDate);
        }
        return null;
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        if(Objects.nonNull(date)) {
            return date.toLocalDate();
        }
        return null;
    }
}
