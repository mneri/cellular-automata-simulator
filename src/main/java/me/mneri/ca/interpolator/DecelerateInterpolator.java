package me.mneri.ca.interpolator;

public class DecelerateInterpolator implements Interpolator {
    private float mFactor;

    public DecelerateInterpolator() {
        this(1.0f);
    }

    public DecelerateInterpolator(float factor) {
        mFactor = factor;
    }

    @Override
    public float interpolation(float input) {
        if (mFactor == 1.0f) {
            return (1.0f - (1.0f - input) * (1.0f - input));
        } else {
            return (1.0f - (float) Math.pow((1.0f - input), 2 * mFactor));
        }
    }
}
