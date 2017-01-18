package me.mneri.ca.tests;

import infodynamics.measures.discrete.EntropyRateCalculatorDiscrete;
import me.mneri.ca.measures.Entropy;

public class Test {

    public static void main(String[] args) throws Exception {

        int[] streamX = new int[] { 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0 };
        int[] streamY = new int[] { 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0 };

        int[] col1 = new int[] { 1, 1, 0, 0 };
        int[] col2 = new int[] { 1, 1, 1, 1 };

        // Lizier calculators
        EntropyRateCalculatorDiscrete erCalc = new EntropyRateCalculatorDiscrete(2, 2);

        // entropy rate

        erCalc.addObservations(col1);
        erCalc.addObservations(col2);
        double[] c1 = erCalc.computeLocalFromPreviousObservations(col1);
        double[] c2 = erCalc.computeLocalFromPreviousObservations(col2);

        // our local entropy rate
        double[][] erRes = Entropy.entropyRate(new int[][] { { 1, 1 }, { 1, 1 }, { 0, 1 }, { 0, 1 } }, 2);
        System.out.printf("(Our) Local Entropy Rate: \n");
        for (int i = 0; i < erRes.length; i++) {
            for (int j = 0; j < erRes[0].length; j++) {
                System.out.printf(" ->(%d,%d): %f \n", i, j, erRes[i][j]);
            }
        }
        // Lizier entropy rate
        System.out.printf("(Lizier) Local Entropy Rate: \n");
        for (int i = 0; i < col1.length; i++)
            System.out.printf(" ->(col1,%d): %f \n", i, c1[i]);
        for (int i = 0; i < col1.length; i++)
            System.out.printf(" ->(col2,%d): %f \n", i, c2[i]);
    }

}
