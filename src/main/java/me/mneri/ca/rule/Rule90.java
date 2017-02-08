package me.mneri.ca.rule;

public class Rule90 extends ElementaryRule {
    @Override
    public int update(int[] states) {
        int p = states[0];
        int r = states[1];

        return (p + r) % 2;
    }
}
