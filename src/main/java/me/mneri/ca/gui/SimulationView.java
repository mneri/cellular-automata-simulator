package me.mneri.ca.gui;

import me.mneri.ca.diagram.Diagram;
import me.mneri.ca.util.IconFactory;
import me.mneri.ca.widget.DisplayPanel;

import javax.swing.*;
import java.awt.*;

public class SimulationView extends JFrame {
    private static final int FRAME_HEIGHT = 540;
    private static final int FRAME_WIDTH = 960;
    private static final int SPACE_LARGE = 8;
    private static final int SPACE_SMALL = 4;

    private JSpinner mKSpinner;
    private JComboBox<Diagram.Enum> mMeasureCombo;
    private JSpinner mRuleSpinner;
    private DisplayPanel mDisplayPanel;
    private JButton mSettingsButton;
    private JButton mZoomInButton;
    private JButton mZoomOriginalButton;
    private JButton mZoomOutButton;

    public SimulationView() {
        init();
        buildLayout();
    }

    private void buildLayout() {
        setLayout(new BorderLayout());

        buildToolbar();

        mDisplayPanel = new DisplayPanel();
        add(mDisplayPanel, BorderLayout.CENTER);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    private void buildToolbar() {
        IconFactory icons = IconFactory.instance();

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);

        toolBar.add(Box.createHorizontalGlue());

        toolBar.add(new JLabel("Rule:"));
        toolBar.add(Box.createHorizontalStrut(SPACE_SMALL));
        mRuleSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
        toolBar.add(mRuleSpinner);

        toolBar.addSeparator();

        toolBar.add(new JLabel("Diagram:"));
        toolBar.add(Box.createHorizontalStrut(SPACE_SMALL));
        mMeasureCombo = new JComboBox<>();
        toolBar.add(mMeasureCombo);

        toolBar.add(Box.createHorizontalStrut(SPACE_LARGE));
        toolBar.add(new JLabel("k:"));
        toolBar.add(Box.createHorizontalStrut(SPACE_SMALL));
        mKSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
        toolBar.add(mKSpinner);

        toolBar.addSeparator();

        mZoomInButton = new JButton(icons.get("zoom-in.png"));
        toolBar.add(mZoomInButton);

        mZoomOriginalButton = new JButton(icons.get("zoom-original.png"));
        toolBar.add(mZoomOriginalButton);

        mZoomOutButton = new JButton(icons.get("zoom-out.png"));
        toolBar.add(mZoomOutButton);

        toolBar.addSeparator();

        mSettingsButton = new JButton(icons.get("settings.png"));
        toolBar.add(mSettingsButton);
    }

    JComboBox<Diagram.Enum> getDiagramCombo() {
        return mMeasureCombo;
    }

    JSpinner getKSpinner() {
        return mKSpinner;
    }

    JSpinner getRuleSpinner() {
        return mRuleSpinner;
    }

    JButton getSettingsButton() {
        return mSettingsButton;
    }

    DisplayPanel getDisplayPanel() {
        return mDisplayPanel;
    }

    JButton getZoomInButton() {
        return mZoomInButton;
    }

    JButton getZoomOriginalButton() {
        return mZoomOriginalButton;
    }

    JButton getZoomOutButton() {
        return mZoomOutButton;
    }

    private void init() {
        setTitle("Cellular Automata Simulator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
