package com.github.eltonsandre.app.jfx.util.dialog;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
@UtilityClass
public class DialogUtils {

    private final String CSS = AlertUtils.class.getResource("/assets/css/dark-theme.css").toExternalForm();

    private final ButtonType[] BUTTONS_CONFIMATION = new ButtonType[]{ButtonType.YES, ButtonType.NO};
    private final JFXAlert<?> ALERT_DEFAULT = alert();
    private final JFXDialogLayout layout = new JFXDialogLayout();

    public Optional<ButtonType> showConfirmation(final String header, final String content) {
        return showAlertConfirmation(header, null, content);
    }

    public Optional<ButtonType> showConfirmation(final String header, final String title, final String content) {
        return showAlertConfirmation(header, title, content);
    }

    public void showError(final String header, final String title, final String content) {
        showAlertOk(header, title, content, AlertType.ERROR);
    }

    public void showWarning(final String header, final String content) {
        showAlertOk(header, null, content, AlertType.WARNING);
    }

    public void showWarning(final String header, final String title, final String content) {
        showAlertOk(header, title, content, AlertType.WARNING);
    }

    public void showInfo(final String header, final String content) {
        showAlertOk(header, null, content, AlertType.INFORMATION);
    }

    public void showInfo(final String header, final String title, final String content) {
        showAlertOk(header, title, content, AlertType.INFORMATION);
    }

    public void showError(final Stage stage, final String title, final String content) {
        showInfo(stage, title, content);
    }

    public void showInfo(final Stage stage, final String title, final String content) {
        jfxAlert(null, title, content, "OK");
    }

    private Optional<ButtonType> showAlertConfirmation(final String header, final String title, final String content) {
        final Alert alert = new Alert(AlertType.CONFIRMATION, content, BUTTONS_CONFIMATION);

        alert.setHeaderText(header);
        alert.setTitle(title);
        return alert.showAndWait();
    }

    private void showAlertOk(final String header, final String title, final String content, final AlertType alertType) {
        jfxAlert(null, title, content, "OK");
    }

    private JFXAlert alert() {
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        var alert = new JFXAlert<>(owner);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        alert.getDialogPane().getStylesheets().add(CSS);

        return alert;
    }

    public void jfxAlert(final Stage stage, final String heading, final String body, final String btnAccept) {
        var alert = ALERT_DEFAULT;

        layout.setHeading(new Label(heading));
        layout.setBody(new Label(body));
//        layout.getStylesheets().add(CSS);

        JFXButton closeButton = new JFXButton(btnAccept);
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.getStyleClass().add("confirmation");
        closeButton.setOnAction(event -> alert.hideWithAnimation());

        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }

    //TODO
    private void setOnAction() {
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        // Ensure that the user can't close the alert.
        JFXAlert<String> alert = new JFXAlert<>(owner);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        var usernameTextField = new JFXTextField();
        // Create the content of the JFXAlert with JFXDialogLayout
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Enter Username"));
        layout.setBody(new VBox(new Label("Please enter the username of the person you would like to add."),
                usernameTextField));

        // Buttons get added into the actions section of the layout.
        JFXButton addButton = new JFXButton("ADD");
        addButton.setDefaultButton(true);
        addButton.setOnAction(addEvent -> {
            // When the button is clicked, we set the result accordingly
            alert.setResult(usernameTextField.getText());
            alert.hideWithAnimation();
        });

        JFXButton cancelButton = new JFXButton("CANCEL");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(closeEvent -> alert.hideWithAnimation());

        layout.setActions(addButton, cancelButton);
        alert.setContent(layout);

        Optional<String> result = alert.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }
    }

    public void generic(final StackPane stackPane, final String heading, final String body) {
        layout.setHeading(new Text(heading));
        layout.setBody(new Text(body));

        var dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);

        var button = new JFXButton("OK!");
        button.setStyle("-fx-background-color: #246321;");
        button.setTextFill(Paint.valueOf("WHITE"));
        button.setOnAction(event -> dialog.close());
        layout.setActions(button);

        dialog.show();
    }

}