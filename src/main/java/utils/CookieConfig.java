package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author: decaywood
 * @date: 2015/11/23 18:40
 */
public abstract class CookieConfig {

    private static final String COOKIE_RELETIVE_PATH = "cookie/cookie.txt";
    private static final String COOKIE_PATH = CookieConfig.class.getResource("").getPath() + "../" + COOKIE_RELETIVE_PATH;

    private static String cookie;

    public static String loadCookie() {
        return loadCookie(COOKIE_PATH);
    }

    public static String loadCookie(String path) {
        if(cookie != null) return cookie;
        String encoding = "UTF-8";
        StringBuilder builder = new StringBuilder();
        try (InputStreamReader read = new InputStreamReader(new FileInputStream(path), encoding)) {

            BufferedReader bufferedReader = new BufferedReader(read);
            String text;
            while ((text = bufferedReader.readLine()) != null) builder.append(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookie = builder.toString();
    }


}
