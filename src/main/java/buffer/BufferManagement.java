package buffer;

public class BufferManagement extends AbsBufferManagement{
	private static AbsBufferManagement instance = null;
	
	private BufferManagement(){
		super();
	}
	
	public static AbsBufferManagement getInstance(){
		if(instance!=null){
			return instance;
		}else{
			instance = new BufferManagement();
			return instance;
		}
	}

	BufferNode loadNewBuffer(int bufferId){
		System.out.println("未找到节点："+bufferId +"：尝试创建");
		return new BufferNode(bufferId);
	}
	
	void freeBuffer(BufferNode node){
		node  = null;
	}
	
	public static void main(String[] args) {
		System.out.println("缓冲区大小为:"+ BufferManagement.getInstance().bufferLinkListLimit);
		for (int i = 1; i < 15; i++) {
			BufferManagement.getInstance().useBuffer(i);
		}
		BufferManagement.getInstance().useBuffer(1);
		BufferManagement.getInstance().useBuffer(9);
		BufferManagement.getInstance().bl.printList();
		BufferManagement.getInstance().flush();
	}
}
