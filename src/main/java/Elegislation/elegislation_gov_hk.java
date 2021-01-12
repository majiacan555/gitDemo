package Elegislation;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import LiberyUtils.HttpClient;
import LiberyUtils.LiberyCache;

public class elegislation_gov_hk {
	public static void main(String[] args) throws KeyManagementException, Exception {
		String BasePath = "E:\\数据分析\\Elegislation\\";
		String filePathString = BasePath + "File\\";
		String UrlSavePath = BasePath + "Url\\";
		List<String> GrounpList = new ArrayList<String>();
//		GrounpList.add("1-100");
		GrounpList.add("101-200");
		GrounpList.add("201-300");
		GrounpList.add("301-400");
		GrounpList.add("401-500");
		GrounpList.add("501-600");
		GrounpList.add("601-700");
		GrounpList.add("1001-1100");
		GrounpList.add("1101-1200");
		Map<String, String> mapRequestProperty = new HashMap<String, String>();
		mapRequestProperty.put("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		mapRequestProperty.put("Accept-Encoding", "gzip, deflate, br");
		mapRequestProperty.put("Accept-Language", "zh-CN,zh;q=0.9");
		mapRequestProperty.put("Connection", "keep-alive");
		mapRequestProperty.put("Cookie",
				"JSWP1=F05962D7A5F5DB544A81EFDF9BCA896D; CLIENT_URL_FORWARD=UBIeSAUCHhxHERwTWFQDDVEFVFBHWQkFE1pXEERQHRdYXVQDExJeUAcaTBNKX0ZdBA5PAkhWVwlQbGhjdVtaG2lhNi8FRB5lamAjVg4bdCckfyN5dnYNIw==; TaJM6jvsmH6aaGI39F7o7inO=v1KsIxJQSDcIB; CLIENT_CONFIG_RESULT_ATTRIBUTE=%7B%22isOSSupported%22%3Atrue%2C%22isJvmVersionSupported%22%3Afalse%2C%22isBrowserSupported%22%3Atrue%2C%22isJvmSupported%22%3Afalse%2C%22isOSVersionSupported%22%3Atrue%2C%22isBrowserVersionSupported%22%3Atrue%7D; CLIENT_CONFIG_ATTRIBUTE=%7B%22branchCode%22%3A%2200%22%2C%22jvmVendor%22%3A%22%22%2C%22osVersion%22%3A%22Windows+10%22%2C%22jvmVersion%22%3A%22%22%2C%22browserVersion%22%3A%2278.0%22%2C%22isJavascriptEnabled%22%3Atrue%2C%22browserName%22%3A%22Chrome%22%2C%22userAgent%22%3A%22Mozilla%2F5.0+%28Windows+NT+10.0%3B+Win64%3B+x64%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F78.0.3904.97+Safari%2F537.36%22%2C%22applicationId%22%3A%22RA001%22%2C%22osName%22%3A%22Windows%22%2C%22isJvmEnabled%22%3Afalse%2C%22isCookieEnabled%22%3Atrue%7D; CLIENT_REDIRECT_URL_ATTRIBUTE=https://www.elegislation.gov.hk/client-check; clientCheckStatus=S; fontSize=default");
		mapRequestProperty.put("Host", "www.elegislation.gov.hk");
		mapRequestProperty.put("Sec-Fetch-Mode", "navigate");
		mapRequestProperty.put("Sec-Fetch-Site", "same-origin");
		mapRequestProperty.put("Sec-Fetch-User", "?1");
		mapRequestProperty.put("Upgrade-Insecure-Requests", "1");
		mapRequestProperty.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");

		for (String GROUPING : GrounpList) {
			String fileName = UrlSavePath + GROUPING + ".txt";
			File file = new File(fileName);
			List<String> urlList = LiberyCache.ReadTextFromTxt(file, "utf-8");
			for (String string : urlList) {
				JSONArray jsonArray = new JSONArray(string);
				for (Object dataObj : jsonArray) {
					JSONArray dataArray = (JSONArray) dataObj;
					String title = (String) dataArray.get(1);
					String name = (String) dataArray.get(3); 
					String filePath = filePathString + GROUPING + "\\" + title + "\\";
					String EnUrl = "https://www.elegislation.gov.hk" + name + "!en.assist.rtf?FROMCAPINDEX=Y";
					String ChUrl = "https://www.elegislation.gov.hk" + name + "!zh-Hant-HK.assist.rtf?FROMCAPINDEX=Y";
					String eFileName = "efile.rtf";
					String cFileName = "cfile.rtf";
					try {
						String efinString = filePath + eFileName;
						File efile = new File(efinString);
						if (!efile.exists()) {
							String Enresult = HttpClient.HttpsGetFilePath(EnUrl, mapRequestProperty, "utf-8", true, false,
									filePath, eFileName);
							LiberyCache.spiderSleep(1500, 1500);
						} else {
							System.out.println(GROUPING + "--文件已存在" + efinString);
						}
						String cfinString = filePath + cFileName;
						File cfile = new File(cfinString);
						if (!cfile.exists()) {
							String Chresult = HttpClient.HttpsGetFilePath(ChUrl, mapRequestProperty, "utf-8", true, false,
									filePath, cFileName);
							LiberyCache.spiderSleep(1500, 1500);
						} else {
							System.out.println(GROUPING + "--文件已存在" + cfinString);
						}
						
					} catch (Exception e) {
						LiberyCache.spiderSleep(50000, 50000);
					}
				}
			}
		}

	}
}
