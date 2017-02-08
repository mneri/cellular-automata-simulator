package me.mneri.ca.diagram;

import java.awt.*;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.color.Gradient;
import me.mneri.ca.color.HsbGradient;
import me.mneri.ca.drawable.Drawable;
import me.mneri.ca.interpolator.LinearInterpolator;

public abstract class Diagram implements Drawable {
    private static final int CELL_HEIGHT = 1;
    private static final int CELL_WIDTH = 1;

    private Gradient mGradient;
    private double[][] mData;
    private float mScale = 1.0f;
    private int mScrollX;
    private int mScrollY;

    public Diagram(Automaton history) {
        this(history, new HsbGradient(Color.GREEN, Color.RED, new LinearInterpolator(), 4));
    }

    public Diagram(Automaton history, Gradient gradient) {
        mData = prepare(history);
        mGradient = gradient;
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
                    g.setColor(mGradient.get(Math.min(1.0, mData[i][j]))); // TODO: data can be higher than 1
                else
                    g.setColor(mGradient.get(0.0));

                g.fillRect(j - gridLeft, i - gridTop, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    protected abstract double[][] prepare(Automaton history);

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
