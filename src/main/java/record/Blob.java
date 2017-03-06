package record;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;

import disk.Block;
import disk.DiskManagement;

public class Blob {
	private InnerBlockLocation isGroupOffset = new InnerBlockLocation(0, 1);
	private InnerBlockLocation hasNextOffset = new InnerBlockLocation(1, 1);
	private InnerBlockLocation nextBlockPositionOffset = new InnerBlockLocation(2, 8);
	private InnerBlockLocation dataEndPosition = new InnerBlockLocation(10, 12);
	private InnerBlockLocation lastheader = dataEndPosition; // 最后一个头信息

	public Blob() {

	}

	public Blob(int basicOffset) {
		isGroupOffset = new InnerBlockLocation(basicOffset + 0, 1);
		hasNextOffset = new InnerBlockLocation(basicOffset + 1, 1);
		nextBlockPositionOffset = new InnerBlockLocation(basicOffset + 2, 8);
	}

	public Block[] separateBlock(byte[] buffer) {
		int realBlockSize = DiskManagement.BLOCKSIZE - lastheader.getPositon(); // 去掉头信息的块大小
		int realStart = lastheader.getPositon();

		int blockAmountNeeded = (buffer.length / realBlockSize) + 1;
		Block[] blocks = new Block[blockAmountNeeded];

		int tempBlockIndex = 0;
		for (int i = 0; i < blocks.length; i++) {

			blocks[tempBlockIndex] = DiskManagement.getInstance().getNewBlock();

			for (int j = 0; j < realBlockSize; j++) {
				if (tempBlockIndex * realBlockSize + j >= buffer.length) {
					break;
				}
				blocks[tempBlockIndex].getBuffer()[realStart + j] = buffer[tempBlockIndex
						* realBlockSize + j];

			}
			tempBlockIndex++;
		}
		return blocks;
	}

	public InnerBlockLocation getIsGroupOffset() {
		return isGroupOffset;
	}

	public void setIsGroupOffset(InnerBlockLocation isGroupOffset) {
		this.isGroupOffset = isGroupOffset;
	}

	public InnerBlockLocation getHasNextOffset() {
		return hasNextOffset;
	}

	public void setHasNextOffset(InnerBlockLocation hasNextOffset) {
		this.hasNextOffset = hasNextOffset;
	}

	public InnerBlockLocation getNextPostionOffset() {
		return nextBlockPositionOffset;
	}

	public void setNextPostionOffset(InnerBlockLocation nextPostionOffset) {
		this.nextBlockPositionOffset = nextPostionOffset;
	}

	public static void main(String[] args) throws FileNotFoundException {
		FileInputStream file = new FileInputStream("E:\\132MP0MLGNX.jpg");
		Blob blob = new Blob();
		byte[] b = new byte[10240];
		try {
			file.read(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Block[] blocks = blob.separateBlock(b);
		
		for (Block block : blocks) {
			block.printBuffer();
		}

	}
}
