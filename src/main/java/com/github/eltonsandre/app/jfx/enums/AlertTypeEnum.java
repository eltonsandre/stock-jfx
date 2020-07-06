package com.github.eltonsandre.app.jfx.enums;

import javafx.scene.control.Alert;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author eltonsandre
 * date: Jul 3, 2017 10:07:20 PM
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AlertTypeEnum {

    SUCESSO("Sucesso", Alert.AlertType.NONE),
    AVISO("Aviso", Alert.AlertType.WARNING),
    INFO("Informação", Alert.AlertType.INFORMATION),
    ERRO("Erro", Alert.AlertType.ERROR);

    private final String descricao;
    private final Alert.AlertType type;

}
