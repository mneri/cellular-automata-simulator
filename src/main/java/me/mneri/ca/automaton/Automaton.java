package me.mneri.ca.automaton;

import me.mneri.ca.rule.Rule;

import java.util.HashSet;

public class Automaton {
    private Rule mRule;
    private HashSet<Integer> mActive = new HashSet<>();

    private Automaton(Rule rule) {
        mRule = rule;
    }

    public static Automaton canonical(Rule rule) {
        Automaton automaton = new Automaton(rule);
        automaton.mActive.add(0);
        return automaton;
    }

    private HashSet<Integer> concernedCells(HashSet<Integer> active) {
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

    public HashSet<Integer> getState() {
        return mActive;
    }

    public void update() {
        int arity = mRule.arity();
        int[] neighbors = new int[arity];
        boolean[] states = new boolean[arity];
        HashSet<Integer> active = new HashSet<>();
        HashSet<Integer> concerned = concernedCells(mActive);

        for (Integer cell :concerned) {
            mRule.neighborhood(cell, neighbors);

            for (int i = 0; i < neighbors.length; i++)
                states[i] = mActive.contains(neighbors[i]);

            if (mRule.update(states))
                active.add(cell);
        }

        mActive = active;
    }
}
