package com.shaharyi.montecarlo;

public class Main {

	public static void main(String[] args) {
		// Pro-tip for students: Sobol works absolutely best when total points are
		// powers of 2!
		int totalPointsToThrow = 2048;
		int pointsInsideCircle = 0;

		// Create our specialized, object-oriented generator
		ScrambledSobolGenerator dartThrower = new ScrambledSobolGenerator();

		System.out.println("Beginning Monte Carlo Simulation...");
		System.out.println("Throwing " + totalPointsToThrow + " perfectly distributed points...");

		for (int i = 0; i < totalPointsToThrow; i++) {
			// Get a brand new point from our object
			Point point = dartThrower.next2DPoint();

			// Check if the point lands inside the circle boundary using x² + y² <= 1
			double distanceSquared = (point.x() * point.x()) + (point.y() * point.y());

			if (distanceSquared <= 1.0) {
				pointsInsideCircle++;
			}
		}

		// Pi Estimate = (Points Inside / Total Points) * 4
		double estimatedPi = ((double) pointsInsideCircle / totalPointsToThrow) * 4.0;
		double actualPi = Math.PI;
		double error = Math.abs(actualPi - estimatedPi);

		// Print the amazing results!
		System.out.println("\n--- Simulation Results ---");
		System.out.printf("Points inside the circle: %d%n", pointsInsideCircle);
		System.out.printf("Estimated Pi Value:       %.6f%n", estimatedPi);
		System.out.printf("Actual Value of Pi:       %.6f%n", actualPi);
		System.out.printf("Absolute Error Margin:    %.6f%n", error);
	}
}
