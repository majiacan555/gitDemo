package luceneIK;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import LiberyUtils.DBUtil;

 
  
public class IndexFile {
      
     private static Connection conn = null;    
     private static Statement stmt = null; 
     private final int NUM=500000;
     private LuceneSearch ls;
     private long count=0;
       
     public ResultSet deal6SourceTable(String tableName) throws SQLException{
           String sql = "SELECT distinct `NAME`,SKYDRIVER_NAME,USERNAME,SHARE_TIME,DESCRIB,TYPE_ID,ID,URL FROM "+tableName+" where STATUS=1 and TYPE_ID !='-1' and (TYPE_NAME is null or TYPE_NAME!=1) limit "+NUM;
           //System.out.println(sql);
           ResultSet rs = (ResultSet) stmt.executeQuery(sql);
           return rs;
     }
       
     public void update6SourceTable(String tableName) throws SQLException{
           Statement st = (Statement) conn.createStatement();
           String sql = "update "+tableName+" set TYPE_NAME=1 where STATUS=1 and TYPE_ID !='-1' and (TYPE_NAME is null or TYPE_NAME!=1) limit "+NUM;
           //System.out.println("update"+sql);
            try {
                st.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
     }
       
     public void indexInit(){//���ݿ�+lcene��ʼ��
        conn = (Connection) DBUtil.getConnection();    
        if(conn == null) {    
            try {
                throw new Exception("���ݿ�����ʧ�ܣ�");
            } catch (Exception e) {
                e.printStackTrace();
            }    
        }
        ls=new LuceneSearch();
        try {
            ls.init();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
     }
       
     public void indexEnd(){//���ݿ�+lcene�ر�
         ls.closeWriter();
         try {
                conn.close();//�ر����ݿ�
             } catch (SQLException e) {
                e.printStackTrace();
          }
     }
       
     
       
     public ResultSet deal2Share(String tableName) throws SQLException{
        String sql = "SELECT * from "+tableName+" where urlType = 97";  
        ResultSet rs = (ResultSet) stmt.executeQuery(sql);
        return rs;
    }
     public ResultSet dealShareWithUrlType(String tableName,Integer urlType) throws SQLException{
         String sql = "SELECT * from "+tableName+" where urlType =  "+urlType;  
         ResultSet rs = (ResultSet) stmt.executeQuery(sql);
         return rs;
     }
      
    public ResultSet deal3Share(String tableName) throws SQLException{
        String sql = "SELECT  distinct title,channel,uid,ctime,description,port,id,shorturl from "+tableName+" where name ='1' limit "+NUM; 
        ResultSet rs = (ResultSet) stmt.executeQuery(sql);
        return rs;
    }
      
    public void Index3Data() throws SQLException{
            try {
                stmt = (Statement) conn.createStatement();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
              
            ResultSet r1=null;
              
            boolean stop=false;
//            do{
                 r1=deal2Share("CEDataNLP3");
                 stop=this.createIndex(r1,ls,"7");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø������� 
                 if(!stop){
                     ls.commit();//�Ӹ��ж�����
                 }
                 //System.out.println("stop"+stop);
//                  
//            }while(!stop);
             
            stop=true;
            
        }
    public void IndexDataWithUrlType(String TableName, int urlType) throws SQLException{
        try {
            stmt = (Statement) conn.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } 
        ResultSet r1=null; 
        boolean stop=false; 
        r1=dealShareWithUrlType(TableName,urlType);
        stop=this.createIndex(r1,ls,"7");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø������� 
        if(!stop){
            ls.commit();//�Ӹ��ж�����
        }  
        stop=true; 
    }
      
        public void update2ShareTable(String tableName) throws SQLException{
            Statement st = (Statement) conn.createStatement();
           String sql = "update "+tableName+" set FS_ID=0 where STATUS=1  and FS_ID ='1' limit "+NUM; //����FS_ID����ֶΣ�ûʲô�ô�
           //System.out.println("update"+sql);
            try {
                st.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
          
       
             
        public boolean createIndex(ResultSet rs,LuceneSearch ls,String mark) {
            try {
                String tableName=null; 
                if(mark.equals("7")){
                    tableName="CEDataNLP3";
                } 
                boolean isNull=rs.next();
                //System.out.println("hehe"+isNull);
                if(isNull==false){
                    return true;//�������
                }
                while(isNull){
                    if(Integer.parseInt(mark)>=1&&Integer.parseInt(mark)<=8){
                        Document doc = new Document(); 
                        //System.out.println("name"+rs.getString("NAME"));       
                        Field Id = new Field("Id",String.valueOf(rs.getInt("Id")) ,Field.Store.YES,Field.Index.NOT_ANALYZED);
                        Field ChineseData = new Field("ChineseData",rs.getString("ChineseData"),Field.Store.YES,Field.Index.ANALYZED);
                        String chineseNlp = rs.getString("ChineseNLP");
                        if (chineseNlp == null) {
							chineseNlp = "1";
						}
                        Field ChineseNLP = new Field("ChineseNLP",chineseNlp,Field.Store.YES,Field.Index.NOT_ANALYZED);
                        Field EnglishData = new Field("EnglishData",rs.getString("EnglishData"),Field.Store.YES,Field.Index.ANALYZED);
                        Field EnglishNLP = new Field("EnglishNLP",rs.getString("EnglishNLP") ,Field.Store.YES,Field.Index.NOT_ANALYZED);
                        Field URL = new Field("URL",rs.getString("URL"),Field.Store.YES,Field.Index.NOT_ANALYZED);
                        Field title = new Field("title",rs.getString("title"),Field.Store.YES,Field.Index.ANALYZED);
                        Field urlType = new Field("urlType",String.valueOf(rs.getInt("urlType")),Field.Store.YES,Field.Index.NOT_ANALYZED);
                        Field titleID = new Field("titleID",String.valueOf(rs.getInt("titleID")) ,Field.Store.YES,Field.Index.NOT_ANALYZED);
                        Field Memo = new Field("Memo",rs.getString("Memo"),Field.Store.YES,Field.Index.ANALYZED);
//                        Field Memo2 = new Field("Memo2",rs.getString("Memo2"),Field.Store.YES,Field.Index.NOT_ANALYZED);
//                        Field Memo3 = new Field("Memo3",rs.getString("Memo3"),Field.Store.YES,Field.Index.NOT_ANALYZED);
//                        System.out.println(rs.getString("time"));
                        doc.add(Id);
                        doc.add(ChineseData);
                        doc.add(ChineseNLP);
                        doc.add(EnglishData);
                        doc.add(EnglishNLP);
                        doc.add(URL);
                        doc.add(title);
                        doc.add(urlType);
                        doc.add(titleID);
                        doc.add(Memo);
//                        doc.add(Memo2);
//                        doc.add(Memo3);
                        ls.singleIndex(doc);//�ø��¸�Ϊ����    
                        isNull=rs.next();
                    }
                    
                    count=count+1;
                }
//                if(Integer.parseInt(mark)==7){
//                    update2ShareTable(tableName);//������ɺ�����־
//                }
                System.out.println("Has index "+count+tableName);
                  
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
}
