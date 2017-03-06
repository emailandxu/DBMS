package conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Conf {
	static private Properties property = null;
	
	static{
		property = new Properties();
		File file = new File("conf.xml");
		FileInputStream in=null;
		
		try {
			if(!file.exists()){
				file.createNewFile();
				property.load(new FileInputStream(file));
				property.setProperty("encoding", "utf-16");
				property.setProperty("blockSize", "4096");
				property.setProperty("bufferLinkListLimit", "10");
				property.setProperty("dbFilePath", "E:\\DB.xdb");
				property.storeToXML(new FileOutputStream(file), "default", "utf-8");
			}else{
				property.loadFromXML(new FileInputStream(file));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static String get(String key){
		return property.getProperty(key);
	}
	
	public static void main(String[] args) {
		String encoding = Conf.get("encoding");
		System.out.print(encoding);
	}
	
}
