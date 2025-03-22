package com.invadermonky.hearthfire.util;

public class MathUtils {
    public static int ceil(float pValue) {
        int i = (int) pValue;
        return pValue > (float) i ? i + 1 : i;
    }
}
