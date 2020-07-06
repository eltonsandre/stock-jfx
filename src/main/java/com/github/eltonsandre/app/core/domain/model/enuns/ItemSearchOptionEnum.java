package com.github.eltonsandre.app.core.domain.model.enuns;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ItemSearchOptionEnum {
    SEARCH_BY_ID("Código"),
    SEARCH_BY_CATEGORY("Categoria"),
    SEARCH_BY_CREATED_AT("Data de Cadastro"),
    SEARCH_BY_DESCRIPTION("Descrição"),
    SEARCH_BY_STOCK_QUANTITY("Quantidade Estoque"),
    SEARCH_BY_LAST_STOCK_UPDATE("Última Atualização");

    private final String searchLabel;

    @Override public String toString() {
        return this.searchLabel;
    }
}
