package me.mneri.ca.interpolator;

public class InterpolatorFactory {
    public String[] enumerate() {
        return new String[] {"linear", "accelerate", "decelerate", "accelerate_decelerate"};
    }

    public Interpolator fromString(String string) {
        if (string.equals("linear"))
            return new LinearInterpolator();
        else if (string.equals("accelerate"))
            return new AccelerateInterpolator();
        else if (string.equals("decelerate"))
            return new DecelerateInterpolator();
        else if (string.equals("accelerate_decelerate"))
            return new AccelerateDecelerateInterpolator();

        return new LinearInterpolator();
    }
}
