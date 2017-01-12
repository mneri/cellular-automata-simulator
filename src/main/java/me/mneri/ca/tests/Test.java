package me.mneri.ca.tests;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import infodynamics.measures.discrete.EntropyCalculatorDiscrete;
import infodynamics.measures.discrete.EntropyRateCalculatorDiscrete;
import me.mneri.ca.measures.Entropy;

public class Test {

    public static void main(String[] args) {

        int[] streamX = new int[] { 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1 };
        int[] streamY = new int[] { 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0 };
        int[] streamZ = new int[] { 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1 };

        int[] col1 = new int[] { 1, 1, 0, 0 };
        int[] col2 = new int[] { 1, 1, 1, 1 };

        // Lizier Entropy calculators
        EntropyRateCalculatorDiscrete erCalc = new EntropyRateCalculatorDiscrete(2, 2);
        EntropyCalculatorDiscrete eCalc = new EntropyCalculatorDiscrete(2);

        erCalc.addObservations(col1);
        erCalc.addObservations(col2);
        double[] c1 = erCalc.computeLocalFromPreviousObservations(col1);
        double[] c2 = erCalc.computeLocalFromPreviousObservations(col2);

        double[][] erRes = Entropy.localEntropyRate(new int[][] { { 1, 1 }, { 1, 1 }, { 0, 1 }, { 0, 1 } }, 2);

        // local entropy rate
        System.out.printf("(Our) Local Entropy Rate: \n");
        for (int i = 0; i < erRes.length; i++) {
            for (int j = 0; j < erRes[0].length; j++) {
                System.out.printf(" ->(%d,%d): %f \n", i, j, erRes[i][j]);
            }
        }
        // Lizier entropy rate
        System.out.printf("(Lizier) Local Entropy Rate: \n");
        for (int i = 0; i < col1.length; i++) {
            System.out.printf(" ->(col1,%d): %f \n", i, c1[i]);
            System.out.printf(" ->(col2,%d): %f \n", i, c2[i]);
        }

        // Our Global Conditional entropy
        System.out.printf("(Our) Global Conditional Entropy: %f \n",
                Entropy.globalConditionalEntropy(streamX, streamY));

        // Our local Conditional entropy
        double res[] = Entropy.localConditionalEntropy(streamX, streamY);
        System.out.printf("(Our) Local Conditional Entropy (foreach pair): \n");
        for (double d : res) {
            System.out.printf(" -> %f \n", d);
        }

        System.out.printf("------------------- OLD ------------------- \n", Entropy.globalEntropy(streamX));

        // Our global joint entropy
        System.out.printf("(Our) Global Joint Entropy: %f \n", Entropy.globalJointEntropy(streamX, streamY));

        // Our local joint entropies
        System.out.printf("(Our) Local Joint Entropy: \n");
        res = Entropy.localJointEntropy(streamX, streamY);
        for (double d : res) {
            System.out.printf(" -> %f \n", d);
        }

        // Our Global entropy
        System.out.printf("(Our) Global Entropy: %f \n", Entropy.globalEntropy(streamX));

        // Our Local entropy
        double[] le = Entropy.localEntropy(streamX);
        System.out.printf("(Our) Local Entropy: \n");
        for (double d : le) {
            System.out.printf(" -> %f \n", d);
        }

        // Lizie Global entropy
        System.out.printf("(Lizier) Global Entropy: %f \n", eCalc.computeAverageLocal(streamX));
        
        // Lizier local entropy
        System.out.printf("(Lizier) Local Entropy: \n");
        for (double val : eCalc.computeLocal(streamX)) {
            System.out.printf(" -> %f \n", val);
        }
    }

}
