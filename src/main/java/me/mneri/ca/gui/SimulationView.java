package me.mneri.ca.gui;

import me.mneri.ca.util.IconFactory;
import me.mneri.ca.widget.SimulationPanel;

import javax.swing.*;
import java.awt.*;

public class SimulationView extends JFrame {
    private static final int FRAME_HEIGHT = 540;
    private static final int FRAME_WIDTH = 960;

    private JButton mOpenButton;
    private JButton mPlayButton;
    private SimulationPanel mSimulationPanel;
    private JButton mSettingsButton;
    private JLabel mStatusLabel;
    private JButton mStepButton;
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

        mOpenButton = new JButton(icons.get("open.png"));
        toolBar.add(mOpenButton);

        toolBar.addSeparator();

        mStepButton = new JButton(icons.get("iteration.png"));
        toolBar.add(mStepButton);

        mPlayButton = new JButton(icons.get("play.png"));
        toolBar.add(mPlayButton);

        toolBar.add(Box.createHorizontalGlue());

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

    JButton getOpenButton() {
        return mOpenButton;
    }

    JButton getPlayButton() {
        return mPlayButton;
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

    JButton getStepButton() {
        return mStepButton;
    }

    JButton getZoomInButton() {
        return mZoomInButton;
    }

    JButton getZoomOriginalButton() {
        return mZoomOriginalButton;
    }

    JButton getmZoomOutButton() {
        return mZoomOutButton;
    }

    private void init() {
        setTitle("Cellular Automaton Simulator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
