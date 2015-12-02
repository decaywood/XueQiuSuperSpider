package org.decaywood.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: decaywood
 * @date: 2015/11/23 18:40
 */
public abstract class FileLoader {

    private static final String COOKIE_FLUSH_PATH = "cookie/index.txt";
    private static final String TEST_JSON_PATH = "testJson/testJson.txt";
    private static final String ROOT_PATH = FileLoader.class.getResource("").getPath();

    private static final Map<String, String> cookie = new HashMap<>();

    public static String loadCookie(String key) {
        if(cookie.containsKey(key)) return cookie.get(key);
        return EmptyObject.emptyString;
    }

    public static void updateCookie(String cookie, String key) {
        FileLoader.cookie.put(key, cookie);
        String replacedKey = key.substring(7, key.indexOf(".com"));
        updateFile(COOKIE_FLUSH_PATH, cookie, replacedKey);
    }


    public static String loadTestJson() {
        return loadFile(TEST_JSON_PATH);
    }


    public static String loadFile(String rawPath) {
        return loadFile(rawPath, new StringBuilder());
    }


    public static void updateFile(String rawPath, String text, String key) {
        updateFile(rawPath, text, key, new StringBuilder());
    }


    public static void updateFile(String rawPath, String text, String key, StringBuilder builder) {
        String path = ROOT_PATH + rawPath;
        File cookie = new File(path);
        String p;
        if(!cookie.exists()) updateFile(builder.append("../").toString() + rawPath, text, key, builder);
        else {

            try {
                p = path.replace("index", key);
                System.out.println(p);
                File file = new File(p);
                if(!file.exists()) file.createNewFile();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(p))) {
                    writer.write(text);
                    writer.flush();
                    writer.close();
                }

            } catch (Exception e) {
                System.out.println("FileLoader -> 写入失败!");
            }
        }
    }


    public static String loadFile(String rawPath, StringBuilder builder) {
        String path = ROOT_PATH + rawPath;
        String encoding = "UTF-8";
        StringBuilder textBuilder = new StringBuilder();
        try (InputStreamReader read = new InputStreamReader(new FileInputStream(path), encoding)) {

            BufferedReader bufferedReader = new BufferedReader(read);
            String text;
            while ((text = bufferedReader.readLine()) != null) textBuilder.append(text);

        } catch (Exception e) {
            return loadFile(builder.append("../").toString() + rawPath, builder);
        }
        return textBuilder.toString();
    }

}
