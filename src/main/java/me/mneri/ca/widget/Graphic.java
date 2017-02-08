package me.mneri.ca.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JToolTip;

public class Graphic extends JPanel {

    int sizex = 900;
    int sizey = 680;
    int padding = 50;
    String str = "";
    int negated = -1;

    private double[] vett;

    public Graphic(double[] x, boolean negate, String title) {
        vett = x;
        str = title;
        setPreferredSize(new Dimension(sizex, sizey));
        negated = negate ? -1 : 1;
    }

    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int rows = vett.length;

        g.drawString(str, (rows - padding) / 2, padding / 2);

        int index = padding;

        int y = sizey / 2;

        int nsegx = 20;
        int nsegy = 40;
        int nstepy = 100;

        int xStepSize = (sizex - (2 * padding)) / nsegx;
        int yStepSize = (sizey - (2 * padding)) / nsegy;

        // x header
        g.drawString("k", sizex - padding + 20, sizey / 2);
        // x axis
        for (int i = 0; i <= nsegx; i++) {
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
        for (int i = 1; i <= nsegy / 2; i++) {
            g.drawLine(padding - 2, y - index, padding + 2, y - index);
            g.drawLine(padding - 2, y + index, padding + 2, y + index);
            if (i % (nsegy / 10) == 0) {
                g.drawString("" + (double) i / nstepy, padding / 2 - 5, y - index + 5);
                g.drawString("" + (double) -i / nstepy, padding / 2 - 5, y + index + 5);
            }
            index += yStepSize;
        }
        // y
        index -= yStepSize;
        g.drawLine(padding, y + index, padding, y - index);

        // plot function
        g.setColor(Color.RED);
        index = padding;
        xStepSize = (sizex - (2 * padding)) / nsegx;
        yStepSize = ((sizey - 2 * padding) / nsegy) * nstepy;
        // first
        g.fillOval(index - 1, y - 1 + negated * (int) (vett[0] * yStepSize), 3, 3);
        index += xStepSize;
        for (int i = 1; i <= nsegx; i++) {
            g.fillOval(index - 1, y - 1 + negated * (int) (vett[i] * yStepSize), 3, 3);
            g.drawLine(index - xStepSize, y + negated * (int) (vett[i - 1] * yStepSize), index,
                    y + negated * (int) (vett[i] * yStepSize));

            if (i <= 10) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(index, y + negated * (int) (vett[i] * yStepSize), padding,
                        y + negated * (int) (vett[i] * yStepSize));
                // g.drawLine(index, y + negated * (int) (vett[i] * yStepSize),
                // index, y);

                g.setColor(Color.RED);
            }

            index += xStepSize;
        }
    }

}
