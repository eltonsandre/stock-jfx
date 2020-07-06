package com.github.eltonsandre.app.core.domain.model.enuns;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CategoryTypeEnum {
    SERVICE("Serviço"),
    PRODUCT("Produto");

    private final String type;

    @Override public String toString() {
        return this.type;
    }
}
