package me.mneri.ca.rule;

public interface Rule {
    int arity();

    void neighborhood(int i, int[] out);

    boolean update(boolean[] states);
}
