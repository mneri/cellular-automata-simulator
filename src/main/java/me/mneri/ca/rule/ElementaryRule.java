package me.mneri.ca.rule;

public class ElementaryRule implements Rule {
    private static final int ARITY = 3;

    private int mLookup;

    public ElementaryRule(int num) {
        mLookup = num;
    }

    @Override
    public int arity() {
        return ARITY;
    }

    @Override
    public void neighborhood(int i, int[] out) {
        out[0] = i - 1;
        out[1] = i;
        out[2] = i + 1;
    }

    @Override
    public int update(int[] states) {
        int shift = 0;

        for (int i = 0; i < ARITY; i++) {
            shift <<= 1;
            shift |= states[i];
        }

        return (mLookup >> shift) & 1;
    }
}
