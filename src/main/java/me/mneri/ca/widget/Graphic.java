package me.mneri.ca.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class Graphic extends JPanel {

    int sizex = 1000;
    int sizey = 680;
    int padding = 50;
    String str = "";
    String srule = "";

    private double[] vett;

    public Graphic(double[] x, String title, int rule) {
        vett = x;
        str = title;
        setPreferredSize(new Dimension(sizex, sizey));
        srule = " " + rule;
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = getFont();
        g.setFont(new Font("default", Font.BOLD, 20));
        g.drawString(str, sizex / 3 - 0, padding / 2);
        g.drawString("RULE: " + srule, sizex / 2 - 0, padding / 2);

        g.setFont(font);

        int y = sizey / 2;
        int index = padding;

        int rowsX = 50;
        int rowsY = 40;
        int nStepY = 20;

        int xStepSize = (sizex - (2 * padding)) / rowsX;
        int yStepSize = (sizey - (2 * padding)) / rowsY;

        // x header
        g.drawString("k", sizex - padding + 20, sizey / 2);
        // x axis
        for (int i = 0; i <= rowsX; i++) {
            g.drawLine(index, y + 2, index, y - 2);
            if (i % 5 == 0 && i != 0) {
                g.drawString("" + i, index - 2, y + padding / 2);
            }
            index += xStepSize;
        }
        // x line
        index -= xStepSize;
        g.drawLine(padding, y, index, y);

        // y header
        g.drawString("Hµ(k)", padding / 2 - 20, padding - 20);
        // y axis
        index = yStepSize;
        g.drawString("0", padding / 2 - 5, y + 5);
        for (int i = 1; i <= rowsY / 2; i++) {
            g.drawLine(padding - 2, y - index, padding + 2, y - index);
            g.drawLine(padding - 2, y + index, padding + 2, y + index);
            if (i % (rowsY / 10) == 0) {
                g.drawString(String.format("%.2f", (double) i / nStepY), padding / 2 - 5, y - index + 5);
                g.drawString(String.format("%.2f", (double) -i / nStepY), padding / 2 - 5, y + index + 5);
            }
            index += yStepSize;
        }
        // y
        index -= yStepSize;
        g.drawLine(padding, y + index, padding, y - index);

        // plot function
        g.setColor(Color.RED);
        index = padding;
        xStepSize = (sizex - (2 * padding)) / rowsX;
        yStepSize = ((sizey - 2 * padding) / rowsY) * nStepY;
        // first
        g.fillOval(index - 1, y - 1 - (int) (vett[0] * yStepSize), 3, 3);
        index += xStepSize;
        for (int i = 1; i <= rowsX; i++) {
            g.fillOval(index - 1, y - 1 - (int) (vett[i] * yStepSize), 3, 3);
            g.drawLine(index - xStepSize, y - (int) (vett[i - 1] * yStepSize), index,
                    y - (int) (vett[i] * yStepSize));

            // gray line for projection of the function point (y -> vett[i])
            // if (i <= 10) {
            // g.setColor(Color.LIGHT_GRAY);
            // g.drawLine(index, y + negated * (int) (vett[i] * yStepSize),
            // padding,
            // y + negated * (int) (vett[i] * yStepSize));
            // // g.drawLine(index, y + negated * (int) (vett[i] * yStepSize),
            // // index, y);
            //
            // g.setColor(Color.RED);
            // }

            index += xStepSize;
        }
    }

}
