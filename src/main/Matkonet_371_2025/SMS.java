
public class SMS {
	private String sender;
	private String receiver;
	private String text;

	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getText() {
		return text;
	}

	public boolean isReply(SMS other) {
		return receiver.equals(other.sender);
	}	
}
