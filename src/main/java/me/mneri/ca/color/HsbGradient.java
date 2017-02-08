package me.mneri.ca.color;

import java.awt.*;

import me.mneri.ca.interpolator.Interpolator;
import me.mneri.ca.interpolator.LinearInterpolator;

public class HsbGradient implements Gradient {
    private Color[] mColors;

    public HsbGradient(Color start, Color end) {
        this(start, end, new LinearInterpolator(), 256);
    }

    public HsbGradient(Color start, Color end, Interpolator interpolator) {
        this(start, end, interpolator, 256);
    }

    public HsbGradient(Color start, Color end, int steps) {
        this(start, end, new LinearInterpolator(), steps);
    }

    public HsbGradient(Color start, Color end, Interpolator interpolator, int steps) {
        float[] startHsb = Colors.toHsbArray(start);
        float[] endHsb = Colors.toHsbArray(end);
        float[] diffHsb = new float[3];
        mColors = new Color[steps];

        for (int i = 0; i < 3; i++)
            diffHsb[i] = endHsb[i] - startHsb[i];

        for (int i = 0; i < steps; i++) {
            float[] hsb = new float[3];

            for (int j = 0; j < 3; j++)
                hsb[j] = startHsb[j] + diffHsb[j] * interpolator.get(((float) i) / steps);

            mColors[i] = Colors.fromHsbArray(hsb);
        }
    }

    @Override
    public Color get(double value) {
        if (value < 0.0f || value > 1.0f)
            throw new IllegalArgumentException("Value should be between 0 and 1; it was: " + value);

        int length = mColors.length;
        int index = (int) Math.min(value * (length - 1), length - 1);

        return mColors[index];
    }

    @Override
    public int size() {
        return mColors.length;
    }
}
