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

public class getShaShiBiYaText {
	static int i;
	static String url;
	static List<UrlList> urlLists;
	public static void main(String[] args) throws Exception {
	 url = "https://yuedu.163.com/getArticleContent.do?sourceUuid=d62a26da4bae473099d460e1de21b3fb_4&articleUuid=82e586e301444d19ad3b31028f3ad0be_5&bigContentId=8796093024616354985";
				GetChineseAndEnglish( url ); 
		 
	}
	 
	
	public static void GetChineseAndEnglish(String Url ) throws InterruptedException, SQLException, IOException 
	{
		URL serverUrl;
	 
			serverUrl = new URL(Url);
			HttpsURLConnection conn;
			 
				conn = (HttpsURLConnection) serverUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
				conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
				conn.setRequestProperty("Connection", "keep-alive");
				conn.setRequestProperty("Cookie", "YUEDUDYAMIC=430a12725ec1db6093e555d0984f020199639472; NTESYUEDUSI=652D13CC6451A140E46759DB5E78B3C0.hzabj-yaolu56.server.163.org-8010; YUEDU_V_DID=1551188378968220; __utma=63080999.1501281982.1551188377.1551188377.1551188377.1; __utmc=63080999; __utmz=63080999.1551188377.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Province=020; City=0752; __da_ntes_utma=63080999.755753040.1551188377.1551188377.1551188377.1; davisit=1; __da_ntes_utmz=63080999.1551188377.1.1.utmcsr%3D(direct)%7Cutmccn%3D(direct)%7Cutmcmd%3D(none); __da_ntes_utmfc=utmcsr%3D(direct)%7Cutmccn%3D(direct)%7Cutmcmd%3D(none); __da_ntes_utmb=63080999.2.10.1551188377; __utmb=63080999.7.8.1551188456023");
				conn.setRequestProperty("Host", "yuedu.163.com");
				conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
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
					Document doc = Jsoup.parse(result,"UTF-8");
					Elements memoElements = doc.getElementsByClass("section-title");
					String Memo = memoElements.text();
					Elements TitleElements = doc.getElementsByClass("article-header");
					Elements Titles = TitleElements.select("h1");
					String ChineseTitle = Titles.get(0).text();
					String EnglishTitle = Titles.get(1).text();
					Elements pp = doc.getElementsByClass("article-paragraph");
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < pp.size(); i++) {
						String text = pp.get(i).text();
						if (text.length()>1 && !text.equals("_____")) {
							list.add(text);
						}
					}
					 
					System.out.println(list.size()/2.0);
					bufferedReader.close();
					inputStreamReader.close();
					inputStream.close();
					 
				}
				 
				conn.disconnect();
			 
		 
	}
	
		 
	
}