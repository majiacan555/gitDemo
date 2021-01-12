package LiberyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
 
import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.org.apache.regexp.internal.recompile;
 
import TransLationModel.CEDataNLP2;
import TransLationModel.ChineseAndEnglishModel;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordCoreNlPCommon {
	public static StanfordCoreNLP EnglishPipeLine = null;
	public static StanfordCoreNLP ChinesePipeLine = null;
    
    private static StanfordCoreNLP GetEnglishPipeLine(){
		Properties props = new Properties(); 
		props.setProperty("annotators", "tokenize, ssplit, pos"); 
		StanfordCoreNLP Englishpipeline = new StanfordCoreNLP(props);  
		return Englishpipeline;
	}
	private static StanfordCoreNLP GetChinesePipeLine() throws IOException{
	    Properties props = new Properties();
        File file = new File("E:\\Stanford_Core\\stanford-chinese-corenlp-2018-02-27-models\\StanfordCoreNLP-chinese.properties");
        InputStream inputStream = new FileInputStream(file);
        props.load(inputStream);
        StanfordCoreNLP Chinesepipeline = new StanfordCoreNLP(props);
		return Chinesepipeline;
	} 

	public static String getEnglishPos(String Text) throws SQLException {
		String Result = "";
		if (EnglishPipeLine == null)  
			EnglishPipeLine = GetEnglishPipeLine();  
		Annotation document = new Annotation(Text); 
		EnglishPipeLine.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);  
        for (CoreMap sentence : sentences) {
        	for (CoreLabel token : sentence.get(TokensAnnotation.class)) { 
        		String word = token.get(TextAnnotation.class);
        		String pos = token.get(PartOfSpeechAnnotation.class);
        		Result += word+"_"+pos+" ";
        	}
        }
        if (Result.length()>=2) 
			Result = Result.substring(0,Result.length()-1); 
        return Result;
        
	} 
	public static String getChinesePos(String Text) throws SQLException, IOException {
		String Result = "";
		if (ChinesePipeLine == null)  
			ChinesePipeLine = GetChinesePipeLine();  
		if (Text!=null && Text.length()>0) { 
			Annotation document = new Annotation(Text);
			ChinesePipeLine.annotate(document);
			List<CoreMap> sentences = document.get(SentencesAnnotation.class); 
			for (CoreMap sentence : sentences) {
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) { 
					String word = token.get(TextAnnotation.class);
					String pos = token.get(PartOfSpeechAnnotation.class);  
					Result += word+"_"+pos+" ";
				}
			} 
			if (Result.length()>=2) 
				Result = Result.substring(0,Result.length()-1); 
		} 
		return Result;
	}  
	
	public static JSONArray getEnglishPosJSONArray(String Text) throws SQLException {
		JSONArray jaArray = new JSONArray(); 
		if (EnglishPipeLine == null)  
			EnglishPipeLine = GetEnglishPipeLine();  
		Annotation document = new Annotation(Text); 
		EnglishPipeLine.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);  
        for (CoreMap sentence : sentences) {
        	for (CoreLabel token : sentence.get(TokensAnnotation.class)) { 
        		String word = token.get(TextAnnotation.class);
        		String pos = token.get(PartOfSpeechAnnotation.class); 
        		JSONObject jObject = new JSONObject();
        		jObject.put(word, pos);
        		jaArray.put(jObject);
        	}
        } 
        return jaArray;
        
	} 
	public static JSONArray getChinesePosJSONArray(String Text) throws SQLException, IOException {
		JSONArray jaArray = new JSONArray(); 
		if (ChinesePipeLine == null)  
			ChinesePipeLine = GetChinesePipeLine();  
		if (Text!=null && Text.length()>0) { 
			Annotation document = new Annotation(Text);
			ChinesePipeLine.annotate(document);
			List<CoreMap> sentences = document.get(SentencesAnnotation.class); 
			for (CoreMap sentence : sentences) {
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) { 
					String word = token.get(TextAnnotation.class);
					String pos = token.get(PartOfSpeechAnnotation.class);  
					JSONObject jObject = new JSONObject();
	        		jObject.put(word, pos);
	        		jaArray.put(jObject);
				}
			}  
		} 
		return jaArray;
	}  
}
