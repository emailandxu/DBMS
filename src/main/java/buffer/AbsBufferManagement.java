package buffer;

import conf.Conf;

public abstract class AbsBufferManagement {
	protected int bufferLinkListLimit = Integer.parseInt(Conf.get("bufferLinkListLimit"));
	protected BufferLinkList bl = new BufferLinkList();
	
	//需要被实现的方法
	abstract BufferNode loadNewBuffer(int bufferId);
	abstract void freeBuffer(BufferNode node);
	
	
	public BufferNode useBuffer(int bufferId){
		BufferNode buffer = null;
		try {
			buffer = bl.pop(bufferId);
			bl.headInsert(buffer);
		} catch (IndexOutOfBoundsException e) {  //没有在缓冲区中找到缓存，就要去硬盘中查找
			buffer = loadNewBuffer(bufferId);
			bl.headInsert(buffer);
			if(bl.bufferLinkListLength>this.bufferLinkListLimit){
				BufferNode tail = bl.pop(bl.tail);
				int tailId = tail.bufferId;
				freeBuffer(tail);   //缓冲区满，则释放链表尾部节点
				System.out.println("释放节点："+tailId);
			}
		}
		return buffer;
	}
	
	public void flush(){
		while(true){
			try {
				freeBuffer(bl.pop(bl.tail));
			} catch (IllegalArgumentException e) {
				System.out.println("缓冲区已清空");
				return;
			}
		}
	}
	
}
