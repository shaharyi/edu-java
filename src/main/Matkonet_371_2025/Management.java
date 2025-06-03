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
		String[] r = new String[arr.length];
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			int j = 0;
			boolean found = false;
			while (j < arr.length && !found) {
				found = arr[j].isReply(arr[i]) || arr[i].isReply(arr[j]);
				j++;
			}
			if (!found) {
				r[count] = arr[i].getReceiver();
				count++;
			}
		}
		String[] ret = new String[count];
		for (int i = 0; i < count; i++) {
			ret[i] = r[i];
		}
		return ret;
	}
}
