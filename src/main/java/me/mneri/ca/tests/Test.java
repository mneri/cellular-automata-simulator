package me.mneri.ca.tests;

import javax.swing.JFrame;
import infodynamics.measures.discrete.EntropyRateCalculatorDiscrete;
import me.mneri.ca.automaton.AutomatonState;
import me.mneri.ca.automaton.Automaton;
import me.mneri.ca.measure.Entropy;
import me.mneri.ca.automaton.ElementaryRule;
import me.mneri.ca.widget.Graphic;

public class Test {

    public static void main(String[] args) throws Exception {

        int rows = 600;
        int cols = 150;
        int maxStep = 101;

        // Lizier calculators
        EntropyRateCalculatorDiscrete erCalc = new EntropyRateCalculatorDiscrete(2, 2);
        erCalc.initialise();

        int[][] matrix = new int[rows][cols];
        int rule = 30;
        Automaton history = new Automaton(AutomatonState.random(new ElementaryRule(rule), cols));
        history.tick(rows);
        history.toArray(matrix);

        System.out.printf("Block Entropy for all k values \n\n");
        double[] er = new double[Math.min(rows, maxStep)];
        for (int i = 0; i < rows && i < maxStep; i++) {
            er[i] = Entropy.averagedBlockEntropy(matrix, i);
            System.out.printf("%d-> %f   \n", i, er[i]);
            erCalc = null;
        }
        System.out.printf("\n");

        System.out.printf("Block Entropy 0 vs entropy: %f = %f \n\n", er[0], Entropy.globalEntropy(matrix));

        // graph
        JFrame jf4 = new JFrame();
        Graphic panel4 = new Graphic(er, false, "Block Entropy for each k", rule);
        panel4.setVisible(true);
        jf4.add(panel4);
        jf4.setVisible(true);
        jf4.pack();

        System.out.printf("Derived 1: \n\n");
        er = null;
        er = Entropy.derivedBlockEntropy(matrix, 0);
        for (int i = 0; i < rows - 1 && i < maxStep; i++) {
            System.out.printf("%f   ", er[i]);
            erCalc = null;
        }
        System.out.printf("\n");

        // graph
        JFrame jf = new JFrame();
        Graphic panel = new Graphic(er, true, "Derived (1 times) H(k) for each k", rule);
        panel.setVisible(true);
        jf.add(panel);
        jf.setVisible(true);
        jf.pack();

        System.out.printf("Derived 2: \n\n");
        er = null;
        er = Entropy.derivedBlockEntropy(matrix, 1);
        for (int i = 0; i < rows - 2 && i < maxStep; i++) {
            System.out.printf("%f   ", er[i]);
            erCalc = null;
        }
        System.out.printf("\n");

        // graph
        JFrame jf2 = new JFrame();
        Graphic panel2 = new Graphic(er, true, "Derived (2 times) H(k) for each k ", rule);
        panel2.setVisible(true);
        jf2.add(panel2);
        jf2.setVisible(true);
        jf2.pack();

        System.out.printf("------------------------------------------ \n\n");

        System.out.printf(
                "For each one-dimensional cellular automaton F of radius r it is well known that\nhµ(F) ≤ h(F) ≤ 2r ln(#A): \n\n");
        System.out.printf("%f   ≤ %f    ≤ %f \n\n", Entropy.averagedBlockEntropy(matrix, rows - 1),
                Entropy.globalEntropy(matrix), 2 * Math.log(2));
    }

}
