package Globaltimes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import LiberyUtils.HttpClient;
import LiberyUtils.LiberyCache;
import TransLationModel.UrlList;

public class SCMPGetContent {
	static int i;
	static String url;
	static List<UrlList> urlLists;

	public static void main(String[] args) throws Exception {
		String baseUrl = "SCMP";
		String UrlfilePath = "E:\\数据分析\\urlList\\";
		List<String> searchWordList = new ArrayList<String>();
		String Type = "utf-8";
//		searchWordList.add("extradition bill");
		searchWordList.add("hong kong protest");
		for (String searchWord : searchWordList) { 
			GetResultBySearchWord(UrlfilePath, baseUrl, searchWord, Type); 
		}
	}

	private static void GetResultBySearchWord(String urlfilePath, String baseUrl, String searchWord, String type)
			throws IOException, InterruptedException, SQLException {
		String wordString = CommonUtils.GetWordString(searchWord);
		String fileName = baseUrl + "_" + wordString + ".txt";
		String UrlFilename = urlfilePath + fileName;
		File file = new File(UrlFilename);
		List<String> urlList = LiberyCache.ReadTextFromTxt(file, type);
		String SavePath = "E:\\数据分析\\SearchWord\\" + baseUrl + "\\" + searchWord + "\\";
		int count = 0;
		for (String string : urlList) { 
			count++;
			String[] urlStrings = string.split("_");
			String titleString = urlStrings[0];
			String time = urlStrings[1];
			String author = "Reporter:" + urlStrings[2];
			String Url = urlStrings[3];
			System.out.println(Url);
			List<String> contentList = GetChineseAndEnglish(Url);
			if (contentList.size() <= 0) {
				System.out.println(Url + "获取到的数据过少 直接退出");
				break;
			}
			List<String> stringsList = new ArrayList<String>();
			stringsList.add(titleString);
			stringsList.add(author);
			for (int i = 0; i < contentList.size(); i++) {
				stringsList.add(contentList.get(i));
			}
			titleString = LiberyCache.ReplaceString(titleString, "?", "");
			titleString = LiberyCache.ReplaceString(titleString, ":", "");
			int beginIndex = time.indexOf("[Hong Kong]");
			int endIndex = time.indexOf("2019");
			if (endIndex + 4 >= beginIndex + 11) {
				String timeDate = time.substring(beginIndex + 11, endIndex + 4);
				String[] strsTime = timeDate.split(" ");
				if (strsTime.length >= 3) {
					String month = GetEnglishMonthToNum(strsTime[1]);
					timeDate = strsTime[2] + "-" + month + "-" + strsTime[0];
					String[] titleSplit = titleString.split(" ");
					if (titleSplit.length>=20) {
						titleString = "";
						for (int i = 0; i < 20; i++) {
							titleString += titleSplit[i]+" ";
						}
						titleString+="_";
					}
					String SaveFilePath = SavePath + timeDate + " " + titleString + ".txt";
					SaveFilePath = LiberyCache.ReplaceString(SaveFilePath, "/", " ");
					LiberyCache.WriteStringToFile(SaveFilePath, stringsList);
				}
			} 
			LiberyCache.spiderSleep(10000, 20000);
		}
	}


	public static List<String> GetChineseAndEnglish(String Url) throws InterruptedException, SQLException, IOException {
		List<String> UrlList = new ArrayList<String>();
		try {
			Map<String, String> mapRequestProperty = new HashMap<String, String>();
			mapRequestProperty.put("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
			mapRequestProperty.put("Accept-Encoding", "gzip, deflate, br");
			mapRequestProperty.put("Accept-Language", "zh-CN,zh;q=0.9");
			mapRequestProperty.put("Connection", "keep-alive");
			mapRequestProperty.put("Cookie",
					"authenticatedBy=IP; _vwo_uuid_v2=D09FD6210887BFA913AE22A4CF36383CE|04531f4f71f30a3d7d19a80849dfea3c; _vwo_uuid=D09FD6210887BFA913AE22A4CF36383CE; _vwo_ds=3%3Aa_0%2Ct_0%3A0%241572319274%3A84.21256759%3A%3A%3A340_0%2C305_0%3A1; _ga=GA1.3.1680956129.1572319277; _ga=GA1.5.1680956129.1572319277; fulltextShowAll=YES; _vis_opt_exp_358_exclude=1; OS_PERSISTENT=\"mxU/A1V+VhQ0rHO7F9yK/i7gyYV8nIsIZLVHYdHKMxA=\"; _gid=GA1.3.1882596791.1572831210; _gid=GA1.5.1882596791.1572831210; ezproxy=lTCVHe5xVGxo0PK; JSESSIONID=210499FB0923510AADEE42F59A752B91.i-0060418a6c7c5f5e1; OS_VWO_COUNTRY=HK; OS_VWO_INSTITUTION=11440; OS_VWO_LANGUAGE=eng; OS_VWO_MY_RESEARCH=false; OS_VWO_REFERRING_URL=N; OS_VWO_REQUESTED_URL=\"https://search.proquest.com/internationalnews1/docview/2299004685/6079C8BAF173497DPQ/1580?accountid=11440\"; OS_VWO_VISITOR_TYPE=returning; AWSELB=211F3B2F14E486D8303965AD2DFCA61E745238FF82CB36B06055650B19C0C6FE05576E68788A101FE55EBAE5EAC62DA2FF9C09565378C6F16E33625055A931000F38B96C79D555115DA5D882EC617D5A6A24C48D0C; AppVersion=r20191.9.0.272.4209; oneSearchTZ=480; _vis_opt_s=3%7C; _vis_opt_test_cookie=1; osTimestamp=1572861515.501; availability-zone=us-east-1a; _vwo_sn=540319%3A3; _gat_UA-61126923-3=1");
			mapRequestProperty.put("Referer", "https://search-proquest-com.lib-ezproxy.hkbu.edu.hk/internationalnews1/results/18AAF35E574742E7PQ/1?accountid=11440");
			mapRequestProperty.put("Host", "search-proquest-com.lib-ezproxy.hkbu.edu.hk");
			mapRequestProperty.put("Sec-Fetch-Mode", "navigate");
			mapRequestProperty.put("Sec-Fetch-Site", "none");
			mapRequestProperty.put("Sec-Fetch-User", "?1");
			mapRequestProperty.put("Upgrade-Insecure-Requests", "1");
			mapRequestProperty.put("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");
			String result = HttpClient.HttpsGetString(Url, mapRequestProperty, "utf-8", false);
			if (!result.isEmpty()) {
				Document doc = Jsoup.parse(result, "UTF-8");
				Element contentElements = doc.getElementById("readableContent");
				Elements elements  = contentElements.select("text");
				if (elements.size() >=0) {
					Elements elements2 = elements.get(0).select("p");
					for (Element element : elements2) {
						String resuString = element.text(); 
						if (!resuString.isEmpty())
							UrlList.add(resuString);
					}
				}else {
					System.out.println("contentElements.select(\"text\"); 为空");
				} 
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return UrlList;
	}
	private static String GetEnglishMonthToNum(String string) {
		Map<String, String> monthMap = new HashMap<String, String>();
		String value = "00";
		monthMap.put("jan", "01");
		monthMap.put("feb", "02");
		monthMap.put("mar", "03");
		monthMap.put("apr", "04");
		monthMap.put("may", "05");
		monthMap.put("june", "06");
		monthMap.put("july", "07");
		monthMap.put("aug", "08");
		monthMap.put("sep", "09");
		monthMap.put("oct", "10");
		monthMap.put("nov", "11");
		monthMap.put("dec", "12");
		String key = string.toLowerCase().trim();
		if (monthMap.containsKey(key)) {
			value = monthMap.get(key);
		}
		return value;
	}

}