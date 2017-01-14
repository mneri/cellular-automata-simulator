package me.mneri.ca.widget;

import javax.swing.*;
import java.awt.*;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

public class GradientPreview extends JComponent {
    private static final int BORDER_LENGTH = 1;
    private static final int MARGIN_LENGTH = 1;

    private Color[] mGradient;
    private final RenderingHints mHints = new RenderingHints(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHints(mHints);

        if (mGradient != null) {
            double stepWidth = ((double) (getWidth() - (MARGIN_LENGTH + BORDER_LENGTH) * 2)) / mGradient.length;
            double stepHeight = getHeight() - (MARGIN_LENGTH + BORDER_LENGTH) * 2;

            for (int i = 0; i < mGradient.length; i++) {
                int height = (int) stepHeight;
                int width = (int) Math.ceil(stepWidth);
                int x = (int) Math.floor(i * stepWidth) + MARGIN_LENGTH + BORDER_LENGTH;

                g.setColor(mGradient[i]);
                g.fillRect(x, MARGIN_LENGTH, width, height);
            }
        }
    }

    public void setGradient(Color[] gradient) {
        mGradient = gradient;
        repaint();
    }
}
