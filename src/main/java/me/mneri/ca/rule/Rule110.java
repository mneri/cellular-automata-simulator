package me.mneri.ca.rule;

public class Rule110 implements Rule {
    @Override
    public int arity() {
        return 3;
    }

    @Override
    public void neighborhood(int i, int[] out) {
        out[0] = i - 1;
        out[1] = i;
        out[2] = i + 1;
    }

    @Override
    public boolean update(boolean[] states) {
        return (states[1] & !states[0]) | (states[1] ^ states[2]);
    }
}
