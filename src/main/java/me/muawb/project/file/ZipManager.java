package me.muawb.project.file;

import me.muawb.project.utils.Starter;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipManager {

    private ZipInputStream in = null;
    private FileOutputStream out = null;
    private static ZipEntry entry;
    private File folder;
    private String name;

    public ZipManager(String name){
        this.name = name;
    }

    public void cleanUp(){
        if (out != null){
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        new Thread(() -> {
            try {
                in = new ZipInputStream(new FileInputStream(
                        FileManager.getWorkDir().getAbsolutePath() + File.separator + name));
                while ((entry = in.getNextEntry()) != null){
                    folder = new File(FileManager.getWorkDir().getAbsolutePath() +
                            File.separator + entry.getName());

                    if (entry.isDirectory()){
                        if (folder.exists()){
                            System.out.println(entry.getName());
                        } else {
                            folder.mkdirs();
                        }
                    } else {
                        if (folder.getParentFile() != null && !folder.getParentFile().exists()){
                            folder.getParentFile().mkdirs();
                        }
                        if (!folder.exists()){
                            folder.createNewFile();
                        }
                    }
                    try {
                        out = new FileOutputStream(folder);
                        IOUtils.copy(in, out);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        cleanUp();
                    }
                }
                Starter start = new Starter();
                start.launch();
            } catch (IOException e) {
                e.printStackTrace();
                cleanUp();
            }
        })
                .start();
    }
}

















