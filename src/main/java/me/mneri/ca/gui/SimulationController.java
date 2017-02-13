package me.mneri.ca.gui;

import me.mneri.ca.app.Application;
import me.mneri.ca.app.Settings;
import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.automaton.AutomatonState;
import me.mneri.ca.color.HsbGradient;
import me.mneri.ca.diagram.*;
import me.mneri.ca.automaton.ElementaryRule;
import me.mneri.ca.widget.DisplayPanel;

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
        mModel.addListener(this::updateDiagram);
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
            updateDiagram();
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
        mView.getKSpinner().addChangeListener(changeEvent -> {
            updateDiagram();
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

        int rule = (Integer) mView.getRuleSpinner().getValue();
        mModel.setAutomaton(new Automaton(AutomatonState.random(new ElementaryRule(rule), 1024)));

        JComboBox<Diagram.Enum> diagramCombo = mView.getDiagramCombo();
        diagramCombo.setModel(new DefaultComboBoxModel<>(Diagram.Enum.values()));

        updateDiagram();
        updateZoomButtons();
    }

    public void showView() {
        mView.setVisible(true);
    }

    private void updateDiagram() {
        Diagram.Enum type = (Diagram.Enum) mView.getDiagramCombo().getSelectedItem();
        Diagram.Preprocessor preproc;

        switch (type) {
            case CONDITIONAL_ENTROPY:
                preproc = new ConditionalEntropyPreprocessor();
                break;
            case ENTROPY:
                preproc = new EntropyPreprocessor();
                break;
            case ENTROPY_RATE:
                preproc = new EntropyRatePreprocessor((Integer) mView.getKSpinner().getValue());
                break;
            case JOINT_ENTROPY:
                preproc = new JointEntropyPreprocessor();
                break;
            case STATE:
                preproc = new StatePreprocessor();
                break;
            default:
                throw new IllegalArgumentException();
        }

        Diagram diagram = new Diagram.Builder()
                .setAutomaton(mModel.getAutomaton())
                .setGradient(new HsbGradient(Color.GREEN, Color.RED))
                .setPreprocessor(preproc)
                .setSize(1024, 1024)
                .build();

        DisplayPanel simPanel = mView.getDisplayPanel();
        simPanel.setDiagram(diagram);
        simPanel.repaint();

        mView.getKSpinner().setEnabled(type == Diagram.Enum.ENTROPY_RATE);
    }

    private void updateZoomButtons() {
        DisplayPanel simPanel = mView.getDisplayPanel();

        mView.getZoomInButton().setEnabled(simPanel.canZoomIn());
        mView.getZoomOriginalButton().setEnabled(simPanel.canZoomOriginal());
        mView.getZoomOutButton().setEnabled(simPanel.canZoomOut());
    }
}
