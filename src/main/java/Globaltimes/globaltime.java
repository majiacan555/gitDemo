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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import LiberyUtils.LiberyCache;
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

public class globaltime {

	public static void main(String[] args) throws Exception { 
		String searchWord = "hong+kong+protest";
		String fileName = "E:\\数据分析\\urlList\\globaltimes_"+searchWord+".txt";
		int LastPage = 5;
		for (int i = 1; i <= LastPage; i++) { 
			String url = "http://search.globaltimes.cn/QuickSearchCtrl?page_no="+i+"&search_txt="+searchWord;
			System.out.println("开始获取url："+url);
			List<String> urlList =  GetChineseAndEnglish(url);  
			LiberyCache.appendStringToFile(fileName, urlList);  
			LiberyCache.spiderSleep(3000, 3000);
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
		mapRequestProperty.put( "Cookie", "__utmc=232209344; _ga=GA1.2.1905655980.1571323045; UM_distinctid=16dda26ff0a56c-0338d57c94f6bc-b363e65-144000-16dda26ff0b726; _gid=GA1.2.551871125.1571489457; __utmz=232209344.1571492129.3.3.utmcsr=globaltimes.cn|utmccn=(referral)|utmcmd=referral|utmcct=/; JSESSIONID=2074D133E3B119EFBE477D014A0BF346; __utma=232209344.1905655980.1571323045.1571492129.1571542485.4; __utmt=1; __utmb=232209344.5.10.1571542485"  );
		mapRequestProperty.put("Host", "search.globaltimes.cn");
		mapRequestProperty.put("Upgrade-Insecure-Requests", "1"); 
		mapRequestProperty.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36"); 
		String result  = 	LiberyUtils.HttpClient.HttpGetString(Url, mapRequestProperty, "utf-8");
		if (!result.isEmpty()) {  
			Document doc = Jsoup.parse(result,"UTF-8");
			Elements memoElements = doc.getElementsByClass("row-fluid");
			for (Element element : memoElements) { 
				String SaveURL = "";
				Elements elements =	element.getElementsByClass("span3");
				if (elements.size()>0) { 
					String timeString = elements.get(0).text(); 
					if (!timeString.isEmpty())  
						SaveURL+= timeString + "_";
				}
				Elements elements2 =element.getElementsByClass("span9");
				if (elements2.size()>0) { 
					Elements elements3 =	elements2.get(0).select("a") ;
					String urlString = elements3.attr("href"); 
					if (!urlString.isEmpty())  
						SaveURL+= urlString;
				}
				if (!SaveURL.isEmpty()) {
					if (!UrlList.contains(SaveURL)) {
						System.out.println(SaveURL);
						UrlList.add(SaveURL);
					}
				}
			} 
		}  
		return UrlList; 
	}   
}