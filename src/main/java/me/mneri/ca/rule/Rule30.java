package me.mneri.ca.rule;

public class Rule30 extends ElementaryRule {
    @Override
    public int update(int[] states) {
        int p = states[0];
        int q = states[1];
        int r = states[2];

        return (p + q + r + q * r) % 2;
    }
}
