package com.yunheenet.pcroom.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class LogoutWindow extends JFrame {
    private static String title = "PICO System";
    private int screenWidth;
    private int screenHeight;

    public LogoutWindow() {
        getScreenSize();
        initializeFrame();
        start();
        restartSystem();
    }

    private void start() {
        JPanel panel = new JPanel();
        // Title Label
        JLabel lbTitle = new JLabel(title);
        lbTitle.setBounds(screenWidth/2-80, screenHeight/2-150, 200, 200);

        // Panel, AbsoluteXYLayout
        panel.setLayout(null);
        panel.add(lbTitle);

        // Panel > Frame
        add("Center", panel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();

                JOptionPane.showConfirmDialog(
                        frame,
                        "이용 시간을 모두 소진하셨습니다.",
                        title,
                        JOptionPane.DEFAULT_OPTION);
            }
        });
    }

    private void restartSystem() {
        String shutdownCmd = "shutdown -r";
        try {
            Process child = Runtime.getRuntime().exec(shutdownCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setAlwaysOnTop(true);
        setUndecorated(true);
    }

    private void getScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int)screenSize.getWidth();
        screenHeight = (int)screenSize.getHeight();
    }
}
