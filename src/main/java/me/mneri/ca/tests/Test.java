package me.mneri.ca.tests;

import infodynamics.measures.discrete.EntropyRateCalculatorDiscrete;
import me.mneri.ca.measures.Entropy;

public class Test {

    public static void main(String[] args) throws Exception {
        
        /*
         * 1 1 1
         * 1 1 0
         * 0 0 1
         * 0 1 0
         */

        //int[][] matrix = new int[][] { { 1, 1 }, { 1, 1 }, { 0, 1}, { 0, 1 } };
        //int[][] matrix = new int[][] { { 1, 1, 1 }, { 1, 1, 0 }, { 0, 1, 1}, { 0, 1, 0 } };
        //int[][] matrix = new int[][] { { 1, 1, 1 }, { 1, 1, 1 }, { 0, 0, 1}, { 0, 0, 1 } };
        
        int[][] matrix = new int[14][14];
        
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                matrix[i][j] = i == j ? 1 : 0;
            }
        }
        
        // Lizier calculators
        EntropyRateCalculatorDiscrete erCalc = new EntropyRateCalculatorDiscrete(2, 5);
        erCalc.initialise();

        // total excess entropy
        System.out.printf("Total excess entropy: %f \n", Entropy.excessEntropy(matrix));

        // local excess entropy
        double[][] lee = Entropy.localExcessEntropy(matrix, 5);
        System.out.printf("Local Excess Entropy: \n");
        for (int i = 0; i < lee.length; i++) {
            for (int j = 0; j < lee[0].length; j++) {
                System.out.printf(" %f ", lee[i][j]);
            }
            System.out.printf("\n");
        }

        System.out.printf("------------------------------------------ \n");

        System.out.printf("(Our) Av. Entropy Rate: %f \n", Entropy.averagedEntropyRate(matrix, 5));
        System.out.printf("(Lizier) Av. Entropy Rate: %f \n", erCalc.computeAverageLocal(matrix));

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
        double[][] LizEr = erCalc.computeLocal(matrix);
        System.out.printf("(Lizier) Local Entropy Rate: \n");
        for (int i = 0; i < LizEr.length; i++) {
            for (int j = 0; j < LizEr[0].length; j++) {
                System.out.printf(" %f ", LizEr[i][j]);
            }
            System.out.printf("\n");
        }
    }

}
