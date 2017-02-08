package me.mneri.ca.diagram;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.diagram.Diagram;
import me.mneri.ca.measure.Entropy;

public class EntropyDiagram extends Diagram {
    public EntropyDiagram(Automaton history) {
        super(history);
    }

    @Override
    protected double[][] prepare(Automaton history) {
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