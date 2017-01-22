package me.mneri.ca.tests;

import infodynamics.measures.discrete.EntropyRateCalculatorDiscrete;
import me.mneri.ca.measures.Entropy;

public class Test {

    public static void main(String[] args) throws Exception {

        int[] streamX = new int[] { 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0 };
        int[] streamY = new int[] { 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0 };

        int[] col1 = new int[] { 1, 1, 0, 0 };
        int[] col2 = new int[] { 1, 1, 1, 1 };
        
        int[][] matrix =new int[][] { { 1, 1 }, { 1, 1 }, { 0, 1 }, { 0, 1 } };
        
        // Lizier calculators
        EntropyRateCalculatorDiscrete erCalc = new EntropyRateCalculatorDiscrete(2, 2);
        erCalc.initialise();

        // entropy rate

        erCalc.addObservations(col1);
        erCalc.addObservations(col2);
        //erCalc.addObservations(matrix);
        double[] c1 = erCalc.computeLocalFromPreviousObservations(col1);
        double[] c2 = erCalc.computeLocalFromPreviousObservations(col2);
        
        
        
        System.out.printf("(Our) Av. Entropy Rate: %f \n", Entropy.averagedEntropyRate(matrix, 2));
        System.out.printf("(Lizier) Av. Entropy Rate: %f \n", erCalc.computeAverageLocalOfObservations());
        
        // our local entropy rate
        double[][] erRes = Entropy.localEntropyRate(matrix, 2);
        System.out.printf("(Our) Local Entropy Rate: \n");
        for (int i = 0; i < erRes.length; i++) {
            for (int j = 0; j < erRes[0].length; j++) {
                System.out.printf(" %f ", erRes[i][j]);
            }
            System.out.printf("\n");
        }
        // Lizier entropy rate
        System.out.printf("(Lizier) Local Entropy Rate: \n");
        for (int i = 0; i < col1.length; i++)
            System.out.printf(" %f %f \n",c1[i],c2[i]);
    }

}
