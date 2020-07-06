package com.github.eltonsandre.app.core.domain.model.enuns;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OperationTypeEnum {
    ENTRY("Entrada"),
    OUT("Saída");

    private final String operationLabel;

    @Override public String toString() {
        return this.operationLabel;
    }
}
