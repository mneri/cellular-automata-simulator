package me.mneri.ca.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import me.mneri.ca.app.Application;
import me.mneri.ca.app.Settings;
import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.automaton.AutomatonState;
import me.mneri.ca.diagram.Diagram;
import me.mneri.ca.diagram.DiagramEnum;
import me.mneri.ca.rule.ElementaryRule;
import me.mneri.ca.widget.DisplayPanel;

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
            Diagram diagram = ((DiagramEnum) mView.getDiagramCombo().getSelectedItem()).toDiagram(mModel.getAutomaton());
            DisplayPanel simPanel = mView.getDisplayPanel();
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
            Diagram diagram = ((DiagramEnum) mView.getDiagramCombo().getSelectedItem()).toDiagram(mModel.getAutomaton());
            DisplayPanel simPanel = mView.getDisplayPanel();
            simPanel.setDiagram(diagram);
            simPanel.repaint();
        });
        mView.getRuleSpinner().addChangeListener(changeEvent -> {
            int rule = (int) mView.getRuleSpinner().getValue();
            Automaton automaton = new Automaton(AutomatonState.random(new ElementaryRule(rule), 1024));

            new Thread(() -> {
                automaton.tick(1024);
                SwingUtilities.invokeLater(() -> mModel.setAutomaton(automaton));
            }).start();
        });
        mView.getSettingsButton().addActionListener((ActionEvent e) -> {
            SettingsController controller = SettingsController.createMVC(mView);
            controller.showView();
        });
        mView.getDisplayPanel().addMouseListener(new MouseAdapter() {
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
        mView.getDisplayPanel().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                mView.getDisplayPanel().scroll(mLastDragX - x, mLastDragY - y);
                mLastDragX = x;
                mLastDragY = y;
            }
        });
        mView.getZoomInButton().addActionListener((ActionEvent e) -> {
            mView.getDisplayPanel().zoomIn();
            updateZoomButtons();
        });
        mView.getZoomOriginalButton().addActionListener((ActionEvent e) -> {
            mView.getDisplayPanel().zoomOriginal();
            updateZoomButtons();
        });
        mView.getZoomOutButton().addActionListener((ActionEvent e) -> {
            mView.getDisplayPanel().zoomOut();
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
        DisplayPanel simPanel = mView.getDisplayPanel();

        mView.getZoomInButton().setEnabled(simPanel.canZoomIn());
        mView.getZoomOriginalButton().setEnabled(simPanel.canZoomOriginal());
        mView.getZoomOutButton().setEnabled(simPanel.canZoomOut());
    }
}
