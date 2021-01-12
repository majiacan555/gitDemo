package SimHashService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import LiberyUtils.LiberyCache;

public class HLMMain {
	public static void main(String[] args) throws IOException, InterruptedException { 
		String BaseSavePath ="E:\\数据分析\\HLMSimilarity\\Hawkes\\";
		double ReqSimilarPercent = 0.7; //要求的句子相似度
		String ycpath = "E:\\数据分析\\HLM\\Yang\\Chinese\\";
		Map<String, List<String>> YCResultList = GetChineseString(ycpath);
		String hcpath = "E:\\数据分析\\HLM\\Hawkes\\Chinese\\";
		Map<String, List<String>> HCResultList = GetChineseString(hcpath);
		String yepath = "E:\\数据分析\\HLM\\Yang\\English\\";
		Map<String, List<String>> YEResultList = GetEnglishString(yepath);
		String hepath = "E:\\数据分析\\HLM\\Hawkes\\English\\";
		Map<String, List<String>> HEResultList = GetEnglishString(hepath);
		Map<Integer, List<SimHashModel>>   hMap= GetResultMap("Yang", YCResultList, YEResultList);
		Map<Integer, List<SimHashModel>> yMap = GetResultMap("Hawkes", HCResultList, HEResultList);
		Map<Integer, List<SimHashModel>> resultMap = new TreeMap<Integer, List<SimHashModel>>();
		for (Integer key : yMap.keySet()) {
			if (hMap.containsKey(key)) {
				int id = 0;
				List<SimHashModel> yList = yMap.get(key);
				List<SimHashModel> hList = hMap.get(key);
				int NoGetCount = 0;
				List<SimHashModel> resultList = new ArrayList<SimHashModel>();
				long l3 = System.currentTimeMillis();
				for (int i = 0; i < yList.size(); i++) {
					id++;
					SimHashModel ymodel = yList.get(i);
					String ycString = ymodel.gethChineseData();
					SimHash hash1 = new SimHash(ycString);
					 
					double MaxPercent = -1; //找出前后30行中，相似度最高的一句
					SimHashModel BestModel = null;
					for (int j = i - 30; j < i + 30; j++) {
						if (j >= 0 && j < hList.size()) {
							SimHashModel hModel = hList.get(j);
							String hcString = hModel.gethChineseData();
							SimHash hash2 = new SimHash(hcString); 
							double SimilarPercent = hash1.getSemblance(hash2);
							if (SimilarPercent >= ReqSimilarPercent) { 
								 
								if (SimilarPercent>MaxPercent) {
									MaxPercent = SimilarPercent;
									BestModel = hModel;
								}
								 
							}
						}
					} 
					SimHashModel resultModel = new SimHashModel();
					resultModel.setNumID(id);
					resultModel.setyChineseData(ycString); 
					resultModel.setyEnglishData(ymodel.gethEnglishData()); 
					if (BestModel!=null) { 
						String	hcString = BestModel.gethChineseData(); 
						resultModel.sethChineseData(hcString); 
						resultModel.sethEnglishData(BestModel.gethEnglishData()); 
						resultModel.setSimilarPercent(MaxPercent);
					}else { 
						NoGetCount++; 
					}
					resultList.add(resultModel);
					String savePath = BaseSavePath+ "Chapter "+key+".xls";
					LiberyUtils.SetToExcel.SetToExcelSimHashHLM(savePath ,resultList);
				} 
				
				
				System.out.println("章节:"+key+"---总行数:"+yList.size()+"没找到对应句子行数:"+NoGetCount);
				long l4 = System.currentTimeMillis();
				System.out.println("总耗时:"+(l4 - l3)/1000.0+"s"); 
				System.out.println("");
				System.out.println("");
			}
		}

	}

	private static Map<Integer, List<SimHashModel>> GetResultMap(String author, Map<String, List<String>> YCResultList,
			Map<String, List<String>> YEResultList) {
		Map<Integer, List<SimHashModel>> Map = new TreeMap<Integer, List<SimHashModel>>();
		for (String cKey : YCResultList.keySet()) {
			for (String eKey : YEResultList.keySet()) {
				String cNum = cKey.split("\\.")[0].substring(2);
				String eNum = "-1";
				if (author.equals("Yang")) {
					eNum = eKey.split("\\.")[0].substring(3);
				} else
					eNum = eKey.split("\\.")[0].substring(2);

				if (cNum.equals(eNum)) {
					List<String> cList = YCResultList.get(cKey);
					List<String> eList = YEResultList.get(eKey);
					if (cList.size() != eList.size()) {
						System.out.println(cNum + "----cCount:" + cList.size() + " eCount" + eList.size());
					} else {
						int num = Integer.parseInt(cNum);
						List<SimHashModel> simList = new ArrayList<SimHashModel>();
						for (int i = 0; i < cList.size(); i++) {
							String cStr = cList.get(i);
							String eStr = eList.get(i);
							SimHashModel model = new SimHashModel();
							model.sethChineseData(cStr);
							model.sethEnglishData(eStr);
							model.setNumID(num);
							simList.add(model);
						}
						Map.put(num, simList);
					}
				}
			}
		}
		return Map;
	}

	private static Map<String, List<String>> GetEnglishString(String path) throws IOException {
		File Efile = new File(path);
		File[] efiles = Efile.listFiles();
		Map<String, List<String>> mapList = new TreeMap<String, List<String>>();
		for (File f3 : efiles) {
			String fileName = f3.getName();
			List<String> strList = LiberyCache.ReadTextFromTxt(f3, "gbk");
			List<String> strList2 = new ArrayList<String>();
			for (String str : strList) {
				if (str.contains("§")) {
					str = str.replace("§", "");
				}
				strList2.add(str);
			}
			mapList.put(fileName, strList2);
		}
		return mapList;
	}

	private static Map<String, List<String>> GetChineseString(String path) throws IOException {
		File Cfile = new File(path);
		File[] cfiles = Cfile.listFiles();
		Map<String, List<String>> mapList = new TreeMap<String, List<String>>();
		for (File f3 : cfiles) {
			String fileName = f3.getName();
			List<String> strList = LiberyCache.ReadTextFromTxt(f3, "gbk");
			List<String> strList2 = new ArrayList<String>();
			for (String str : strList) {
				str = str.replace(" ", "");
				if (str.contains("\\.")) {
					str = str.replace("\\.", "");
				}
				if (str.contains("\"")) {
					str = str.replace("\"", "“");
				}
				if (str.contains("§")) {
					str = str.replace("§", "");
				}
				strList2.add(str);
			}
			mapList.put(fileName, strList2);
		}
		return mapList;
	}
}
