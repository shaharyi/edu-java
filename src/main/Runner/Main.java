
public class Main {

	public static void main(String[] args) {

	}

	/*
	 * Q4 We assume there is no null in the array.
	 */
	public static Runner getBest(Runner[] runners) {
		Runner best = runners[0];
		for (int i = 0; i < runners.length; i++) {
			if (runners[i].calcGrade() > best.calcGrade())
				best = runners[i];
		}
		return best;
	}

	/*
	 * Q5 Return cities that don't have any runners.
	 */
	public static String[] orphanCities(String[] cities, Runners[] runners) {
		String[] orphans = new String[cities.length];
		int count = 0;
		for (int i = 0; i < cities.length; i++) {
			String city = cities[i];
			boolean found = false;
			for (int j = 0; j < runners.length; j++) {
				Runner runner = runners[j];
				if (runner.hasCity(city))
					found = true;
			}
			if (!found) {
				orphans[count] = city;
				count++;
			}
		}
		// we want to return array of the exact needed size
		String[] ret = new String[count];
		for (int i = 0; i < count; i++) {
			ret[i] = orphans[i];
		}
		return ret;
	}

	/*
	 * Q6 Return for each city, how many runners competed there If a runner did 2
	 * times or more in a city, it is counted as 1.
	 */
	public static int[] cityPopularity(String[] cities, Runner[] runners) {
		int[] pop = new int[cities.length];
		for (int i = 0; i < cities.length; i++) {
			for (int j = 0; j < runners.length; j++) {
				if (runners[j].hasCity(cities[i]))
					pop[i]++;
			}
		}
		return pop;
	}
}
