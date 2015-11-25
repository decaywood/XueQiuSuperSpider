package org.decaywood.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author: decaywood
 * @date: 2015/11/23 18:40
 */
public abstract class FileLoader {

    private static final String COOKIE_RELETIVE_PATH = "cookie/cookie.txt";
    private static final String TEST_JSON_PATH = "testJson/testJson.txt";
    private static final String ROOT_PATH = FileLoader.class.getResource("").getPath();

    private static String cookie;

    public static String loadCookie() {
        return loadFile(COOKIE_RELETIVE_PATH);
    }

    public static String loadTestJson() {
        return loadFile(TEST_JSON_PATH);
    }

    public static String loadFile(String rowPath) {
        if(cookie != null) return cookie;
        return loadFile(rowPath, new StringBuilder());
    }

    public static String loadFile(String rowPath, StringBuilder builder) {
        String path = ROOT_PATH + rowPath;
        String encoding = "UTF-8";
        StringBuilder textBuilder = new StringBuilder();
        try (InputStreamReader read = new InputStreamReader(new FileInputStream(path), encoding)) {

            BufferedReader bufferedReader = new BufferedReader(read);
            String text;
            while ((text = bufferedReader.readLine()) != null) textBuilder.append(text);

        } catch (Exception e) {
            return loadFile(builder.append("../").toString() + rowPath, builder);
        }
        return cookie = textBuilder.toString();
    }


}
