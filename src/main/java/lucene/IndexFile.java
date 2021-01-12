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
//    public void indexInit(){//数据库+lcene初始化
//       conn = (Connection) JdbcUtil.getConnection();    
//       if(conn == null) {    
//           try {
//               throw new Exception("数据库连接失败！");
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
//    public void indexEnd(){//数据库+lcene关闭
//          
//        ls.closeWriter();
//        try {
//               conn.close();//关闭数据库
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
//                stop=this.createIndex(r1,ls,"1");   //给数据库创建索引,此处执行一次，不要每次运行都创建索引，以后数据有更新可以后台调用更新索引
//                if(!stop){
//                    ls.commit();//加个判断条件
//                }
//                //System.out.println("stop"+stop);
//                 
//           }while(!stop);
//            
//           stop=false;
//           do{
//                r2=deal6SourceTable("music_and_mv_info");
//                stop=this.createIndex(r2,ls,"2");   //给数据库创建索引,此处执行一次，不要每次运行都创建索引，以后数据有更新可以后台调用更新索引 
//                if(!stop){
//                    ls.commit();//加个判断条件
//                }
//                 
//           }while(!stop);
//             
//           stop=false;
//           do{
//                r3=deal6SourceTable("e_book_info");
//                stop=this.createIndex(r3,ls,"3");   //给数据库创建索引,此处执行一次，不要每次运行都创建索引，以后数据有更新可以后台调用更新索引 
//                if(!stop){
//                    ls.commit();//加个判断条件
//                }
//                 
//           }while(!stop);
//             
//           stop=false;
//           do{
//                r4=deal6SourceTable("bt_file_info");
//                stop=this.createIndex(r4,ls,"4");   //给数据库创建索引,此处执行一次，不要每次运行都创建索引，以后数据有更新可以后台调用更新索引 
//                if(!stop){
//                    ls.commit();//加个判断条件
//                }
//                 
//           }while(!stop);
//             
//           stop=false;
//           do{
//                r5=deal6SourceTable("characteristic_software_info");
//                stop=this.createIndex(r5,ls,"5");   //给数据库创建索引,此处执行一次，不要每次运行都创建索引，以后数据有更新可以后台调用更新索引 
//                if(!stop){
//                    ls.commit();//加个判断条件
//                }
//                 
//           }while(!stop);
//             
//           stop=false;
//           do{
//                r6=deal6SourceTable("source_code_info");
//                stop=this.createIndex(r6,ls,"6");   //给数据库创建索引,此处执行一次，不要每次运行都创建索引，以后数据有更新可以后台调用更新索引 
//                if(!stop){
//                    ls.commit();//加个判断条件
//                }
//                 
//           }while(!stop);
//           stop=false;
//            
//    }
//      
//    public ResultSet deal2Share(String tableName) throws SQLException{
//       String sql = "SELECT  distinct NAME,SKYDRIVER_NAME,USERNAME,SHARE_TIME,DESCRIB,TYPE_ID,ID,SHORTURL from "+tableName+" where STATUS=1  and FS_ID ='1' limit "+NUM; //利用FS_ID这个字段，没什么用处
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
//                stop=this.createIndex(r1,ls,"7");   //给数据库创建索引,此处执行一次，不要每次运行都创建索引，以后数据有更新可以后台调用更新索引 
//                if(!stop){
//                    ls.commit();//加个判断条件
//                }
//                //System.out.println("stop"+stop);
//                 
//           }while(!stop);
//            
//           stop=false;
//           do{
//                r2=deal2Share("share2");
//                stop=this.createIndex(r2,ls,"8");   //给数据库创建索引,此处执行一次，不要每次运行都创建索引，以后数据有更新可以后台调用更新索引 
//                if(!stop){
//                    ls.commit();//加个判断条件
//                }
//                 
//           }while(!stop);
//             
//           stop=false;
//           do{
//                r3=deal3Share("share3");
//                stop=this.createIndex(r3,ls,"9");   //给数据库创建索引,此处执行一次，不要每次运行都创建索引，以后数据有更新可以后台调用更新索引 
//                if(!stop){
//                    ls.commit();//加个判断条件
//                }
//                 
//           }while(!stop);
//           stop=false;
//       }
//     
//       public void update2ShareTable(String tableName) throws SQLException{
//           Statement st = (Statement) conn.createStatement();
//          String sql = "update "+tableName+" set FS_ID=0 where STATUS=1  and FS_ID ='1' limit "+NUM; //利用FS_ID这个字段，没什么用处
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
//                   return true;//处理完毕
//               }
//               while(isNull){
//                   if(Integer.parseInt(mark)>=1&&Integer.parseInt(mark)<=8){
//                       Document doc = new Document(); 
//                       //System.out.println("name"+rs.getString("NAME"));       
//                       Field name = new Field("name",rs.getString("NAME"),Field.Store.YES,Field.Index.ANALYZED);
//                       String skName=rs.getString("SKYDRIVER_NAME");
//                       if(skName==null){
//                           skName="百度";
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
//                       ls.singleUpdate(doc);//用跟新更为合适    
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
//                       ls.singleUpdate(doc);//用跟新更为合适    
//                       isNull=rs.next();
//                   }
//                   count=count+1;
//               }
//               if(Integer.parseInt(mark)>=1&&Integer.parseInt(mark)<=6){
//                   update6SourceTable(tableName);//处理完成后做标志
//               }
//               else if(Integer.parseInt(mark)>=7&&Integer.parseInt(mark)<=8){
//                   update2ShareTable(tableName);//处理完成后做标志
//               }
//               else{
//                   update3ShareTable(tableName);//处理完成后做标志
//               }
//               System.out.println("Has index "+count+"条数据，数据来自表"+tableName);
//                 
//           } catch (Exception e) {
//               e.printStackTrace();
//           }
//           return false;
//       }
//}
