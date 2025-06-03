
public class Solution {

	private int problemId;
	private int time;
	private int attempts;

	public Solution(int problemId, int time, int attempts) {
		this.problemId = problemId;
		this.time = time;
		this.attempts = attempts;
	}

	/*
	 * Q4a
	 */
	public double calculateScore() {
		double grade = 100;
		if (time - 60 > 0)
			grade -= (time - 60) * 0.5;
		grade -= (attempts - 1) * 10;
		if (grade >= 0)
			return grade;
		return 0;
	}	
}
