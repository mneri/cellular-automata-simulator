package me.mneri.ca.automaton;

import java.util.ArrayList;

public class AutomatonHistory {
    private ArrayList<Automaton> mAutomatons = new ArrayList<>();
    private Automaton mLast;

    public AutomatonHistory(Automaton automaton) {
        mLast = automaton;
        mAutomatons.add(automaton);
    }

    public void evolve() {
        mLast = mLast.evolve();
        mAutomatons.add(mLast);
    }

    public Automaton get(int time) {
        return mAutomatons.get(time);
    }

    public int size() {
        return mAutomatons.size();
    }
}
