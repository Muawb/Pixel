package me.muawb.project.file;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.muawb.project.gui.Login;
import me.muawb.project.gui.Options;
import javax.swing.*;
import java.io.*;
import java.util.Properties;

public class FileManager {

    private static final String PATH_TO_DATA = "src/main/resources/data/user.properties";

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
            out = new FileOutputStream(PATH_TO_DATA);
            data.store(out, comment);
        } catch (IOException e){
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
            in = new FileInputStream(PATH_TO_DATA);
            data.load(in);
            str = data.getProperty(key);
        } catch (IOException e){
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
        try(FileWriter writer = new FileWriter("src/main/resources/data/settings.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String str = gson.toJson(op);
            writer.write(str);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String fromMapping(int i){
        Options op = new Options();
        try(FileReader reader = new FileReader("src/main/resources/data/settings.json")) {
            Gson gson = new Gson();
            op = gson.fromJson(reader, Options.class);
        } catch (IOException e){
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


