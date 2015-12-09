package org.decaywood.utils;

import java.util.Arrays;

/**
 * @author: decaywood
 * @date: 2015/12/9 20:38
 */
public abstract class MathUtils {

    public static double min(double... nums) {
        if(nums.length == 0) return Double.MIN_VALUE;
        return Arrays.stream(nums).min().getAsDouble();
    }

    public static int min(int... nums) {
        if(nums.length == 0) return Integer.MIN_VALUE;
        return Arrays.stream(nums).min().getAsInt();
    }


    public static long min(long... nums) {
        if(nums.length == 0) return Long.MIN_VALUE;
        return Arrays.stream(nums).min().getAsLong();
    }


    public static double max(double... nums) {
        if(nums.length == 0) return Double.MIN_VALUE;
        return Arrays.stream(nums).max().getAsDouble();
    }

    public static int max(int... nums) {
        if(nums.length == 0) return Integer.MIN_VALUE;
        return Arrays.stream(nums).max().getAsInt();
    }


    public static long max(long... nums) {
        if(nums.length == 0) return Long.MIN_VALUE;
        return Arrays.stream(nums).max().getAsLong();
    }




}
