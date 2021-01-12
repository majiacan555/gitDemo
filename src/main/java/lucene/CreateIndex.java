//package lucene;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.List;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.Field.Index;
//import org.apache.lucene.document.Field.Store;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriter.MaxFieldLength;
//import org.apache.lucene.util.Constants;
//import org.wltea.analyzer.lucene.IKAnalyzer;
//
//public class CreateIndex {
//	 /** 
//     * ���������� 
//     */  
//    public static void initIndex() {  
//        File fileDir = new File(Constants.indexPath);  
//        IndexWriter indexWriter = null;  
//        try {  
//            Analyzer analyzer = new IKAnalyzer(); // IK���ķִ���  
////            �����true��ʾ�Ƿ����´���  
//            indexWriter = new IndexWriter(fileDir, analyzer,true , MaxFieldLength.LIMITED);  
//            List<File> fileList = getFiles();  
//            int count = fileList.size();  
//            for (int i = 0; i < count; i++) {  
//                Document document = null;  
//                document = fileToText(fileList.get(i));  
//                indexWriter.addDocument(document);  
//            }  
//            System.out.println("����������ɹ���");  
//        } catch (Exception e) {  
//            System.err.println("������ȷ����������");  
//            fileDir.deleteOnExit(); // �������������ʧ�ܣ���ɾ���Ѿ�����������Ŀ¼���´����´���  
//        } finally {  
//            try {  
//                if (indexWriter != null)  
//                    indexWriter.close();  
//            } catch (Exception e) {  
//                System.err.println("���ܹر�indexWriter");  
//            }  
//        }  
//    }  
//    /** 
//     * ���ļ����ݷ�װ��Document 
//     * @param file 
//     * @return 
//     * @throws IOException 
//     */  
//    private static Document fileToText(File file) throws IOException {  
//        Document document = new Document();  
//        document.add(new Field("path",file.getAbsolutePath(),Store.YES,Index.NOT_ANALYZED));  
//        document.add(new Field("colContent",getContent(file),Store.YES,Index.ANALYZED));  
//        return document;  
//    }  
//      
//    /** 
//     * �ĵ��ַ��� 
//     * @param file 
//     * @return 
//     */  
//    public static String getContent(File file) {  
//        byte[]buffer = new byte[1024];  
//        StringBuffer sb = new StringBuffer();  
//        try {  
//            InputStream in = new FileInputStream(file);  
//            while(in.read(buffer) > 0) {  
//                sb.append(new String(buffer));  
//            }  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//        return sb.toString();  
//    }  
//    public static List<File> getFiles() {  
//        File file = new File(Constants.filePath);  
//        return Arrays.asList(file.listFiles());  
//    }  
//      
//    public static void main(String[] args) {  
//        initIndex();  
//    }  
//}
