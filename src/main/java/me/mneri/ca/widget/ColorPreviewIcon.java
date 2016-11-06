package me.mneri.ca.widget;

import static java.awt.RenderingHints.*;

import javax.swing.*;
import java.awt.*;

public class ColorPreviewIcon implements Icon {
    private static final int ARC_HEIGHT = 8;
    private static final int ARC_WIDTH = 8;
    private static final int ICON_HEIGHT = 16;
    private static final int ICON_WIDTH = 16;

    private Color mColor;
    private final RenderingHints mHints = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
    private final Color mReflectionColor = new Color(1.0f, 1.0f, 1.0f, 0.33f);

    public ColorPreviewIcon(Color color) {
        mColor = color;
    }

    @Override
    public void paintIcon(Component component, Graphics graphics, int x, int y) {
        Graphics2D g = (Graphics2D) graphics;

        // Draw background
        g.setRenderingHints(mHints);
        g.setColor(mColor);
        g.fillRoundRect(x, y, ICON_WIDTH, ICON_HEIGHT, ARC_WIDTH, ARC_HEIGHT);

        // Draw reflection
        g.setColor(mReflectionColor);
        g.fillRoundRect(x + 1, y + 1, ICON_WIDTH - 2, ICON_HEIGHT - 2, ARC_WIDTH, ARC_HEIGHT);

        // Cover part of the reflection
        g.setColor(mColor);
        g.fillRoundRect(x + 1, y + 2, ICON_WIDTH - 2, ICON_HEIGHT - 3, ARC_WIDTH, ARC_HEIGHT);
    }

    @Override
    public int getIconWidth() {
        return ICON_WIDTH;
    }

    @Override
    public int getIconHeight() {
        return ICON_HEIGHT;
    }
}
