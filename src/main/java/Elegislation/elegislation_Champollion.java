package Elegislation;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import org.json.JSONArray;

import com.spreada.utils.chinese.ChineseCoverter;

import LiberyUtils.HttpClient;
import LiberyUtils.LiberyCache;

public class elegislation_Champollion {
	public static void main(String[] args) throws KeyManagementException, Exception {
		String BasePath = "E:\\数据分析\\Elegislation\\";
		String filePathString = BasePath + "File\\";
		File[] grouping = new File(filePathString).listFiles();
		int AllFileCount = 0; 
		for (File file : grouping) {
			File[] groupingFiles = file.listFiles();
			for (File cefile : groupingFiles) {
				String filePath = cefile.getPath() + "\\";
				String dirNameString = cefile.getName();
				String efinString = filePath + "efile.rtf";
				String cfinString = filePath + "cfile.rtf";
				File efile = new File(efinString);
				File cfile = new File(cfinString);
				if (efile.exists() && cfile.exists()) {
					try {
						AllFileCount++; 
						String eString = getTextFromRtf(efile, "utf-8");
						String cString = getTextFromRtf(cfile, "utf-8"); 
						String[] eResult = eString.split("\n");
						String[] cResult = cString.split("\n"); 
						List<String> eList = new ArrayList<String>();
						List<String> cList = new ArrayList<String>();
						for (String e : eResult) {
							if (!e.trim().isEmpty() && e.trim().length() > 0) {
								while (e.substring(0, 1).equals("	")) {
									e = e.substring(1);
								}
								eList.add(e);
							}
						}
						for (String c : cResult) { 
							if (c.contains("\\.")) {
								c = c.replace("\\.", " ");
							}
							if (c.contains(".")) {
								c = c.replace(".", " ");
							} 
							while (c.length() > 0 && c.substring(0, 1).equals("	")) {
								c = c.substring(1);
							}
							while (c.length() > 0 && c.substring(0, 1).equals(" ")) {
								c = c.substring(1);
							}
							if (!c.trim().isEmpty() && c.trim().length() > 0) { 
								cList.add(c);
							}
						} 
						System.out.println("Begin Save Count: "+ AllFileCount);
						String errPath = BasePath + "Elegislation\\" + dirNameString.replace(" ", "_") + "\\";
						if (eList.size() > 700) {
							if (eList.size() == cList.size()) {
								errPath = BasePath + "ElegislationMax_700AndSameSize\\" + dirNameString.replace(" ", "_") + "\\";
								System.out.println("errPath:"+ errPath+" eList.size():"+eList.size()+"cList.size()"+cList.size());

							}else {
								errPath = BasePath + "ElegislationMax_700\\" + dirNameString.replace(" ", "_") + "\\";
								System.out.println("errPath:"+ errPath+" eList.size():"+eList.size()+"cList.size()"+cList.size());
							}
						} 
						String eErrorFilePath = errPath + "TextE.txt";
						String cErrorFilePath = errPath + "TextC.txt";
						LiberyCache.WriteStringToFile(eErrorFilePath, eList);
						LiberyCache.WriteStringToFile(cErrorFilePath, cList);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
		}
	}

	public static String getTextFromRtf(File file, String Type) throws IOException {
		String result = null;
		DefaultStyledDocument styledDoc = new DefaultStyledDocument();
		InputStream streamReader = new FileInputStream(file);
		try {
			new RTFEditorKit().read(streamReader, styledDoc, 0);
			// 以 ISO-8859-1的编码形式获取字节byte[], 并以 GBK 的编码形式生成字符串
			result = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes("utf-8"), Type);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		} finally {
			if (streamReader != null) {
				streamReader.close();
			}
		}
		return result;
	}

	public static String getTextFromRtfBuffer(File file, String Type) throws IOException, BadLocationException {
		String result = null;
		BufferedReader strm = new BufferedReader(new FileReader(file.getPath()));
		StringBuffer sb = new StringBuffer();
		int s;
		while ((s = strm.read()) != -1) {
			sb.append((char) s);
		}
		RTFEditorKit rtfeditorKit = new RTFEditorKit();
		DefaultStyledDocument styledDoc = new DefaultStyledDocument();
		ByteArrayInputStream byreArrayInputStream = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));
		rtfeditorKit.read(byreArrayInputStream, styledDoc, 0);
		// 以 ISO-8859-1的编码形式获取字节byte[], 并以 GBK 的编码形式生成字符串
		result = new String(styledDoc.getText(0, styledDoc.getLength()).getBytes("utf-8"), Type);

		return result;
	}
}
