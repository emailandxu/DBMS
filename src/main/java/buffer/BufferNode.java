package buffer;

public class BufferNode {
	int bufferId;
	BufferNode previous;
	BufferNode next;
	private byte[] buffer = null;

	BufferNode(int bufferId) {
		this.bufferId = bufferId;
	}
	
	public BufferNode(int bufferId,byte[] buffer) {
		this(bufferId);
		this.buffer = buffer;
	}
	
	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		BufferNode other = (BufferNode) arg0;
		return other.bufferId == this.bufferId;
	}



}
