package me.mneri.ca.rule;

public class Rule51 extends ElementaryRule {
    @Override
    public int update(int[] states) {
        int q = states[1];
        return (1 + q) % 2;
    }
}
