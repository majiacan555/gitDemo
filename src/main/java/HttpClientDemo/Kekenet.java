package HttpClientDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

public class Kekenet {
	static String Url;
	static int i;
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException, InterruptedException {
		
//		
//		for (int i = 3; i <= 191; i++) {
//			Url = "http://www.kekenet.com/read/news/entertainment/List_"+i+".shtml";
//			System.out.println(Url);
//			GetChineseAndEnglish(Url);
//		}
//		for (int i = 1; i <= 327; i++) {
//			Url = "http://www.kekenet.com/read/news/shehui/List_"+i+".shtml";
//			System.out.println(Url);
//			GetChineseAndEnglish(Url);
//		}
//		for (int i = 1; i <= 270; i++) {
//			Url = "http://www.kekenet.com/read/news/economy/List_"+i+".shtml";
//			System.out.println(Url);
//			GetChineseAndEnglish(Url);
//		}
		
//		for (int i = 243; i <= 255; i++) {
//			Url = "http://www.kekenet.com/read/essay/List_"+i+".shtml";
//			System.out.println(Url);
//			GetChineseAndEnglish(Url);
//		}
//		for (int i = 1; i <= 266; i++) {
//			Url = "http://www.kekenet.com/read/story/List_"+i+".shtml";
//			System.out.println(Url);
//			GetChineseAndEnglish(Url);
//		}
//		for (int i = 1; i <= 135; i++) {
//			Url = "http://www.kekenet.com/read/ss/culture/List_"+i+".shtml";
//			System.out.println(Url);
//			GetChineseAndEnglish(Url);
//		}
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000.0)+"秒");
		Thread.sleep(Random);
		
	}
	public static void GetChineseAndEnglish(String Url) throws SQLException, InterruptedException
	{
		URL serverUrl;
		try {
			serverUrl = new URL(Url);
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) serverUrl.openConnection();
				conn.setRequestMethod("GET");
		        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		        conn.setRequestProperty("Connection", "keep-alive");
		        conn.setRequestProperty("Cookie", "UM_distinctid=164c9c62325463-0f8303943b5cb-5e4f2b18-1fa400-164c9c62326711; pgv_pvid=4014071455; bdshare_firstime=1534060306561; __cfduid=d8319febfe7d3cae28262ee88e8df861c1534061188; Hm_lvt_9c68e8500a8dd6cbf338b9afc70517ec=1534041729,1534059754,1534064627,1534123178; Hm_lpvt_9c68e8500a8dd6cbf338b9afc70517ec=1534123250; CNZZDATA1037071=cnzz_eid%3D54743330-1532393234-%26ntime%3D1534122526");
		        conn.setRequestProperty("Host", "www.kekenet.com");
		        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
		        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
				
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
		            Document doc = Jsoup.parse(result,"UTF-8");Elements pp = doc.getElementsByClass("story-headline");
					Element aa = doc.getElementById("menu-list");
					Elements bb = aa.select("a");
					int urlsize = 0;
					AddData addData = new AddData();
					List<UrlList> urlLists = addData.Search("8");
					for (int i = 0; i < bb.size(); i++) {
						String url = bb.get(i).attr("href");
						if (url.contains("www.kekenet.com") && url.contains(".shtml")) {
							urlsize++;
							Boolean isContains = false;
							for (int k = 0; k < urlLists.size(); k++) {
								if (urlLists.get(k).getURL().contains(url) ) {
									isContains=true;
									System.out.println(url+"已存在");
									break;
								}
							}
							if (! isContains) {			
								UrlList urlList = new UrlList();
								urlList.setURL(url);
								urlList.setUrlType(8);
								addData.Add(urlList);
							}
						}
					}
					System.out.println(urlsize);
					bufferedReader.close();
					inputStreamReader.close();
					inputStream.close();
					spiderSleep(4000, 4000);
				}
		       
		       else {
					System.out.println(Url+"访问失败");
					spiderSleep(2000, 2000);
				}
		       conn.disconnect();
			} catch (IOException e) {
				System.out.println("IO出错,重新链接--------------");
				spiderSleep(3000, 5000);
				GetChineseAndEnglish(Url);
				e.printStackTrace();
			}
	        
		} catch (MalformedURLException e) {
			
			System.out.println("Malformed出错,重新链接-----------");
			spiderSleep(3000, 5000);
			GetChineseAndEnglish(Url);
			e.printStackTrace();
		}
	}
}