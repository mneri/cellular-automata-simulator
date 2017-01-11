package me.mneri.ca.gui;

import me.mneri.ca.app.Application;
import me.mneri.ca.app.Settings;
import me.mneri.ca.util.IconFactory;
import me.mneri.ca.widget.SimulationPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationController {
    private int mLastDragX;
    private int mLastDragY;
    private SimulationModel mModel;
    private Component mParentView;
    private SimulationView mView;

    private SimulationController(SimulationModel model, SimulationView view) {
        mModel = model;
        mView = view;

        init();
        attachSettingsCallbacks();
        attachModelCallbacks();
        attachViewCallbacks();
    }

    private void attachModelCallbacks() {
        mModel.addModelListener(() -> mView.getSimulationPanel().repaint());
    }

    private void attachSettingsCallbacks() {
        Settings settings = Application.instance().getSettings();

        settings.addSettingsListener(() -> {
            mView.getSimulationPanel().setBackgroundColor(settings.getBackgroundColor());
        });
    }

    private void attachViewCallbacks() {
        mView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent componentEvent) {
                Application.instance().getSettings().setLocation(mView.getX(), mView.getY());
            }
        });
        mView.getPlayButton().addActionListener((ActionEvent e) -> Application.invokeLater(() -> {
            JButton playButton = mView.getPlayButton();
            IconFactory icons = IconFactory.instance();

            if (mModel.toggle())
                playButton.setIcon(icons.get("pause.png"));
            else
                playButton.setIcon(icons.get("play.png"));
        }));
        mView.getStepButton().addActionListener((ActionEvent e) -> Application.invokeLater(() -> mModel.step()));
        mView.getOpenButton().addActionListener((ActionEvent e) -> {

        });
        mView.getSettingsButton().addActionListener((ActionEvent e) -> {
            SettingsController controller = SettingsController.createMVC(mView);
            controller.showView();
        });
        mView.getSimulationPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mView.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                mLastDragX = e.getX();
                mLastDragY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        mView.getSimulationPanel().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                mView.getSimulationPanel().scroll(mLastDragX - x, mLastDragY - y);
                mLastDragX = x;
                mLastDragY = y;
            }
        });
        mView.getZoomInButton().addActionListener((ActionEvent e) -> mView.getSimulationPanel().zoomIn());
        mView.getZoomOriginalButton().addActionListener((ActionEvent e) -> mView.getSimulationPanel().zoomOriginal());
        mView.getmZoomOutButton().addActionListener((ActionEvent e) -> mView.getSimulationPanel().zoomOut());
    }

    public static SimulationController createMVC() {
        return createMVC(null);
    }

    public static SimulationController createMVC(Component parent) {
        SimulationModel model = new SimulationModel();
        SimulationView view =  new SimulationView();
        SimulationController controller = new SimulationController(model, view);
        controller.mParentView = parent;

        return controller;
    }

    private void init() {
        Settings settings = Application.instance().getSettings();

        Point point = settings.getLocation();

        if (point != null)
            mView.setLocation(point.x, point.y);
        else
            mView.setLocationRelativeTo(mParentView);

        SimulationPanel simPanel = mView.getSimulationPanel();
        simPanel.setBackgroundColor(settings.getBackgroundColor());
        simPanel.setDiagram(mModel.getDiagram());

        JLabel statusLabel = mView.getStatusLabel();
        statusLabel.setText(" ");
    }

    public void showView() {
        mView.setVisible(true);
    }
}
