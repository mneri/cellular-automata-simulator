package me.mneri.ca.gui;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.drawable.SpaceTimeDiagram;
import me.mneri.ca.rule.Rule30;

import java.util.ArrayList;
import java.util.List;

public class SimulationModel {
    public interface Listener {
        void onUpdate();
    }

    private Automaton mAutomaton = Automaton.canonical(1024, new Rule30());
    private SpaceTimeDiagram mDiagram = new SpaceTimeDiagram(mAutomaton);
    private List<Listener> mListeners = new ArrayList<>();
    private boolean mRunning;

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    public SpaceTimeDiagram getDiagram() {
        return mDiagram;
    }

    public boolean isRunning() {
        return mRunning;
    }

    private void notifyListeners() {
        for (Listener listener : mListeners)
            listener.onUpdate();
    }

    public void start() {
        mRunning = true;

        (new Thread(() -> {
            int iterations = 500;

            while (iterations-- > 0) {
                mDiagram.tick();
                notifyListeners();
            }
        })).start();
    }

    public void step() {

    }

    public void stop() {
        mRunning = false;
    }

    public boolean toggle() {
        if (mRunning)
            stop();
        else
            start();

        return mRunning;
    }
}
