package com.github.eltonsandre.app.controller;

import com.github.eltonsandre.app.StockApplication;
import com.github.eltonsandre.app.controller.category.CategoryItemController;
import com.github.eltonsandre.app.controller.components.ToolbarController;
import com.github.eltonsandre.app.controller.departament.DepartmentController;
import com.github.eltonsandre.app.controller.item.ItemController;
import com.github.eltonsandre.app.controller.stock.StockController;
import com.github.eltonsandre.app.controller.supplier.SupplierController;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import io.datafx.controller.ViewController;
import io.vavr.control.Try;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static com.github.eltonsandre.app.StockApplication.getRoot;
import static com.github.eltonsandre.app.StockApplication.load;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

@Log4j2
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewController(value = "/fxml/home-main.fxml", title = "Stock")
public class MainController implements Initializable {

    @Getter
    @FXML
    ImageView imageMain;

    @Getter
    @FXML
    JFXDrawer drawer;

    @Getter
    @FXML
    JFXHamburger hamburger;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.initDrawer();
    }

    private void initDrawer() {

        Try.<VBox>of(StockApplication.fxmlLoader(ToolbarController.class)::load)
                .onSuccess(toolbar -> this.drawer.setSidePane(toolbar))
                .onFailure(ex -> log.error("toobar load error", ex))
                .get();

        final HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(this.hamburger);
        task.setRate(-1);
        this.hamburger.addEventHandler(MOUSE_CLICKED, (Event event) -> this.drawer.toggle());

        this.drawer.setOnDrawerOpening(event -> {
            task.setRate(task.getRate() * -1);
            task.play();
            this.drawer.toFront();
        });
        this.drawer.setOnDrawerClosed(event -> {
            this.drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });
    }

    private boolean isVisibleInCenter(final ObjectProperty<AnchorPane> view) {
        final Node center = getRoot().getCenter();

        if (view.get() == null) {
            throw new NullPointerException("View not initialized.");
        }

        if (view.get().getId() == null) {
            throw new NullPointerException("View id not setted.");
        }

        return (center != null && center.getId().equals(view.get().getId()));
    }

    private void showInCenter(final ObjectProperty<AnchorPane> view) {
        log.debug("Parent={}", view.get());
        if (!this.isVisibleInCenter(view)) {
            final BorderPane root = getRoot();
            root.setCenter(view.get());
            root.getCenter().toFront();
        }
    }

    private void initializeView(final ObjectProperty<AnchorPane> view, final Class<?> viewControllerClass) {
        log.debug("Parent={}", view.get());
        if (view.get() == null) {
            view.set(load(viewControllerClass));
        }
    }

    @FXML
    public void showDepartamentView(final ActionEvent event) {
        this.showView(DepartmentController.class);
    }

    @FXML
    public void showGroupItemView(final ActionEvent event) {
        this.showView(CategoryItemController.class);
    }

    @FXML
    public void showItemView(final ActionEvent event) {
        this.showView(ItemController.class);
    }

    @FXML
    public void showStockView(final ActionEvent event) {
        this.showView(StockController.class);
    }

    @FXML
    public void showSupplierView(final ActionEvent event) {
        this.showView(SupplierController.class);
    }

    private void showView(final Class<?> clazs) {
        var view = new SimpleObjectProperty<AnchorPane>();
        this.initializeView(view, clazs);
        this.showInCenter(view);
    }

}
