package disk;

public class Block {
	private int blockId;
	private byte[] buffer;

	public Block(int blockId, byte[] buffer) {
		this.setBlockId(blockId);
		this.setBuffer(buffer);
	}
	
	public Block(int blockId) {
		this(blockId, new byte[DiskManagement.BLOCKSIZE]);
	}
	
	public void printBuffer() {
		for (byte b : buffer) {
			System.out.print(b);
		}
		System.out.println();
	}

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

}
