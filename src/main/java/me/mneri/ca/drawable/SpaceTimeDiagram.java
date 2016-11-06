package me.mneri.ca.drawable;

import me.mneri.ca.automaton.Automaton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class SpaceTimeDiagram {
    private Automaton mAutomaton;
    private ArrayList<Set<Integer>> mStates = new ArrayList<>();

    public SpaceTimeDiagram(Automaton automaton) {
        mAutomaton = automaton;
    }

    public void tick() {
        mAutomaton.update();
        mStates.add(mAutomaton.getState());
    }

    public Set<Integer> getState(int i) {
        if (i < 0 || i >= mStates.size())
            return Collections.emptySet();

        return mStates.get(i);
    }
}
