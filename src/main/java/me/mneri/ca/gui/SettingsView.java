package me.mneri.ca.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import me.mneri.ca.interpolator.InterpolatorEnum;
import me.mneri.ca.widget.ColorPreviewTextField;
import me.mneri.ca.widget.GradientPreview;

public class SettingsView extends JFrame {
    private static final int FRAME_WIDTH = 340;
    private static final int PADDING = 4;

    private ColorPreviewTextField mColorHighField;
    private ColorPreviewTextField mColorLowField;;
    private GradientPreview mGradientPreview;
    private JComboBox<InterpolatorEnum> mInterpolatorComboBox;
    private JComboBox<String> mIterationsComboBox;

    public SettingsView() {
        init();
        buildLayout();
    }

    private void buildLayout() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setContentPane(panel);

        add(Box.createRigidArea(new Dimension(FRAME_WIDTH, 0)));

        JComponent interfacePanel = createInterfacePanel();
        add(interfacePanel);

        JComponent behaviorPanel = createBehaviorPanel();
        add(behaviorPanel);

        pack();
    }

    private JComponent createBehaviorPanel() {
        JComponent panel = createFramedPanel("Behavior");
        panel.setLayout(new GridLayout(0, 2));

        JLabel iterationsLabel = new JLabel("Iterations");
        mIterationsComboBox = new JComboBox<>();
        iterationsLabel.setLabelFor(mIterationsComboBox);
        panel.add(iterationsLabel);
        panel.add(mIterationsComboBox);

        return panel;
    }

    private JComponent createFramedPanel(String title) {
        JPanel panel = new JPanel();

        Border outer = BorderFactory.createTitledBorder(title);
        Border inner = BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING);
        panel.setBorder(BorderFactory.createCompoundBorder(outer, inner));

        return panel;
    }

    private JComponent createInterfacePanel() {
        JComponent panel = createFramedPanel("Interface");
        panel.setLayout(new GridLayout(0, 2));

        JLabel highColorLabel = new JLabel("Cell Color High");
        mColorHighField = new ColorPreviewTextField();
        panel.add(highColorLabel);
        panel.add(mColorHighField);

        JLabel lowColorLabel = new JLabel("Cell Color Low");
        mColorLowField = new ColorPreviewTextField();
        panel.add(lowColorLabel);
        panel.add(mColorLowField);

        JLabel interpolatorLabel = new JLabel("Cell Color Interpolator");
        mInterpolatorComboBox = new JComboBox<>();
        panel.add(interpolatorLabel);
        panel.add(mInterpolatorComboBox);

        JLabel gradientLabel = new JLabel("Cell Gradient Preview");
        mGradientPreview = new GradientPreview();
        panel.add(gradientLabel);
        panel.add(mGradientPreview);

        return panel;
    }

    ColorPreviewTextField getCellColorHighField() {
        return mColorHighField;
    }

    ColorPreviewTextField getCellColorLowField() {
        return mColorLowField;
    }

    GradientPreview getGradientPreview() {
        return mGradientPreview;
    }

    JComboBox<InterpolatorEnum> getInterpolatorComboBox() {
        return mInterpolatorComboBox;
    }

    JComboBox<String> getIterationsComboBox() {
        return mIterationsComboBox;
    }

    private void init() {
        setTitle("Settings");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }
}
