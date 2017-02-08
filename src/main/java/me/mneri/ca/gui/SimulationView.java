package me.mneri.ca.gui;

import me.mneri.ca.drawable.DiagramEnum;
import me.mneri.ca.util.IconFactory;
import me.mneri.ca.widget.SimulationPanel;

import javax.swing.*;
import java.awt.*;

public class SimulationView extends JFrame {
    private static final int FRAME_HEIGHT = 540;
    private static final int FRAME_WIDTH = 960;

    private JSpinner mKSpinner;
    JComboBox<DiagramEnum> mMeasureCombo;
    private JButton mPlayButton;
    private JSpinner mRuleSpinner;
    private SimulationPanel mSimulationPanel;
    private JButton mSettingsButton;
    private JLabel mStatusLabel;
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

        mSimulationPanel = new SimulationPanel();
        add(mSimulationPanel, BorderLayout.CENTER);

        buildStatusbar();

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    private void buildStatusbar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(panel, BorderLayout.SOUTH);

        mStatusLabel = new JLabel();
        panel.add(mStatusLabel);
    }

    private void buildToolbar() {
        IconFactory icons = IconFactory.instance();

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);

        mPlayButton = new JButton(icons.get("play.png"));
        toolBar.add(mPlayButton);

        toolBar.add(Box.createHorizontalGlue());

        mRuleSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
        toolBar.add(mRuleSpinner);

        toolBar.addSeparator();
        
        mMeasureCombo = new JComboBox<>();
        toolBar.add(mMeasureCombo);

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

    JComboBox<DiagramEnum> getDiagramCombo() {
        return mMeasureCombo;
    }

    JSpinner getKSpinner() {
        return mKSpinner;
    }

    JButton getPlayButton() {
        return mPlayButton;
    }

    JSpinner getRuleSpinner() {
        return mRuleSpinner;
    }

    JButton getSettingsButton() {
        return mSettingsButton;
    }

    SimulationPanel getSimulationPanel() {
        return mSimulationPanel;
    }

    JLabel getStatusLabel() {
        return mStatusLabel;
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
