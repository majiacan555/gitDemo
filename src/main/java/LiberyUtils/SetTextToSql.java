package LiberyUtils;

 
import java.io.File; 
import java.io.IOException; 
import java.sql.SQLException;
import java.util.List;
 
import TransLationModel.ChineseAndEnglishModel; 
 

public class SetTextToSql {
	public static void main(String[] args) throws IOException, SQLException {
		String basePath = "E:\\数据分析\\HLM\\"; 
		String typeString = "Yang";
		basePath = basePath + typeString+"\\";
		int csubNum = 2;
		int esubNum = 3;
		String ChinesePath =basePath+ "Chinese";
		String EnglishPath = basePath + "English"; 
		File cFile = new File(ChinesePath); 
		File eFile = new File(EnglishPath);
		File[] cfiles = cFile.listFiles();
		File[] efiles = eFile.listFiles();
		for (File file : cfiles) {
			String cfileNum = file.getName().substring(csubNum);
			cfileNum = cfileNum.replace(".txt.txt", "");
			for (File file2 : efiles) {
				String efileNum = file2.getName().substring(esubNum);
				efileNum = efileNum.replace(".txt", "");
				if (cfileNum.equals(efileNum)) {
					List<String> clistString =LiberyCache. ReadTextFromTxt( file, "gbk");
					List<String> elistString =LiberyCache. ReadTextFromTxt( file2, "gbk");
					if (clistString.size() !=elistString.size() ) { 
						System.out.println(file.getName()+" "+file2.getName()+"配对失败");
					}
					else { 
						int count = 0; 
						AddData addData = new AddData();
						for (int i = 0; i < clistString.size(); i++) {
							count++;
							ChineseAndEnglishModel CE = new ChineseAndEnglishModel(); 
							CE.setChineseData(clistString.get(i));
							CE.setEnglishData(elistString.get(i));
							CE.setMemo("hongloumeng");
							CE.setTitleID(3000000+count);
							CE.setUrlType(98);
							CE.setURL(typeString);
							CE.setTitle("Chapter " + efileNum);
							addData.Add("ChineseAndEnglishModel",CE);
							System.out.println(count);
						}   
						System.out.println(file.getName()+" "+file2.getName()+"配对成功");
					}
				}
			}
		}
		
	}
	 
	 
}
