package me.mneri.ca.automaton;

import me.mneri.ca.rule.Rule;

import java.util.HashSet;

public class Automaton {
    private Rule mRule;
    private int[] mState;

    private Automaton(int size, Rule rule) {
        mRule = rule;
        mState = new int[size];
    }

    public static Automaton canonical(int size, Rule rule) {
        Automaton automaton = new Automaton(size, rule);
        automaton.mState[size / 2] = 1;
        return automaton;
    }

    public int getState(int location) {
        return mState[location];
    }

    public Automaton evolve() {
        int size = mState.length;
        int arity = mRule.arity();
        int[] neighbors = new int[arity];
        int[] neighborsState = new int[arity];
        Automaton evolved = new Automaton(size, mRule);

        for (int i = 0; i < size; i++) {
            mRule.neighborhood(i, neighbors);

            for (int j = 0; j < neighbors.length; j++) {
                if (neighbors[j] < 0 || neighbors[j] >= size)
                    neighborsState[j] = 1;
                else
                    neighborsState[j] = mState[neighbors[j]];
            }

            evolved.mState[i] = mRule.update(neighborsState);
        }

        return evolved;
    }

    public int size() {
        return mState.length;
    }
}
