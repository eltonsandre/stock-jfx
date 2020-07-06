package com.github.eltonsandre.app.core.domain.model.entity.converte;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateJpaConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(final LocalDate entityValue) {
        return (entityValue == null) ? null : Date.valueOf(entityValue);
    }

    @Override
    public LocalDate convertToEntityAttribute(final Date databaseValue) {
        return (databaseValue == null) ? null : databaseValue.toLocalDate();
    }

}
