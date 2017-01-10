package me.mneri.ca.measures;

public class Information {

    // calculate global mutual information given two data streams
    public static double globalMutualInformation(int[] x, int[] y) {

        return Entropy.globalEntropy(x) - Entropy.globalConditionalEntropy(x, y);
    }

    // calculate local mutual information given two data streams
    public static double[][] localMutualInformation(int[] x, int[] y) {
        
        double[][] result = new double[2][2];
        double[][] lce = Entropy.localConditionalEntropy(x, y);

        // computing local mutual informations
        result[0][0] = Entropy.localEntropy(x, 0) + Entropy.localEntropy(y, 0) - lce[0][0];
        result[0][1] = Entropy.localEntropy(x, 0) + Entropy.localEntropy(y, 1) - lce[0][1];
        result[1][0] = Entropy.localEntropy(x, 1) + Entropy.localEntropy(y, 0) - lce[1][0];
        result[1][1] = Entropy.localEntropy(x, 1) + Entropy.localEntropy(y, 1) - lce[1][1];

        return result;
    }

    // calculate global mutual information given two data streams
    public static double globalConditionalMutualInformation(int[] x, int[] y, int[] z) {
       
        // TODO
        return 0.0;
    }

    // calculate local conditional mutual information given two data streams
    public static double localConditionalMutualInformation(int[] x, int y, int[] z) {
        
        // TODO
        return 0.0;
    }
}
