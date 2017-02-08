package me.mneri.ca.widget;

import me.mneri.ca.color.Gradient;

import javax.swing.*;
import java.awt.*;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

public class GradientPreview extends JComponent {
    private static final int BORDER_LENGTH = 1;
    private static final int MARGIN_LENGTH = 1;

    private Gradient mGradient;

    @Override
    protected void paintComponent(Graphics g) {
        if (mGradient != null) {
            int canvasWidth = getWidth() - (MARGIN_LENGTH + BORDER_LENGTH) * 2;
            int canvasHeight = getHeight() - (MARGIN_LENGTH + BORDER_LENGTH) * 2;
            int steps = Math.min(canvasWidth, mGradient.size());
            double stepWidth = ((double) canvasWidth) / steps;
            double stepHeight = canvasHeight;

            for (int i = 0; i < steps; i++) {
                int height = (int) stepHeight;
                int width = (int) Math.ceil(stepWidth);
                int x = (int) Math.floor(i * stepWidth) + MARGIN_LENGTH + BORDER_LENGTH;

                g.setColor(mGradient.get(((float) i) / steps));
                g.fillRect(x, MARGIN_LENGTH, width, height);
            }
        }
    }

    public void setGradient(Gradient gradient) {
        mGradient = gradient;
        repaint();
    }
}
