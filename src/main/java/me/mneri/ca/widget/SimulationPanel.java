package me.mneri.ca.widget;

import me.mneri.ca.drawable.SpaceTimeDiagram;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;

public class SimulationPanel extends JPanel {
    private static final Color CELL_COLOR = Color.decode("#cdcdcd");
    private static final RenderingHints HINTS = new RenderingHints(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
    private static final double SCALE_FACTOR = 1.2d;
    private static final double SCALE_DEFAULT = 4.0d;
    private static final double SCALE_MIN = 1.0d;
    private static final double SCALE_MAX = 8.0d;

    private Color mBackgroundColor;
    private SpaceTimeDiagram mDiagram;
    private double mScrollX;
    private double mScrollY;
    private double mScale = SCALE_DEFAULT;
    private double mZoomFactor = SCALE_FACTOR;

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.scale(mScale, mScale);
        g.setRenderingHints(HINTS);

        int width = getWidth();
        int height = getHeight();

        int gridLeft = (int) mScrollX;
        int gridWidth = (int) Math.ceil(width / mScale) + 1;
        int gridRight = gridLeft + gridWidth;

        int gridTop = (int) mScrollY;
        int gridHeight = (int) Math.ceil(height / mScale) + 1;
        int gridBottom = gridTop + gridHeight;

        g.setColor(mBackgroundColor);
        g.fillRect(0, 0, width, height);

        g.setColor(CELL_COLOR);

        for (int i = gridTop; i < gridBottom; i++) {
            Set<Integer> state = mDiagram.getState(i);

            for (int j = gridLeft; j < gridRight; j++) {
                if (state.contains(j)) {
                    int x = (int) (j - mScrollX);
                    int y = (int) (i - mScrollY);

                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }

    public void scroll(int dx, int dy) {
        mScrollX += dx / mScale;
        mScrollY += dy / mScale;
        repaint();
    }

    public void setBackgroundColor(Color color) {
        mBackgroundColor = color;
        repaint();
    }

    public void setDiagram(SpaceTimeDiagram diagram) {
        mDiagram = diagram;
    }

    private void setScale(double scale) {
        if (scale < SCALE_MIN || scale > SCALE_MAX)
            return;

        int width = getWidth();
        int height = getHeight();

        int gridWidthOld = (int) Math.ceil(width / mScale) + 1;
        int gridWidth = (int) Math.ceil(width / scale) + 1;

        int gridHeightOld = (int) Math.ceil(height / mScale) + 1;
        int gridHeight = (int) Math.ceil(height / scale) + 1;

        mScale = scale;
        mScrollX -= (gridWidth - gridWidthOld) / 2;
        mScrollY -= (gridHeight - gridHeightOld) / 2;
        repaint();
    }

    public void zoomIn() {
        setScale(mScale * mZoomFactor);
    }

    public void zoomOriginal() {
        setScale(SCALE_DEFAULT);
    }

    public void zoomOut() {
        setScale(mScale / mZoomFactor);
    }
}
