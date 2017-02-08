package me.mneri.ca.gui;

import static me.mneri.ca.interpolator.InterpolatorEnum.*;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import me.mneri.ca.interpolator.Interpolator;
import me.mneri.ca.interpolator.InterpolatorEnum;
import me.mneri.ca.util.Colors;

public class SettingsController {
    private static final InterpolatorEnum[] INTERPOLATORS = {LINEAR, ACCELERATE, DECELERATE, ACCELERATE_DECELERATE};
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
        mModel.addListener(() -> {
            updateGradientPreview();
        });
    }

    private void attachViewCallbacks() {
        mView.getBackgroundColorField().addColorListener((Color color) -> mModel.setBackgroundColor(color));
        mView.getCellColorHighField().addColorListener((Color color) -> mModel.setCellColorHigh(color));
        mView.getCellColorLowField().addColorListener((Color color) -> mModel.setCellColorLow(color));
        mView.getInterpolatorComboBox().addActionListener((ActionEvent e) -> {
            InterpolatorEnum selected = (InterpolatorEnum) mView.getInterpolatorComboBox().getSelectedItem();
            mModel.setInterpolator(selected);
        });
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
        mView.getCellColorHighField().setColor(mModel.getCellColorHigh());
        mView.getCellColorLowField().setColor(mModel.getCellColorLow());
        JComboBox<InterpolatorEnum> interpolatorCombo = mView.getInterpolatorComboBox();
        interpolatorCombo.setModel(new DefaultComboBoxModel<>(INTERPOLATORS));
        interpolatorCombo.setSelectedItem(mModel.getInterpolator());
        updateGradientPreview();

        JComboBox<String> iterationsCombo = mView.getIterationsComboBox();
        iterationsCombo.setModel(new DefaultComboBoxModel<>(ITERATIONS));
        iterationsCombo.setSelectedItem(mModel.getIterations());
    }

    public void showView() {
        mView.setLocationRelativeTo(mParentView);
        mView.setVisible(true);
    }

    private void updateGradientPreview() {
        Color start = mModel.getCellColorHigh();
        Color end = mModel.getCellColorLow();
        Interpolator inter = mModel.getInterpolator().toInterpolator();
        mView.getGradientPreview().setGradient(Colors.createHsbGradient(start, end, inter, 20));
    }
}
