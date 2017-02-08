package me.mneri.ca.interpolator;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum InterpolatorEnum {
    LINEAR("Linear", LinearInterpolator.class),
    ACCELERATE("Accelerate", AccelerateInterpolator.class),
    DECELERATE("Decelerate", DecelerateInterpolator.class),
    ACCELERATE_DECELERATE("Accelerate-Decelerate", AccelerateDecelerateInterpolator.class);

    private static final Map<String, InterpolatorEnum> STRING_ENUM_MAP;

    private String mName;
    private Class<? extends Interpolator> mClass;

    InterpolatorEnum(String name, Class<? extends Interpolator> clazz) {
        mName = name;
        mClass = clazz;
    }

    public static InterpolatorEnum fromString(String name) {
        return STRING_ENUM_MAP.get(name);
    }

    public Interpolator toInterpolator() {
        try {
            return mClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return mName;
    }

    static {
        Map<String, InterpolatorEnum> stringEnumMap = new ConcurrentHashMap<>();

        for (InterpolatorEnum value : InterpolatorEnum.values())
            stringEnumMap.put(value.toString(), value);

        STRING_ENUM_MAP = Collections.unmodifiableMap(stringEnumMap);
    }
}
