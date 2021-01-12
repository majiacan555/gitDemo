package HttpClientDemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AnalysisTaiwan_Panorama {
	public static void main(String[] args) throws Exception { 
		File file = new File("C:\\Users\\pc\\Desktop\\Taiwan_Panorama\\text"); 
		File[] files = file.listFiles();
		int Count =0; 
		
		for (File file2 : files) {
			String title = "";
			List<String> stringList = getFileText(file2,"gbk");
			String result  = "";
			for (String string : stringList)  
				result += string; 
			String[] strings = result.split("@#@#"); 
			String URL = strings[1];
			Count++;
			System.out.println("开始解析："+URL +"-----"+Count); 
			Document doc = Jsoup.parse(result,"UTF-8");
			Element memoElements = doc.getElementById("detail_Text"); 
			Element EmglishElement = doc.getElementById("lang-3"); 
			if (memoElements!=null &&EmglishElement!=null) {
				title = memoElements.getElementsByClass("detail_Title").text();  
				Elements summaryelements = memoElements.getElementsByClass("summary");
				List<String> CtextList = new ArrayList<String>();
				if (!summaryelements.get(0).children().hasClass("p1")) {
					String TEXT = summaryelements.text();  
					if (TEXT.trim().length()>=2) {
						String[] sstrings = TEXT.split("。");
						for (String string : sstrings) {
							CtextList.add(string+"。");    
						}
					} 
				}
				Elements elements = memoElements.getElementsByClass("p1");
				Elements elements2 = memoElements.getElementsByClass("p2");
				Elements elements3 = memoElements.getElementsByClass("p3");
				if ((elements2.hasText()&&elements2.size()>3)||(elements3.hasText()&&elements3.size()>3)) { 
					continue;
				} 
				if ((elements==null||elements.size()<1)) { 
					elements = memoElements.getElementsByTag("p"); 
				}  
				for (Element element : elements) {
					String TEXT = element.text();
					if (TEXT.trim().length()>=2) {
						String[] sstrings = TEXT.split("。");
						for (String string : sstrings) {
							if (string.contains("「"))  
								string = string.replace("「", "");  
							if (string.contains("」"))  
								string = string.replace("」", ""); 
							if (string.contains("?"))  
								string = string.replace("?", ""); 
							if (string.contains("§"))  
								string = string.replace("§", ""); 
							if (string.contains("※"))  
								string = string.replace("※", ""); 
							if (string.contains("＊"))  
								string = string.replace("＊", ""); 
							
							if (string.trim().length()>=2) {
							CtextList.add(string+"。");  
							}
							 
						}
					} 
				} 
				String sourcePath = "C://TaiWanText//"+Count+"//";
				if (sourcePath.contains("?")) {
					sourcePath = sourcePath.replace("?", "");
				} 
				String cpath = sourcePath+ "TextC.txt"; 
				List<String> EtextList = new ArrayList<String>();  
				String text =  EmglishElement.text();
				String[] textstrings = text.split("\\.");
				for (String string : textstrings) {
					if (string.contains("「"))  
						string = string.replace("「", "");  
					if (string.contains("」"))  
						string = string.replace("」", ""); 
					if (string.contains("?"))  
						string = string.replace("?", ""); 
					if (string.contains("§"))  
						string = string.replace("§", ""); 
					if (string.contains("※"))  
						string = string.replace("※", ""); 
						if (string.contains("＊"))  
							string = string.replace("＊", "'"); 
					if (string.trim().length()>=2) {
					EtextList.add(string);   
					}
				}  
				String Epath = sourcePath +"TextE.txt"; 
				if (CtextList.size()>5 && EtextList.size()>5) {
					try {
						writeFileContext(CtextList,cpath); 
						writeFileContext(EtextList,Epath); 
					} catch (Exception e) {
						System.out.println(Epath);
					}
				}
			} 
		}  
		System.out.println("files.length"+files.length);
	}
	public static  List<String> getFileText(File file,String Type) throws IOException {
		List<String> list = new ArrayList<>();
		InputStream inputStream1 = new FileInputStream(file);
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(inputStream1, Type));
		String line;
		while ((line = br.readLine()) != null)
		{
			list.add(line);
		}
		br.close();
		inputStream1.close();
		return list;
		
	}
	public static void writeFileContext(List<String>  strings, String path) throws Exception {
		File file = new File(path);
        //如果没有文件就创建
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
        if (!file.isFile()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(path,true);
        for (String l:strings){
            writer.write(l + "\r\n");
        }
        writer.close();
    } 
}
