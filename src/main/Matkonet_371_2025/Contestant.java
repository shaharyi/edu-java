
public class Contestant {
	private int id;
	private String name;
	private Solution[] solutions;
	private int count;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	/*
	 * Q4b
	 */
	public boolean addSolution(int problemId, int time, int attempts) {
		if (count >= solutions.length)
			return false;
		Solution s = new Solution(problemId, time, attempts);
		solutions[count] = s;
		count++;
		return true;
	}

	/*
	 * Q4c
	 */
	public double calculateTotalScore() {
		double sum = 0;
		for (int i = 0; i < count; i++) {
			sum += solutions[i].calculateScore();
		}
		return sum / count;
	}

}
