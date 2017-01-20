package me.mneri.ca.rule;

public abstract class ElementaryRule implements Rule {
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
}
