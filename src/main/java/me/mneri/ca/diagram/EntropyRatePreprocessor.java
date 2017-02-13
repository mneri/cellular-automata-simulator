package me.mneri.ca.diagram;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.measure.Entropy;

public class EntropyRatePreprocessor implements Diagram.Preprocessor {
    private int mK;

    public EntropyRatePreprocessor(int k) {
        mK = k;
    }

    @Override
    public double[][] exec(Automaton history, int width, int height) {
        int[][] state = new int[height][width];

        history.toArray(state);
        return Entropy.localBlockEntropy(state, mK);
    }
}
