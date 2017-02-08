package me.mneri.ca.interpolator;

public enum InterpolatorEnum {
    LINEAR("Linear"),
    ACCELERATE("Accelerate"),
    DECELERATE("Decelerate"),
    ACCELERATE_DECELERATE("Accelerate-Decelerate");

    private String mName;

    InterpolatorEnum(String name) {
        mName = name;
    }

    public static InterpolatorEnum fromString(String inter) {
        switch (inter) {
            case "Linear":
                return LINEAR;
            case "Accelerate":
                return ACCELERATE;
            case "Decelerate":
                return DECELERATE;
            case "Accelerate-Decelerate":
                return ACCELERATE_DECELERATE;
            default:
                return LINEAR;
        }
    }

    public Interpolator toInterpolator() {
        switch (mName) {
            case "Linear":
                return new LinearInterpolator();
            case "Accelerate":
                return new AccelerateInterpolator();
            case "Decelerate":
                return new DecelerateInterpolator();
            case "Accelerate-Decelerate":
                return new AccelerateDecelerateInterpolator();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return mName;
    }
}
