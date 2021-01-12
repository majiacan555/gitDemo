package Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.json.JSONArray;
import org.json.JSONObject;

import TransLationModel.CEDataNLP2;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;


public class StanfordTextPos { 
    public static void main(String[] args) throws SQLException, IOException {
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        System.out.println("程序开始执行"+df.format(new Date())); 
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit");  
//        ，StanfordCoreNLP的各个组件（annotator）按“tokenize（分词）, ssplit（断句）, pos（词性标注）, lemma（词元化）,
//        ner（命名实体识别）, parse（语法分析）, dcoref（同义词分辨）”顺序进行处理。
//        , lemma, ner, parse, dcoref
        
//        File file = new File("E:\\Stanford_Core\\stanford-chinese-corenlp-2018-02-27-models\\StanfordCoreNLP-chinese.properties");
//        InputStream inputStream = new FileInputStream(file);
//        props.load(inputStream); 
        StanfordCoreNLP apipe = new StanfordCoreNLP(props);   
        String filePath = "C:\\老师需求文档\\FROWN";
        String resultPath = filePath +"result\\";
        File file1 = new File(filePath); 
        File[] files = file1.listFiles();
        for (File file2 : files) { 
        	String fileName = file2.getName();
        	String[] Name = fileName.split("\\.");
        	String txtName = Name[0]+".txt";
        	String CE = ""; 
        	if (fileName.contains("doc")) {
        		CE = readWord2003(file2.getPath());
			}else {
				CE = getFileText(file2);
			} 
        	String result =  getString(CE,apipe);
        	 setStringToTxt(resultPath,txtName, result);
		} 
    }
    private static void setStringToTxt(String resultPath,String fileName,String result) throws IOException {
    	FileOutputStream fop = null;
    	File filedir = new File(resultPath);
    	  if (!filedir.exists()) {
    		  filedir.mkdirs();
  		   } 
		  File file = new File(resultPath+fileName);
		   fop = new FileOutputStream(file,true);  
		   if (!file.exists()) {
		    file.createNewFile();
		   }  
		   byte[] contentInBytes = result.getBytes("utf-8"); 
		   fop.write(contentInBytes);
		   System.out.println("end set file:"+fileName+"to txt");
		   fop.flush();
		   fop.close();
		
	}
	public static  String getFileText(File file) throws IOException { 
		InputStream inputStream1 = new FileInputStream(file);
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(inputStream1, "gbk"));
		String line;
		String string = "";
		int Count = 0;
		while ((line = br.readLine()) != null )
		{
			if (  line.trim().length()>0) { 
				string+= line; 
			}
		}
		br.close();
		inputStream1.close();
		return string;
		
	}
	public static String readWord2003(String filePath) {     
        FileInputStream fis;
        HWPFDocument doc;
        String text = null;
		try {
			File f = new File(filePath);
			fis = new FileInputStream(f);
			doc = new HWPFDocument(fis);
			Range rang = doc.getRange();     
			text = rang.text();   
	        System.out.println(text); 
	        fis.close();     
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}        
        return text;     
    } 
	private static String getString(String Englishtext, StanfordCoreNLP Englishpipeline) throws SQLException { 
		Annotation document = new Annotation(Englishtext); 
    	Englishpipeline.annotate(document);
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);  
        String resultTxt = "";
        for (CoreMap sentence : sentences) {
        	for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
        		JSONObject jObject = new JSONObject();
        		if (token.get(TextAnnotation.class).trim().length()>0) {
        			resultTxt += token.get(TextAnnotation.class)+" ";   
				}else {
					System.out.println("--");
				}
        	}
        }  
        return resultTxt;
	}

	 
}
