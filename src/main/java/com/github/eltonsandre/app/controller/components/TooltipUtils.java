package com.github.eltonsandre.app.controller.components;

import javafx.scene.control.Tooltip;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TooltipUtils {

    public static final Tooltip TOOLTIP_BUTTON_NEW = new Tooltip("Inicie um novo cadastro!");
    public static final Tooltip TOOLTIP_BUTTON_SAVE = new Tooltip("Grave os dados informados no cadastro!");
    public static final Tooltip TOOLTIP_BUTTON_SEARCH = new Tooltip("Filtre sua pesquisa por partes do termo pesquisado!");
    public static final Tooltip TOOLTIP_BUTTON_EDIT = new Tooltip("Edite os dados do registro selecionado!");
    public static final Tooltip TOOLTIP_BUTTON_DELETE = new Tooltip("Delete o registro selecionado!");
}