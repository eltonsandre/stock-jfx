package com.github.eltonsandre.app.config;

import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author eltonsandre
 */
@Configuration
@Log4j2
public class I18nConfig {

    public static final Locale LOCALE_PT_BR = new Locale("pt", "BR");

    @Bean("bundle-i18n")
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("i18n/i18n", LOCALE_PT_BR);
    }

    public String getString(final String key) {
        return Try.success(this.resourceBundle().getString(key))
                .getOrElse(key);
    }

}
