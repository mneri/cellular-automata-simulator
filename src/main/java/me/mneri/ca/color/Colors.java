package me.mneri.ca.color;

import me.mneri.ca.interpolator.Interpolator;

import java.awt.*;

public class Colors {
    public static Color fromHsbArray(float[] hsb) {
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color fromRgbArray(float[] rgb) {
        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    public static void createRgbGradient(Color start, Color end, Interpolator interpolator, Color[] out) {

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
