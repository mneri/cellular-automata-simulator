package me.mneri.ca.diagram;

import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.diagram.Diagram;

public class StateDiagram extends Diagram {
    public StateDiagram(Automaton history) {
        super(history);
    }

    @Override
    protected double[][] prepare(Automaton history) {
        double[][] data = new double[1024][1024];

        for (int i = 0; i < 1024; i++) {
            for (int j = 0; j < 1024; j++)
                data[i][j] = history.get(i).get(j - 512);
        }

        return data;
    }
}
