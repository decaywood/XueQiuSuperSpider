package org.decaywood.utils;

import java.util.Arrays;

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

    public static boolean nullOrEmpty(String... args) {
        return !Arrays.stream(args)
                .noneMatch(x -> x == null || x.length() == 0 || EmptyObject.emptyString.equals(x));
    }

}
