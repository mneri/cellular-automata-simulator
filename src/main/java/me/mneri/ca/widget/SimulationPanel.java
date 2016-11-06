package me.mneri.ca.widget;

import static java.awt.RenderingHints.*;

import java.awt.*;
import java.util.Set;

import javax.swing.*;

import me.mneri.ca.drawable.SpaceTimeDiagram;

public class SimulationPanel extends JPanel {
    private static final Color CELL_COLOR = Color.decode("#cdcdcd");
    private static final int CELL_SIZE = 4;
    private static final RenderingHints HINTS = new RenderingHints(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
    private static final double SCALE_FACTOR = 1.2d;

    private Color mBackgroundColor;
    private SpaceTimeDiagram mDiagram;
    private int mScrollX;
    private int mScrollY;
    private double mScale = 1.0d;
    private double mZoomFactor = SCALE_FACTOR;

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHints(HINTS);

        int width = getWidth();
        int height = getHeight();

        int gridLeft = mScrollX / CELL_SIZE;
        int gridWidth = width / CELL_SIZE + 1;
        int gridRight = gridLeft + gridWidth;

        int gridTop = mScrollY / CELL_SIZE;
        int gridHeight = height / CELL_SIZE + 1;
        int gridBottom = gridTop + gridHeight;

        g.setColor(mBackgroundColor);
        g.fillRect(0, 0, width, height);

        g.setColor(CELL_COLOR);

        for (int i = gridTop; i < gridBottom; i++) {
            Set<Integer> state = mDiagram.getState(i);

            for (int j = gridLeft; j < gridRight; j++) {
                if (state.contains(j)) {
                    int x = j * CELL_SIZE - mScrollX;
                    int y = i * CELL_SIZE - mScrollY;

                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public void scroll(int dx, int dy) {
        mScrollX += dx;
        mScrollY += dy;
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
        mScale = scale;
        repaint();
    }

    public void zoomIn() {
        setScale(mScale * mZoomFactor);
    }

    public void zoomOriginal() {
        setScale(1.0d);
    }

    public void zoomOut() {
        setScale(mScale / mZoomFactor);
    }
}
