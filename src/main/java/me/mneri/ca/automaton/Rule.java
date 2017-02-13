package me.mneri.ca.automaton;

public interface Rule {
    int arity();

    void neighborhood(int i, int[] out);

    int update(int[] states);
}
