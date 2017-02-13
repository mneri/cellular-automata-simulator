package me.mneri.ca.gui;

import java.util.ArrayList;
import java.util.List;

import me.mneri.ca.automaton.AutomatonState;
import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.rule.*;

public class SimulationModel {
    public interface Listener {
        void onUpdate();
    }

    private Automaton mAutomaton;
    private List<Listener> mListeners = new ArrayList<>();

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    public Automaton getAutomaton() {
        return mAutomaton;
    }

    private void notifyListeners() {
        for (Listener listener : mListeners)
            listener.onUpdate();
    }

    public void removeListener(Listener listener) {
        mListeners.remove(listener);
    }

    public void setAutomaton(Automaton automaton) {
        mAutomaton = automaton;
        notifyListeners();
    }
}
