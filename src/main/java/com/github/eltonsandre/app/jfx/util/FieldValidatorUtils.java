package com.github.eltonsandre.app.jfx.util;

import com.github.eltonsandre.app.jfx.validator.CurrencyZeroValidator;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.RequiredFieldValidator;
import lombok.experimental.UtilityClass;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

/**
 * @author eltonsandre
 */
@UtilityClass
public class FieldValidatorUtils {

    final String iconAlertDefault = "fas-exclamation-triangle";
    final String messageRequiredDefault = "Campo obrigatório.";
    final String messageAmountNotZeroDefault = "Valor não pode ser zero.";

    public RequiredFieldValidator requiredFieldValidator() {
        return requiredFieldValidator(messageRequiredDefault);
    }

    public void requiredFieldsValidator(List<? extends IFXValidatableControl> controls) {
        controls.forEach(control -> control.getValidators()
                .add(FieldValidatorUtils.requiredFieldValidator()));
    }


    public RequiredFieldValidator requiredFieldValidator(final String message) {
        return requiredFieldValidator(message, iconAlertDefault);
    }

    public RequiredFieldValidator requiredFieldValidator(final String message, final String iconCode) {
        final var validator = new RequiredFieldValidator(message);
        validator.setIcon(new FontIcon(iconCode));

        return validator;
    }

    public CurrencyZeroValidator currencyValidator() {
        return currencyValidator(messageAmountNotZeroDefault);
    }

    public CurrencyZeroValidator currencyValidator(final String message) {
        return currencyValidator(message, iconAlertDefault);
    }

    public CurrencyZeroValidator currencyValidator(final String message, final String iconCode) {
        final var validator = new CurrencyZeroValidator(message);
        validator.setIcon(new FontIcon(iconCode));

        return validator;
    }
}
