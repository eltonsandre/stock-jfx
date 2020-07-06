package com.github.eltonsandre.app.core.domain.model.entity.converte;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Converter
@Component
@RequiredArgsConstructor
public class JsonStringType implements AttributeConverter<Object, String> {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override public String convertToDatabaseColumn(final Object attribute) {
        return this.objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override public Object convertToEntityAttribute(final String dbData) {
        return this.objectMapper.readValue(dbData, Object.class);
    }
}
