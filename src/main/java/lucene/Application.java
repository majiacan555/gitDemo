//package lucene;
//
//import java.sql.SQLException;
//
//public class Application {
//	public static void main(String[] args){
//        /*IndexFile indexFile=new IndexFile();
//        indexFile.indexInit();
//        try {
//            indexFile.Index6Data();
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }
//        indexFile.indexEnd();*/
//          
//        IndexFile indexFile1=new IndexFile();
//        indexFile1.indexInit();
//        try {
//            indexFile1.Index3Data();
//        } catch (SQLException e1) {
//            e1.printStackTrace();
//        }
//        indexFile1.indexEnd();
//          
//        LuceneSearch lch=new LuceneSearch();
//        try {
//            long a = System.currentTimeMillis();
//            lch.highLightSearch("name", "flv", 1,3);
//            long b = System.currentTimeMillis();
//            long c = b - a;
//            System.out.println("[高级检索花费时间：" + c + "毫秒]");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
