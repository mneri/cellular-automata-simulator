package me.mneri.ca.drawable;

import me.mneri.ca.automaton.AutomatonHistory;
import me.mneri.ca.measures.Entropy;

public class EntropyRateTimeDiagram extends Diagram {
    public EntropyRateTimeDiagram(AutomatonHistory history) {
        super(history);
    }

    @Override
    protected double[][] prepare(AutomatonHistory history) {
        int[][] state = new int[1024][1024];

        history.toArray(state);
        double[][] data = Entropy.localEntropyRate(state, 4);

        return data;
    }
}
