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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
 

import LiberyUtils.LiberyCache; 

public class GlobaltimeGetContent {

	public static void main(String[] args) throws Exception { 
		String fileName = "globaltimes_extradition+bill.txt";
		String filePath = "E:\\数据分析\\urlList\\";
		String SavePath = "E:\\数据分析\\SearchWord\\globaltimes\\extradition bill\\";
		String pathname =filePath+fileName ;
		File file = new File(pathname);
		String Type = "utf-8"; 
		List<String> urlList = LiberyCache.ReadTextFromTxt(file, Type);
		for (String string : urlList) { 
			String[] strs = string.split("_");
			String[] strsTime = strs[0].split("/");
			String timeDate =strsTime[0]+"-"+strsTime[1]+"-"+strsTime[2] ; 
			String Url = strs[1];
			System.out.println(Url); 
			List<String> contentList = GetChineseAndEnglish(Url);
			String titleString = contentList.get(0); 
			if (titleString.contains("?")) { 
				titleString = titleString.replace("?", "");
			}
			if (titleString.contains(":")) { 
				titleString = titleString.replace(":", "");
			}
			String lastString = contentList.get(contentList.size()-1);
			while ( lastString.contains("@") ||lastString.contains("(Global Times)")||lastString.contains("Global Times")  ) {
				System.out.println(titleString+"删除无用语句："+lastString);
				contentList.remove(contentList.size()-1);
				lastString = contentList.get(contentList.size()-1); 
			}
			String SaveFilePath = SavePath+ timeDate+ " " + titleString+".txt"; 
			if (contentList.size()>=3) { 
				LiberyCache.appendStringToFile(SaveFilePath, contentList);  
				LiberyCache.spiderSleep(500, 500);
			}
			else {
				System.out.println(string + "contentList.size:"+ contentList.size());
			}
		}
	}

	public static List<String>  GetChineseAndEnglish(String Url ) throws InterruptedException, ClientProtocolException, IOException 
	{
		List<String> UrlList = new ArrayList<String>();
		Map<String, String> mapRequestProperty = new HashMap<String, String>();
		mapRequestProperty.put( "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3" );
		mapRequestProperty.put("Accept-Encoding" ,  "gzip, deflate");
		mapRequestProperty.put("Accept-Language", "zh-CN,zh;q=0.9");
		mapRequestProperty.put("Connection", "keep-alive");
		mapRequestProperty.put("Cache-Control", "max-age=0");
		mapRequestProperty.put( "Cookie", "__utmc=232209344; _ga=GA1.2.1905655980.1571323045; UM_distinctid=16dda26ff0a56c-0338d57c94f6bc-b363e65-144000-16dda26ff0b726; _gid=GA1.2.551871125.1571489457; __utmz=232209344.1571492129.3.3.utmcsr=globaltimes.cn|utmccn=(referral)|utmcmd=referral|utmcct=/; __utma=232209344.1905655980.1571323045.1571556577.1571574264.6; _gat=1; CNZZDATA1274811350=1743039451-1571319230-null%7C1571575592; __utmt=1; __atuvc=8%7C42%2C2%7C43; __atuvs=5dac5740b6137af9001; __utmb=232209344.7.10.1571574264"  );
		mapRequestProperty.put("Host", "www.globaltimes.cn");
		mapRequestProperty.put("Upgrade-Insecure-Requests", "1");
		mapRequestProperty.put("If-Modified-Since", "Sun, 20 Oct 2019 12:44:19 GMT");
		mapRequestProperty.put("If-None-Match", "\"3599-59556ed05a761-gzip\"");
		mapRequestProperty.put("Referer", "http://search.globaltimes.cn/QuickSearchCtrl");
		mapRequestProperty.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36"); 
		String result  = 	LiberyUtils.HttpClient.HttpGetString(Url, mapRequestProperty, "utf-8");
		if (!result.isEmpty()) { 
			Document doc = Jsoup.parse(result,"UTF-8");
			Elements title = doc.getElementsByClass("article-title");
			String Title = "";
			if (title.size()>0) {
				  Title = title.get(0).text();  
			}
			UrlList.add(Title); 
			String Author = "";
			Elements sourcElements = doc.getElementsByClass("article-source");
			if (sourcElements .size()>0) {
				Elements author= doc.getElementsByClass("text-left");
				if (author .size()>0) {
				  Author = author.get(0).text();  
				}
			}
			int index = Author.indexOf("Source:");
			if (index>0) {
				Author = Author.substring(0,index);
			}
			UrlList.add("Reporter:"+Author); 
			
			Elements memoElements = doc.getElementsByClass("span12");
			for (Element element : memoElements) {  
				for (TextNode element2 : element.textNodes()) { 
					 String nodeString = element2.toString(); 
						if (nodeString.contains("&quot;")) {
							nodeString = nodeString.replace("&quot;", "");
						}
						if (nodeString.contains("<br />")) {
							nodeString = nodeString.replace("<br />", "");
						}
						if (nodeString.contains("&nbsp;")) {
							nodeString = nodeString.replace("&nbsp;", "");
						} 
						if (nodeString.contains("○")) {
							nodeString = nodeString.replace("○", "");
						}
						
						if ( !nodeString.trim().isEmpty() &&  !nodeString.trim().equals("")) { 
							UrlList.add(nodeString);  
						}
					}  
			}  
		}  
		return UrlList; 
	}   
}