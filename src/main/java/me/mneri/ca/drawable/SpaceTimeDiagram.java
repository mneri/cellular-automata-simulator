package me.mneri.ca.drawable;

import me.mneri.ca.automaton.Automaton;

import java.util.ArrayList;

public class SpaceTimeDiagram {
    private ArrayList<Automaton> mAutomatons = new ArrayList<>();

    public SpaceTimeDiagram(Automaton automaton) {
        mAutomatons.add(automaton);
    }

    public void tick() {
        Automaton last = mAutomatons.get(mAutomatons.size() - 1);
        Automaton evolved = last.evolve();
        mAutomatons.add(evolved);
    }

    public boolean getState(int i, int j) {
        if (!(i >= 0 && i < mAutomatons.size()))
            return false;

        Automaton automaton = mAutomatons.get(i);

        return automaton.get(j);
    }
}
