package me.mneri.ca.rule;

public class Rule124 extends ElementaryRule {
    @Override
    public int update(int[] states) {
        int p = states[0];
        int q = states[1];
        int r = states[2];

        return (p + q + p * q + p * q * r) % 2;
    }
}
