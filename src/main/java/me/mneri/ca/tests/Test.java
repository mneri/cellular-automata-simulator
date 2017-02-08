package me.mneri.ca.tests;

import javax.swing.JFrame;
import infodynamics.measures.discrete.EntropyRateCalculatorDiscrete;
import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.automaton.AutomatonHistory;
import me.mneri.ca.measures.Entropy;
import me.mneri.ca.rule.ElementaryRule;
import me.mneri.ca.widget.Graphic;

public class Test {

    public static void main(String[] args) throws Exception {

        int rows = 200;
        int cols = 250;

        // Lizier calculators
        EntropyRateCalculatorDiscrete erCalc = new EntropyRateCalculatorDiscrete(2, 2);
        erCalc.initialise();

        int[][] matrix = new int[rows][cols];
        AutomatonHistory history = new AutomatonHistory(Automaton.random(new ElementaryRule(110), cols));
        history.tick(rows);

        System.out.printf("Entropy Rate for all k values \n\n");
        double[] er = new double[Math.min(rows, 100)];
        for (int i = 0; i < rows && i < 100; i++) {
            er[i] = Entropy.averagedEntropyRate(matrix, i);
            System.out.printf("%d-> %f   \n", i, er[i]);
            erCalc = null;
        }
        System.out.printf("\n");

        System.out.printf("Derived 1: \n\n");
        double[] er1 = new double[Math.min(rows - 1, 100)];
        er1 = Entropy.derivedEntropyRate(matrix, 0);
        for (int i = 0; i < rows - 1 && i < 100; i++) {
            System.out.printf("%f   ", er1[i]);
            erCalc = null;
        }
        System.out.printf("\n");

        System.out.printf("Derived 2: \n\n");
        double[] er2 = new double[Math.min(rows - 2, 100)];
        er2 = Entropy.derivedEntropyRate(matrix, 1);
        for (int i = 0; i < rows - 2 && i < 100; i++) {
            System.out.printf("%f   ", er2[i]);
            erCalc = null;
        }
        System.out.printf("\n");

        // graphs
        JFrame jf4 = new JFrame();
        Graphic panel4 = new Graphic(er, true, "Entropy Rate for each k from 0 to rows");
        panel4.setVisible(true);
        jf4.add(panel4);
        jf4.setVisible(true);
        jf4.pack();

        JFrame jf = new JFrame();
        Graphic panel = new Graphic(er1, true, "Derived (1 times) Hµ(k) for each k from 0 to rows");
        panel.setVisible(true);
        jf.add(panel);
        jf.setVisible(true);
        jf.pack();

        JFrame jf2 = new JFrame();
        Graphic panel2 = new Graphic(er2, true, "Derived (2 times) Hµ(k) for each k from 0 to rows");
        panel2.setVisible(true);
        jf2.add(panel2);
        jf2.setVisible(true);
        jf2.pack();

        System.out.printf("------------------------------------------ \n\n");

        System.out.printf(
                "For each one-dimensional cellular automaton F of radius r it is well known that\nhµ(F) ≤ h(F) ≤ 2r ln(#A): \n\n");
        System.out.printf("%f   ≤ %f    ≤ %f \n\n", Entropy.averagedEntropyRate(matrix, rows - 1),
                Entropy.globalEntropy(matrix), 2 * Math.log(2));
    }

}
