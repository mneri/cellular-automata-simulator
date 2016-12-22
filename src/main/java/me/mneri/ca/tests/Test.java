package me.mneri.ca.tests;

import infodynamics.measures.discrete.EntropyCalculatorDiscrete;
import me.mneri.ca.measures.Entropy;

public class Test {

	public static void main(String[] args) {

		int[] stream = new int[] { 1, 1, 1, 1, 1, 1 };
		int[] stream2 = new int[] { 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1 };

		// Lizier Entropy calculator
		EntropyCalculatorDiscrete eCalc = new EntropyCalculatorDiscrete(2);

		// Global entropy
		System.out.printf("(Our) Global Entropy: %f \n", Entropy.globalBinaryEntropy(stream));

		// Our Local entropy
		System.out.printf("(Our) Local Entropy (of 1s): %f \n", Entropy.localBinaryEntropy(stream, 1));
		System.out.printf("(Our) Local Entropy (of 0s): %f \n", Entropy.localBinaryEntropy(stream, 0));
		
		//Our global joint entropies
		System.out.printf("(Our) Global Joint Entropy: %f \n", Entropy.globalJointEntropy(stream, stream2));
				
		//Our local joint entropies
		System.out.printf("(Our) Local Joint Entropy (foreach pair): \n");
		double res[][] = Entropy.localJointEntropy(stream, stream2);
		System.out.printf("	-(0,0): %f \n", res[0][0]);
		System.out.printf("	-(0,1): %f \n", res[0][1]);
		System.out.printf("	-(1,0): %f \n", res[1][0]);
		System.out.printf("	-(1,1): %f \n", res[1][1]);
		
		//Our global joint entropy

		// Lizier local entropy
		System.out.printf("Lizier Local Entropy (foreach cell): \n");
		for (double val : eCalc.computeLocal(stream)) {
			System.out.printf("	- %f \n", val);
		}
	}

}
