package me.mneri.ca.util;

import java.awt.*;

public class Colors {
    public static String toHexString(Color color) {
        return String.format("#%06x", color.getRGB() & 0x00FFFFFF);
    }
}
