package me.mneri.ca.widget;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class IconTextField extends JTextField {
    private static final int ICON_SPACING = 4;

    private Border mBorder;
    private Icon mIcon;
    private int mIconSpacing = ICON_SPACING;

    public IconTextField() {
        super();
    }

    public IconTextField(int cols) {
        super(cols);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (mIcon != null) {
            Insets iconInsets = mBorder.getBorderInsets(this);
            mIcon.paintIcon(this, graphics, iconInsets.left, iconInsets.top);
        }
    }

    private void resetBorder() {
        setBorder(mBorder);
    }

    public void setIcon(Icon icon) {
        mIcon = icon;
        resetBorder();
    }

    public void setIconSpacing(int spacing) {
        mIconSpacing = spacing;
        resetBorder();
    }

    @Override
    public void setBorder(Border border) {
        mBorder = border;

        if (mIcon == null) {
            super.setBorder(mBorder);
        } else {
            Border margin = BorderFactory.createEmptyBorder(0, mIcon.getIconWidth() + mIconSpacing, 0, 0);
            Border compound = BorderFactory.createCompoundBorder(border, margin);
            super.setBorder(compound);
        }
    }
}
