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

public class HongKongFPGetContent {
	static int i;
	static String url;
	static List<UrlList> urlLists;

	public static void main(String[] args) throws Exception {
		String baseUrl = "hongkongfp";
		String year = "2019";
		String UrlfilePath = "E:\\数据分析\\urlList\\";
		List<String> searchWordList = new ArrayList<String>();
		String Type = "utf-8";
		searchWordList.add("extradition bill");
		searchWordList.add("hong kong protest");
		for (String searchWord : searchWordList) {
			GetResultBySearchWord(UrlfilePath,baseUrl,searchWord,year,Type);
			
		} 
	} 
	private static void GetResultBySearchWord(String urlfilePath, String baseUrl, String searchWord, String year,
			String type) throws IOException, InterruptedException, SQLException {
		String wordString = CommonUtils. GetWordString(searchWord); 
		String fileName = baseUrl + "_" + wordString + ".txt";
		String UrlFilename = urlfilePath + fileName;
		File file = new File(UrlFilename);
		List<String> urlList = LiberyCache.ReadTextFromTxt(file,type );
		String SavePath = "E:\\数据分析\\SearchWord\\" + baseUrl + "\\" + searchWord + "\\";
		for (String string : urlList) {
			if (!string.contains(year)) {
				continue;
			}
			String[] urlStrings = string.split("_");
			String Url = urlStrings[0];
			String titleString = urlStrings[1];
			System.out.println(Url);
			List<String> contentList = GetChineseAndEnglish(Url);
			if (contentList.size() <= 3) {
				System.out.println(Url + "获取到的数据过少 直接返回");
				continue;
			}
			List<String> stringsList = new ArrayList<String>(); 
			stringsList.add(titleString);
			for (int i = 0; i < contentList.size(); i++) {
				stringsList.add(contentList.get(i));
			}
			titleString = LiberyCache.ReplaceString(titleString, "?", "");
			titleString = LiberyCache.ReplaceString(titleString, ":", ""); 
			int index = Url.indexOf(year);
			if (Url.length()>=index + 10) { 
				String timeDate = Url.substring(index, index + 10);
				String[] strsTime = timeDate.split("/");
				if (strsTime.length>=3) {
					timeDate = strsTime[0] + "-" + strsTime[1] + "-" + strsTime[2]; 
					String SaveFilePath = SavePath + timeDate + " " + titleString + ".txt";
					SaveFilePath =  LiberyCache.ReplaceString(SaveFilePath, "/", " "); 
					LiberyCache.WriteStringToFile(SaveFilePath, stringsList);
				}
			}
			LiberyCache.spiderSleep(500, 500);
		} 
	}



	public static List<String> GetChineseAndEnglish(String Url) throws InterruptedException, SQLException, IOException {
		List<String> UrlList = new ArrayList<String>();
		try {
			Map<String, String> mapRequestProperty = new HashMap<String, String>();
			mapRequestProperty.put("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
			mapRequestProperty.put("Accept-Encoding", "gzip, deflate");
			mapRequestProperty.put("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.8, en-US; q=0.5, en; q=0.3");
			mapRequestProperty.put("Cookie",
					"__cfduid=d0a76d3270d919437764ecb949f857b0a1571669620; __stripe_mid=69d4ee9b-5f17-450d-b803-60e80feff4ce; _ga=GA1.2.136404372.1571669630; _gid=GA1.2.491244290.1571669635; __gads=ID=5e66051a8214303a:T=1571669635:S=ALNI_MYRjFG1ByGVl3YA_VVU4bwrk_0lHA; __stripe_sid=5a20558a-b7cb-4dea-8cb7-30e14d94d639; _gat=1");
			mapRequestProperty.put("Host", "https://www.hongkongfp.com/?s=hong+kong+protest");
			mapRequestProperty.put("If-Modified-Since", "Thu, 06 Sep 2018 07:44:06 GMT");
			mapRequestProperty.put("If-None-Match", "1536219846000|#public|0|zh_CN|||0|135818715|211970278");
			mapRequestProperty.put("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");
			String result = HttpClient.HttpsGetString(Url, mapRequestProperty, "utf-8", true);
			if (!result.isEmpty()) {
				Document doc = Jsoup.parse(result, "UTF-8");
				Element contentElements = doc.getElementById("content");
				String author = "";
				Elements authorElements = contentElements.getElementsByClass("fn");
				if (authorElements.size() > 0) {
					author = authorElements.get(0).text();
				}
				UrlList.add("Reporter:" + author);
				Elements memoElements = contentElements.getElementsByClass("entry-content");
				Elements elements = memoElements.get(0).select("p");
				Elements tweetElements = doc.getElementsByClass("twitter-tweet");
				String twitterString = "";
				if (tweetElements.size() > 0) {
					twitterString = tweetElements.get(0).text();
					System.out.println("TwitterString" + twitterString);
				}
				for (Element element : elements) {
					String resuString = element.text();
					if (resuString.toLowerCase().contains("photo:"))
						continue;
					if (twitterString.contains(resuString))
						continue;
					if (!resuString.isEmpty())
						UrlList.add(resuString);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return UrlList;
	}

}