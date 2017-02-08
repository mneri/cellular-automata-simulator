package me.mneri.ca.color;

import me.mneri.ca.interpolator.Interpolator;
import me.mneri.ca.interpolator.LinearInterpolator;

import java.awt.*;

public class RgbGradient implements Gradient {
    private Color[] mColors;

    public RgbGradient(Color start, Color end) {
        this(start, end, new LinearInterpolator(), 256);
    }

    public RgbGradient(Color start, Color end, Interpolator interpolator) {
        this(start, end, interpolator, 256);
    }

    public RgbGradient(Color start, Color end, int steps) {
        this(start, end, new LinearInterpolator(), steps);
    }

    public RgbGradient(Color start, Color end, Interpolator interpolator, int steps) {
        float[] startRgb = Colors.toRgbArray(start);
        float[] endRgb = Colors.toRgbArray(end);
        float[] stepRgb = new float[3];
        mColors = new Color[steps];

        for (int i = 0; i < 3; i++)
            stepRgb[i] = (endRgb[i] - startRgb[i]) / (mColors.length - 1);

        float[] rgb = new float[3];

        for (int i = 0; i < mColors.length; i++) {
            for (int j = 0; j < 3; j++)
                rgb[j] = (startRgb[j] + stepRgb[j] * i);

            mColors[i] = Colors.fromRgbArray(rgb);
        }
    }

    @Override
    public Color get(double value) {
        if (value < 0.0f || value > 1.0f)
            throw new IllegalArgumentException("Value should be between 0 and 1.");

        int length = mColors.length;
        int index = (int) Math.min(value * (length - 1), length - 1);

        return mColors[index];
    }

    @Override
    public int size() {
        return mColors.length;
    }
}
