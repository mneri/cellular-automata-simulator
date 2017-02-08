package me.mneri.ca.drawable;

import java.awt.*;

import me.mneri.ca.automaton.AutomatonHistory;
import me.mneri.ca.interpolator.AccelerateInterpolator;
import me.mneri.ca.interpolator.LinearInterpolator;
import me.mneri.ca.util.Colors;

public abstract class Diagram implements Drawable {
    private static final int CELL_HEIGHT = 1;
    private static final int CELL_WIDTH = 1;

    private Color[] mGradient;
    private double[][] mData;
    private float mScale = 1.0f;
    private int mScrollX;
    private int mScrollY;

    public Diagram(AutomatonHistory history) {
        this(history, Colors.createHsbGradient(Color.decode("#ff6d00"), Color.decode("#64dd17"), new LinearInterpolator(), 4));
    }

    public Diagram(AutomatonHistory history, Color[] gradient) {
        mData = prepare(history);
        mGradient = gradient;
    }

    protected Color colorOf(double value) {
        int length = mGradient.length;
        int index = (int) Math.min(value * (length - 1), length - 1);
        return mGradient[index];
    }

    public int getScrollX() {
        return mScrollX;
    }

    public int getScrollY() {
        return mScrollY;
    }

    public void paint(Graphics2D g, int canvasWidth, int canvasHeight) {
        g.scale(mScale, mScale);

        canvasWidth = (int) Math.ceil(canvasWidth / mScale);  // canvasWidth = canvasWidth * (1 / mScale)
        canvasHeight = (int) Math.ceil(canvasHeight / mScale); // canvasHeight = canvasHeight * (1 / mScale)

        int gridLeft = (int) Math.floor(mScrollX / mScale);
        int gridRight = gridLeft + canvasWidth;
        int gridTop = (int) Math.floor(mScrollY / mScale);
        int gridBottom = gridTop + canvasHeight;

        int dataHeight = mData.length;
        int dataWidth = mData[0].length;

        for (int i = gridTop; i < gridBottom; i++) {
            for (int j = gridLeft; j < gridRight; j++) {
                if (i > 0 && j > 0 && i < dataHeight && j < dataWidth)
                    g.setColor(colorOf(mData[i][j]));
                else
                    g.setColor(colorOf(0.0));

                g.fillRect(j - gridLeft, i - gridTop, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    protected abstract double[][] prepare(AutomatonHistory history);

    public void scroll(int dx, int dy) {
        setScroll(mScrollX + dx, mScrollY + dy);
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public void setScroll(int scrollX, int scrollY) {
        mScrollX = scrollX;
        mScrollY = scrollY;
    }
}
