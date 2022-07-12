package me.muawb.project.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.muawb.project.gui.Login;
import me.muawb.project.gui.Options;
import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

public class FileManager {

    private static File wrk;
    private static File workDir = null;
    private Properties data;
    private FileOutputStream out = null;
    private FileInputStream in = null;

    public static File getWorkDir(){
        if (workDir == null){
            workDir = getPath("minecraft");
        }
        return workDir;
    }

    private static File getPath(String workDir){
        String userHome = System.getProperty("user.home", ".");
        switch (getPlatform().ordinal()){
            case 0:
                String appData = System.getenv("APPDATA");
                if (appData != null){
                    wrk = new File(appData, "." + workDir + "/");
                } else {
                    wrk = new File(userHome, "." + workDir + "/");
                }
                break;
        }
        if (!wrk.exists() && !wrk.mkdirs()) {
            throw new RuntimeException("Не удалось обнаружить рабочую директорию: " + workDir);
        }
    return wrk;
}

    private static OS getPlatform() {
        String name = System.getProperty("os.name").toLowerCase();
        if (name.contains("win")) {
            return OS.Windows;
        } else {
            return OS.Unknown;
        }
    }

    public void setName(JTextField field, String key, String comment){
        try {
            data = new Properties();
            data.setProperty(key, field.getText());
            out = new FileOutputStream(FileManager.getWorkDir().getAbsolutePath() +
                    File.separator + "user.properties");
            data.store(out, comment);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName(String key){
        String str = null;
        try {
            data = new Properties();
            in = new FileInputStream(FileManager.getWorkDir().getAbsolutePath() +
                    File.separator + "user.properties");
            data.load(in);
            str = data.getProperty(key);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return str;
    }

    public void toMapping(JTextField width, JTextField heith){
        Options op = new Options(width.getText(), heith.getText());
        try(FileWriter writer = new FileWriter(FileManager.getWorkDir().getAbsolutePath() +
        File.separator + "settings.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String str = gson.toJson(op);
            writer.write(str);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String fromMapping(int i){
        Options op = new Options();
        try(FileReader reader = new FileReader(FileManager.getWorkDir().getAbsolutePath() +
        File.separator +  "settings.json")) {
            Gson gson = new Gson();
            op = gson.fromJson(reader, Options.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        switch (i){
            case 0:
              return op.getParamWidth();
            case 1:
                return op.getParamHeigth();
        }
        return null;
    }

    private enum OS {
        Windows,
        Unknown
    }
}


