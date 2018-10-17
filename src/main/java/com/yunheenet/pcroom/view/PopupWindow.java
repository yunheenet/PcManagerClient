package com.yunheenet.pcroom.view;

import com.yunheenet.pcroom.domain.User;
import com.yunheenet.pcroom.domain.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PopupWindow extends JFrame {
    private static String title = "PICO System";

    private int screenWidth;
    private int screenHeight;
    private User user;
    private JLabel labelTime;
    private ScheduledExecutorService scheduledExecutorService;

    public PopupWindow(String id) {
        super("PopupWindow");
        this.user = new UserDAO().getUserInfo(id);

        getScreenSize();
        initializeFrame();
        start();
        startUserTimeTimer();
    }

    private void start() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    final int R = 200;
                    final int G = 200;
                    final int B = 200;

                    Paint p =
                            new GradientPaint(0.0f, 0.0f, new Color(R, G, B, 255),
                                    0.0f, getHeight(), new Color(R, G, B, 255), true);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setPaint(p);
                    g2d.fillRect(0,0, getWidth(), getHeight());
                }
            }
        };
        setContentPane(panel);
        setLayout(new GridBagLayout());

        add(new JLabel(user.getName() + "님 반갑습니다."
                , SwingConstants.HORIZONTAL)
                , makeGridBagConstraints(0, 0, 1, 1));

        add(new JLabel("시작 시간 : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date(System.currentTimeMillis()))
                , SwingConstants.HORIZONTAL)
                , makeGridBagConstraints(0, 1, 1, 1));

        labelTime = new JLabel(getUserTime()
                , SwingConstants.HORIZONTAL);
        add(labelTime, makeGridBagConstraints(0, 2, 1, 1));

        JButton btnLogout = new JButton("로그아웃");
            btnLogout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Component component = (Component) e.getSource();
                    JFrame frame = (JFrame) SwingUtilities.getRoot(component);

                    int result = JOptionPane.showConfirmDialog(
                        frame,
                        "로그아웃 하시겠습니까?",
                        title,
                        JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        logout();
                    } else {
                        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    }
                }
            });
        add(btnLogout, makeGridBagConstraints(0, 3, 1, 1));
    }

    private void logout() {
        dispose();
        new UserDAO().logout(user.getId());
        scheduledExecutorService.shutdown();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LogoutWindow lw = new LogoutWindow();
                lw.setVisible(true);
            }
        });
    }

    private String getUserTime() {
        return String.format("남은 시간 : %02d:%02d", user.getTime()/60, user.getTime()%60);
    }

    private void startUserTimeTimer() {
        Runnable userTimeTimer = new Runnable() {
            @Override
            public void run() {
                System.out.println("####"
                        + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date())
                        + "####");

                user.setTime(user.getTime()-1);
                if (user.getTime() <= 0) {
                    logout();
                } else {
                    labelTime.setText(getUserTime());
                    new UserDAO().updateUserTime(user.getId());
                }
            }
        };

        scheduledExecutorService =
                Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> scheduledFuture =
                scheduledExecutorService.scheduleWithFixedDelay(
                        userTimeTimer, 60, 60, TimeUnit.SECONDS);
    }

    private GridBagConstraints makeGridBagConstraints(int x, int y, int wx, int wy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = wx;
        gbc.weighty = wy;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    private void initializeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(screenWidth-400, 100, 300,  120);
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
    }

    private void getScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int)screenSize.getWidth();
        screenHeight = (int)screenSize.getHeight();
    }

    public static void main(String[] args) {
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        boolean isPerPixelTranslucencySupported =
                gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSLUCENT);

        if (!isPerPixelTranslucencySupported) {
            System.out.println(
                "Per-pixel translucency is not supported");
                System.exit(0);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PopupWindow pw = new PopupWindow("123");
                pw.setVisible(true);
            }
        });
    }
}
