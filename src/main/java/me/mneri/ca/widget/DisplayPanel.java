package me.mneri.ca.widget;

import me.mneri.ca.diagram.Diagram;

import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel {
    private static final float[] ZOOM_LEVELS = {1.0f, 2.0f, 3.0f, 4.0f, 6.0f, 8.0f, 10.0f};

    private Diagram mDiagram;
    private int mLastScrollX;
    private int mLastScrollY;
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
        if (mDiagram == null)
            return;

        Graphics2D g = (Graphics2D) graphics;
        mDiagram.paint(g, 0, 0, getWidth(), getHeight());
    }

    public void scroll(int dx, int dy) {
        if (mDiagram == null)
            return;

        mDiagram.scroll(dx, dy);
        mLastScrollX = mScrollX;
        mLastScrollY = mScrollY;
        mScrollX = mDiagram.getScrollX();
        mScrollY = mDiagram.getScrollY();

        repaint();
    }

    public void setDiagram(Diagram diagram) {
        mDiagram = diagram;
        mDiagram.setScale(ZOOM_LEVELS[mZoomIndex]);
        mDiagram.setScroll(mScrollX, mScrollY);
    }

    private void setScale(float scale) {
        if (mDiagram == null)
            return;

        mDiagram.setScale(scale);
        repaint();
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
