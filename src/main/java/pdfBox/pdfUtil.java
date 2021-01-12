package pdfBox;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.bouncycastle.jce.provider.asymmetric.ec.Signature.ecCVCDSA;

public class pdfUtil {
	//实体类的名称:pdfDomainVO

    private Integer id;//id

    private Date time;//操作时间

    private String filename;//文件名

    private String filesize;//文件大小

    private String filetype;//文件类型

    private String details;//操作详情
 
    private String outputfile;//输出路径(保存路径)

    private String inputfile;//要操作的pdf路径

    private String strtofind;//需要替换的文本

    private String message;//替换的文本

    private String imagefile;//图片路径

    private String imagelist;//图片集合

    private Integer pageno;//指定页码

    private Integer pages;//总页数

    private Integer rid;//...

    private Integer pageoperation;//操作页数

    private Integer pagestart;//开始页

    private Integer pageend;//结束页

    private String position;//位置:X,Y

    private String fileSizeAfter;//操作后文件大小

    private Integer status;//状态

    private Integer afterPages;//操作后页码

    private Integer imgSize;//图片大小
    /**
     * 读取pdf中文字信息(全部)
     */
    public static void READPDF(String Directory,String inputFile){
        //创建文档对象
        PDDocument doc =null;
        
        try {
            //加载一个pdf对象
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
             
            //获取一个PDFTextStripper文本剥离对象  
//            System.out.println("内容:"+content);
            //关闭文档
            
            System.out.println("全部页数"+doc.getNumberOfPages());  
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
    	            if(f.isDirectory()){// 判断是否文件夹
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
