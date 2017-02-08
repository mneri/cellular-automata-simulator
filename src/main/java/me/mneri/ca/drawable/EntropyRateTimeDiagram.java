package me.mneri.ca.drawable;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.measure.Entropy;

public class EntropyRateTimeDiagram extends Diagram {
    public EntropyRateTimeDiagram(Automaton history) {
        super(history);
    }

    @Override
    protected double[][] prepare(Automaton history) {
        int[][] state = new int[1024][1024];

        history.toArray(state);
        double[][] data = Entropy.localEntropyRate(state, 4);

        return data;
    }
}
