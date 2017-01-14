package me.mneri.ca.gui;

import me.mneri.ca.app.Application;
import me.mneri.ca.app.Settings;
import me.mneri.ca.interpolator.Interpolator;

import java.awt.*;

public class SettingsModel {
    private Settings mSettings = Application.instance().getSettings();

    public Color getBackgroundColor() {
        return mSettings.getBackgroundColor();
    }

    public Color getCellColorHigh() {
        return mSettings.getCellColorHigh();
    }

    public Color getCellColorLow() {
        return mSettings.getCellColorLow();
    }

    public String getIterations() {
        return mSettings.getIterations();
    }

    public void setBackgroundColor(Color color) {
        mSettings.setBackgroundColor(color);
    }

    public void setCellColorHigh(Color color) {
        mSettings.setCellColorHigh(color);
    }

    public void setCellColorLow(Color color) {
        mSettings.setCellColorLow(color);
    }

    public void setInterpolator(Interpolator interpolator) {

    }

    public void setIterations(String iterations) {
        mSettings.setIterations(iterations);
    }
}
