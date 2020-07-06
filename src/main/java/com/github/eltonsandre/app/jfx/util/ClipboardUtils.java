package com.github.eltonsandre.app.jfx.util;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author eltonsandre
 * date: 5 de jul de 2016 18:54:44
 */
@Slf4j
@UtilityClass
public class ClipboardUtils {

    /**
     * @param text
     */
    public void copy(final String text) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    /**
     * @return String
     */
    public String paste() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard.hasContent(DataFormat.PLAIN_TEXT)) {
            return clipboard.getString();
        }
        return StringUtils.EMPTY;
    }

}
