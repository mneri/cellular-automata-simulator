package me.mneri.ca.color;

import java.awt.*;

public interface Gradient {
    Color get(double value);

    int size();
}
