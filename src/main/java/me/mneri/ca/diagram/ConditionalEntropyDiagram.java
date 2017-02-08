package me.mneri.ca.diagram;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.diagram.Diagram;
import me.mneri.ca.measure.Entropy;

public class ConditionalEntropyDiagram extends Diagram {
    public ConditionalEntropyDiagram(Automaton history) {
        super(history);
    }

    @Override
    protected double[][] prepare(Automaton history) {
        double[][] res = new double[1024][1024];
        int[] x = new int[1024];
        int[] y = new int[1024];

        for (int j = 0; j < 1024 - 1; j++) {
            for (int i = 0; i < 1024; i++) {
                x[i] = history.get(i).get(j - 512);
                y[i] = history.get(i).get(j - 512 + 1);
            }

            double[] je = Entropy.localConditionalEntropy(x, y);

            for (int i = 0; i < 1024; i++)
                res[i][j] = je[i];
        }

        return res;
    }
}
