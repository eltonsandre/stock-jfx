package com.github.eltonsandre.app.jfx.util.dialog;

import com.github.eltonsandre.app.jfx.enums.AlertTypeEnum;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eltonsandre
 * date: Jul 3, 2017 9:08:26 PM
 */
@Slf4j
@UtilityClass
public class AlertUtils {

    private final String CSS = AlertUtils.class.getResource("/assets/css/dark-theme.css").toExternalForm();


    public void sucess(final String header, final String content) {
        info(AlertTypeEnum.SUCESSO.getDescricao(), header, content);
    }

    public void info(final String title, final String header, final String content) {
        generic(AlertType.INFORMATION, title, header, content);
    }

    public void info(final String header, final String content) {
        generic(AlertType.INFORMATION, AlertTypeEnum.INFO.getDescricao(), header, content);
    }

    public void warn(final String header) {
        generic(AlertType.WARNING, AlertTypeEnum.AVISO.getDescricao(), header, "");
    }

    public void warn(final String header, final String content) {
        generic(AlertType.WARNING, AlertTypeEnum.AVISO.getDescricao(), header, content);
    }

    public void error(final String header, final String content) {
        generic(AlertType.ERROR, AlertTypeEnum.ERRO.getDescricao(), header, content);
    }

    public void generic(final AlertType type, final String title, final String header, final String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public void jfxAlert(final Stage stage, final String heading, final String body) {
        var alert = new JFXAlert<>(stage);
        //        JFXAlert alert = new JFXAlert((Stage) alertButton.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(heading));
        layout.setBody(new Label(body));

        layout.getStylesheets().add(CSS);

        JFXButton closeButton = new JFXButton("ACCEPT");
                closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(event -> alert.hideWithAnimation());

        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }

}
