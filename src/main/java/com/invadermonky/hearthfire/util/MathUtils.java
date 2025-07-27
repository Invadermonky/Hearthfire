package com.invadermonky.hearthfire.util;

public class MathUtils {
    public static int ceil(float pValue) {
        int i = (int) pValue;
        return pValue > (float) i ? i + 1 : i;
    }

    public static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static double clamp(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public static float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

}
