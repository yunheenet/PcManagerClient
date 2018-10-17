package com.yunheenet.pcroom;

import com.yunheenet.pcroom.view.LoginWindow;

import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        boolean isPerPixelTranslucencySupported =
                gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSLUCENT);

        if (!isPerPixelTranslucencySupported) {
            System.err.println(
                "Per-pixel translucency is not supported");
                System.exit(0);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginWindow pw = new LoginWindow();
                pw.setVisible(true);
            }
        });
    }
}
