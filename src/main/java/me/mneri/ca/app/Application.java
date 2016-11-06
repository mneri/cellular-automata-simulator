package me.mneri.ca.app;

import javax.swing.*;

import me.mneri.ca.gui.SimulationController;

public class Application {
    private static Application sInstance = new Application();

    private ApplicationLoop mLoop = new ApplicationLoop();
    private Settings mSettings = new Settings();

    private Application() { }

    public Settings getSettings() {
        return mSettings;
    }

    public static Application instance() {
        return sInstance;
    }

    public static void invokeLater(Runnable runnable) {
        sInstance.mLoop.enqueue(runnable);
    }

    public static void main(String... args) {
        Application.instance().run();
    }

    private void run() {
        mLoop.start();
        mLoop.enqueue(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) { }

            SimulationController controller = SimulationController.createMVC();
            controller.showView();
        });
    }
}
