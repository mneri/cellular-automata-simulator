package me.mneri.ca.rule;

public class Rule45 extends ElementaryRule {
    @Override
    public int update(int[] states) {
        int p = states[0];
        int q = states[1];
        int r = states[2];

        return (1 + p + r + q * r) % 2;
    }
}
