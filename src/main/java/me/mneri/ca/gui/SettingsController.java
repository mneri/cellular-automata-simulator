package me.mneri.ca.gui;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class SettingsController {
    private static final String[] ITERATIONS = {"1", "10", "100", "1000", "Continuous"};

    private SettingsModel mModel;
    private Component mParentView;
    private SettingsView mView;

    private SettingsController(SettingsModel model, SettingsView view) {
        mModel = model;
        mView = view;

        init();
        attachModelCallbacks();
        attachViewCallbacks();
    }

    private void attachModelCallbacks() {

    }

    private void attachViewCallbacks() {
        mView.getBackgroundColorField().addColorListener((Color color) -> mModel.setBackgroundColor(color));
        mView.getIterationsComboBox().addActionListener((ActionEvent e) -> {
            String selected = (String) mView.getIterationsComboBox().getSelectedItem();
            mModel.setIterations(selected);
        });
    }

    public static SettingsController createMVC() {
        return createMVC(null);
    }

    public static SettingsController createMVC(Component parent) {
        SettingsModel model = new SettingsModel();
        SettingsView view = new SettingsView();
        SettingsController controller = new SettingsController(model, view);
        controller.mParentView = parent;

        return controller;
    }

    private void init() {
        mView.getBackgroundColorField().setColor(mModel.getBackgroundColor());

        JComboBox<String> iterationsCombo = mView.getIterationsComboBox();
        iterationsCombo.setModel(new DefaultComboBoxModel<>(ITERATIONS));
        iterationsCombo.setSelectedItem(mModel.getIterations());
    }

    public void showView() {
        mView.setLocationRelativeTo(mParentView);
        mView.setVisible(true);
    }
}
