package HttpClientDemo;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

public class hierarchy {
	static int i;
	static String url;
	static List<UrlList> urlLists;
	public static void main(String[] args) throws Exception {
		
				url  = "https://sites.google.com/site/yangxianyidainaidie/system/app/pages/sitemap/hierarchy";
				
				GetChineseAndEnglish(url,1);
//			String url = "https://cn.nytimes.com/morning-brief/20171016/mogadishu-iran-north-korea/dual/";
//			GetChineseAndEnglish(url,1);
			
		
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000.0)+"秒");
		Thread.sleep(Random);
		
	}
	
	public static void GetChineseAndEnglish(String Url,int id) throws KeyManagementException, Exception 
	{
		URL serverUrl;
		try {
			serverUrl = new URL(Url);
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) serverUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(100000);
				conn.setRequestProperty("Accept", "text/html, application/xhtml+xml, image/jxr, */*");
		        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
		        conn.setRequestProperty("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.8, en-US; q=0.5, en; q=0.3");
		        conn.setRequestProperty("Cookie", "aftzc=QXNpYS9Ib25nX0tvbmc6WCtidnc4YXVKN2ZaZXN5TDlob282d0ZXRnEwPQ; NID=139=taUcCvME0oU0ukZ6I3V-U23ukmZFU0LP86mSUvARh8FZ0rjyxMec5etYzB8n6P9cV40b_YWxdtQ4WYIMKyfBj2fYIrYGpKF-sK2aR9Pj8nqS0QcmfD8FtvcldEhpUXdj; 1P_JAR=2018-09-20-06");
		        conn.setRequestProperty("Host", "sites.google.com");
		        conn.setRequestProperty("If-Modified-Since", "Thu, 06 Sep 2018 07:44:06 GMT");
		        conn.setRequestProperty("If-None-Match", "1536219846000|#public|0|zh_CN|||0|135818715|211970278");
		        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");
				
//				
//				conn.setRequestProperty("authority", "sites.google.com");
//				conn.setRequestProperty("method", "GET");
//				conn.setRequestProperty("path", "/site/yangxianyidainaidie/system/app/pages/sitemap/hierarchy");
//				conn.setRequestProperty("scheme", "https");
//				conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//				conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
//				conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
//				conn.setRequestProperty("Cookie", "NID=136=TUNO4_DoHE27jnVgvcioLaObeqeoHNR7RCaEjVhW78YGCyO7MxoqG9rY-BCo-9X1rETbepver7fNwsoANpZihVmi-LaGLxoABMV4g_ZzIhg8-Ln6r-mUKFHvxkdLilUT; aftzc=QXNpYS9Ib25nX0tvbmc6WCtidnc4YXVKN2ZaZXN5TDlob282d0ZXRnEwPQ; 1P_JAR=2018-09-20-04; enabledapps.uploader=0");
//				conn.setRequestProperty("if-modified-since", "Thu, 06 Sep 2018 07:44:06 GMT");
//				conn.setRequestProperty("if-none-match", "\"1536219846000|#public|0|zh_CN|||0|135818715|211970278\"");
//				
//				
//				conn.setRequestProperty("upgrade-insecure-requests", "1");
//				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
				//必须设置false，否则会自动redirect到重定向后的地址
				conn.setInstanceFollowRedirects(false);
				conn.connect();
				/*请求url获取返回的内容*/
				
				StringBuffer buffer = new StringBuffer();
				//将返回的输入流转换成字符串
				if (conn.getResponseCode() == 200) {
					
					InputStream inputStream = conn.getInputStream();
					GZIPInputStream gis = new GZIPInputStream(inputStream); 
					InputStreamReader inputStreamReader = new InputStreamReader(gis,"utf-8");
					BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
					String str = null;
					while ((str = bufferedReader.readLine()) != null) {
						buffer.append(str);
					}
					String result = buffer.toString();
					System.out.println(result);
//					Document doc = Jsoup.parse(result,"UTF-8");
//					Elements memoElements = doc.getElementsByClass("section-title");
//					String Memo = memoElements.text();
//					Elements TitleElements = doc.getElementsByClass("article-header");
//					Elements Titles = TitleElements.select("h1");
//					String ChineseTitle = Titles.get(0).text();
//					String EnglishTitle = Titles.get(1).text();
//					Elements pp = doc.getElementsByClass("article-paragraph");
//					List<String> list = new ArrayList<String>();
//					for (int i = 0; i < pp.size(); i++) {
//						String text = pp.get(i).text();
//						if (text.length()>1 && !text.equals("_____")) {
//							list.add(text);
//						}
//					}
//					AddData addData = new AddData();
//					if (list.size() % 2 == 0) {
//						for (int i = 0; i < list.size(); i=i+2) {
//							ChineseAndEnglishModel CE = new ChineseAndEnglishModel();
//							CE.setChineseData(list.get(i+1));
//							CE.setEnglishData(list.get(i));
//							CE.setMemo(Memo);
//							CE.setTitleID(100000+id);
//							CE.setUrlType(7);
//							CE.setURL(Url);
//							CE.setTitle(EnglishTitle+"_"+ChineseTitle);
//							addData.Add(CE);
//							ChineseModel C = new ChineseModel();
//							C.setChineseData(list.get(i+1));
//							C.setMemo(Memo);
//							C.setTitle(ChineseTitle);
//							C.setTitleID(100000+id);
//							C.setURL(Url);
//							C.setUrlType(7);
//							addData.Add(C);
//							EnglishModel E = new EnglishModel();
//							E.setEnglishData(list.get(i));
//							E.setMemo(Memo);
//							E.setChineseId(id);
//							E.setTitle(EnglishTitle);
//							E.setTitleID(100000+id);
//							E.setURL(Url);
//							E.setUrlType(7);
//							addData.Add(E);
//						}
//					}
//					else {
//						System.out.println(Url+"数据出错");
//						ErrorUrlModel errorUrlModel = new ErrorUrlModel();
//						errorUrlModel.setURL( Url);
//						errorUrlModel.setUrlType(7);
//						addData.Add(errorUrlModel);
//					}
//					System.out.println(list.size()/2.0);
					bufferedReader.close();
					inputStreamReader.close();
					inputStream.close();
					spiderSleep(2000, 1000);
				}
				else {
					System.out.println(Url+"无数据");
					spiderSleep(1000, 2000);
				}
				conn.disconnect();
			} catch (IOException e) {
				System.out.println("IO出错,重新链接--------------");
				spiderSleep(3000, 5000);
				GetChineseAndEnglish(url,1);
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			System.out.println("Malformed出错,重新链接-----------");
			spiderSleep(3000, 5000);
			GetChineseAndEnglish(url,urlLists.get(i).getId());
			e.printStackTrace();
		}
	}
	
		 
	
}