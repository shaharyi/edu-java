////// start of Q6b
public class Management {

	private String company;
	private SMS[] arr;
	private int current;
/////// end of 6b

	/*
	 * Q6c
	 */
	public boolean add(SMS s) {
		if (current == arr.length)
			return false;
		arr[current] = s;
		current++;
		return true;
	}

	/*
	 * Q6d
	 */
	public String[] notReplied() {
		boolean[] pair = new boolean[arr.length];
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			if (!pair[i]) {
				int j = 0;
				boolean found = false;
				while (j < arr.length && !found) {
					found = arr[j].isReply(arr[i]);
					j++;
				}
				if (found) {
					pair[i] = true;
					pair[j] = true;
				} else {
					count++;
				}
			}
		}
		String[] ret = new String[count];
		int j = 0;
		for (int i = 0; i < pair.length; i++) {
			if (!pair[i]) {
				ret[j] = arr[i].getReceiver();
			}
		}
		return ret;
	}

}
