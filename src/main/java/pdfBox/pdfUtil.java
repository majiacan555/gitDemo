package pdfBox;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.bouncycastle.jce.provider.asymmetric.ec.Signature.ecCVCDSA;

public class pdfUtil {
	//ʵ���������:pdfDomainVO

    private Integer id;//id

    private Date time;//����ʱ��

    private String filename;//�ļ���

    private String filesize;//�ļ���С

    private String filetype;//�ļ�����

    private String details;//��������
 
    private String outputfile;//���·��(����·��)

    private String inputfile;//Ҫ������pdf·��

    private String strtofind;//��Ҫ�滻���ı�

    private String message;//�滻���ı�

    private String imagefile;//ͼƬ·��

    private String imagelist;//ͼƬ����

    private Integer pageno;//ָ��ҳ��

    private Integer pages;//��ҳ��

    private Integer rid;//...

    private Integer pageoperation;//����ҳ��

    private Integer pagestart;//��ʼҳ

    private Integer pageend;//����ҳ

    private String position;//λ��:X,Y

    private String fileSizeAfter;//�������ļ���С

    private Integer status;//״̬

    private Integer afterPages;//������ҳ��

    private Integer imgSize;//ͼƬ��С
    /**
     * ��ȡpdf��������Ϣ(ȫ��)
     */
    public static void READPDF(String Directory,String inputFile){
        //�����ĵ�����
        PDDocument doc =null;
        
        try {
            //����һ��pdf����
            doc =PDDocument.load(new File(inputFile));
            int pageCount =  doc.getNumberOfPages();
            for (int i = 1; i < pageCount; i++) {
            	PDFTextStripper textStripper =new PDFTextStripper("GBK");
            	textStripper.setSortByPosition(true);
            	textStripper.setStartPage(i);
            	textStripper.setEndPage(i);
            	String content=textStripper.getText(doc);
                FileOutputStream fop = null;
                File fileDir = new File("D://documents//"+Directory+"//DocuMent"+i);
                if(!fileDir.exists()){
                	if (!fileDir.getParentFile().exists()) {
                		fileDir.getParentFile().mkdirs();
                	}
                	fileDir.mkdirs();
                }
                File file = null;
                if(inputFile.contains("CHN")){
                	file = new File("D://documents//"+Directory+"//DocuMent"+i+"//TextC.txt");
                }else {
                	file = new File("D://documents//"+Directory+"//DocuMent"+i+"//TextE.txt");
				}
                if (file.exists()) {
	            	file.delete();
	            	file.createNewFile();
	            }
                if (!file.exists()) {
                	file.createNewFile();
                }
                fop = new FileOutputStream(file);
                byte[] contentInBytes = content.getBytes();
                fop.write(contentInBytes);
                fop.flush();
                fop.close();
                System.out.println("D://documents//DocuMent"+i+"//TextE.txt--Done");
				
			}
             
            //��ȡһ��PDFTextStripper�ı��������  
//            System.out.println("����:"+content);
            //�ر��ĵ�
            
            System.out.println("ȫ��ҳ��"+doc.getNumberOfPages());  
            doc.close();
            System.out.println("Done");
           
             
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
    	File Basefile =   new File("D:\\FinancialReport");
    	 File[] files = Basefile.listFiles();
    	 if (files!=null) {
    		 for (File f : files) {
    	            if(f.isDirectory()){// �ж��Ƿ��ļ���
    	            	File[] files2 = f.listFiles();
    	            	 if (files2!=null) {
    	            		 for (File f2 : files2) {
    	            			 	String file = f2.getPath();
    	            			 	String directory = f.getName();
    	            			 	READPDF(directory,file);
    	            	}
    	            	 }
    	            	 }
    	            }
    	            
    	        }
    
		}
	


}
