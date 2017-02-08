package me.mneri.ca.automaton;

import java.util.ArrayList;

public class AutomatonHistory {
    private ArrayList<Automaton> mAutomatons = new ArrayList<>();
    private Automaton mLast;

    public AutomatonHistory(Automaton automaton) {
        mLast = automaton;
        mAutomatons.add(automaton);
    }

    public Automaton get(int time) {
        if (time < 0 || time >= mAutomatons.size())
            return Automaton.empty(null);

        return mAutomatons.get(time);
    }

    public int size() {
        return mAutomatons.size();
    }

    public void tick() {
        mLast = mLast.evolve();
        mAutomatons.add(mLast);
    }

    public void tick(int n) {
        for (int i = 0; i < n; i++)
            tick();
    }

    public void toArray(int[][] out) {
        int length = out.length;

        for (int i = 0; i < length; i++)
            get(i).toArray(out[i]);
    }
}
