package me.mneri.ca.automaton;

import java.util.HashSet;

import me.mneri.ca.rule.Rule;

public class Automaton {
    private boolean mDefault;
    private Rule mRule;
    private HashSet<Integer> mSet = new HashSet<>();

    private Automaton(Rule rule) {
        mRule = rule;
    }

    public static Automaton canonical(Rule rule) {
        Automaton automaton = new Automaton(rule);
        automaton.mSet.add(0);
        return automaton;
    }

    public Automaton evolve() {
        int arity = mRule.arity();
        int[] neighbors = new int[arity];
        boolean[] states = new boolean[arity];
        HashSet<Integer> concerned = getConcernedCells(mSet);
        Automaton evolved = new Automaton(mRule);

        for (int i = 0; i < arity; i++)
            states[i] = mDefault;

        if (mRule.update(states) != mDefault)
            evolved.mDefault = !mDefault;

        for (Integer cell : concerned) {
            mRule.neighborhood(cell, neighbors);

            for (int i = 0; i < neighbors.length; i++)
                states[i] = get(neighbors[i]);

            if (mRule.update(states) != evolved.mDefault)
                evolved.mSet.add(cell);
        }

        return evolved;
    }

    public boolean get(int location) {
        return mSet.contains(location) != mDefault;
    }

    private HashSet<Integer> getConcernedCells(HashSet<Integer> active) {
        int[] neighbors = new int[mRule.arity()];
        HashSet<Integer> concerned = new HashSet<>();

        for (Integer cell : active) {
            concerned.add(cell);
            mRule.neighborhood(cell, neighbors);

            for (int neighbor : neighbors)
                concerned.add(neighbor);
        }

        return concerned;
    }
}
