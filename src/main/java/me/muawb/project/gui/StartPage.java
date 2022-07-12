package me.muawb.project.gui;

import me.muawb.project.file.CheckFile;
import me.muawb.project.file.FileManager;
import me.muawb.project.utils.BarPainter;
import me.muawb.project.utils.Sound;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.File;

public class StartPage extends JComponent implements Frame {

    private static final int WIDTH = 750;
    private static final int HEITH = 400;

    private String title;
    private JFrame frame;
    private JPanel panel;
    private JButton start;
    private JButton settings;
    private JProgressBar load;
    private JLabel wait;
    private volatile boolean breack = false;

    public StartPage(){

    }

    public StartPage(String title){
        this.title = title;
    }

    @Override
    public void paintComponent(Graphics g) {
        try {
            Graphics2D g2 = (Graphics2D) g;
            Image img = new ImageIcon(this.getClass().getResource("/image/snow.png")).getImage();
            g2.drawImage(img, 0, 0, null);

            Line2D lineLeft = new Line2D.Float(264,176,264,275);
            Line2D lineDown = new Line2D.Float(264,275,465,275);
            Line2D lineRight = new Line2D.Float(465,275,465,175);
            Line2D lineUp = new Line2D.Float(264,174,465,174);
            g2.setBackground(new Color(51,51,51));
            g2.draw(lineLeft);
            g2.draw(lineDown);
            g2.draw(lineRight);
            g2.draw(lineUp);

        } catch (Exception e){
            System.err.println(e.getCause());
        }
    }

    @Override
    public void createFrame() {
        new Thread(() -> {
            frame = new JFrame();
            frame.setLayout(new GridBagLayout());
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(WIDTH, HEITH);
            frame.setLocationRelativeTo(null);
            frame.setTitle(title);

            StartPage color = new StartPage();
            frame.setContentPane(color);

            panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            panel.setBackground(new Color(204, 255, 255)); //255 255 204
            panel.setBounds(265, 175, 200, 100);
            frame.add(panel);

            start = new JButton("Играть");
            start.setPreferredSize(new Dimension(70, 35));
            start.setBackground(new Color(255, 204, 255));
            panel.add(start, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.NONE,
                    new Insets(10, 50, 0, 0), 0, 0));

            wait = new JLabel();
            wait.setText("");
            panel.add(wait, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(30, 0, 0, 0), 0, 0));

            start.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    start.setEnabled(false);
                    CheckFile.check(frame, wait, load);
                }
            });

            ImageIcon icon = new ImageIcon(this.getClass().getResource("/image/icon.png"));
            settings = new JButton();
            settings.setPreferredSize(new Dimension(36, 36));
            settings.setBackground(new Color(204, 51, 51));
            settings.setIcon(icon);
            panel.add(settings, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(0, 30, 43, 0), 0, 0));

            settings.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Options settings = new Options();
                    settings.createFrame();
                }
            });

            load = new JProgressBar();
            UIDefaults defaults = new UIDefaults();
            defaults.put("ProgressBar[Enabled].backgroundPainter", new BarPainter(new Color(204, 255, 204)));
            defaults.put("ProgressBar[Enabled].foregroundPainter", new BarPainter(new Color(0, 255, 255)));
            load.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.TRUE);
            load.putClientProperty("Nimbus.Overrides", defaults);
            load.setPreferredSize(new Dimension(185, 30));
            load.setStringPainted(true);
            load.setMinimum(0);
            load.setMaximum(100);
            load.setValue(0);

            load.setVisible(false);
            panel.add(load, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.SOUTH, GridBagConstraints.NONE,
                    new Insets(0, 0, 5, 0), 0, 0));

            frame.validate();
            frame.setVisible(true);


            File f = new File(FileManager.getWorkDir().getAbsolutePath() +
                    File.separator + "key.wav");
            breack = true;
            try (Sound sound = new Sound(f)){
                while (breack){
                    sound.play();
                    sound.join();
                }
            }
        })
                .start();
    }

    public void stop(){
        breack = false;
    }
}







