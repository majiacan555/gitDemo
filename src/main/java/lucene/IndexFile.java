//package lucene;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//
//public class IndexFile {
//	private static Connection conn = null;    
//    private static Statement stmt = null; 
//    private final int NUM=500000;
//    private LuceneSearch ls;
//    private long count=0;
//      
//    public ResultSet deal6SourceTable(String tableName) throws SQLException{
//          String sql = "SELECT distinct `NAME`,SKYDRIVER_NAME,USERNAME,SHARE_TIME,DESCRIB,TYPE_ID,ID,URL FROM "+tableName+" where STATUS=1 and TYPE_ID !='-1' and (TYPE_NAME is null or TYPE_NAME!=1) limit "+NUM;
//          //System.out.println(sql);
//          ResultSet rs = (ResultSet) stmt.executeQuery(sql);
//          return rs;
//    }
//      
//    public void update6SourceTable(String tableName) throws SQLException{
//          Statement st = (Statement) conn.createStatement();
//          String sql = "update "+tableName+" set TYPE_NAME=1 where STATUS=1 and TYPE_ID !='-1' and (TYPE_NAME is null or TYPE_NAME!=1) limit "+NUM;
//          //System.out.println("update"+sql);
//           try {
//               st.executeUpdate(sql);
//           } catch (SQLException e) {
//               e.printStackTrace();
//           }
//    }
//      
//    public void indexInit(){//���ݿ�+lcene��ʼ��
//       conn = (Connection) JdbcUtil.getConnection();    
//       if(conn == null) {    
//           try {
//               throw new Exception("���ݿ�����ʧ�ܣ�");
//           } catch (Exception e) {
//               e.printStackTrace();
//           }    
//       }
//       ls=new LuceneSearch();
//       try {
//           ls.init();
//       } catch (Exception e2) {
//           e2.printStackTrace();
//       }
//    }
//      
//    public void indexEnd(){//���ݿ�+lcene�ر�
//          
//        ls.closeWriter();
//        try {
//               conn.close();//�ر����ݿ�
//            } catch (SQLException e) {
//               e.printStackTrace();
//         }
//    }
//      
//    public void Index6Data() throws SQLException{  
//           try {
//               stmt = (Statement) conn.createStatement();
//           } catch (SQLException e1) {
//               e1.printStackTrace();
//           }
//             
//           ResultSet r1=null;
//           ResultSet r2=null;
//           ResultSet r3=null;
//           ResultSet r4=null;
//           ResultSet r5=null;
//           ResultSet r6=null;
//             
//           boolean stop=false;
//           do{
//                r1=deal6SourceTable("film_and_tv_info");
//                stop=this.createIndex(r1,ls,"1");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø�������
//                if(!stop){
//                    ls.commit();//�Ӹ��ж�����
//                }
//                //System.out.println("stop"+stop);
//                 
//           }while(!stop);
//            
//           stop=false;
//           do{
//                r2=deal6SourceTable("music_and_mv_info");
//                stop=this.createIndex(r2,ls,"2");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø������� 
//                if(!stop){
//                    ls.commit();//�Ӹ��ж�����
//                }
//                 
//           }while(!stop);
//             
//           stop=false;
//           do{
//                r3=deal6SourceTable("e_book_info");
//                stop=this.createIndex(r3,ls,"3");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø������� 
//                if(!stop){
//                    ls.commit();//�Ӹ��ж�����
//                }
//                 
//           }while(!stop);
//             
//           stop=false;
//           do{
//                r4=deal6SourceTable("bt_file_info");
//                stop=this.createIndex(r4,ls,"4");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø������� 
//                if(!stop){
//                    ls.commit();//�Ӹ��ж�����
//                }
//                 
//           }while(!stop);
//             
//           stop=false;
//           do{
//                r5=deal6SourceTable("characteristic_software_info");
//                stop=this.createIndex(r5,ls,"5");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø������� 
//                if(!stop){
//                    ls.commit();//�Ӹ��ж�����
//                }
//                 
//           }while(!stop);
//             
//           stop=false;
//           do{
//                r6=deal6SourceTable("source_code_info");
//                stop=this.createIndex(r6,ls,"6");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø������� 
//                if(!stop){
//                    ls.commit();//�Ӹ��ж�����
//                }
//                 
//           }while(!stop);
//           stop=false;
//            
//    }
//      
//    public ResultSet deal2Share(String tableName) throws SQLException{
//       String sql = "SELECT  distinct NAME,SKYDRIVER_NAME,USERNAME,SHARE_TIME,DESCRIB,TYPE_ID,ID,SHORTURL from "+tableName+" where STATUS=1  and FS_ID ='1' limit "+NUM; //����FS_ID����ֶΣ�ûʲô�ô�
//       ResultSet rs = (ResultSet) stmt.executeQuery(sql);
//       return rs;
//   }
//     
//   public ResultSet deal3Share(String tableName) throws SQLException{
//       String sql = "SELECT  distinct title,channel,uid,ctime,description,port,id,shorturl from "+tableName+" where name ='1' limit "+NUM; 
//       ResultSet rs = (ResultSet) stmt.executeQuery(sql);
//       return rs;
//   }
//     
//   public void Index3Data() throws SQLException{
//           try {
//               stmt = (Statement) conn.createStatement();
//           } catch (SQLException e1) {
//               e1.printStackTrace();
//           }
//             
//           ResultSet r1=null;
//           ResultSet r2=null;
//           ResultSet r3=null;
//             
//           boolean stop=false;
//           do{
//                r1=deal2Share("share1");
//                stop=this.createIndex(r1,ls,"7");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø������� 
//                if(!stop){
//                    ls.commit();//�Ӹ��ж�����
//                }
//                //System.out.println("stop"+stop);
//                 
//           }while(!stop);
//            
//           stop=false;
//           do{
//                r2=deal2Share("share2");
//                stop=this.createIndex(r2,ls,"8");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø������� 
//                if(!stop){
//                    ls.commit();//�Ӹ��ж�����
//                }
//                 
//           }while(!stop);
//             
//           stop=false;
//           do{
//                r3=deal3Share("share3");
//                stop=this.createIndex(r3,ls,"9");   //�����ݿⴴ������,�˴�ִ��һ�Σ���Ҫÿ�����ж������������Ժ������и��¿��Ժ�̨���ø������� 
//                if(!stop){
//                    ls.commit();//�Ӹ��ж�����
//                }
//                 
//           }while(!stop);
//           stop=false;
//       }
//     
//       public void update2ShareTable(String tableName) throws SQLException{
//           Statement st = (Statement) conn.createStatement();
//          String sql = "update "+tableName+" set FS_ID=0 where STATUS=1  and FS_ID ='1' limit "+NUM; //����FS_ID����ֶΣ�ûʲô�ô�
//          //System.out.println("update"+sql);
//           try {
//               st.executeUpdate(sql);
//           } catch (SQLException e) {
//               e.printStackTrace();
//           }
//       }
//         
//       public void update3ShareTable(String tableName) throws SQLException{
//           Statement st = (Statement) conn.createStatement();
//          String sql = "update "+tableName+" set name=0 where name ='1' limit "+NUM; 
//          //System.out.println("update"+sql);
//           try {
//               st.executeUpdate(sql);
//           } catch (SQLException e) {
//               e.printStackTrace();
//           }
//       }
//            
//       public boolean createIndex(ResultSet rs,LuceneSearch ls,String mark) {
//           try {
//               String tableName=null;
//               if(mark.equals("1")){
//                   tableName="film_and_tv_info";
//               }
//               if(mark.equals("2")){
//                   tableName="music_and_mv_info";
//               }
//               if(mark.equals("3")){
//                   tableName="e_book_info";
//               }
//               if(mark.equals("4")){
//                   tableName="bt_file_info";
//               }
//               if(mark.equals("5")){
//                   tableName="characteristic_software_info";
//               }
//               if(mark.equals("6")){
//                   tableName="source_code_info";
//               }
//               if(mark.equals("7")){
//                   tableName="share1";
//               }
//               if(mark.equals("8")){
//                   tableName="share2";
//               }
//               if(mark.equals("9")){
//                   tableName="share3";
//               }
// 
//               boolean isNull=rs.next();
//               //System.out.println("hehe"+isNull);
//               if(isNull==false){
//                   return true;//�������
//               }
//               while(isNull){
//                   if(Integer.parseInt(mark)>=1&&Integer.parseInt(mark)<=8){
//                       Document doc = new Document(); 
//                       //System.out.println("name"+rs.getString("NAME"));       
//                       Field name = new Field("name",rs.getString("NAME"),Field.Store.YES,Field.Index.ANALYZED);
//                       String skName=rs.getString("SKYDRIVER_NAME");
//                       if(skName==null){
//                           skName="�ٶ�";
//                       }
//                       Field skydirverName = new Field("skydirverName",skName, Field.Store.YES,Field.Index.NOT_ANALYZED);
//                       Field username = new Field("username",rs.getString("USERNAME"),Field.Store.YES, Field.Index.ANALYZED);   
//                       Field shareTime = new Field("shareTime",rs.getString("SHARE_TIME"), Field.Store.YES,Field.Index.NOT_ANALYZED);
//                       String desb=rs.getString("DESCRIB");
//                       if(desb==null){
//                           desb="-1";
//                       }
//                       Field describ = new Field("describ",desb,Field.Store.NO,Field.Index.NOT_ANALYZED);    
//                       Field typeId = new Field("typeId",rs.getString("TYPE_ID"), Field.Store.YES,Field.Index.NOT_ANALYZED);
//                       Field id = new Field("id",rs.getString("ID"),Field.Store.YES,Field.Index.NOT_ANALYZED);
//                       Field url =null;
//                       if(Integer.parseInt(mark)>=7&&Integer.parseInt(mark)<=8){
//                           url = new Field("url",rs.getString("SHORTURL"), Field.Store.YES,Field.Index.ANALYZED);
//                       }
//                       else{
//                           url = new Field("url",rs.getString("URL"), Field.Store.YES,Field.Index.ANALYZED); 
//                       }
//                       doc.add(name);
//                       doc.add(skydirverName);
//                       doc.add(username);
//                       doc.add(shareTime);
//                       doc.add(describ);
//                       doc.add(typeId);
//                       doc.add(id);
//                       doc.add(url);
//                       ls.singleUpdate(doc);//�ø��¸�Ϊ����    
//                       isNull=rs.next();
//                   }
//                   else{
//                       Document doc = new Document(); 
//                       //System.out.println("title"+rs.getString("title"));       
//                       Field name = new Field("name",rs.getString("title"),Field.Store.YES,Field.Index.ANALYZED);
//                       String skName=rs.getString("channel");
//                       Field skydirverName = new Field("skydirverName",skName, Field.Store.YES,Field.Index.NOT_ANALYZED);
//                       Field username = new Field("username",rs.getString("uid"),Field.Store.YES, Field.Index.ANALYZED);    
//                       Field shareTime = new Field("shareTime",rs.getString("ctime"), Field.Store.YES,Field.Index.NOT_ANALYZED);
//                       String desb=rs.getString("description");
//                       if(desb==null){
//                           desb="-1";
//                       }
//                       Field describ = new Field("describ",desb,Field.Store.NO,Field.Index.NOT_ANALYZED);    
//                       Field typeId = new Field("typeId",rs.getString("port"), Field.Store.YES,Field.Index.NOT_ANALYZED);
//                       Field id = new Field("id",rs.getString("id"),Field.Store.YES,Field.Index.NOT_ANALYZED);   
//                       Field url = new Field("url",rs.getString("shorturl"), Field.Store.YES,Field.Index.ANALYZED); 
//                         
//                       doc.add(name);
//                       doc.add(skydirverName);
//                       doc.add(username);
//                       doc.add(shareTime);
//                       doc.add(describ);
//                       doc.add(typeId);
//                       doc.add(id);
//                       doc.add(url);
//                       ls.singleUpdate(doc);//�ø��¸�Ϊ����    
//                       isNull=rs.next();
//                   }
//                   count=count+1;
//               }
//               if(Integer.parseInt(mark)>=1&&Integer.parseInt(mark)<=6){
//                   update6SourceTable(tableName);//������ɺ�����־
//               }
//               else if(Integer.parseInt(mark)>=7&&Integer.parseInt(mark)<=8){
//                   update2ShareTable(tableName);//������ɺ�����־
//               }
//               else{
//                   update3ShareTable(tableName);//������ɺ�����־
//               }
//               System.out.println("Has index "+count+"�����ݣ��������Ա�"+tableName);
//                 
//           } catch (Exception e) {
//               e.printStackTrace();
//           }
//           return false;
//       }
//}
