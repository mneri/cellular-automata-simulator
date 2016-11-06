package me.mneri.ca.app;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import me.mneri.ca.util.Colors;
import me.mneri.ca.util.Fs;

public class Settings {
    private static final String JAR_PROPS = Fs.path("", "me", "mneri", "ca", "conf.properties");
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String USER_PROPS = Fs.path(USER_HOME, ".local", "share", "me.mneri.ca", "conf.properties");

    public interface SettingsListener {
        void onUpdate();
    }

    private ArrayList<SettingsListener> mListeners = new ArrayList<>();
    private Properties mProps;

    Settings() {
        init();
    }

    public void addSettingsListener(SettingsListener listener) {
        mListeners.add(listener);
    }

    public Color getBackgroundColor() {
        Color color = null;

        try {
            color = Color.decode(mProps.getProperty("background_color"));
        } catch (NumberFormatException ignored) { }

        return color;
    }

    public String getIterations() {
        return "1";
    }

    private void init() {
        try {
            mProps = new Properties(mProps);
            mProps.load(getClass().getResourceAsStream(JAR_PROPS));

            Properties userProps = new Properties(mProps);
            userProps.load(new FileInputStream(USER_PROPS));
            mProps = userProps;
        } catch (IOException ignored) { }
    }

    private void notifyListeners() {
        for (SettingsListener listener : mListeners)
            listener.onUpdate();
    }

    public void removeSettingsListener(SettingsListener listener) {
        mListeners.remove(listener);
    }

    public void setBackgroundColor(Color color) {
        setAndStore("background_color", Colors.toHexString(color));
    }

    public void setIterations(String iterations) {

    }

    private void setAndStore(String name, String value) {
        mProps.setProperty(name, value);

        Application.invokeLater(() -> {
            try {
                File confDir = new File(USER_PROPS).getParentFile();

                if (confDir.exists() || confDir.mkdirs())
                    mProps.store(new FileOutputStream(USER_PROPS), null);

                notifyListeners();
            } catch (IOException ignored) { }
        });
    }
}
