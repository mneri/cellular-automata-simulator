package me.mneri.ca.automaton;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import me.mneri.ca.rule.Rule;

public class AutomatonState {
    private int mDefault;
    private Rule mRule;
    private HashSet<Integer> mSet = new HashSet<>();

    private AutomatonState(Rule rule) {
        mRule = rule;
    }

    public static AutomatonState canonical(Rule rule) {
        AutomatonState automaton = AutomatonState.empty(rule);
        automaton.mSet.add(0);
        return automaton;
    }

    public static AutomatonState empty(Rule rule) {
        return new AutomatonState(rule);
    }

    public AutomatonState evolve() {
        int arity = mRule.arity();
        int[] neighbors = new int[arity];
        int[] states = new int[arity];
        AutomatonState evolved = new AutomatonState(mRule);

        for (int i = 0; i < arity; i++)
            states[i] = mDefault;

        if (mRule.update(states) != mDefault)
            evolved.mDefault = mDefault ^ 1;

        HashSet<Integer> concerned = getConcernedCells(mSet);

        for (Integer cell : concerned) {
            mRule.neighborhood(cell, neighbors);

            for (int i = 0; i < neighbors.length; i++)
                states[i] = get(neighbors[i]);

            if (mRule.update(states) != evolved.mDefault)
                evolved.mSet.add(cell);
        }

        return evolved;
    }

    public int get(int location) {
        return mSet.contains(location) ? mDefault ^ 1 : mDefault;
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

    public static AutomatonState random(Rule rule, int size) {
        AutomatonState automaton = AutomatonState.empty(rule);
        Random random = ThreadLocalRandom.current();

        for (int i = -size / 2; i < size / 2; i++) {
            if (random.nextBoolean())
                automaton.mSet.add(i);
        }

        return automaton;
    }

    public void toArray(int[] out) {
        int size = out.length;

        for (int i = 0; i < size; i++)
            out[i] = get(i - size / 2);
    }
}