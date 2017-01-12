package me.mneri.ca.test;

import static org.junit.Assert.*;

import java.util.Random;
import infodynamics.measures.discrete.EntropyCalculatorDiscrete;
import infodynamics.measures.discrete.EntropyRateCalculatorDiscrete;
import me.mneri.ca.measures.Entropy;

public class Test {

    // test streams
    private int[] streamX;
    private int[] streamY;

    // Lizier Entropy calculators
    EntropyRateCalculatorDiscrete erCalc = new EntropyRateCalculatorDiscrete(2, 2);
    EntropyCalculatorDiscrete eCalc = new EntropyCalculatorDiscrete(2);

    @org.junit.Before
    public void setUp() {

        // generating random input streams
        Random rand = new Random(); // java.util.Random
        int len = rand.nextInt(100) + 1;

        streamY = new int[len];
        streamX = new int[len];

        for (int i = 0; i < len; i++) {
            streamX[i] = rand.nextInt(2);
            streamY[i] = rand.nextInt(2);
        }
    }

    @org.junit.Test
    public void localEntropy() {
        assertArrayEquals(Entropy.localEntropy(streamX), eCalc.computeLocal(streamX), 0.000001);
        assertArrayEquals(eCalc.computeLocal(streamY), Entropy.localEntropy(streamY), 0.000001);
    }

    @org.junit.Test
    public void globalEntropy() {
        assertEquals(Entropy.globalEntropy(streamX), eCalc.computeAverageLocal(streamX), 0.000001);
        assertEquals(Entropy.globalEntropy(streamY), eCalc.computeAverageLocal(streamY), 0.000001);
    }
}
