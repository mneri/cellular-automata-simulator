package me.mneri.ca.rule;

public class ElementaryRule implements Rule {
    private static final int ARITY = 3;

    private byte mLookup;

    public ElementaryRule(int num) {
        if (num < 0 || num > 255)
            throw new IllegalArgumentException("Rule number should be between 0 and 255.");

        mLookup = (byte) num;
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

        /*
         * Elementary rules are generally represented using the subsequent schema.
         *
         *       7          6          5          4          3          2          1          0
         *  +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+
         *  |##|##|##| |##|##|  | |##|  |##| |##|  |  | |  |##|##| |  |##|  | |  |  |##| |  |  |  |
         *  +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+ +--+--+--+
         *     |  |       |##|       |##|       |  |       |##|       |##|       |##|       |  |
         *     +--+       +--+       +--+       +--+       +--+       +--+       +--+       +--+
         *
         * For each neighbor configuration this schema tells the future state of the cell. Each neighbor configuration
         * is labelled with a number from 0 to 7, thus the rule can be represented with a number from 0 to 255
         * (2^7 - 1). The one above is rule 110 (in binary, rule 01101110). Each bit corresponds to a given neighbors'
         * configuration so, for example, the third bit from the left tells the cell's future state when neighbors'
         * states are 101.
         *
         * Here we convert the neighbors' states into a decimal number and then use that number to shift the rule
         * number. For example, say we use rule 110 (01101110 in binary) and our neighborhood has the state 101
         * (that is 5 in decimal). Now, shifting 01101110 to the right by 5 positions we obtain 00000011. The rightmost
         * bit is the future state of the cell (and then, 00000011 & 1 = 1).
         */

        // Convert the neighbors' states into decimal
        for (int i = 0; i < ARITY; i++) {
            shift <<= 1;
            shift |= states[i];
        }

        // Shift right and get the rightmost bit
        return (mLookup >> shift) & 1;
    }
}
