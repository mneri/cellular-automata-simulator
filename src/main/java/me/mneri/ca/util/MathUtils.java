package me.mneri.ca.util;

public class MathUtils {
    public static double log(double n, int base) {
        return Math.log(n) / Math.log(base);
    }

    public static double log2(double n) {
        return log(n, 2);
    }
}
