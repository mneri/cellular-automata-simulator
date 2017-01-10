package me.mneri.ca.tests;

import infodynamics.measures.discrete.EntropyCalculatorDiscrete;
import me.mneri.ca.measures.Entropy;

public class Test {

    public static void main(String[] args) {

        int[] streamX = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        int[] streamY = new int[] { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        int[] streamZ = new int[] { 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1 };

        // Lizier Entropy calculators
        EntropyCalculatorDiscrete eCalc = new EntropyCalculatorDiscrete(2);

        // Our Global Conditional entropy
        System.out.printf("(Our) Global Conditional Entropy: %f \n",
                Entropy.globalConditionalEntropy(streamX, streamY));

        // Our local Conditional entropy
        double res[][] = Entropy.localConditionalEntropy(streamX, streamY);
        System.out.printf("(Our) Local Conditional Entropy (foreach pair): \n");
        System.out.printf(" ->(0,0): %f \n", res[0][0]);
        System.out.printf(" ->(0,1): %f \n", res[0][1]);
        System.out.printf(" ->(1,0): %f \n", res[1][0]);
        System.out.printf(" ->(1,1): %f \n", res[1][1]);

        System.out.printf("------------------- OLD ------------------- \n", Entropy.globalEntropy(streamX));

        // Global entropy
        System.out.printf("(Our) Global Entropy: %f \n", Entropy.globalEntropy(streamX));

        // Our Local entropy
        System.out.printf("(Our) Local Entropy (of 1s): %f \n", Entropy.localEntropy(streamX, 1));
        System.out.printf("(Our) Local Entropy (of 0s): %f \n", Entropy.localEntropy(streamX, 0));

        // Our global joint entropy
        System.out.printf("(Our) Global Joint Entropy: %f \n", Entropy.globalJointEntropy(streamX, streamY));

        // Our local joint entropies
        System.out.printf("(Our) Local Joint Entropy (foreach pair): \n");
        res = Entropy.localJointEntropy(streamX, streamY);
        System.out.printf("	->(0,0): %f \n", res[0][0]);
        System.out.printf("	->(0,1): %f \n", res[0][1]);
        System.out.printf("	->(1,0): %f \n", res[1][0]);
        System.out.printf("	->(1,1): %f \n", res[1][1]);

        // Lizier local entropy
        System.out.printf("Lizier Local Entropy (foreach cell): \n");
        for (double val : eCalc.computeLocal(streamX)) {
            System.out.printf("	-> %f \n", val);
        }
    }

}
