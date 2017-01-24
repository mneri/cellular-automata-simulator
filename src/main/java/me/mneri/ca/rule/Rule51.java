package me.mneri.ca.rule;

public class Rule51 extends ElementaryRule {
    @Override
    public boolean update(boolean[] states) {
        boolean q = states[1];
        return !q;
    }
}
