
public class Train {
	private int trainNum;
	private Carriage[] arrCarriage;

	/*
	 * Q5a
	 */
	public int maxPass() {
		int r = 0;
		for (int i = 0; i < arrCarriage.length; i++) {
			r += arrCarriage[i].getMaxPass();
		}
		return r;
	}

	/*
	 * Q5b
	 */
	public void leave(int i, int n) {
		int c = arrCarriage[i].getCurrentPass();
		arrCarriage[i].setCurrentPass(c - n);
	}

	/*
	 * Q5c
	 */
	public int join(int i, int want) {
		Carriage c = arrCarriage[i];
		int curr = c.getCurrentPass();
		int free = c.getMaxPass() - curr;
		int can = Math.min(want, free);
		c.setCurrentPass(curr + can);
		return want - can;
	}

	/*
	 * Q5d
	 */
	public int board(int want) {
		for (int i = 0; i < arrCarriage.length; i++) {
			want = join(i, want);
		}
		return want;
	}	
}
