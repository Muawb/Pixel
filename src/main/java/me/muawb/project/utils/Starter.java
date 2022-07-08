package me.muawb.project.utils;

import me.muawb.project.file.FileManager;
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Starter {

    private static final String CLASS_PATH = "net.minecraft.client.main.Main";
    private FileManager manager;

    public void launch() {
        new Thread(() -> {
            List < URL > url = new ArrayList < URL >();
            try {
                url.addAll(getLibs(new File(FileManager.getWorkDir().getAbsolutePath() +
                        File.separator + "versions")));
                url.addAll(getLibs(new File(FileManager.getWorkDir().getAbsolutePath()
                        + File.separator + "libraries")));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            URLClassLoader cl = new URLClassLoader(url.toArray(new URL[url.size()]));

            System.out.println("Список полученных файлов: " + url);
            System.setProperty("_JAVA_OPTIONS", "-Xms128");
            System.setProperty("_JAVA_OPTIONS", "-Xmx512");
            System.setProperty("fml.ignoreInvalidMinecraftCertificates", "true");
            System.setProperty("fml.ignorePatchDiscrepancies", "true");
            System.setProperty("org.lwjgl.librarypath", FileManager.getWorkDir().getAbsolutePath() +
                    File.separator + "versions" + File.separator + "1.12.2" + File.separator + "natives");
            System.setProperty("net.java.games.input.librarypath", FileManager.getWorkDir().getAbsolutePath() +
                    File.separator + "versions" + File.separator + "1.12.2" + File.separator + "natives");
            System.setProperty("java.library.path", FileManager.getWorkDir().getAbsolutePath() +
                    File.separator + "versions" + File.separator + "1.12.2" + File.separator + "natives");

            List < String > params = new ArrayList < String >();
            manager = new FileManager();

            try {
                cl.loadClass("com.mojang.authlib.Agent");
                params.add("--accessToken");
                params.add("1");
                params.add("--uuid");
                params.add("12345");
                params.add("--userProperties");
                params.add("{}");
                params.add("--assetIndex");
                params.add("1.12.2");
                params.add("--width");
                params.add(manager.fromMapping(0));
                params.add("--height");
                params.add(manager.fromMapping(1));
            } catch (ClassNotFoundException x) {
                params.add("--session");
                params.add("1");
            }

            params.add("--username");
            params.add(manager.getName("name"));
            params.add("--version");
            params.add("1.12.2");
            params.add("--gameDir");
            params.add(FileManager.getWorkDir().getAbsolutePath() + "/");
            params.add("--assetsDir");
            params.add(FileManager.getWorkDir().getAbsolutePath() + "/assets");

            try {
                Class < ? > start = cl.loadClass(CLASS_PATH);
                Method main = start.getMethod("main", new Class[]{String[].class});
                main.invoke(null, new Object[]{
                        params.toArray(new String[0])
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        })
                .start();
    }

    private List < URL > getLibs(File libsFolder) throws MalformedURLException {
            List < URL > libs = new ArrayList < URL > ();
            for (File file : libsFolder.listFiles()) {
                if (file.isDirectory()) {
                    libs.addAll(getLibs(file));
                } else {
                    if (file.getName().endsWith(".jar")) {
                        libs.add(file.toURI().toURL());
                    }
                }
            }
            return libs;
    }
}
