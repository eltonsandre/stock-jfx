package com.github.eltonsandre.app.jfx.util.message;

import com.github.eltonsandre.app.jfx.util.LibraryAssistantUtil;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * @author eltonsandre
 */
@Log4j2
@UtilityClass
public class TrayMessageUtils {

    public void showTrayMessage(final String title, final String message) {
        try {
            final SystemTray tray = SystemTray.getSystemTray();
            final BufferedImage image = ImageIO.read(AlertMaker.class.getResource("assets/images/inventory.png"));

            final TrayIcon trayIcon = new TrayIcon(image, "Stock Assistant");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Stock Assistant");
            tray.add(trayIcon);

            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
            tray.remove(trayIcon);

        } catch (final Exception ex) {
            log.error(message, ex);
        }
    }

}
