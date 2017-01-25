package me.mneri.ca.drawable;

import java.awt.*;
import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.automaton.AutomatonHistory;

public class SpaceTimeDiagram {
    private AutomatonHistory mHistory;

    public SpaceTimeDiagram(Automaton automaton) {
        mHistory = new AutomatonHistory(automaton);
    }

    public void tick() {
        mHistory.tick();
    }

    public boolean getState(int i, int j) {
        if (!(i >= 0 && i < mHistory.size()))
            return false;

        Automaton automaton = mHistory.get(i);

        return automaton.get(j);
    }

    public void paint(Graphics2D g) {

    }
}
