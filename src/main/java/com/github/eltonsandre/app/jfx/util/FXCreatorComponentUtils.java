package com.github.eltonsandre.app.jfx.util;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.util.function.UnaryOperator;
import javafx.scene.Node;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.IntegerStringConverter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eltonsandre
 * date: 4 de nov de 2017 16:11:52
 */
@Slf4j
@UtilityClass
public class FXCreatorComponentUtils {

    private final String STYLE_PADDING_5PX = "-fx-padding: 5px;";
    private final String STYLE_DATA_PICKER_5PX = "-fx-background-color: transparent; -jfx-focus-color: #469962; -jfx-unfocus-color: #a7a7a7; -jfx-label-float: true; -fx-padding: 5px;";

    /**
     * @param label
     * @return Node
     */
    public Node jFXTextField(final String label) {
        var addNode = new JFXTextField();
        addNode.setLabelFloat(true);
        addNode.setPromptText(label);
        addNode.setStyle(STYLE_PADDING_5PX);
        return addNode;
    }

    public Node jFXDatePicker(final String label) {
        var addNode = new JFXDatePicker();
        addNode.setPromptText(label);
        addNode.setStyle(STYLE_PADDING_5PX);
        return addNode;
    }

    /**
     * @param label
     * @return Node
     */
    public Node jFXToggleButton(final String label) {
        var addNode = new JFXToggleButton();
        addNode.setStyle(STYLE_PADDING_5PX);
        return addNode;
    }

    public Node jFXTextFieldNumber(final String label) {
        var field = (JFXTextField) jFXTextField(label);
        field.setTextFormatter(textFormatterNumber());
        return field;
    }

    /**
     * @return TextFormatter<Integer>
     */
    public TextFormatter<Integer> textFormatterNumber() {
        return textFormatterNumber(null);
    }

    /**
     * @param defaultValue
     * @return TextFormatter<Integer>
     */
    public TextFormatter<Integer> textFormatterNumber(final Integer defaultValue) {
        UnaryOperator<Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        return new TextFormatter<>(new IntegerStringConverter(), defaultValue, integerFilter);
    }
}
