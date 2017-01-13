package me.mneri.ca.util;

import java.awt.*;

public class Colors {
    public static Color fromHsbArray(float[] hsb) {
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static void linearGradient(Color start, Color end, Color[] out) {
        float[] startHsb = toHsbArray(start);
        float[] endHsb = toHsbArray(end);
        float[] stepHsb = new float[3];

        for (int i = 0; i < 3; i++)
            stepHsb[i] = (endHsb[i] - startHsb[i]) / (out.length - 1);

        float[] hsb = new float[3];

        for (int i = 0; i < out.length; i++) {
            for (int j = 0; j < 3; j++)
                hsb[j] = (startHsb[j] + stepHsb[j] * i);

            out[i] = fromHsbArray(hsb);
        }
    }

    public static Color[] linearGradient(Color start, Color end, int steps) {
        Color[] gradient = new Color[steps];
        linearGradient(start, end, gradient);
        return gradient;
    }

    public static String toHexString(Color color) {
        return String.format("#%06x", color.getRGB() & 0x00FFFFFF);
    }

    public static float[] toHsbArray(Color color) {
        return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
    }
}
