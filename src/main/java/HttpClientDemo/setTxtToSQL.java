package HttpClientDemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import LiberyUtils.AddData;
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;

public class setTxtToSQL {
	public static void main(String[] args) throws IOException, SQLException {
		File file = new File("D:\\AnalysResult2"); 
		File[] files = file.listFiles();
		int count = 0;
		for (File file2 : files) {
			List<String> listString = getFileText( file2);
			AddData addData = new AddData();
			for (int i = 0; i < listString.size(); i=i+2) {
				count++;
				ChineseAndEnglishModel CE = new ChineseAndEnglishModel(); 
				CE.setChineseData(listString.get(i+1));
				CE.setEnglishData(listString.get(i)+".");
				CE.setMemo("��д");
				CE.setTitleID(2000000+count);
				CE.setUrlType(99);
				CE.setURL("");
				CE.setTitle(file2.getName());
				addData.Add(CE);
				System.out.println(count);
			} 
			System.out.println("������ϣ�"+file2.getName());
		} 
	}
	 
	 
	public static List<String> getFileText(File file) throws IOException {
		List<String> list = new ArrayList<>();
		InputStream inputStream1 = new FileInputStream(file);
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(inputStream1, "gbk"));
		String line;
		while ((line = br.readLine()) != null)
		{
			list.add(line);
		}
		br.close();
		inputStream1.close();
		return list; 
	}
}
