package com.github.eltonsandre.app.jfx.component;

import com.jfoenix.svg.SVGGlyph;
import com.jfoenix.svg.SVGGlyphLoader;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * @author eltonsandre
 */
public class GlyphIcon extends SVGGlyph {

    public static final String SVG_FILE_NAME = "icomoon.svg.";
    private static final AtomicBoolean isLoad = new AtomicBoolean(false);

    @Getter
    private String name;

    @SneakyThrows
    public GlyphIcon() {
        super();

        if (!isLoad.get()) {
            SVGGlyphLoader.loadGlyphsFont(GlyphIcon.class.getResource("/assets/fonts/icomoon.svg"));
            GlyphIcon.isLoad.set(true);
        }
    }

    @SneakyThrows
    public void setName(final String name) {
        this.name = name;
        final SVGGlyph icoMoonGlyph = SVGGlyphLoader.getIcoMoonGlyph(SVG_FILE_NAME + name);
        this.setShape(icoMoonGlyph.getShape());
        this.setRotate(180.0D);
        this.setScaleX(-1.0D);
    }

    public void setStyleClass(final String styleClass) {
        super.getStyleClass().add(styleClass);
    }

}