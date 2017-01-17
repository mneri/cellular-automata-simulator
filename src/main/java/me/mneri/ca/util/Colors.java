package me.mneri.ca.util;

import me.mneri.ca.interpolator.Interpolator;

import java.awt.*;
import java.util.Arrays;

public class Colors {
    public static Color fromHsbArray(float[] hsb) {
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color fromRgbArray(float[] rgb) {
        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    public static void createHsbGradient(Color start, Color end, Interpolator inter, Color[] out) {
        float[] startHsb = toHsbArray(start);
        float[] endHsb = toHsbArray(end);
        float[] diffHsb = new float[3];

        for (int i = 0; i < 3; i++)
            diffHsb[i] = endHsb[i] - startHsb[i];

        for (int i = 0; i < out.length; i++) {
            float[] hsb = new float[3];

            for (int j = 0; j < 3; j++)
                hsb[j] = startHsb[j] + diffHsb[j] * inter.get(((float) i) / out.length);

            out[i] = fromHsbArray(hsb);
        }
    }

    public static Color[] createHsbGradient(Color start, Color end, Interpolator interpolator, int steps) {
        Color[] gradient = new Color[steps];
        createHsbGradient(start, end, interpolator, gradient);
        return gradient;
    }

    public static void createRgbGradient(Color start, Color end, Interpolator interpolator, Color[] out) {
        float[] startRgb = toRgbArray(start);
        float[] endRgb = toRgbArray(end);
        float[] stepRgb = new float[3];

        for (int i = 0; i < 3; i++)
            stepRgb[i] = (endRgb[i] - startRgb[i]) / (out.length - 1);

        float[] rgb = new float[3];

        for (int i = 0; i < out.length; i++) {
            for (int j = 0; j < 3; j++)
                rgb[j] = (startRgb[j] + stepRgb[j] * i);

            out[i] = fromRgbArray(rgb);
        }
    }

    public static Color[] createRgbGradient(Color start, Color end, Interpolator interpolator, int steps) {
        Color[] gradient = new Color[steps];
        createRgbGradient(start, end, interpolator, gradient);
        return gradient;
    }

    public static String toHexString(Color color) {
        return String.format("#%06x", color.getRGB() & 0x00FFFFFF);
    }

    public static float[] toHsbArray(Color color) {
        return Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
    }

    public static float[] toRgbArray(Color color) {
        return color.getRGBColorComponents(null);
    }
}
