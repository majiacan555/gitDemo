package luceneIK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.wltea.analyzer.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import LiberyUtils.DBUtil;
 

public class Text {
	private static Connection conn = null;    
	private static Statement stmt = null; 
	private static String word = "WeiboData4";
	public static void main(String[] args) throws IOException, SQLException{
		int count = 0;
		conn = (Connection) DBUtil.getConnection();    
		stmt = (Statement) conn.createStatement();
		ResultSet r1=null;
		String sql = "SELECT Id,name,data from "+word; //����FS_ID����ֶΣ�ûʲô�ô�
        ResultSet rs = (ResultSet) stmt.executeQuery(sql);
        //System.out.println("hehe"+isNull);
       
        String text="����java���Կ����������������ķִʹ��߰�";  
        while(rs.next()){
        	text = rs.getString("data");     
        	//�����ִʶ���  
        	Analyzer anal=new IKAnalyzer(false);       
        	StringReader reader=new StringReader(text);  
        	//�ִ�  
        	TokenStream ts=anal.tokenStream("", reader);  
        	CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);  
        	//�����ִ�����  
        	while(ts.incrementToken()){  
	        	  FileOutputStream fop = null;
	      		  File file;
	      		  String content =term.toString()+",";
	      		   file = new File("D:/"+word+".txt");
	      		   fop = new FileOutputStream(file,true);
	      		  
	      		   // if file doesnt exists, then create it
	      		   if (!file.exists()) {
	      		    file.createNewFile();
	      		   }
	      		  
	      		   // get the content in bytes
	      		   byte[] contentInBytes = content.getBytes("utf-8");
	      		  
	      		   fop.write(contentInBytes);
	      		   fop.flush();
	      		   fop.close();
      		  
//        		System.out.print(term.toString()+" ");  
        	}  
        	reader.close();  
        	System.out.println(count);
        	count++;
        }
        System.out.println("�ִʽ���");
            
            
        
    }  
	
}
