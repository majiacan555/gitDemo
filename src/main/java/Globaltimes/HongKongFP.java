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
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

public class HongKongFP {
	static int i;
	static String url;
	static List<UrlList> urlLists;

	public static void main(String[] args) throws Exception {
		String baseUrl = "hongkongfp";
		String searchWord = "hong+kong+protest";
		String fileName = "E:\\数据分析\\urlList\\" + baseUrl + "_" + searchWord + ".txt";
		int LastPage = 999999;
		for (int i = 1; i <= LastPage; i++) {
			String url = "https://www.hongkongfp.com/page/" + i + "/?s=" + searchWord;
			System.out.println("开始获取url：" + url);
			List<String> urlList = GetChineseAndEnglish(url);
			if (urlList.size() <= 0) {
				System.out.println(String.format("读取到当前页数：{0},url:{1},读取到的urlList为空，break", i, url));
				break;
			}
			LiberyCache.appendStringToFile(fileName, urlList);
			LiberyCache.spiderSleep(3000, 3000);
		}
	}

	public static List<String> GetChineseAndEnglish(String Url) throws InterruptedException, SQLException, IOException {
		List<String> UrlList = new ArrayList<String>();
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
			Elements memoElements = doc.getElementsByClass("meta-image");
			for (Element element : memoElements) {
				String SaveURL = "";
				Elements elements3 = element.select("a");
				String urlString = elements3.attr("href");
				String Title = elements3.attr("title");
				if (!urlString.isEmpty()) { 
					SaveURL += urlString;
					SaveURL += "_"+ Title; 
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