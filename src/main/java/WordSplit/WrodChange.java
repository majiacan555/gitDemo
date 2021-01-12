package WordSplit;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
 

import com.spreada.utils.chinese.ChineseCoverter;

import LiberyUtils.LiberyCache;
import SimHashService.SimHashModel;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public final class WrodChange {
	static Map<String, List<String>> WordSplitMap;

	public static void main(String[] args) throws InterruptedException, IOException { 
		String nameString  = "三级字";
		List<String> excelList = LiberyCache.ReadTextFromLocal("E:\\数据分析\\蔡老师项目\\通用规范汉字表项目\\通用规范汉字表_"+nameString+".txt", "utf-8");
		String wordLiberaryPath = "E:\\数据分析\\蔡老师项目\\通用规范汉字表项目\\拆分字库文档.txt";
		String SavePath2 = "E:\\数据分析\\蔡老师项目\\通用规范汉字表项目\\没有读取到拼音_拼音拆分.txt";
		List<String> resultList = GetResultList(excelList, wordLiberaryPath,SavePath2);
		String SavePathtxt = "E:\\数据分析\\蔡老师项目\\通用规范汉字表项目\\"+nameString+"拼音拆分.txt";
		String SavePathcsv = "E:\\数据分析\\蔡老师项目\\通用规范汉字表项目\\"+nameString+"拼音拆分.csv"; 
		LiberyCache.WriteStringToFile(SavePathtxt, resultList);
		LiberyCache.WriteStringToCSV(SavePathcsv, resultList);
	} 

	private static List<String> GetResultList(List<String> excelList, String wordLiberaryPath,String SavePath2)
			throws InterruptedException, IOException {
		List<String> resultList = new ArrayList<String>();
		for (String string : excelList) { 
			String[] stringsplit = string.split(",");
			String word = stringsplit[1];
			String wordString = ChineseCoverter.TraditionalToSinple(word);
			String pinyinString = ToPinyin(wordString);
			if (pinyinString.isEmpty())  {
				System.err.println(string + "没有读取到拼音");  
				List<String> resultList2 = new ArrayList<String>();
				resultList2.add(string);
				LiberyCache.appendStringToFile (SavePath2, resultList2);				
			}
			String resultString = string;
			resultString += "," + pinyinString + ",";
			Map<String, List<String>> resultMap = GetWordSplit(wordString, wordLiberaryPath);
			String wordSplit = "";
			if (resultMap.size() > 0) {
				for (List<String> stringList : resultMap.values()) {
					for (int i = 0; i < stringList.size(); i++) {
						if (i < stringList.size() - 1) {
							wordSplit += stringList.get(i) + ",";
						} else {
							wordSplit += stringList.get(i);
						}
					}
				}
			}
			resultString += wordSplit;
			resultList.add(resultString);
		}
		return resultList;
	}

	public static String ToPinyin(String chinese) {
		String pinyinStr = "";
		char[] newChar = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < newChar.length; i++) {
			if (newChar[i] > 128) {
				try {
					String[] pinString = PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat);
					if (pinString != null&&pinString.length>0)
						pinyinStr += pinString[0]; 
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinStr += newChar[i];
			}
		}
		return pinyinStr;
	}

	/**
	 * @Description: 汉字转换为拼音首字母
	 * @Author: vdi100
	 * @CreateDate: 2018/6/23
	 * @Version: 1.0
	 */
	public static String ToFirstChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}
	public static Map<String, List<String>> GetWordSplit(String key, String FilePath) throws IOException {
		Map<String, List<String>> resultString = new HashMap<String, List<String>>();
		if (WordSplitMap == null) {
			WordSplitMap = GetWordSplitMap(FilePath);
		}
		if (null != key && !"".equals(key)) {
			char[] arr = key.toCharArray();
			if (arr.length >= 1) {
				for (int k = 0; k < arr.length; k++) {
					String kString = String.valueOf(arr[k]);
					List<String> valueList = WordSplitMap.get(kString);
					if (null != valueList && valueList.size() > 0) {
						resultString.put(kString, valueList);
					} else {
						resultString.put(kString, new ArrayList<String>());
						break;
					}
				}
			}
		}
		return resultString;
	}

	private static Map<String, List<String>> GetWordSplitMap(String filePath) throws IOException {
		List<String> wordSplit = LiberyCache.ReadTextFromLocal(filePath, "UTF-8");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (String line : wordSplit) {
			String tempStr = line + "\r";
			String regExp = "\r";
			Pattern pattern = Pattern.compile(regExp);
			String[] mobiles = pattern.split(tempStr);
			if (null != mobiles && mobiles.length > 0) {
				if (null != mobiles[0] && !"".equals(mobiles[0])) {
					String content = mobiles[0].substring(1);
					if (null != content && !"".equals(content)) {
						List<String> list = extractMessage(content);
						if (null != list && list.size() > 0) {
							map.put(mobiles[0].substring(0, 1), list);
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 提取中括号中内容，忽略中括号中的中括号
	 * 
	 * @param msg
	 * @return
	 */
	public static List<String> extractMessage(String msg) {
		List<String> list = new ArrayList<String>();
		int start = 0;
		int startFlag = 0;
		int endFlag = 0;
		for (int i = 0; i < msg.length(); i++) {
			if (msg.charAt(i) == '〖') {
				startFlag++;
				if (startFlag == endFlag + 1)
					start = i;
			} else if (msg.charAt(i) == '〗') {
				endFlag++;
				if (endFlag == startFlag)
					list.add(msg.substring(start + 1, i));
			}
		}
		return list;
	}
}