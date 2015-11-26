package org.decaywood.utils;

/**
 * @author: decaywood
 * @date: 2015/11/26 10:19
 */
public abstract class StringChecker {

    public static boolean isNumeric(String test) {
        return test != null
                && test.length() > 0
                && test.chars().allMatch(Character::isDigit);
    }

}
