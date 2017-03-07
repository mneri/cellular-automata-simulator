package me.mneri.ca.diagram;

import java.awt.*;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.color.Gradient;
import me.mneri.ca.draw.Paintable;

public class Diagram implements Paintable {
    private static final int CELL_HEIGHT = 1;
    private static final int CELL_WIDTH = 1;

    private double[][] mData;
    private Gradient mGradient;
    private float mGradientScale = 1.0f;
    private float mScale = 1.0f;
    private int mScrollX;
    private int mScrollY;

    public static class Builder {
        Automaton automaton;
        Gradient gradient;
        int height;
        Preprocessor preprocessor;
        float scale = 1.0f;
        int scrollX;
        int scrollY;
        int width;

        public Diagram build() {
            return new Diagram(this);
        }

        public Builder setAutomaton(Automaton automaton) {
            this.automaton = automaton;
            return this;
        }

        public Builder setGradient(Gradient gradient) {
            this.gradient = gradient;
            return this;
        }

        public Builder setPreprocessor(Preprocessor preprocessor) {
            this.preprocessor = preprocessor;
            return this;
        }

        public Builder setScale(float scale) {
            this.scale = scale;
            return this;
        }

        public Builder setScroll(int scrollX, int scrollY) {
            this.scrollX = scrollX;
            this.scrollY = scrollY;
            return this;
        }

        public Builder setSize(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }
    }

    public enum Enum {
        STATE("State"),
        ENTROPY("Entropy"),
        JOINT_ENTROPY("Joint Entropy"),
        CONDITIONAL_ENTROPY("Conditional Entropy"),
        ENTROPY_RATE("Block entropy");

        private static final Map<String, Enum> STRING_ENUM_MAP;

        private String mName;

        Enum(String name) {
            mName = name;
        }

        public Enum get(String name) {
            return STRING_ENUM_MAP.get(name);
        }

        @Override
        public String toString() {
            return mName;
        }

        static {
            Map<String, Enum> map = new ConcurrentHashMap<>();

            for (Enum value : Enum.values())
                map.put(value.toString(), value);

            STRING_ENUM_MAP = Collections.unmodifiableMap(map);
        }
    }

    public interface Preprocessor {
        double[][] exec(Automaton automaton, int width, int height);
    }

    private Diagram(Builder builder) {
        mData = builder.preprocessor.exec(builder.automaton, builder.width, builder.height);
        mGradient = builder.gradient;
        mScale = builder.scale;
        mScrollX = builder.scrollX;
        mScrollY = builder.scrollY;
    }

    public int getScrollX() {
        return mScrollX;
    }

    public int getScrollY() {
        return mScrollY;
    }

    public void paint(Graphics2D g, int x, int y, int canvasWidth, int canvasHeight) {
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
                if (i > 0 && j > 0 && i < dataHeight && j < dataWidth) {
                    double value = Math.min(1.0, mData[i][j] * mGradientScale);
                    g.setColor(mGradient.get(value));
                } else {
                    g.setColor(mGradient.get(0.0));
                }

                g.fillRect(j - gridLeft, i - gridTop, CELL_WIDTH, CELL_HEIGHT);
            }
        }
    }

    public void scroll(int dx, int dy) {
        setScroll(mScrollX + dx, mScrollY + dy);
    }

    public void setGradientScale(float scale) {
        mGradientScale = scale;
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public void setScroll(int scrollX, int scrollY) {
        mScrollX = scrollX;
        mScrollY = scrollY;
    }
}
