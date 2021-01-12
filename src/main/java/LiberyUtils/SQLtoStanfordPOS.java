package LiberyUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
 

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import Champollion.CEDataNLPText; 
import LiberyUtils.AddData;
import LiberyUtils.DBUtil;
import LiberyUtils.LiberyCache;
import LiberyUtils.StanfordCoreNlPCommon;
import TransLationModel.CEDataNLP2;
import TransLationModel.ChineseAndEnglishModel;
 
public class SQLtoStanfordPOS {
	static Connection conn=DBUtil.getConnection();
    public static void main(String[] args) throws SQLException, IOException {
    	String regionTableName = "ChineseAndEnglishModel";
    	int urlType = 96;
    	String tableName = "CEDataNLP3";
    	AddData addData = new AddData();  
    	List<Integer> IdList   = addData.SearchIDList("Id","urlType", urlType,conn,regionTableName); 
    	List<Integer> ExistIDList   = addData.SearchIDList("DataId","urlType", urlType,conn,tableName); 
    	System.out.println("Search Id Count: "+IdList.size() + " form "+ regionTableName + "where urlType = " +urlType);
    	System.out.println("Search DataId Count: "+ExistIDList.size() + " form "+ tableName + "where urlType = " +urlType); 
    	if (ExistIDList.size() != IdList.size()) {
    		int count = 0; 
		    for (int id : IdList) {
		    	if (ExistIDList.contains(id)) {
		    		//				System.out.println("id： "+id+"  数据已存在");
					continue;
				} 
		    	count ++;
		    	System.out.println("count : "+count +"  Begin anaylys id "+ id);
				ChineseAndEnglishModel CE = addData.SearchData(conn,id,regionTableName); 
				if (CE != null  ) {
					JSONArray ejaArray  =StanfordCoreNlPCommon. getEnglishPosJSONArray(CE.getEnglishData());
					String ejaString= ejaArray.toString();  
					CEDataNLP2 ce = new CEDataNLP2();
			        ce.setDataId(CE.getId());
			        ce.setChineseData(CE.getChineseData());
			        ce.setEnglishData(CE.getEnglishData());
			        ce.setEnglishNLP(ejaString); 
			        ce.setMemo(CE.getMemo());
			        ce.setTitle(CE.getTitle());
			        ce.setTitleID(CE.getTitleID());
			        ce.setURL(CE.getURL());
			        ce.setUrlType(CE.getUrlType()); 
			        addData.Add(ce,   tableName); 
				}  
			}  
    	}else {
			System.out.println("ExistIDList.size() == IdList.size()" );
		}
        List<Integer> CNLP_IDList = addData.searchIDListWhereNLPisNull("Id","urlType", urlType,conn,tableName); 
        System.out.println(CNLP_IDList.size() +"--------------------------");
        int CNLP_Count = 0;
        StanfordCoreNlPCommon.EnglishPipeLine = null;
        Map<Integer, String> resultMap = new HashMap<Integer, String>();
        for (int id : CNLP_IDList) {  
        	try {
        		CNLP_Count ++;
        		System.out.println("count : "+CNLP_Count +"  Begin anaylys id "+ id);
        		String ChineseData = addData.SearchChineseData("ChineseData",id,conn,tableName); 
        		if (ChineseData != null  ) {  
        			JSONArray cjaArray  =StanfordCoreNlPCommon. getChinesePosJSONArray(ChineseData);
        			String cjaString= cjaArray.toString();   
        			resultMap.put(id, cjaString);
        		}  
        		if (resultMap.size() >= 600) {
					for (Integer mapId : resultMap.keySet()) {
						String cjaString = resultMap.get(mapId); 
						addData.UpdateChineseDataNlp(mapId, cjaString, conn, tableName) ; 
					}	
					resultMap.clear();
				}
				
			} catch (Exception e) {
				String errFileName = "C:\\Users\\superadmin.DLHZ106\\Desktop\\elegislation_SQLtoStanfordPOS_ErrFile.txt";
				List<String> errList = new ArrayList<>();
				errList.add(e.toString());
				LiberyCache.appendStringToFile(errFileName, errList);
			}
        	
		}  
        conn.close(); 
    } 
}
