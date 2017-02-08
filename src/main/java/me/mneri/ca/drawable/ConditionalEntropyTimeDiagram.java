package me.mneri.ca.drawable;

import me.mneri.ca.automaton.AutomatonHistory;
import me.mneri.ca.measures.Entropy;

public class ConditionalEntropyTimeDiagram extends Diagram {
    public ConditionalEntropyTimeDiagram(AutomatonHistory history) {
        super(history);
    }

    @Override
    protected double[][] prepare(AutomatonHistory history) {
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
