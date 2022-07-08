package me.muawb.project.utils;

import me.muawb.project.file.ZipManager;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Update implements AutoCloseable{

    private URL url = null;
    private URLConnection conn = null;
    private BufferedInputStream in = null;
    private FileOutputStream out = null;
    private String pathURL;
    private String pathClient;

    public Update(String pathURL, String pathClient){
        this.pathURL = pathURL;
        this.pathClient = pathClient;
    }

    public void start(JProgressBar st, JLabel active){
        new Thread(() -> {
            st.setVisible(true);
            try {
                url = new URL(pathURL);
                conn = url.openConnection();
                long len = (conn.getContentLength());
                st.setMaximum((int) len);
                File client = new File(pathClient);

                in = new BufferedInputStream(conn.getInputStream());
                out = new FileOutputStream(client);

                byte[] buffer = new byte[4096];
                int count = 0;

                while ((count = in.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                    st.setValue((int) client.length());
                    st.setString("Скачано: " + (int) (client.length() / 1000) + " Кбайт " + "из: " + (len / 1000));
                }
                st.setVisible(false);
                System.out.println("Готово");
                active.setText("Распаковка клиента...");

                ZipManager unZip = new ZipManager("client.zip");
                unZip.start();
                unZip.cleanUp();

            } catch (IOException e) {
                e.printStackTrace();
            }
        })
       .start();
    }

    @Override
    public void close() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null){
                out.close();
            }
        } catch (IOException e){

        }
    }
}








