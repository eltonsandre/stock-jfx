package com.github.eltonsandre.app.jfx.converter;

import com.github.eltonsandre.app.jfx.property.ItemSelected;
import java.lang.reflect.Field;
import javafx.util.StringConverter;

/**
 * @author eltonsandre
 * date 3 de nov de 2017 15:11:54
 */
public class ItemTypeConverter extends StringConverter<ItemSelected> {

    @Override
    public String toString(final ItemSelected t) {
        if (t == null) {
            return "[none]";
        }

        String label = ((Field) t.getValue()).getDeclaringClass().toString().replace(".Faker", "");
        return label;
    }

    /* (non-Javadoc)
     *
     * @see javafx.util.StringConverter#fromString(java.lang.String) */
    @Override
    public ItemSelected fromString(final String string) {
        throw new RuntimeException("not required for non editable ComboBox");
    }
}
