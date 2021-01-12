package Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CreateCorpusUser {
	public static void main(String[] args) {
		String filename = "Survey results.xlsx";
		String path = "C:\\Users\\pc\\Documents\\WeChat Files\\mjc1995_\\FileStorage\\File\\2019-05\\";
		File file = new  File(path+filename);
		HSSFWorkbook excel = readExcel(file);
		
	}
	public static HSSFWorkbook readExcel(File file) {
        try {
            // 创建输入流，读取Excel
        	FileInputStream  is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
        	HSSFWorkbook  workbook =  new HSSFWorkbook(is);
            // Excel的页签数量
        	is.close();
        	return workbook;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
          catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
