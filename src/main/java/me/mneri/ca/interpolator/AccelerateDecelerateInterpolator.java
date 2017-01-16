package me.mneri.ca.interpolator;

public class AccelerateDecelerateInterpolator implements Interpolator {
    @Override
    public float get(float input) {
        return (float) (Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
    }
}
