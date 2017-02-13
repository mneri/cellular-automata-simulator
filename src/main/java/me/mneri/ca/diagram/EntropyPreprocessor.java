package me.mneri.ca.diagram;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.measure.Entropy;

public class EntropyPreprocessor implements Diagram.Preprocessor {
    @Override
    public double[][] exec(Automaton history, int width, int height) {
        int[][] state = new int[height][width];
        double[] row;
        double[][] data = new double[height][width];

        history.toArray(state);

        for (int i = 0; i < height; i++) {
            row = Entropy.localEntropy(state[i]);

            for (int j = 0; j < width; j++)
                data[i][j] = row[j];
        }

        return data;
    }
}
