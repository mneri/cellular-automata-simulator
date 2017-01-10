package me.mneri.ca.measures;

import me.mneri.ca.util.MathUtils;

public class Entropy {

    // calculate global entropy in a binary data stream
    public static double globalEntropy(int[] states) {

        float ones = 0;
        for (int i = 0; i < states.length; i++)
            ones += states[i];

        double fone = ones / states.length;
        double fzero = 1 - fone;
        if (fone == 0.0 || fzero == 0.0)
            return 0.0;

        return -(fone * MathUtils.log2(fone) + fzero * MathUtils.log2(fzero));
    }

    // calculate global joint entropy given two data streams
    public static double globalJointEntropy(int[] x, int[] y) {

        double[][] freqs = pairsFrequencies(x, y);
        double[][] ljes = localJointEntropy(x, y);
        double res = 0.0;

        // averaged sum over local joint entropies
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                res += (ljes[i][j] * freqs[i][j]);

        return res;
    }

    // calculate local entropy in a binary data stream for given value
    public static double localEntropy(int[] states, int value) {

        if (value == 0 || value == 1) {

            float mVal = 0;
            for (int i = 0; i < states.length; i++) {
                if (states[i] == value)
                    mVal++;
            }
            double fval = mVal / states.length;

            return fval != 0.0 ? -MathUtils.log2(fval) : 0.0;

        } else
            throw new IllegalArgumentException("Value must be 1 or 0");
    }

    // calculate local joint entropy given two data streams
    public static double[][] localJointEntropy(int[] x, int[] y) {

        double[][] result = pairsFrequencies(x, y);

        // computing local joint entropies
        result[0][0] = result[0][0] != 0.0 ? -MathUtils.log2(result[0][0]) : 0.0;
        result[0][1] = result[0][1] != 0.0 ? -MathUtils.log2(result[0][1]) : 0.0;
        result[1][0] = result[1][0] != 0.0 ? -MathUtils.log2(result[1][0]) : 0.0;
        result[1][1] = result[1][1] != 0.0 ? -MathUtils.log2(result[1][1]) : 0.0;

        return result;
    }

    // calculate global conditional entropy given two data streams
    public static double globalConditionalEntropy(int[] x, int[] y) {

        return globalJointEntropy(x, y) - globalEntropy(x);
    }

    // calculate local conditional entropy given two data streams
    public static double[][] localConditionalEntropy(int[] x, int[] y) {

        double[][] result = new double[2][2];
        double[][] lje = localJointEntropy(x, y);

        // computing frequencies
        result[0][0] = lje[0][0] - localEntropy(x, 0);
        result[0][1] = lje[0][1] - localEntropy(x, 0);
        result[1][0] = lje[1][0] - localEntropy(x, 1);
        result[1][1] = lje[1][1] - localEntropy(x, 1);

        return result;
    }

    // calculate single value occurrences in a stream
    public static double[] valuesFrequencies(int[] x) {

        double[] result = new double[2];

        for (int i = 0; i < x.length; i++) {
            result[x[i]]++;
        }

        // computing frequencies
        result[0] /= x.length;
        result[1] /= x.length;

        return result;
    }

    // calculate relative frequencies (for each pair) given two data streams
    private static double[][] pairsFrequencies(int[] x, int[] y) {

        if (x.length != y.length)
            throw new IllegalArgumentException("The two arrays must have the same length");

        double[][] result = new double[2][2];
        int n = x.length; // Note: x.length is equal to y.length

        // count pairs occurrences: (0,0) , (0,1) , (1,0) , (1,1)
        for (int i = 0; i < n; i++) {
            result[x[i]][y[i]]++;
        }

        // computing frequencies
        result[0][0] /= n;
        result[0][1] /= n;
        result[1][0] /= n;
        result[1][1] /= n;

        return result;
    }
}