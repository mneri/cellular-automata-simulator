package me.mneri.ca.gui;

import java.awt.*;
import java.util.ArrayList;

import me.mneri.ca.app.Application;
import me.mneri.ca.app.Settings;
import me.mneri.ca.interpolator.InterpolatorEnum;

public class SettingsModel {
    public interface Listener {
        void onUpdate();
    }

    private Settings mSettings = Application.instance().getSettings();
    private ArrayList<Listener> mListeners = new ArrayList<>();

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    public Color getBackgroundColor() {
        return mSettings.getBackgroundColor();
    }

    public Color getCellColorHigh() {
        return mSettings.getCellColorHigh();
    }

    public Color getCellColorLow() {
        return mSettings.getCellColorLow();
    }

    public InterpolatorEnum getInterpolator() {
        return InterpolatorEnum.fromString(mSettings.getInterpolator());
    }

    public String getIterations() {
        return mSettings.getIterations();
    }

    private void notifyListeners() {
        for (Listener listener : mListeners)
            listener.onUpdate();
    }

    public void setBackgroundColor(Color color) {
        mSettings.setBackgroundColor(color);
        notifyListeners();
    }

    public void setCellColorHigh(Color color) {
        mSettings.setCellColorHigh(color);
        notifyListeners();
    }

    public void setCellColorLow(Color color) {
        mSettings.setCellColorLow(color);
        notifyListeners();
    }

    public void setInterpolator(InterpolatorEnum interpolator) {
        mSettings.setInterpolator(interpolator.toString());
        notifyListeners();
    }

    public void setIterations(String iterations) {
        mSettings.setIterations(iterations);
        notifyListeners();
    }
}
