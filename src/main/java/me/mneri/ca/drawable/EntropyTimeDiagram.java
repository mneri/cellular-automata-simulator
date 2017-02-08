package me.mneri.ca.drawable;

import me.mneri.ca.automaton.AutomatonHistory;
import me.mneri.ca.measures.Entropy;

public class EntropyTimeDiagram extends Diagram {
    public EntropyTimeDiagram(AutomatonHistory history) {
        super(history);
    }

    @Override
    protected double[][] prepare(AutomatonHistory history) {
        int[][] state = new int[1024][1024];
        double[] row;
        double[][] data = new double[1024][1024];

        history.toArray(state);

        for (int i = 0; i < 1024; i++) {
            row = Entropy.localEntropy(state[i]);

            for (int j = 0; j < 1024; j++)
                data[i][j] = row[j];
        }

        return data;
    }
}
