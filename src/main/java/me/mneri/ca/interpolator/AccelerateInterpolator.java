package me.mneri.ca.interpolator;

public class AccelerateInterpolator implements Interpolator {
    private float mFactor;
    private float mDoubleFactor;

    public AccelerateInterpolator() {
        this(1.0f);
    }

    public AccelerateInterpolator(float factor) {
        mFactor = factor;
        mDoubleFactor = 2 * factor;
    }

    @Override
    public float get(float input) {
        if (mFactor == 1.0f)
            return input * input;
        else
            return (float) Math.pow(input, mDoubleFactor);
    }

    @Override
    public String toString() {
        return "Accelerate Interpolator";
    }
}
