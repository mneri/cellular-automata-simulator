package me.mneri.ca.drawable;

import me.mneri.ca.automaton.Automaton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

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

    public int getState(int i, int j) {
        if (i < 0 || j < 0)
            return 0;

        if (i >= mAutomatons.size())
            return 0;

        Automaton automaton = mAutomatons.get(i);

        if (j >= automaton.size())
            return 0;

        return automaton.getState(j);
    }
}
