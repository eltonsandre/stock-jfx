package com.github.eltonsandre.app;

import com.github.eltonsandre.app.controller.MainController;
import com.github.eltonsandre.app.jfx.util.dialog.AlertUtils;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@SpringBootApplication
public class StockApplication extends javafx.application.Application {

    public static final ConfigurableApplicationContext springContext = SpringApplication.run(StockApplication.class);

    private static Scene sScene;
    private static BorderPane rootNode;

    @Value("${spring.application.name}")
    private static String appName;

    public static void main(final String[] args) {
        javafx.application.Application.launch(args);
    }

    private static void showErrorDialog(final Thread thread, final Throwable throwable) {
        log.error("{}", throwable.getMessage(), throwable);
        AlertUtils.error("Vácilo, Erro não tratado", throwable.getMessage());
    }

    @SneakyThrows(IOException.class)
    public static <T> T load(final Class<?> viewControllerClass) {
        return fxmlLoader(viewControllerClass).load();
    }

    public static Scene getScene() {
        if (sScene==null) {
            sScene = new Scene(Objects.requireNonNull(StockApplication.load(MainController.class)));
        }

        return sScene;
    }

    public static FXMLLoader fxmlLoader(final Class<?> viewControllerClass) {
        final ViewController annotation = viewControllerClass.getAnnotation(ViewController.class);
        return fxmlLoader(annotation.value());
    }

    public static FXMLLoader fxmlLoader(final String location) {
        final var fxmlLoader = new FXMLLoader(StockApplication.class.getResource(location));
        fxmlLoader.setControllerFactory(springContext::getBean);

        fxmlLoader.setResources((ResourceBundle) springContext.getBean("bundle-i18n"));
        return fxmlLoader;
    }

    public static BorderPane getRoot() {
        if (rootNode==null) {
            rootNode = (BorderPane) StockApplication.getScene().getRoot();
        }
        return rootNode;
    }

    @Override
    public void init() throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(StockApplication::showErrorDialog);
        rootNode = fxmlLoader(MainController.class).load();
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setScene(new Scene(rootNode));
        primaryStage.setTitle(appName);

        final InputStream resource = StockApplication.class.getResourceAsStream("/assets/images/c2.png");
        primaryStage.getIcons().add(new Image(resource));
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }

}
