package disk;

import java.io.File;

import conf.Conf;
import buffer.BufferManagement;

public class DiskManagement {
	/**
	 * 块的大小
	 */
	public static final int BLOCKSIZE = Integer
			.parseInt(Conf.get("blockSize"));
	private static DiskManagement instance;
	
	private FileHandler fh = new FileHandler(new File(Conf.get("dbFilePath")));

	private DiskManagement() {

	}

	public static DiskManagement getInstance() {
		if (instance != null) {
			return instance;
		} else {
			instance = new DiskManagement();
			return instance;
		}
	}

	public int getNewBlockID() {
		return this.fh.getNewBlockID();
	}

	public Block getBlock(int blockID) {
		return new Block(blockID, fh.getBlock(blockID));
	}
	public Block getNewBlock(){
		return new Block(getNewBlockID());
	}
	public void writeBlock(Block block) {
		writeBlock(block.getBlockId(), block.getBuffer());
	}

	private void writeBlock(int blockID, byte[] buffer) {
		fh.writeBlock(blockID, buffer);
	}

	public static void main(String[] args) {
		DiskManagement instance = DiskManagement.getInstance();
		instance.writeBlock(new Block(2, new byte[] { 0, 1, 2, 3 }));
		instance.getBlock(2).printBuffer();
	}

}
