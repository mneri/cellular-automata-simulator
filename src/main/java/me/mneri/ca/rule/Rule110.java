package me.mneri.ca.rule;

public class Rule110 extends ElementaryRule {
    @Override
    public boolean update(boolean[] states) {
        boolean p = states[0];
        boolean q = states[1];
        boolean r = states[2];

        return (q & !p) | (q ^ r);
    }
}
