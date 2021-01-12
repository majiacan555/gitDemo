package Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class asad {
	public static void main(String[] args) throws IOException {
		File _4846file = new File("D://4846.txt");
		File _4817file = new File("D://4817.txt");
		String _4846_ = getFileText(_4846file);
		String _4817_ = getFileText(_4817file);
		 String[] aaa = _4846_.split(","); 
		 String[] bbb = _4817_.split(",");
		 
		for (int i = 0; i < bbb.length; i++) {
			String bString = bbb[i];
			boolean isExit = false;
			for (int k = 0; k < aaa.length;  k++) {
				String aString = aaa[k];
				if (bString.equals(aString)) { 
					isExit = true;
				}
				
			}
			if (!isExit) {
				System.out.println("在4817不在4846的票"+bString);
			}
		}
	}
	public static  String getFileText(File file) throws IOException { 
		InputStream inputStream1 = new FileInputStream(file);
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(inputStream1, "gbk"));
		String line;
		String string = "";
		int Count = 0;
		while ((line = br.readLine()) != null )
		{
			if (  line.trim().length()>0) { 
				string+= line; 
			}
		}
		br.close();
		inputStream1.close();
		return string;
		
	}
}
