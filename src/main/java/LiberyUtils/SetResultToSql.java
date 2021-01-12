package LiberyUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Champollion.CEDataNLPText; 
import LiberyUtils.AddData;
import LiberyUtils.LiberyCache;
import TransLationModel.ChineseAndEnglishModel;

//将通过champollion对齐好的Ctext文档和Etext文档组装成一份文档
public class SetResultToSql {
	static int NoAnalysCount = 0;

	public static void main(String[] args) throws IOException, SQLException {
		 String ResultPath = "C:\\Users\\superadmin.DLHZ106\\Desktop\\FYPResult\\FYPResult\\MCSplit";
		 String tableName = "ChineseAndEnglishModel";
		 File[] files = new File(ResultPath).listFiles(); 
		for (File file : files) {
			String fileName = file.getName().split("\\.")[0];
			  List<String> resultList = LiberyCache.ReadTextFromTxt(file, "utf-8");
			SetResultToSQL(fileName,resultList,tableName);
		}
	}

	static int count = 0;
	public static void SetResultToSQL(String fileName, List<String> listString,String tableName) throws IOException, SQLException {
		AddData addData = new AddData();
		count++;
		for (int i = 0; i < listString.size(); i=i+2) {
			ChineseAndEnglishModel CE = new ChineseAndEnglishModel(); 
			String ctextString = listString.get(i+1);
			if (!ctextString.equals("___________") &&!ctextString.equals("________")  ) {
				CE.setEnglishData(listString.get(i));
				CE.setChineseData(listString.get(i+1));
				CE.setMemo("FYPMC");
				CE.setTitleID(5050000+count);
				CE.setUrlType(95);
				CE.setURL("");
				CE.setTitle(fileName);
				addData.Add(tableName,CE);
			}
		}  
		System.out.println(count); 
	}

}
