package me.muawb.project.gui;

import com.google.gson.annotations.SerializedName;
import me.muawb.project.file.FileManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

public class Options implements Frame, Serializable {

    private static final long serialVersionUID = 1L;

    private transient JFrame frame;
    private transient JTextField width;
    private transient JTextField height;
    private transient JTextField xms;
    private transient JTextField xmx;
    private FileManager manager;

    @SerializedName("width")
    private String paramWidth;
    @SerializedName("heigth")
    private String paramHeigth;

    public Options(){
    }

    public Options(String w, String h){
        this.paramWidth = w;
        this.paramHeigth = h;
    }

    @Override
    public void createFrame() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        frame.setSize(250,180);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(204,255,204));
        frame.setTitle("Настройки");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                manager = new FileManager();
                manager.toMapping(width, height);
            }
        });

        JLabel sizeText = new JLabel("Размер екрана:");
        sizeText.setFont(new Font("Verdana", Font.BOLD, 12));
        frame.add(sizeText, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0,0,0,0),0,0));
        JLabel widthText = new JLabel("ширина:");
        frame.add(widthText, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0,0,80,60),0,0));
        JLabel heightText = new JLabel("высота:");
        frame.add(heightText, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.CENTER,GridBagConstraints.NONE,
                new Insets(0,0,30,58),0,0));

        width = new JTextField();
        width.setText(String.valueOf(800));
        width.setPreferredSize(new Dimension(40,25));
        frame.add(width, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0,45,80,0),0,0));
        height = new JTextField();
        height.setText(String.valueOf(450));
        height.setPreferredSize(new Dimension(40,25));
        frame.add(height, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0,45,30,0),0,0));

        JLabel memory = new JLabel("Выделение памяти:");
        memory.setFont(new Font("Verdana", Font.BOLD, 12));
        frame.add(memory, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(25,0,0,0),0,0));
        JLabel textXms = new JLabel("Xms:");
        frame.add(textXms, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.SOUTH,GridBagConstraints.NONE,
                new Insets(0,0,30,40),0,0));
        JLabel textXmx = new JLabel("Xmx:");
        frame.add(textXmx, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.SOUTH,GridBagConstraints.NONE,
                new Insets(0,0,5,40),0,0));

        xms = new JTextField();
        xms.setText("128");
        xms.setPreferredSize(new Dimension(40,25));
        frame.add(xms, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.SOUTH,GridBagConstraints.NONE,
                new Insets(0,45,24,0),0,0));
        xmx = new JTextField();
        xmx.setText("1024");
        xmx.setPreferredSize(new Dimension(40,25));
        frame.add(xmx, new GridBagConstraints(0,0,1,1,1,1,
                GridBagConstraints.SOUTH,GridBagConstraints.NONE,
                new Insets(0,45,0,0),0,0));

        frame.setVisible(true);
        manager = new FileManager();
        manager.toMapping(width, height);
    }

    public String getParamWidth() {
        return paramWidth;
    }

    public String getParamHeigth() {
        return paramHeigth;
    }
}









