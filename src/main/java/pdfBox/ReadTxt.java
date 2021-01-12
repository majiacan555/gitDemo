package pdfBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class ReadTxt {
	 public static void main(String[] args) {
		 File Basefile =   new File("D://documents");
    	 File[] files = Basefile.listFiles();
    	 if (files!=null) {
    		 for (File f : files) {
    	            if(f.isDirectory()){// 判断是否文件夹
    	            	File[] files2 = f.listFiles();
    	            	 if (files2!=null) {
    	            		 for (File f2 : files2) {
    	            			 File[] files3 = f2.listFiles();
    	    	            	 if (files3!=null) {
    	    	            		 for (File f3 : files3) {
    	            			 	String file = f3.getPath(); 
    	            			 	if (file.contains("TextE")) {
    	            			 		 READPDFE(file);
									}else {
										READPDFC(file);
									}
    	    	              }
    	            	}
    	    	     }
    	            	 }
    	            }
    	      }
    	            
    	 } 
		 
		}
	 public static void READPDFE(String inputFile){
		 StringBuilder result = new StringBuilder();
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(inputFile));//构造一个BufferedReader类来读取文件
	            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            	if((!s.equals(" "))&&(!s.equals("  "))&&(!s.equals("        "))&&s.length()>1){
	            		
	            		result.append(s);
	                }
	            }
	            FileOutputStream fop = null;
	            File file = new File(inputFile);;
	            if (!file.exists()) {
	            	file.createNewFile();
	            }
	            if (file.exists()) {
	            	file.delete();
	            	file.createNewFile();
	            }
	            fop = new FileOutputStream(file);
	            String string =  result.toString();
	            String[] resultSpilt = string.split("\\.");
	            String EnglishData = "";
	            for(int i =0;i< resultSpilt.length-1;i++){
	            	EnglishData += resultSpilt[i]+".\r\n";
	            }
	            EnglishData += resultSpilt[ resultSpilt.length-1]+".";
	            byte[] contentInBytes = EnglishData.getBytes();
	            fop.write(contentInBytes);
	            fop.flush();
	            fop.close();
	            System.out.println("Done");
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
        }
    }
	 public static void READPDFC(String inputFile){
		 StringBuilder result = new StringBuilder();
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(inputFile));//构造一个BufferedReader类来读取文件
	            String s = null;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            	if((!s.equals(" "))&&(!s.equals("  "))&&(!s.equals("        "))&& s.length()>1){
	            		result.append(s);
	                }
	            }
	            FileOutputStream fop = null;
	            File file = new File(inputFile);;
	            if (!file.exists()) {
	            	file.createNewFile();
	            }
	            if (file.exists()) {
	            	file.delete();
	            	file.createNewFile();
	            }
	            fop = new FileOutputStream(file);
	            String[] resultSpilt = result.toString().split("。");
	            String ChineseData = "";
	            for(int i =0;i< resultSpilt.length-1;i++){
	            	ChineseData += resultSpilt[i]+"。\r\n";
	            }
	            ChineseData += resultSpilt[resultSpilt.length-1]+"。";
	            byte[] contentInBytes = ChineseData.getBytes();
	            fop.write(contentInBytes);
	            fop.flush();
	            fop.close();
	            System.out.println("Done");
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }
        }
}
