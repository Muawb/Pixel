package me.muawb.project.gui;

import me.muawb.project.file.FileManager;
import me.muawb.project.manager.CreateFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.UIManager.*;

public class Login extends JComponent implements Frame, Serializable{

    private static final long serialVersionUID = 1L;

    private static final int WIDTH = 750;
    private static final int HEiGHT = 400;
    private static final String BUTTON_NAME = "Войти";

    private String title;
    private JTextField userName;
    private JTextField password;
    private JButton join;
    private JPanel panelLog;
    private JFrame jf;
    private CreateFactory factory;
    private FileManager manager;

    public Login(){
    }

    public Login(String t){
        this.title = t;
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            Graphics2D g2 = (Graphics2D) g;
            Image image = new ImageIcon(this.getClass().getResource("/image/logo.png")).getImage();
            g2.drawImage(image, 0,0,null);
        } catch (Exception e){
            System.out.println(e.getCause());
        }
    }

    @Override
    public void createFrame() {
        jf = new JFrame();
        jf.setLayout(new GridBagLayout());
        jf.setResizable(false);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(WIDTH, HEiGHT);
        jf.setLocationRelativeTo(null);
        jf.setTitle(title);

        Login logo = new Login();
        jf.setContentPane(logo);

        userName = new JTextField();
        userName.setBounds(305, 210, 110, 30);
        jf.add(userName);

        password = new JPasswordField();
        password.setBounds(305, 245, 110, 30);
        jf.add(password);

        join = new JButton();
        join.setText(BUTTON_NAME);
        join.setBackground(new Color(180, 255, 255));
        join.setPreferredSize(new Dimension(80, 35));

        panelLog = new JPanel();
        panelLog.setBounds(322, 272, 80, 40);
        panelLog.setBackground(new Color(239, 229, 178));
        panelLog.add(join);
        jf.add(panelLog);

        manager = new FileManager();
        userName.setText(manager.getName("name"));

        join.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.setName(userName, "name", "save user name from Minecraft");
                try {
                    factory = new CreateFactory();
                    factory.createConnection(userName.getText(), password.getText(), join, jf);
                } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            jf.validate();
            jf.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }

            Login authorize = new Login("Pixel");
            authorize.createFrame();
        } catch (Exception e){
            System.out.println(e.getCause());
        }
    }
}







