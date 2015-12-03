package org.decaywood.utils;

import java.util.Arrays;

/**
 * @author: decaywood
 * @date: 2015/11/26 10:19
 */
public abstract class StringUtils {

    public static boolean isNumeric(String test) {
        return test != null
                && test.length() > 0
                && test.chars().allMatch(Character::isDigit);
    }

    public static boolean nullOrEmpty(String... args) {
        return Arrays.stream(args)
                .anyMatch(x -> x == null || x.length() == 0 || EmptyObject.emptyString.equals(x));
    }

    public static String string2Unicode(String string) {

        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            unicode.append("\\u").append(Integer.toHexString(c));
        }
        return unicode.toString();
    }

    public static String unicode2String(String unicode) {

        StringBuilder string = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }
        return string.toString();
    }

}
