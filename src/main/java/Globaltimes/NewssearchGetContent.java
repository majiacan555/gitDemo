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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import LiberyUtils.LiberyCache;
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

public class NewssearchGetContent {

	public static void main(String[] args) throws Exception {
		String baseUrl = "chinadaily";
		String searchWord = "extradition bill";
		String wordString= "";
		if (searchWord.contains(" ")) {
			String[] strsString = searchWord.split(" ");
			for (int i = 0; i < strsString.length; i++) {
				if (i == strsString.length-1)  
					wordString+= strsString[i];
				 else 
					wordString+= strsString[i]+"+"; 
			}
		}
		String fileName = wordString+".txt";
		String filePath = "E:\\数据分析\\urlList\\";
		String SavePath = "E:\\数据分析\\SearchWord\\"+baseUrl+"\\"+searchWord+"\\";
		String pathname = filePath + fileName;
		File file = new File(pathname);
		String Type = "utf-8";
		List<String> urlList = LiberyCache.ReadTextFromTxt(file, Type); 
		for (String Url : urlList) {
			if (!Url.contains("2019")) {
				continue;
			}
			System.out.println(Url);
			List<String> contentList = GetChineseAndEnglish(Url);
			if (contentList.size() <= 3) {
				System.out.println(Url + "获取到的数据过少 直接返回");
				continue;
			}
			String[] TitleTIME = contentList.get(0).split("_");
			String titleString = TitleTIME[0];
			if (titleString.contains("?")) {
				titleString = titleString.replace("?", "");
			}
			contentList.set(0, titleString);
			String lastString = contentList.get(contentList.size() - 1);
			while (lastString.contains("@") || lastString.contains("(HK Edition")) {
				System.out.println(titleString + "删除无用语句：" + lastString);
				contentList.remove(contentList.size() - 1);
				lastString = contentList.get(contentList.size() - 1);
			}
			int index = TitleTIME[1].indexOf("2019");
			String timeDate = TitleTIME[1].substring(index, index + 10);
			String SaveFilePath = SavePath + timeDate + " " + titleString + ".txt";
			LiberyCache.appendStringToFile(SaveFilePath, contentList);
			LiberyCache.spiderSleep(500, 500);
		}
	}

	public static List<String> GetChineseAndEnglish(String Url) throws InterruptedException, SQLException, IOException {
		List<String> UrlList = new ArrayList<String>(); 
		Map<String, String> mapRequestProperty = new HashMap<String, String>();
		mapRequestProperty.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		mapRequestProperty.put("Accept-Encoding", "gzip, deflate");
		mapRequestProperty.put("Accept-Language", "zh-CN,zh;q=0.9");
		mapRequestProperty.put("Connection", "keep-alive");
		mapRequestProperty.put("Cookie","wdcid=2ecab5ddf76553af; UM_distinctid=16dda2080e523b-06609365195c9e-b363e65-144000-16dda2080e651d; __auc=88d627d716dda208270ef7de8a7; U_COOKIE_ID=4b26b8b5ba84b331bea4ab5c32eb85c9; __utmc=155578217; vjuids=3355b1d91.16de4289384.0.cb47b482bf9cf; pt_s_3bfec6ad=vt=1571493849379&cad=; 7NSx_98ef_sid=62sIiH; vjlast=1571490927.1571500967.13; pt_3bfec6ad=uid=ZPWgMJbz/duPJONKmPDfaQ&nid=0&vid=cK0nAbxWkixeyLBlo369AA&vn=11&pvn=1&sact=1571549753048&to_flag=0&pl=ya/b1A1C2MyEIw5Hfgnh7A*pt*1571549752944; __utmz=155578217.1571554394.4.4.utmcsr=newssearch.chinadaily.com.cn|utmccn=(referral)|utmcmd=referral|utmcct=/en/search; __utma=155578217.1140027659.1571490927.1571554394.1571584455.5; __utmt=1; CNZZDATA3089622=cnzz_eid%3D1787920864-1571322212-%26ntime%3D1571582318; wdses=3eef089c3b45e7a2; wdlast=1571584457; __utmb=155578217.2.10.1571584455");
		mapRequestProperty.put("Host","www.chinadaily.com.cn");
		mapRequestProperty.put("Upgrade-Insecure-Requests", "1");
	 	mapRequestProperty.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
		mapRequestProperty.put("X-Requested-With", "XMLHttpRequest");
		String result = LiberyUtils.HttpClient.HttpGetString(Url, mapRequestProperty, "utf-8");
		if (!result.isEmpty()) {
			Document doc = Jsoup.parse(result, "UTF-8");
			String Title = "";
			String dateTime = "";
			String author = "";
			Element title = doc.getElementById("Title_e");
			if (title != null) {
				Title = title.select("h2").get(0).text();
				dateTime = title.select("h5").get(0).text();
				author = title.select("h3").get(0).text();
			} else {
				title = doc.getElementById("lft-art");
				if (title != null) {
					Title = title.select("h1").text();
					dateTime = doc.getElementsByClass("info_l").get(0).text();
					String[] strsStrings = dateTime.split("\\|");
					if (strsStrings.length > 0 && strsStrings[0].toLowerCase().contains("by")) {
						author = strsStrings[0];
					}
				} else {
					title = doc.getElementsByClass("lft_art").get(0);
					if (title != null) {
						Title = title.select("h1").text();
						dateTime = doc.getElementsByClass("info_l").get(0).text();
						String[] strsStrings = dateTime.split("\\|");
						if (strsStrings.length > 0 && strsStrings[0].toLowerCase().contains("by")) {
							author = strsStrings[0];
						}
					} else {
						title = doc.getElementsByClass("titleTxt32 blueTxt mb10").get(0);
						if (title != null) {
							Title = title.text();
							dateTime = doc.getElementsByClass("greyTxt6").get(0).text();
							author = doc.getElementsByClass("block blueTxt mb10").get(0).text();
						}
					}
				} 
			}
			String titleTime = Title + "_" + dateTime;
			titleTime = LiberyCache.ReplaceString(titleTime, "(China Daily)", "");
			author = LiberyCache.ReplaceString(author, "(China Daily)", "");
			author = LiberyCache.ReplaceString(author, "(HK Edition)", "");
			UrlList.add(titleTime);
			UrlList.add("Reporter:" + author);
			Element memoElement = doc.getElementById("Content");
			Elements elements = memoElement.select("p");
			for (Element element : elements) {
				String resultString = element.text();
				if (!resultString.trim().isEmpty()) {
					UrlList.add(resultString);
				}
			} 
		} 
		return UrlList;
	}
}