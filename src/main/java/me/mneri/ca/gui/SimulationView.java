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

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
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

    public JButton getOpenButton() {
        return mOpenButton;
    }

    public JButton getPlayButton() {
        return mPlayButton;
    }

    public JButton getSettingsButton() {
        return mSettingsButton;
    }

    public SimulationPanel getSimulationPanel() {
        return mSimulationPanel;
    }

    public JButton getStepButton() {
        return mStepButton;
    }

    public JButton getZoomInButton() {
        return mZoomInButton;
    }

    public JButton getZoomOriginalButton() {
        return mZoomOriginalButton;
    }

    public JButton getmZoomOutButton() {
        return mZoomOutButton;
    }

    private void init() {
        setTitle("Cellular Automaton Simulator");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
