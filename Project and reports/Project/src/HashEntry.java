
public class HashEntry {

	private long key;
	private Node value;
	private SingleLinkedList SLL;

	public SingleLinkedList getSLL() {
		return SLL;
	}

	public void setSLL(SingleLinkedList sLL) {
		SLL = sLL;
	}

	HashEntry(long key, Node value) {
		this.key = key;
		this.value = value;
	}

	public long getKey() {
		return key;
	}

	public Node getValue() {
		return value;
	}

}
