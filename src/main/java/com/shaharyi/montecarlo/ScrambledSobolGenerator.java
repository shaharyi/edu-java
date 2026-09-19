package com.shaharyi.montecarlo;

import java.util.Random;

public class ScrambledSobolGenerator {
    private static final int BITS = 30;
    
    // CORRECTED: A clean 2D grid: 2 rows (one for X, one for Y) and 30 columns for bits
    private final int[][] directionNumbers = new int[2][BITS];
    private final int[] internalBitState = new int[2];
    private final int[] scrambleMask = new int[2];
    private int pointsGeneratedCount = 0;

    public ScrambledSobolGenerator() {
        setupDirectionNumbers();
        generateRandomScrambleMasks();
    }

    private void setupDirectionNumbers() {
        // Setup X-Axis direction numbers (Row 0)
        for (int i = 0; i < BITS; i++) {
            directionNumbers[0][i] = 1 << (31 - (i + 1));
        }
        
        // Setup Y-Axis direction numbers (Row 1)
        directionNumbers[1][0] = 1 << 30;
        for (int i = 1; i < BITS; i++) {
            directionNumbers[1][i] = directionNumbers[1][i - 1] ^ (directionNumbers[1][i - 1] >> 1);
        }
    }

    private void generateRandomScrambleMasks() {
        Random wallet = new Random();
        scrambleMask[0] = wallet.nextInt(1 << 30); // X mask
        scrambleMask[1] = wallet.nextInt(1 << 30); // Y mask
    }

    /**
     * Ask the generator for a single 1D decimal point.
     * @return a single double between 0.0 and 1.0
     */
    public double next1DPoint() {
        double[] coordinates = calculateNextPoints(1);
        return coordinates[0]; // Just hand back the X-axis coordinate
    }

    /**
     * Ask the generator for a perfectly balanced 2D point.
     * @return a Point object containing X and Y between 0.0 and 1.0
     */
    public Point next2DPoint() {
        double[] coordinates = calculateNextPoints(2);
        return new Point(coordinates[0], coordinates[1]); // Hand back both X and Y
    }

    // This shared private helper handles the actual bitwise math for either 1 or 2 dimensions
    private double[] calculateNextPoints(int dimensions) {
        pointsGeneratedCount++;
        
        int zeroBitIndex = 0;
        int checkValue = pointsGeneratedCount - 1;
        while ((checkValue & 1) == 1) {
            zeroBitIndex++;
            checkValue >>= 1;
        }

        double[] coordinates = new double[dimensions];
        for (int axis = 0; axis < dimensions; axis++) {
            // Use [axis][zeroBitIndex] properly in the fixed 2D grid
            internalBitState[axis] ^= directionNumbers[axis][zeroBitIndex];
            int scrambledBits = internalBitState[axis] ^ scrambleMask[axis];
            coordinates[axis] = (double) (scrambledBits >>> 1) / (1 << 30);
        }
        return coordinates;
    }
}
