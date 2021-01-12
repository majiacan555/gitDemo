package Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import HttpClientDemo.AddData;
import HttpClientDemo.DBUtil;
import TransLationModel.CEDataNLP2;
import TransLationModel.CEDataNLPText;
import TransLationModel.ChineseAndEnglishModel; 


public class StanfordText {
    public static void main(String[] args) throws SQLException, IOException {
    	
    	File file1 = new File("D:\\Ultimate_utf8.txt");//Ultimate
    	InputStream inputStream1 = new FileInputStream(file1);
    	BufferedReader br = null;
    	br = new BufferedReader(new InputStreamReader(inputStream1, "utf-8"));
    	StringBuilder sb = new StringBuilder();
    	String line;
    	while ((line = br.readLine()) != null)
    	{
    		sb.append(line);
    	}
    	br.close();
    	inputStream1.close();
    	String Text = sb.toString();
    	System.out.println(Text);
        Properties props = new Properties();
        File file = new File("E:\\Stanford_Core\\stanford-chinese-corenlp-2018-02-27-models\\StanfordCoreNLP-chinese.properties");
        InputStream inputStream = new FileInputStream(file);
        props.load(inputStream);
        StanfordCoreNLP Chineapipe = new StanfordCoreNLP(props);
		getChineseNLP(Text,Chineapipe);
		inputStream.close();
		
		
//		FileOutputStream fop = null;
//		  File file11;
//		   file11 = new File("D:/result11.txt");
//		   fop = new FileOutputStream(file11,true);
//		  
//		   // if file doesnt exists, then create it
//		   if (!file11.exists()) {
//		    file11.createNewFile();
//		   }
//		  
//		   // get the content in bytes
//		   byte[] contentInBytes = Text.getBytes("utf-8");
//		  
//		   fop.write(contentInBytes);
//		   fop.flush();
//		   fop.close();
	
		
        
//    	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
//      System.out.println(df.format(new Date())); ;// new Date()为获取当前系统时间
//        StanfordCoreNLP corenlp = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
//        String text1 = "马飚向伦古转达了习近平主席的诚挚祝贺和良好祝愿。";
//        Annotation document1 = new Annotation(text1);
//        corenlp.annotate(document1);
//
//        corenlp.prettyPrint(document1, System.out);
//        System.out.println(df.format(new Date())); ;// new Date()为获取当前系统时间
      
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
