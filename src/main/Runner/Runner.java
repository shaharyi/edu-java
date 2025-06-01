
public class Runner {
	private String name;
	private Competition[] competitions;
	private int count;

	public Runner(String name) {
		this.name = name;
		count = 0;
		competitions = new Competition[10];
	}

	public String getName() {
		return name;
	}

	// Q1
	public boolean addComp(String city, int place) {
		if (count < competitions.length) {
			competitions[count] = new Competition(city, place);
			count++;
			return true;
		}

		return false;
	}

	// Q2
	public double calcGrade() {
		int sum = 0;
		for (int i = 0; i < count; i++) {
			int grade = 100 - 10 * (competitions[i].getPlace() - 1);
			if (grade > 0)
				sum += grade;
		}
		return (double) sum / count;
	}

	// Q3
	public boolean hasCity(String city) {
		for (int i = 0; i < count; i++) {
			if (competitions[i].getCity().equals(city))
				return true;
		}
		return false;
	}
}
