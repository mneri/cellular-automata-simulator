package me.mneri.ca.gui;

import me.mneri.ca.app.Application;
import me.mneri.ca.app.Settings;
import me.mneri.ca.diagram.Diagram;
import me.mneri.ca.diagram.DiagramEnum;
import me.mneri.ca.util.IconFactory;
import me.mneri.ca.widget.SimulationPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
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
        mModel.addListener(() -> {
            Diagram diagram = ((DiagramEnum) mView.getDiagramCombo().getSelectedItem()).toDiagram(mModel.getHistory());
            SimulationPanel simPanel = mView.getSimulationPanel();
            simPanel.setDiagram(diagram);
            simPanel.repaint();
        });
    }

    private void attachSettingsCallbacks() {
        Settings settings = Application.instance().getSettings();
    }

    private void attachViewCallbacks() {
        mView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent componentEvent) {
                Application.instance().getSettings().setLocation(mView.getX(), mView.getY());
            }
        });
        mView.getDiagramCombo().addActionListener((ActionEvent e) -> {
            Diagram diagram = ((DiagramEnum) mView.getDiagramCombo().getSelectedItem()).toDiagram(mModel.getHistory());
            SimulationPanel simPanel = mView.getSimulationPanel();
            simPanel.setDiagram(diagram);
            simPanel.repaint();
        });
        mView.getRuleSpinner().addChangeListener((ChangeEvent e) -> {

        });
        mView.getPlayButton().addActionListener((ActionEvent e) -> Application.invokeLater(() -> {
            JButton playButton = mView.getPlayButton();
            IconFactory icons = IconFactory.instance();

            mModel.tick(1000);
        }));
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
        mView.getZoomInButton().addActionListener((ActionEvent e) -> {
            mView.getSimulationPanel().zoomIn();
            updateZoomButtons();
        });
        mView.getZoomOriginalButton().addActionListener((ActionEvent e) -> {
            mView.getSimulationPanel().zoomOriginal();
            updateZoomButtons();
        });
        mView.getZoomOutButton().addActionListener((ActionEvent e) -> {
            mView.getSimulationPanel().zoomOut();
            updateZoomButtons();
        });
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

        JComboBox<DiagramEnum> measureCombo = mView.getDiagramCombo();
        measureCombo.setModel(new DefaultComboBoxModel<>(DiagramEnum.values()));

        updateZoomButtons();
    }

    public void showView() {
        mView.setVisible(true);
    }

    private void updateZoomButtons() {
        SimulationPanel simPanel = mView.getSimulationPanel();

        mView.getZoomInButton().setEnabled(simPanel.canZoomIn());
        mView.getZoomOriginalButton().setEnabled(simPanel.canZoomOriginal());
        mView.getZoomOutButton().setEnabled(simPanel.canZoomOut());
    }
}
