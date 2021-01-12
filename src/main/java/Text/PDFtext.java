package Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
 
import org.bouncycastle.jce.provider.asymmetric.ec.Signature.ecCVCDSA;

import TransLationModel.CEDataNLPText;

public class PDFtext {
	public static void main(String[] args) throws IOException 
	{
		PDFtext obj = new PDFtext();
		String filepath = "D:\\TaiWanText";//D盘下的file文件夹的目录
		File Basefile = new File(filepath);  
		File writename = new File("D:\\result\\output.txt"); 
		if (!writename.getParentFile().exists()) {
			writename.getParentFile().mkdirs();
		}
		if (!writename.exists()) {
			writename.createNewFile();
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
	    writename.createNewFile();
		File file = new File(filepath);//File类型可以是文件也可以是文件夹
		File[] fileList = file.listFiles();//将该目录下的所有文件放置在一个File类型的数组中
		List<File> wjjList = new ArrayList<File>();//新建一个文件夹集合
		for (int i = 0; i < fileList.length; i++) {
		   if (fileList[i].isDirectory()) 
		        wjjList .add(fileList[i]);
		}
		for (int i = 0; i < wjjList.size(); i++) 
		{
		   if (wjjList.get(i).isDirectory()) 
		   {//判断是否为文件夹
			   File[] textFiles = wjjList.get(i).listFiles();
			   List<String> Result = obj.getFileText(textFiles[0]);
			   List<String> listC = obj.getFileText(textFiles[1]);
			   List<String> listE = obj.getFileText(textFiles[2]);
			   List<CEDataNLPText> finalResult = new ArrayList<>();
			   for (int j = 0; j < Result.size(); j++) {
				   String[] spiltStrings = Result.get(j).split("<=>");
				   String[] spiltE = spiltStrings[0].split(",");
				   String[] spiltC = spiltStrings[1].split(",");
				   CEDataNLPText text = new CEDataNLPText();
				   String CData = "";
				   String EData = "";
				   for (int k = 0; k < spiltE.length; k++) {
					   if (!spiltE[k].contains("omitted")) 
						   EData += listE.get(Integer.parseInt(spiltE[k].trim())-1);
				   }
				   for (int k = 0; k < spiltC.length; k++) {
					   if (!spiltC[k].contains("omitted")) 
						   CData += listC.get(Integer.parseInt(spiltC[k].trim())-1);
				   }
				   text.setChineseData(CData);
				   text.setEnglishData(EData);
				   finalResult.add(text);
			   }
			   
			   String English = "";
			   String Chinese = "";
			   for (int j = 0; j < finalResult.size(); j++)
			   {
				   if (finalResult.get(j).getEnglishData()!=null && finalResult.get(j).getEnglishData().length()>0 &&finalResult.get(j).getChineseData()!=null && finalResult.get(j).getChineseData().length()>0) {
					   out.write(English + finalResult.get(j).getEnglishData()+"\r\n");
					   out.write(Chinese +finalResult.get(j).getChineseData()+"\r\n");
					   English = "";
					   Chinese = "";
				   }
				   else {
					   if (finalResult.get(j).getEnglishData()==null ||finalResult.get(j).getEnglishData().length()<=0)
						   Chinese += finalResult.get(j).getChineseData();
					   if (finalResult.get(j).getChineseData()==null || finalResult.get(j).getChineseData().length()<=0) 
						   English+= finalResult.get(j).getEnglishData();
				   }
		           out.flush(); // 把缓存区内容压入文件  
//				   System.out.println(finalResult.get(j).getEnglishData());
//				   System.out.println(finalResult.get(j).getChineseData());
			   }
		   }
		}
		out.close(); 
	}

	public  List<String> getFileText(File file) throws IOException {
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
