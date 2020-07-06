package com.github.eltonsandre.app.jfx.component;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.geometry.NodeOrientation;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author eltonsandre
 * date 03/05/2020 22:14
 */
public class JFXCurrencyField extends JFXTextField {

    private static final BigDecimal ZERO = BigDecimal.valueOf(0.00);
    private static final String NO_NUMBER_REGEX = "[^0-9]";
    private static final String STRING_ZERO = "0";

    private NumberFormat format;
    private BigDecimal amount;

    public JFXCurrencyField() {
        this(Locale.getDefault(), ZERO);
    }

    public JFXCurrencyField(final Locale locale) {
        this(locale, ZERO);
    }

    public JFXCurrencyField(final Locale locale, final BigDecimal initialAmount) {
        super();
        this.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        this.amount = initialAmount;
        this.format = NumberFormat.getCurrencyInstance(locale);

        this.setText(this.format.format(initialAmount));

        this.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                int lenght = this.getText().length();
                this.selectRange(lenght, lenght);
                this.positionCaret(lenght);
            });
        });

        this.textProperty().addListener((observable, oldValue, newValue) -> JFXCurrencyField.this.formatText(newValue));
    }

    /**
     * Get the current amount value
     *
     * @return Total amount
     */
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * Change the current amount value
     *
     * @param newAmount amount
     */
    public void setAmount(final BigDecimal newAmount) {
        if (newAmount.compareTo(ZERO) >= 0) {
            this.amount = newAmount;
            this.formatText(this.format.format(newAmount));
        }
    }

    public void setZero() {
        this.setAmount(ZERO);
    }

    /**
     * Set Currency format
     *
     * @param locale
     */
    public void setCurrencyFormat(final Locale locale) {
        this.format = NumberFormat.getCurrencyInstance(locale);
        this.formatText(this.format.format(this.getAmount()));
    }

    @Override
    public void deleteText(final int start, final int end) {
        final int caretPosition = this.getCaretPosition();

        var builder = new StringBuilder(this.getText());
        builder.delete(start, end);

        this.formatText(builder.toString());
        this.selectRange(start, start);
        this.positionCaret(caretPosition);
    }

    private void formatText(final String text) {
        if (text!=null && !text.isEmpty()) {
            StringBuilder plainText = new StringBuilder(text.replaceAll(NO_NUMBER_REGEX, StringUtils.EMPTY));

            while (plainText.length() < 3) {
                plainText.insert(0, STRING_ZERO);
            }

            StringBuilder builder = new StringBuilder(plainText.toString());
            builder.insert(plainText.length() - 2, ".");

            final var newValue = new BigDecimal(builder.toString());
            this.amount = newValue;
            this.setText(this.format.format(newValue));
        }
    }

}