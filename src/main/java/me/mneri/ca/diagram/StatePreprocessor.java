package me.mneri.ca.diagram;

import me.mneri.ca.automaton.Automaton;

public class StatePreprocessor implements Diagram.Preprocessor {
    @Override
    public double[][] exec(Automaton history, int width, int height) {
        double[][] data = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                data[i][j] = history.get(i).get(j - (width / 2));
        }

        return data;
    }
}
