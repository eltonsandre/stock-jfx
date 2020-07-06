package com.github.eltonsandre.app.jfx.validator;

import com.github.eltonsandre.app.jfx.component.JFXCurrencyField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.beans.DefaultProperty;

import java.math.BigDecimal;

@DefaultProperty("icon")
public class CurrencyZeroValidator extends ValidatorBase {

    public CurrencyZeroValidator(final String message) {
        super(message);
    }

    @Override
    protected void eval() {
        if (this.srcControl.get() instanceof JFXCurrencyField) {
            this.evalTextInputField();
        }
    }

    private void evalTextInputField() {
        var currencyField = (JFXCurrencyField) this.srcControl.get();
        try {
            final int i = BigDecimal.ZERO.compareTo(currencyField.getAmount());
            final boolean isError = i > -1;
            this.hasErrors.set(isError);
        } catch (Exception var3) {
            this.hasErrors.set(true);
        }

    }
}
