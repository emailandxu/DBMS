package disk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import conf.Conf;

public class FileHandler {
	private File file;
	private RandomAccessFile randomFile;
	/**
	 * 块的大小
	 */
	private static final int BLOCKSIZE = DiskManagement.BLOCKSIZE;
	/**
	 * 所有打开过的文件的数量
	 */
	private static int fileHandlerAmount = 0;
	/**
	 * 文件中块的数量
	 */
	private int blockAmount = 0;
	/**
	 * 当前文件的ID号
	 */
	private int fileHandlerID = 0;

	FileHandler(String filename) {
		this(new File(filename));
	}

	public FileHandler(File file) {
		this.file = file;
		try {
			this.randomFile = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.fileHandlerID = fileHandlerAmount;
		fileHandlerAmount++;
	}

	/**
	 * 在文件末尾申请一个新块，并返回其ID
	 * 
	 * @return
	 * @throws IOException
	 */
	int getNewBlockID() {
		try {
			randomFile.setLength(++blockAmount * BLOCKSIZE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blockAmount - 1; // 当前的块数量-1就是块ID
	}

	/**
	 * 读取块中的数据
	 * 
	 * @param blockID
	 * @return
	 * @throws IOException
	 */
	byte[] getBlock(int blockID) {
		byte[] buffer = new byte[BLOCKSIZE]; // 构造一个与块长度等同的缓冲区
		try {
			// 设置适当的偏移量
			randomFile.seek(blockID * BLOCKSIZE);
			randomFile.read(buffer); // 从文件读入到缓冲区
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 写入数据到块中
	 * 
	 * @param blockID
	 * @param buffer
	 * @throws IOException
	 */
	void writeBlock(int blockID, byte[] buffer) {
		byte[] temp_buffer = new byte[BLOCKSIZE];
		for (int i = 0; i < buffer.length; i++) {
			temp_buffer[i] = buffer[i];
		}
		try {
			randomFile.seek(blockID * BLOCKSIZE);
			randomFile.write(temp_buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 申请一个新的块，然后写入数据，再返回新块的块号
	 * 
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	int writeIntoNewBlock(byte[] buffer) throws IOException {
		int blockID = this.getNewBlockID();
		this.writeBlock(blockID, buffer);
		return blockID;
	}

	/**
	 * 删除文件
	 * 
	 * @throws IOException
	 */
	void delete() {
		try {
			this.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		file.delete();
	}

	/**
	 * 关闭文件
	 * 
	 * @throws IOException
	 */
	void close() throws IOException {
		randomFile.close();
	}

	/**
	 * 返回文件中目前块的数量
	 * 
	 * @return
	 */
	int getBlockSize() {
		return blockAmount;
	}

	/**
	 * 获取当前文件的ID号
	 * 
	 * @return
	 */
	int getFileHandlerID() {
		return fileHandlerID;
	}

	public static void main(String[] args) {
		File dbFile = new File(Conf.get("dbFilePath"));
		try {
			FileHandler fh = new FileHandler(dbFile);
			fh.delete();
			fh = new FileHandler(dbFile);
			byte[] a = new byte[BLOCKSIZE];
			a[0] = 33;
			a[1] = (byte) 255;
			fh.writeIntoNewBlock(a);
			System.out.println(fh.getFileHandlerID());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

}