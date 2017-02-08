package me.mneri.ca.widget;

import javax.swing.*;
import java.awt.*;

import me.mneri.ca.diagram.Diagram;

public class SimulationPanel extends JPanel {
    private static final float[] ZOOM_LEVELS = {1.0f, 2.0f, 3.0f, 4.0f, 6.0f, 8.0f, 10.0f};

    private Diagram mDiagram;
    private int mScrollX;
    private int mScrollY;
    private int mZoomIndex;

    public boolean canZoomIn() {
        return mZoomIndex < ZOOM_LEVELS.length - 1;
    }

    public boolean canZoomOriginal() {
        return mZoomIndex != 0;
    }

    public boolean canZoomOut() {
        return mZoomIndex > 0;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        if (mDiagram != null)
            mDiagram.paint(g, getWidth(), getHeight());
    }

    public void scroll(int dx, int dy) {
        if (mDiagram != null) {
            mDiagram.scroll(dx, dy);
            mScrollX = mDiagram.getScrollX();
            mScrollY = mDiagram.getScrollY();
            repaint();
        }
    }

    public void setDiagram(Diagram diagram) {
        mDiagram = diagram;
        mDiagram.setScale(ZOOM_LEVELS[mZoomIndex]);
        mDiagram.setScroll(mScrollX, mScrollY);
    }

    private void setScale(float scale) {
        if (mDiagram != null) {
            mDiagram.setScale(scale);
            repaint();
        }
    }

    public void zoomIn() {
        if (canZoomIn())
            setScale(ZOOM_LEVELS[++mZoomIndex]);
    }

    public void zoomOriginal() {
        if (canZoomOriginal()) {
            mZoomIndex = 0;
            setScale(ZOOM_LEVELS[mZoomIndex]);
        }
    }

    public void zoomOut() {
        if (canZoomOut()) {
            setScale(ZOOM_LEVELS[--mZoomIndex]);
        }
    }
}
