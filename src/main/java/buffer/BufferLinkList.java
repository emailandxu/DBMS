package buffer;

public class BufferLinkList {
	BufferNode head = new BufferNode(0);
	BufferNode tail = head;
	int bufferLinkListLength;

	public BufferLinkList() {
	}

	public BufferLinkList(BufferNode node) {
		this.headInsert(node);
	}

	public void printList() {
		BufferNode p = head;
		while (p.next != null) {
			p = p.next;
			System.out.print(p.bufferId + "\t");
		}
		System.out.println();
	}

	private BufferNode find(int bufferId) {
		BufferNode p = head;
		while (p.next != null) {
			p = p.next;
			if (p.bufferId == bufferId) {
				return p;
			}
		}
		return null;
	}

	public void headInsert(BufferNode node) {
		if (head.equals(tail)) {
			tail = node;
		}
		BufferNode headOldNext = head.next;
		if (headOldNext != null) {
			headOldNext.previous = node;
		}
		head.next = node;
		node.previous = head;
		node.next = headOldNext;
		
		this.bufferLinkListLength++;  //长度自增
	}

	public BufferNode pop(Integer bufferId) {
		if (bufferId == head.bufferId) {
			throw new IllegalArgumentException("Buffer id can't be zero!"); // 尝试移除空头节点
		} else if (bufferId == tail.bufferId) { // 尝试移除尾节点
			tail = tail.previous;
		}

		BufferNode node = find(bufferId);
		if (node == null) {
			throw new IndexOutOfBoundsException("Can't find the buffer that id is %s"
					.format(bufferId.toString()));
		}
		node.previous.next = node.next;
		return node;
	}

	public BufferNode pop(BufferNode node) {
		return this.pop(node.bufferId);
	}

	public static void main(String[] args) {
		BufferNode[] a = { new BufferNode(1), new BufferNode(2),
				new BufferNode(3) };
		BufferLinkList bl = new BufferLinkList();
		for (BufferNode bufferNode : a) {
			bl.headInsert(bufferNode);
		}
		bl.printList();
		System.out.println(bl.tail.bufferId);
		bl.headInsert(bl.pop(1));
		bl.printList();
		System.out.println(bl.tail.bufferId);
	}
}
