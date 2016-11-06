package me.mneri.ca.gui;

import me.mneri.ca.app.Application;
import me.mneri.ca.app.Settings;

import java.awt.*;

public class SettingsModel {
    private Settings mSettings = Application.instance().getSettings();

    public Color getBackgroundColor() {
        return mSettings.getBackgroundColor();
    }

    public String getIterations() {
        return mSettings.getIterations();
    }

    public void setBackgroundColor(Color color) {
        mSettings.setBackgroundColor(color);
    }

    public void setIterations(String iterations) {
        mSettings.setIterations(iterations);
    }
}
