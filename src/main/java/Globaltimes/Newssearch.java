package Globaltimes;

import java.io.BufferedReader;
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
import org.jsoup.select.Elements;

import LiberyUtils.LiberyCache;
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

public class Newssearch {

	public static void main(String[] args) throws Exception { 
		String keywords ="hong+kong+protest";
		int LastPage = 330; 
		String fileName = "E:\\数据分析\\urlList\\"+keywords+".txt";
		for (int i = 111; i < LastPage; i++) {
			String url = "http://newssearch.chinadaily.com.cn/rest/en/search?keywords="+keywords+"&sort=dp&page="+i+"&curType=story&type=&channel=&source=";
			System.out.println("开始获取url："+url);
			List<String> urlList =  GetChineseAndEnglish(url);  
			LiberyCache.appendStringToFile(fileName, urlList); 
			LiberyCache.spiderSleep(3000, 3000);
		}
	}

	public static List<String> GetChineseAndEnglish(String Url ) throws InterruptedException, SQLException, IOException 
	{
		List<String> UrlList = new ArrayList<String>(); 
		Map<String, String> mapRequestProperty = new HashMap<String, String>();
		mapRequestProperty.put("Accept", "*/*");
		mapRequestProperty.put("Accept-Encoding" ,  "gzip, deflate");
		mapRequestProperty.put("Accept-Language", "zh-CN,zh;q=0.9");
		mapRequestProperty.put("Connection", "keep-alive"); 
		mapRequestProperty.put( "Cookie",  "wdcid=2ecab5ddf76553af; UM_distinctid=16dda2080e523b-06609365195c9e-b363e65-144000-16dda2080e651d; __auc=88d627d716dda208270ef7de8a7; U_COOKIE_ID=4b26b8b5ba84b331bea4ab5c32eb85c9; __utmc=155578217; vjuids=3355b1d91.16de4289384.0.cb47b482bf9cf; 7NSx_98ef_sid=62sIiH; __utma=155578217.1140027659.1571490927.1571490927.1571500966.2; __utmz=155578217.1571500966.2.2.utmcsr=newssearch.chinadaily.com.cn|utmccn=(referral)|utmcmd=referral|utmcct=/en/search; vjlast=1571490927.1571500967.13; __utmb=155578217.4.10.1571500966" );
		mapRequestProperty.put("Host", "newssearch.chinadaily.com.cn");
		mapRequestProperty.put("Referer", "http://newssearch.chinadaily.com.cn/en/search?query=extradition%20bill");
		mapRequestProperty.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36"); 
		mapRequestProperty.put("X-Requested-With", "XMLHttpRequest"); 
		String result  = 	LiberyUtils.HttpClient.HttpGetString(Url, mapRequestProperty, "utf-8");
		if (!result.isEmpty()) {  
			JSONObject jsonObj = new JSONObject(result);
			for (String key : jsonObj.keySet()) {
				if (key.equals("content")) {  
					JSONArray jaArray = new JSONArray(jsonObj.get(key).toString()); 
					for (int index = 0; index < jaArray.length(); index++) {
						jaArray.get(index);
						System.out.print("index----"+index+":"); 
						JSONObject jsonObj2 = new JSONObject(jaArray.get(index).toString());
						for (String key2 : jsonObj2.keySet()) { 
							if (key2.equals("url")) {
								System.out.println(jsonObj2.get(key2));
								UrlList.add(jsonObj2.get(key2).toString());
							} 
						}
					} 
				}
			}   
		}  
		return UrlList;
	}   
}