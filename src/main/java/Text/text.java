package Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream; 
import java.io.FileOutputStream; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader; 
import java.sql.SQLException; 
import java.util.List;
import java.util.Properties;
 
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
 

public class text {
    public static void main(String[] args) throws SQLException, IOException {
    	
    	File file1 = new File("F:\\ZCTCWordSmithedition");//Ultimate 
    	File[] files =  file1.listFiles();
    	for (File file : files) { 
    		InputStream inputStream1 = null;
    		BufferedReader br = null;
    		inputStream1 = new FileInputStream(file);
    		br = new BufferedReader(new InputStreamReader(inputStream1, "Unicode"));
    		StringBuilder sb = new StringBuilder();
    		String line;
    		while ((line = br.readLine()) != null)
    		{
    			sb.append(line);
    		}
    		String Text = sb.toString();
    		String[] str = Text.split(" ");
    		String LastText = new  String();
    		for (String string : str) {
    			if (string.contains("_")) {
    				String[] str1 = string.split("_");
    				LastText += str1[0]+" ";
				}else {
					LastText+=string+" ";
				}
			} 
    	   String sss =  file.getName();
    	   FileOutputStream fop = null;
  		   File file2 = new File("F:/ZCTCWordresult/"+sss);
  		   if (!file2.exists()) {
  			 file2.createNewFile();
  		   }
  		   fop = new FileOutputStream(file2,true); 
  		   byte[] contentInBytes = LastText.getBytes("unicode"); 
  		   System.out.println(LastText);
  		   fop.write(contentInBytes);
  		   fop.flush();
  		   fop.close();
    	   br.close();
    		inputStream1.close();
		}
    }
	

	private static void getChineseNLP(String CE, StanfordCoreNLP Chineapipe  ) throws SQLException, IOException {
		
		Annotation document = new Annotation(CE);
		Chineapipe.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		String string =null;
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				String word = token.get(TextAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class);
				string += word+"<"+pos+">"+" ";
			}
		}
		FileOutputStream fop = null;
		  File file;
		   file = new File("D:/Ultimate_utf8.txt");
		   fop = new FileOutputStream(file,true);
		  
		   // if file doesnt exists, then create it
		   if (!file.exists()) {
		    file.createNewFile();
		   }
		  
		   // get the content in bytes
		   byte[] contentInBytes = string.getBytes("utf-8");
		  
		   fop.write(contentInBytes);
		   fop.flush();
		   fop.close();
	}
}
