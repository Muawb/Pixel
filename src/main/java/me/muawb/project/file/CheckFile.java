package me.muawb.project.file;

import me.muawb.project.gui.StartPage;
import me.muawb.project.utils.Sound;
import me.muawb.project.utils.Starter;
import me.muawb.project.utils.Update;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckFile {

    private static final String hch = "18c10414318618b19e1461c717d1391db1611c715516719d";
    private static final long size = 10180113;

    private static String url = "скоро !";
    private static MessageDigest digest;
    private static FileInputStream in;
    private static StringBuilder sb;

    public static void check(JFrame look, JLabel tx, JProgressBar load){
        try {
            File pathToJar = new File(FileManager.getWorkDir().getAbsolutePath() +
                    File.separator + "versions" + File.separator + "1.12.2" + File.separator + "1.12.2.jar");
            digest = MessageDigest.getInstance("MD5");
            String str = buildHash(digest, pathToJar, tx, load);
            System.out.println(str);

            if ((pathToJar.length() == size) && (str.equals(hch)) && (pathToJar.exists() || pathToJar.isFile())){
                System.out.println("Ok !");
                Starter start = new Starter();
                start.launch();
                look.setVisible(false);
                StartPage sound = new StartPage();
                sound.stop();
            } else {
                Update update = new Update(url, FileManager.getWorkDir().getAbsolutePath()
                        + File.separator + "client.zip");
                update.start(load, tx);
                update.close();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String buildHash(MessageDigest digest, File ch, JLabel tx, JProgressBar load){

        try {
            if (ch.exists()) {
                in = new FileInputStream(ch);

                byte[] list = new byte[1024];
                int count = 0;
                while ((count = in.read(list)) != -1) {
                    digest.update(list, 0, count);
                }
                in.close();

                byte[] buffer = digest.digest();

                sb = new StringBuilder();
                for (int i = 0; i < buffer.length; i++) {
                    sb.append(Integer.toString((buffer[i] & 0xff) + 0x100, 16));
                }
            } else {
                Update update = new Update(url, FileManager.getWorkDir().getAbsolutePath() + File.separator + "DivineRPG-1.8.20.jar");
                update.start(load, tx);
                update.close();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
