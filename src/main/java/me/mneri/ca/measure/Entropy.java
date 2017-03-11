package me.mneri.ca.measure;

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

    // calculate global entropy in a binary data stream
    public static double globalEntropy(int[][] states) {

        int rows = states.length;
        int cols = states[0].length;
        float ones = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                ones += states[i][j];

        double fone = ones / (rows * cols);
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

        // H(X|Y) = H(X,Y) - H(Y)
        return globalJointEntropy(x, y) - globalEntropy(y);
    }

    // calculate local conditional entropy given two data streams
    public static double[] localConditionalEntropy(int[] x, int[] y) {

        double[] result = new double[x.length];
        double[] lje = localJointEntropy(x, y);
        double[] lex = localEntropy(y);

        // computing frequencies:
        // h(x|y) = h(x,y) - h(y)
        for (int i = 0; i < result.length; i++)
            result[i] = lje[i] - lex[i];

        return result;
    }

    // calculate local conditional entropy given two data streams
    public static double[][] localBlockEntropy(int[][] matrix, int k) {

        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] result = new double[rows][cols];
        Hashtable<Integer, int[]> histOcc = new Hashtable<Integer, int[]>();

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
                if (histOcc.get(dec) != null) {
                    histOcc.get(dec)[matrix[j][i]]++;

                } else {
                    histOcc.put(dec, new int[2]);
                    histOcc.get(dec)[matrix[j][i]] = 1;

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
                pxy = (double) histOcc.get(dec)[matrix[j][i]] / observations;
                py = (double) (histOcc.get(dec)[0] + histOcc.get(dec)[1]) / observations;

                // update the cell
                result[j][i] = -MathUtils.log2(pxy / py);
            }
        }
        return result;
    }

    // calculate excess entropy
    public static double averagedBlockEntropy(int[][] matrix, int k) {
        double res = 0.0;
        int rows = matrix.length;
        int cols = matrix[0].length;

        Hashtable<Integer, int[]> histOcc = new Hashtable<Integer, int[]>();

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
                if (histOcc.get(dec) != null) {
                    histOcc.get(dec)[matrix[j][i]]++;
                } else {
                    histOcc.put(dec, new int[2]);
                    histOcc.get(dec)[matrix[j][i]] = 1;
                }
            }
        }

        // Calculate ER due to the formula obtained
        // via the conditional entropy:
        //
        // Hm = P( Xn, X(L) ) * log( P( Xn,X(L) ) / P( X(L) ) )
        // given P( Xn | X(L) ) = P( Xn, X(L) ) / P( X(L) )

        // for (int i = 0; i < histOcc.length; i++) {
        for (Integer i : histOcc.keySet()) {
            for (int j = 0; j < 2; j++) {
                // calculate frequencies
                double pxy = (double) histOcc.get(i)[j] / observations;
                double py = (double) (histOcc.get(i)[0] + histOcc.get(i)[1]) / observations;
                if (pxy > 0.0)
                    // update the cell
                    res += pxy * -MathUtils.log2(pxy / py);
            }
        }
        return res;
    }

    // return an array of differences of crescent k-entropy rate
    // or second differences
    public static double[] derivedBlockEntropy(double[] abe, int liv) {

        int rows = abe.length;
        double[] der = new double[rows];

        // calculate first difference
        for (int i = 1; i < rows; i++)
            der[i - 1] = -(abe[i] - abe[i - 1]);
        
       if(liv==1){
        // calculate second difference
           for (int i = 1; i < rows; i++)
               der[i - 1] = -(der[i] - der[i - 1]);
        }

        return der;

    }

    public static double[] derivedBlockEntropy(double[] abe) {
        return derivedBlockEntropy(abe, 1);
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