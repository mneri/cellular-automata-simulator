package me.mneri.ca.measures;

import java.util.Hashtable;

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

    // calculate local entropy in a binary data stream for given value
    public static double[] localEntropy(int[] states) {

        double countOnes = 0.0;
        double result[] = new double[states.length];

        for (int i = 0; i < states.length; i++) {
            countOnes += states[i];
        }

        // compute frequencies array
        double[] freqs = new double[2];
        freqs[1] = countOnes / (double) states.length;
        freqs[0] = 1.0 - freqs[1];

        for (int i = 0; i < result.length; i++) {
            result[i] = -MathUtils.log2(freqs[states[i]]);
        }

        return result;
    }

    // calculate global joint entropy given two data streams
    public static double globalJointEntropy(int[] x, int[] y) {

        double[][] freqs = pairsFrequencies(x, y);
        double res = 0.0;

        // averaged sum over local joint entropies
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                if (freqs[i][j] != 0)
                    res += (freqs[i][j] * MathUtils.log2(freqs[i][j]));

        return -res;
    }

    // calculate local joint entropy given two data streams
    public static double[] localJointEntropy(int[] x, int[] y) {

        double[][] freqs = pairsFrequencies(x, y);
        double[] res = new double[x.length];

        // computing local joint entropies
        freqs[0][0] = -MathUtils.log2(freqs[0][0]);
        freqs[0][1] = -MathUtils.log2(freqs[0][1]);
        freqs[1][0] = -MathUtils.log2(freqs[1][0]);
        freqs[1][1] = -MathUtils.log2(freqs[1][1]);

        for (int i = 0; i < res.length; i++)
            res[i] = freqs[x[i]][y[i]];

        return res;
    }

    // calculate global conditional entropy given two data streams
    public static double globalConditionalEntropy(int[] x, int[] y) {

        return globalJointEntropy(x, y) - globalEntropy(y);
    }

    // calculate local conditional entropy given two data streams
    public static double[] localConditionalEntropy(int[] x, int[] y) {

        double[] result = new double[x.length];
        double[] lje = localJointEntropy(x, y);
        double[] lex = localEntropy(y);

        // computing frequencies
        for (int i = 0; i < result.length; i++)
            result[i] = lje[i] - lex[i];

        return result;
    }

    // calculate local conditional entropy given two data streams
    public static double[][] localEntropyRate(int[][] matrix, int k) {

        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] result = new double[rows][cols];
        // double[][] histOcc = new double[2][(int) Math.pow(2, k)];
        Hashtable<Integer, Hashtable<Integer, Integer>> histOcc = new Hashtable<Integer, Hashtable<Integer, Integer>>();

        int observations = (rows - k) * cols;

        for (int i = 0; i < cols; i++) {
            for (int h = 0; h < rows - k; h++) {

                int dec = 0;

                // calculate decimal representation of history slot
                int j;
                for (j = h; j < h + k; j++) {
                    dec *= 2;
                    dec += matrix[j][i];
                }

                // update occurrences
                // histOcc[matrix[j][i]][dec]++;

                if (histOcc.get(dec) != null) {
                    if (histOcc.get(dec).get(matrix[j][i]) != null)
                        histOcc.get(dec).put(matrix[j][i], histOcc.get(dec).get(matrix[j][i]) + 1);
                    else
                        histOcc.get(dec).put(matrix[j][i], 1);
                } else {
                    histOcc.put(dec, new Hashtable<Integer, Integer>());
                    histOcc.get(dec).put(matrix[j][i], 1);
                    // insert the other counter
                    histOcc.get(dec).put(1 - matrix[j][i], 0);
                }
            }
        }

        for (int i = 0; i < cols; i++) {
            for (int h = 0; h < rows - k; h++) {

                int dec = 0;
                double pxy;
                double py;

                // calculate decimal representation of history slot
                int j;
                for (j = h; j < h + k; j++) {
                    dec *= 2;
                    dec += matrix[j][i];
                }

                // calculate frequencies
                // pxy = histOcc[matrix[j][i]][dec] / observations;
                pxy = (double) histOcc.get(dec).get(matrix[j][i]) / observations;
                // py = (histOcc[0][dec] + histOcc[1][dec]) / observations;
                py = (double) (histOcc.get(dec).get(0) + histOcc.get(dec).get(1)) / observations;

                // update the cell
                result[j][i] = -MathUtils.log2(pxy / py);
            }
        }
        return result;
    }

    // calculate excess entropy
    public static double averagedEntropyRate(int[][] matrix, int k) {
        double res = 0.0;
        int rows = matrix.length;
        int cols = matrix[0].length;

        // double[][] histOcc = new double[2][(int) Math.pow(2, k)];
        Hashtable<Integer, Hashtable<Integer, Integer>> histOcc = new Hashtable<Integer, Hashtable<Integer, Integer>>();

        int observations = (rows - k) * cols;

        for (int i = 0; i < cols; i++) {
            for (int h = 0; h < rows - k; h++) {

                int dec = 0;

                // calculate decimal representation of history slot
                int j;
                for (j = h; j < h + k; j++) {
                    dec *= 2;
                    dec += matrix[j][i];
                }

                // update occurrences
                // histOcc[matrix[j][i]][dec]++;

                if (histOcc.get(dec) != null) {
                    if (histOcc.get(dec).get(matrix[j][i]) != null)
                        histOcc.get(dec).put(matrix[j][i], histOcc.get(dec).get(matrix[j][i]) + 1);
                    else
                        histOcc.get(dec).put(matrix[j][i], 1);
                } else {
                    histOcc.put(dec, new Hashtable<Integer, Integer>());
                    histOcc.get(dec).put(matrix[j][i], 1);
                    // insert the other counter
                    histOcc.get(dec).put(1 - matrix[j][i], 0);
                }
            }
        }

        // for (int i = 0; i < histOcc.length; i++) {
        for (Integer i : histOcc.keySet()) {
            for (int j = 0; j < 2; j++) {
                // calculate frequencies
                // double pxy = histOcc[j][i] / observations;
                double pxy = (double) histOcc.get(i).get(j) / observations;
                // double py = (histOcc[0][i] + histOcc[1][i]) / observations;
                double py = (double) (histOcc.get(i).get(0) + histOcc.get(i).get(1)) / observations;
                if (pxy > 0.0)
                    // update the cell
                    res += pxy * -MathUtils.log2(pxy / py);
            }
        }

        return res;
    }

    // excess entropy
    public static double excessEntropy(int[][] matrix) {
        double res = 0.0;
        int rows = matrix.length;
        double aee = averagedEntropyRate(matrix, rows - 1);

        for (int i = 1; i <= rows - 2; i++) {
            res += averagedEntropyRate(matrix, i) - aee;
        }
        return res;

    }

    // excess entropy
    public static double[][] localExcessEntropy(int[][] matrix, int k) {

        int rows = matrix.length;
        int cols = matrix[0].length;

        double res[][] = new double[rows][cols];

        double[][] lerK = localEntropyRate(matrix, k);
        double[][] lerMax = localEntropyRate(matrix, rows - 1);

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                res[i][j] = lerK[i][j] - lerMax[i][j];

        return res;

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
    public static double[][] pairsFrequencies(int[] x, int[] y) {

        if (x.length != y.length)
            throw new IllegalArgumentException("The two arrays must have the same length.");

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