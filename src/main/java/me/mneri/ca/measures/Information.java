package me.mneri.ca.measures;

public class Information {

    // calculate global mutual information given two data streams
    public static double globalMutualInformation(int[] x, int[] y) {

        return Entropy.globalEntropy(x) - Entropy.globalConditionalEntropy(x, y);
    }

    // calculate local mutual information given two data streams
    public static double[] localMutualInformation(int[] x, int[] y) {

        double[] result = new double[x.length];
        double[] lce = Entropy.localJointEntropy(x, y);
        double[] lex = Entropy.localEntropy(x);
        double[] ley = Entropy.localEntropy(y);

        // computing local mutual informations
        for (int i = 0; i < result.length; i++)
            result[i] = lex[i] + ley[i] - lce[i];

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
