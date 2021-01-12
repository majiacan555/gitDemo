package Stanford_Corenlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import edu.stanford.nlp.util.CoreMap;


public class StanfordText {
	static Connection conn=DBUtil.getConnection();
    public static void main(String[] args) throws SQLException, IOException {
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        System.out.println("程序开始执行"+df.format(new Date())); 
//        Properties props = new Properties();
////        ，StanfordCoreNLP的各个组件（annotator）按“tokenize（分词）, ssplit（断句）, pos（词性标注）, lemma（词元化）,
////        ner（命名实体识别）, parse（语法分析）, dcoref（同义词分辨）”顺序进行处理。
////        , lemma, ner, parse, dcoref
//        props.setProperty("annotators", "tokenize, ssplit, pos");//
//        StanfordCoreNLP Englishpipeline = new StanfordCoreNLP(props); 
        
        
        Properties props = new Properties();
        File file = new File("E:\\Stanford_Core\\stanford-chinese-corenlp-2018-02-27-models\\StanfordCoreNLP-chinese.properties");
        InputStream inputStream = new FileInputStream(file);
        props.load(inputStream);
        StanfordCoreNLP Chineapipe = new StanfordCoreNLP(props);
//        
         
//        System.out.println("SrandFord实例化完成，开始获取数据"+df.format(new Date())); 
        AddData search = new AddData();
//        for (int i = 1565412; i < 1587566; i++) {
//        	System.out.println("现在解析第："+i);
//			ChineseAndEnglishModel CE = search.SearchData(i);
//			try {
//				if (CE != null  ) {
//					getEnglishNLP(CE,Englishpipeline);
//				} 
//			} catch (Exception e) {
//				continue;
//			}
//		}
        
        for (int i = 1537257; i < 1559411; i= i + 2001) {
        	List<CEDataNLP2> CElist = search.SearchChineseData(i,conn);
        	for (int j = 0; j < CElist.size(); j++) {
        		CEDataNLP2 CE = CElist.get(j);
        		System.out.println("现在解析第："+CE.getId());
    			getChineseNLP(CE,Chineapipe);
			}
		}
        conn.close();
        
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

	private static void getEnglishNLP(ChineseAndEnglishModel CE, StanfordCoreNLP Englishpipeline) throws SQLException {
//		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
//		System.out.println("开始标注"+df.format(new Date())); 
		String Englishtext = CE.getEnglishData();
		Annotation document = new Annotation(Englishtext);
//    	System.out.println(df.format(new Date()));
    	Englishpipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
       
        JSONArray jaArray = new JSONArray();
        for (CoreMap sentence : sentences) {
        	for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
        		JSONObject jObject = new JSONObject();
        		String word = token.get(TextAnnotation.class);
        		String pos = token.get(PartOfSpeechAnnotation.class);
        		jObject.put(word, pos);
        		jaArray.put(jObject);
//        		String ne = token.get(NamedEntityTagAnnotation.class);
//        		nerTags.add(ne);
        	}
        }
        AddData addData = new AddData();
        CEDataNLP2 ce = new CEDataNLP2();
        ce.setDataId(CE.getId());
        ce.setChineseData(CE.getChineseData());
        ce.setEnglishData(CE.getEnglishData());
        ce.setEnglishNLP(jaArray.toString());
        ce.setMemo(CE.getMemo());
        ce.setTitle(CE.getTitle());
        ce.setTitleID(CE.getTitleID());
        ce.setURL(CE.getURL());
        ce.setUrlType(CE.getUrlType());
        addData.Add(ce);
        System.out.println(jaArray.toString());
//        System.out.println(df.format(new Date()));		
	}

	private static void getChineseNLP(CEDataNLP2 CE, StanfordCoreNLP Chineapipe  ) throws SQLException {
		String Chinatext = CE.getChineseData();
		if (Chinatext!=null && Chinatext.length()>0) {
			
			Annotation document = new Annotation(Chinatext);
			Chineapipe.annotate(document);
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			JSONArray jaArray = new JSONArray();
			for (CoreMap sentence : sentences) {
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
					JSONObject jObject = new JSONObject();
					String word = token.get(TextAnnotation.class);
					String pos = token.get(PartOfSpeechAnnotation.class);
					jObject.put(word, pos);
					jaArray.put(jObject);
					
				}
			}
			String jaString= jaArray.toString();
			
			if (jaString.contains("'")) {
				jaString = jaString.replace("'", "''");
			}
			AddData addData = new AddData();
			addData.Update(jaString, CE.getId(),conn);
		}
	}
}
