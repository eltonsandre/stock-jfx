package com.github.eltonsandre.app.controller.components;

import com.github.eltonsandre.app.controller.MainController;
import com.github.eltonsandre.app.jfx.util.dialog.DialogUtils;
import io.datafx.controller.ViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@RequiredArgsConstructor
@ViewController(value = "/fxml/home/Toolbar.fxml")
public class ToolbarController implements Initializable {

    private final MainController mainController;


    @Override
    @SneakyThrows
    public void initialize(URL url, ResourceBundle rb) {
        initializeButtons();
    }

    public void initializeButtons() {
    }

    @FXML
    private void loadDepartamentView(ActionEvent event) {
        mainController.getDrawer().close();
        mainController.showDepartamentView(event);
    }

    @FXML
    private void loadGroupItemView(ActionEvent event) {
        mainController.getDrawer().close();
        mainController.showGroupItemView(event);
    }

    @FXML
    private void loadItemView(ActionEvent event) {
        mainController.getDrawer().close();
        mainController.showItemView(event);
    }

    @FXML
    private void loadStockView(ActionEvent event) {
        mainController.getDrawer().close();
        mainController.showStockView(event);
    }

    @FXML
    private void loadSupplierView(ActionEvent event) {
        mainController.getDrawer().close();
        mainController.showSupplierView(event);
    }

    @FXML
    private void loadSettingsView(ActionEvent event) {
        mainController.getDrawer().close();
    }

    @FXML
    private void exit(ActionEvent event) {
        DialogUtils.showConfirmation("sair, sério?!", "você quer sair?", "mas por que?, fica por favor. :(")
                .filter(ButtonType.YES::equals)
                .ifPresent(buttonType -> System.exit(0));

    }

}
