package record;

public class InnerBlockLocation {
	private int offset;
	private int length;

	/**
	 * 
	 * @param offset
	 *            偏移量
	 * @param length
	 *            长度
	 */
	public InnerBlockLocation(int offset, int length) {
		this.offset = offset;
		this.length = length;
	}

	public byte[] getHeader(byte[] buffer) {
		byte[] header = new byte[this.length];
		for (int i = 0; i < header.length; i++) {
			header[i] = buffer[this.offset + i];
		}
		return header;
	}

	public void setHeader(byte[] header, byte[] buffer) {
		for (int i = 0; i < header.length; i++) {
			buffer[this.offset + i] = header[i];
		}
	}

	public int getPositon() {
		return this.offset + this.length;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
