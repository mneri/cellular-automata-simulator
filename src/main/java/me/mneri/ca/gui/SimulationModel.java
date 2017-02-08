package me.mneri.ca.gui;

import java.util.ArrayList;
import java.util.List;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.automaton.AutomatonHistory;
import me.mneri.ca.rule.*;

public class SimulationModel {
    public interface Listener {
        void onUpdate();
    }

    private AutomatonHistory mHistory = new AutomatonHistory(Automaton.random(new Rule110(), 1024));
    private List<Listener> mListeners = new ArrayList<>();

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    public AutomatonHistory getHistory() {
        return mHistory;
    }

    private void notifyListeners() {
        for (Listener listener : mListeners)
            listener.onUpdate();
    }

    public void removeListener(Listener listener) {
        mListeners.remove(listener);
    }

    public void tick(int n) {
        (new Thread(() -> {
            mHistory.tick(n);
            notifyListeners();
        })).start();
    }
}
