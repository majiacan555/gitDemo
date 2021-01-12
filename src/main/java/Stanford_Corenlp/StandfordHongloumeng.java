package Stanford_Corenlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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

import HttpClientDemo.AddData;
import HttpClientDemo.DBUtil;
import TransLationModel.CEDataNLP2;
import TransLationModel.CEDataNLPText;
import TransLationModel.ChineseAndEnglishModel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;


public class StandfordHongloumeng {
	static Connection conn=DBUtil.getConnection();
    public static void main(String[] args) throws SQLException, IOException {
    	  
//        Properties props = new Properties();
//        ��StanfordCoreNLP�ĸ��������annotator������tokenize���ִʣ�, ssplit���Ͼ䣩, pos�����Ա�ע��, lemma����Ԫ����,
//        ner������ʵ��ʶ��, parse���﷨������, dcoref��ͬ��ʷֱ棩��˳����д���
//        , lemma, ner, parse, dcoref
//        props.setProperty("annotators", "tokenize, ssplit, pos");// 
        
        Properties props = new Properties();
        File file = new File("E:\\Stanford_Core\\stanford-chinese-corenlp-2018-02-27-models\\StanfordCoreNLP-chinese.properties");
        InputStream inputStream = new FileInputStream(file);
        props.load(inputStream);
        StanfordCoreNLP Chineapipe = new StanfordCoreNLP(props); 
        
        
        
//        StanfordCoreNLP Chineapipe = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
//        StanfordCoreNLP Englishpipeline = new StanfordCoreNLP(props);
//        System.out.println("SrandFordʵ������ɣ���ʼ��ȡ����"+df.format(new Date())); 
        AddData search = new AddData();
//        for (int i = 1549598; i < 1555352; i++) {
//        	System.out.println("���ڽ����ڣ�"+i);
//			ChineseAndEnglishModel CE = search.SearchData(i);
//			try {
//				if (CE != null  ) {
//					getEnglishNLP(CE,Englishpipeline);
//				}
//				
//			} catch (Exception e) {
//				continue;
//			}
//		}
        
        File Basefile =   new File("D:\\HongloumengParallelCorpus\\Hawkes\\Chinese");
      	 File[] files = Basefile.listFiles();
      	 if (files!=null) {
      		 for (File f : files) { 
            	String filepath = f.getPath();//D���µ�file�ļ��е�Ŀ¼
            	String txt =  READPDFE(filepath);  
  	        } 
   		} 
   	} 
    public static String READPDFE(String inputFile){
		 StringBuilder result = new StringBuilder();
	        try{
	            BufferedReader br = new BufferedReader(new FileReader(inputFile));//����һ��BufferedReader������ȡ�ļ�
	            String s = null;
	            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ�� 
	            	result.append(s);
	            } 
	            String string =  result.toString();
	            System.out.println(string); 
	            br.close();    
	            return string;
	        }catch(Exception e){
	            e.printStackTrace();
       }
	        return "";
   }
//    	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//�������ڸ�ʽ
//      System.out.println(df.format(new Date())); ;// new Date()Ϊ��ȡ��ǰϵͳʱ��
//        StanfordCoreNLP corenlp = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
//        String text1 = "������׹�ת����ϰ��ƽ��ϯ�ĳ�ֿף�غ�����ףԸ��";
//        Annotation document1 = new Annotation(text1);
//        corenlp.annotate(document1);
//
//        corenlp.prettyPrint(document1, System.out);
//        System.out.println(df.format(new Date())); ;// new Date()Ϊ��ȡ��ǰϵͳʱ��
      
     

	private static void getEnglishNLP(ChineseAndEnglishModel CE, StanfordCoreNLP Englishpipeline) throws SQLException {
//		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//�������ڸ�ʽ
//		System.out.println("��ʼ��ע"+df.format(new Date())); 
		String Englishtext = CE.getEnglishData();
		Annotation document = new Annotation(Englishtext);
//    	System.out.println(df.format(new Date()));
    	Englishpipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
       
        
        for (CoreMap sentence : sentences) {
        	for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
        		 
        		String word = token.get(TextAnnotation.class);
        		 
//        		String ne = token.get(NamedEntityTagAnnotation.class);
//        		nerTags.add(ne);
        	}
        }
        AddData addData = new AddData();
        CEDataNLP2 ce = new CEDataNLP2();
        ce.setDataId(CE.getId());
        ce.setChineseData(CE.getChineseData());
        ce.setEnglishData(CE.getEnglishData());
        
        ce.setMemo(CE.getMemo());
        ce.setTitle(CE.getTitle());
        ce.setTitleID(CE.getTitleID());
        ce.setURL(CE.getURL());
        ce.setUrlType(CE.getUrlType());
        addData.Add(ce);
       
	}

	private static void getChineseNLP(String Chinatext, StanfordCoreNLP Chineapipe  ) throws SQLException {
		 
		if (Chinatext!=null && Chinatext.length()>0) {
			
			Annotation document = new Annotation(Chinatext);
			Chineapipe.annotate(document);
			List<CoreMap> sentences = document.get(SentencesAnnotation.class); 
			for (CoreMap sentence : sentences) {
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
					 
					String word = token.get(TextAnnotation.class); 
					
				}
				 Tree tree = sentence.get(TreeAnnotation.class);   
				 System.out.println(tree);
			}
			 
		}
	}
}
