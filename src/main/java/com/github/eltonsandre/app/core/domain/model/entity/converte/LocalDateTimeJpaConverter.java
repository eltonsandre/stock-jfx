package com.github.eltonsandre.app.core.domain.model.entity.converte;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeJpaConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(final LocalDateTime entityValue) {
        return (entityValue == null) ? null : Timestamp.valueOf(entityValue);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(final Timestamp databaseValue) {
        return (databaseValue == null) ? null : databaseValue.toLocalDateTime();
    }

}
