package luceneIK;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class Application {
      
    public static void main(String[] args){
//         IndexFile indexFile=new IndexFile();
//        indexFile.indexInit();
//        try {
//            indexFile.Index6Data();
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }
//        indexFile.indexEnd(); 
          
    	 
        IndexFile indexFile1=new IndexFile();
        long d = System.currentTimeMillis();
        indexFile1.indexInit();
        try {
            indexFile1.IndexDataWithUrlType("CEDataNLP3", 96);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        long g = System.currentTimeMillis();
        long f = g - d;
        System.out.println("解析完成");
        indexFile1.indexEnd();
    	
    	
    	
//		LuceneSearch lch=new LuceneSearch();
//		try {
//			long a = System.currentTimeMillis();
//			lch.highLightSearch("EnglishData", "english", 10000,10000);
////			lch.highLightSearch("ChineseData", "���ż�ɽ", 10000,10000);
//			long b = System.currentTimeMillis();
//			long c = b - a;
//			System.out.println("[�߼���������ʱ�䣺" + c + "����]");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	
    }
}
