package com.yunheenet.pcroom.view;

import javax.swing.*;
import java.awt.*;

public class PopupFrame extends Frame {

    private String userId;
    private int screenWidth;
    private int screenHeight;

    private JFrame frame;
    private JLabel titleLabel;

    public static void main(String args[]) {
        new PopupFrame();
    }

    public PopupFrame() {
        getScreenSize();
        initializeFrame();
        start();
    }

    public PopupFrame(String id) {
        this.userId = id;

        getScreenSize();
        initializeFrame();
        start();
    }

    private void getScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int)screenSize.getWidth();
        screenHeight = (int)screenSize.getHeight();
        System.out.println("#ScreenSize=" + screenWidth + "x" + screenHeight);
    }

    private void initializeFrame() {
        frame = new JFrame();
        frame.setBounds(screenWidth-500, 100, 400, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        //frame.setVisible(true);
    }

    private void start() {
        JPanel panel = new JPanel();
        BorderLayout fl = new BorderLayout();

        // Title Label
        titleLabel = new JLabel();
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        if (userId != null) {
            titleLabel.setText(userId + "님 환영합니다.");
        } else {
            titleLabel.setText("PICO System.");
        }

        panel.setLayout(fl);
        panel.add(titleLabel, BorderLayout.NORTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
