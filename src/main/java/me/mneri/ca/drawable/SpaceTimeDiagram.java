package me.mneri.ca.drawable;

import me.mneri.ca.automaton.AutomatonHistory;

public class SpaceTimeDiagram extends Diagram {
    public SpaceTimeDiagram(AutomatonHistory history) {
        super(history);
    }

    @Override
    protected double[][] prepare(AutomatonHistory history) {
        double[][] data = new double[1024][1024];

        for (int i = 0; i < 1024; i++) {
            for (int j = 0; j < 1024; j++)
                data[i][j] = history.get(i).get(j - 512);
        }

        return data;
    }
}
