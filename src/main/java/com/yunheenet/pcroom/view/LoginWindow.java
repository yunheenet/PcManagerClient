package com.yunheenet.pcroom.view;

import com.yunheenet.pcroom.domain.User;
import com.yunheenet.pcroom.domain.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginWindow extends JFrame implements ActionListener, WindowListener {
    private static String title = "PICO System";
    private int screenWidth;
    private int screenHeight;

    private JLabel lbTitle;
    private JTextField tfLogin;
    private JPasswordField pfPassword;
    private JButton btnLogin;

    public LoginWindow() {
        super(title);

        getScreenSize();
        initializeFrame();
        start();
    }

    private void start() {
        JPanel panel = new JPanel();
        // Title Label
        lbTitle = new JLabel(title);
        lbTitle.setBounds(screenWidth/2-80, screenHeight/2-150, 200, 200);

        // Login Field
        tfLogin = new JTextField(15 );
        tfLogin.setBounds(screenWidth/2-150, screenHeight/2, 200, 25);

        // Password Field
        pfPassword = new JPasswordField(15);
        pfPassword.setBounds(tfLogin.getBounds().x, tfLogin.getBounds().y + tfLogin.getBounds().height, 200, 25);

        // Login Button
        btnLogin = new JButton("LOGIN");
        btnLogin.addActionListener(this);
        btnLogin.setBounds(tfLogin.getBounds().x + tfLogin.getBounds().width, screenHeight/2, 100, 50);

        // Panel, AbsoluteXYLayout
        panel.setLayout(null);
        panel.add(lbTitle);
        panel.add(tfLogin);
        panel.add(pfPassword);
        panel.add(btnLogin);

        // Panel > Frame
        add("Center", panel);
        addWindowListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("LOGIN")) {
            String id = tfLogin.getText();
            String password = new String(pfPassword.getPassword());

            if (id == null || id.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력해주세요.");
            } else {
                // Try Login
                User user = new UserDAO().getUserOrNull(id, password);

                // Login Success
                if (user != null) {
                    new UserDAO().loginSucess(id);

                    Runnable runnable;
                    if (user.getId().equals("admin")) {
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                PopupWindow pw = new PopupWindow(id);
                                pw.setVisible(true);
                            }
                        };
                    } else {
                        runnable = () -> {
                            PopupWindow pw = new PopupWindow(id);
                            pw.setVisible(true);
                        };
                    }

                    SwingUtilities.invokeLater(runnable);
                    dispose();
                } else {
                // Login Fail
                    JOptionPane.showMessageDialog(this, "Login fail.");
                }
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        JFrame frame = (JFrame) e.getSource();

        JOptionPane.showConfirmDialog(
                frame,
                "Welcome to PICO House!",
                title,
                JOptionPane.DEFAULT_OPTION);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        JFrame frame = (JFrame) e.getSource();
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

/*        int result = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure?",
                "Exit Application",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else {
            //frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }*/
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
