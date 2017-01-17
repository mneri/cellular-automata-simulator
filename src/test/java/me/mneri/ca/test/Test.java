package me.mneri.ca.test;

import static org.junit.Assert.*;

import java.util.Random;
import infodynamics.measures.discrete.EntropyCalculatorDiscrete;
import infodynamics.measures.discrete.EntropyRateCalculatorDiscrete;
import infodynamics.measures.discrete.MutualInformationCalculatorDiscrete;
import me.mneri.ca.measures.Entropy;
import me.mneri.ca.measures.Information;

public class Test {

    // test streams
    private int[] streamX;
    private int[] streamY;

    // Lizier Entropy calculators
    EntropyRateCalculatorDiscrete erCalc;
    EntropyCalculatorDiscrete eCalc;
    MutualInformationCalculatorDiscrete miCalc;

    @org.junit.Before
    public void setUp() throws Exception {

        // generating random input streams
        Random rand = new Random(); // java.util.Random
        int len = rand.nextInt(100) + 1;

         // initialize streams
         streamY = new int[len];
         streamX = new int[len];
        
         for (int i = 0; i < len; i++) {
         streamX[i] = rand.nextInt(2);
         streamY[i] = rand.nextInt(2);
         }

        // initializing Lixier's calculators
        erCalc = new EntropyRateCalculatorDiscrete(2, 2);
        eCalc = new EntropyCalculatorDiscrete(2);
        miCalc = new MutualInformationCalculatorDiscrete(2);
        miCalc.addObservations(streamX, streamY);
    }

    @org.junit.Test
    public void localEntropy() {
        assertArrayEquals(Entropy.localEntropy(streamX), eCalc.computeLocal(streamX), 0.000001);
        assertArrayEquals(Entropy.localEntropy(streamY), eCalc.computeLocal(streamY), 0.000001);
    }

    @org.junit.Test
    public void globalEntropy() {
        assertEquals(Entropy.globalEntropy(streamX), eCalc.computeAverageLocal(streamX), 0.000001);
        assertEquals(Entropy.globalEntropy(streamY), eCalc.computeAverageLocal(streamY), 0.000001);
    }

    @org.junit.Test
    public void localMutualInformation() throws Exception {
        assertArrayEquals(Information.localMutualInformation(streamX, streamY),
                miCalc.computeLocalFromPreviousObservations(streamX, streamY), 0.000001);
    }

    @org.junit.Test
    public void globalMutualInformation() {
        double res = miCalc.computeAverageLocalOfObservations();
        double res2 = Information.globalMutualInformation(streamX, streamY);

        assertEquals(Information.globalMutualInformation(streamX, streamY), miCalc.computeAverageLocalOfObservations(),
                0.000001);
    }
}
