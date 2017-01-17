package me.mneri.ca.interpolator;

public class LinearInterpolator implements Interpolator {
    @Override
    public float get(float input) {
        return input;
    }

    @Override
    public String toString() {
        return "Linear Interpolator";
    }
}
