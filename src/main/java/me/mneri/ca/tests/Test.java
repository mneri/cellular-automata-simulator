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

        int rows = 2000;
        int cols = 150;
        int[][] matrix = new int[rows][cols];
        int rule = 18;

        // Lizier calculators
//        EntropyRateCalculatorDiscrete erCalc = new EntropyRateCalculatorDiscrete(2, 2);
//        erCalc.initialise();

        //automata
        Automaton history = new Automaton(AutomatonState.random(new ElementaryRule(rule), cols));
        history.tick(rows);
        history.toArray(matrix);

        double[] abe = new double[rows];
        
        
        System.out.printf("Block Entropy \n\n");
        for (int i = 0; i < rows; i++) {
            abe[i] = Entropy.averagedBlockEntropy(matrix, i);
            System.out.printf("%d-> %f   \n", i, abe[i]);
        }
        System.out.printf("\n");

        // graph
        JFrame jf4 = new JFrame();
        Graphic panel4 = new Graphic(abe, "Block Entropy", rule);
        panel4.setVisible(true);
        jf4.add(panel4);
        jf4.setVisible(true);
        jf4.pack();
        
        double[] er = new double[rows];
        er = Entropy.derivedBlockEntropy(abe, 0);

//        System.out.printf("Derived 1: \n\n");
//        for (int i = 0; i < rows - 1 && i < maxStep; i++) {
//            System.out.printf("%f   ", er[i]);
//        }
//        System.out.printf("\n");

        // graph
        JFrame jf = new JFrame();
        Graphic panel = new Graphic(er, "Derived (1 times)", rule);
        panel.setVisible(true);
        jf.add(panel);
        jf.setVisible(true);
        jf.pack();

        er = Entropy.derivedBlockEntropy(abe, 1);
        
//        System.out.printf("Derived 2: \n\n");
//        for (int i = 0; i < rows - 2 && i < maxStep; i++) {
//            System.out.printf("%f   ", er[i]);
//            erCalc = null;
//        }
//        System.out.printf("\n");

        // graph
        JFrame jf2 = new JFrame();
        Graphic panel2 = new Graphic(er, "Derived (2 times)", rule);
        panel2.setVisible(true);
        jf2.add(panel2);
        jf2.setVisible(true);
        jf2.pack();

//        System.out.printf("------------------------------------------ \n\n");
//
//        System.out.printf(
//                "For each one-dimensional cellular automaton F of radius r it is well known that\nhµ(F) ≤ h(F) ≤ 2r ln(#A): \n\n");
//        System.out.printf("%f   ≤ %f    ≤ %f \n\n", Entropy.averagedBlockEntropy(matrix, rows - 1),
//                Entropy.globalEntropy(matrix), 2 * Math.log(2));
    }

}
