package me.mneri.ca.diagram;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.diagram.Diagram;
import me.mneri.ca.measure.Entropy;

public class JointEntropyPreprocessor implements Diagram.Preprocessor {
    @Override
    public double[][] exec(Automaton automaton, int width, int height) {
        double[][] res = new double[height][width];
        int[] x = new int[height];
        int[] y = new int[height];

        for (int j = 0; j < width - 1; j++) {
            for (int i = 0; i < height; i++) {
                x[i] = automaton.get(i).get(j - (width / 2));
                y[i] = automaton.get(i).get(j - (width / 2) + 1);
            }

            double[] je = Entropy.localJointEntropy(x, y);

            for (int i = 0; i < height; i++)
                res[i][j] = je[i];
        }

        return res;
    }
}
