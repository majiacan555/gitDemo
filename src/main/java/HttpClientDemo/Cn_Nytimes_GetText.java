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

import LiberyUtils.AddData;
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

public class Cn_Nytimes_GetText {
	static int i;
	static String url;
	static List<UrlList> urlLists;
	public static void main(String[] args) throws Exception {
		AddData search = new AddData();
		urlLists = search.Search("7");
		for ( i = 18954; i < urlLists.size(); i++) {
			if (urlLists.get(i).getId() >  46735) {    
				url  = urlLists.get(i).getURL()+"dual/";
				System.out.println(i+":"+ url);
				GetChineseAndEnglish(url,urlLists.get(i).getId());
//			String url = "https://cn.nytimes.com/morning-brief/20171016/mogadishu-iran-north-korea/dual/";
//			GetChineseAndEnglish(url,1);
			}
		}
	}
	
	
	public static void GetChineseAndEnglish(String Url,int id) throws InterruptedException, SQLException 
	{
		URL serverUrl;
		try {
			serverUrl = new URL(Url);
			HttpsURLConnection conn;
			try {
				conn = (HttpsURLConnection) serverUrl.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
				conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
				conn.setRequestProperty("Connection", "keep-alive");
				conn.setRequestProperty("Cookie", "_cb_ls=1; __gads=ID=9b1b64e085b2213e:T=1533303869:S=ALNI_MYhPqBbPSdD6rZt0PzSraoVfTC6XQ; AWSALB=hApU10cBpLK/sa+SotLHF9J7qUi+eYYuom1P1LEOHjtMt/nImMR5XmvwqNBsADTZ39FZv3bsH22lAxi1QVDk1hUKyEmmqH7Wh7z3PbFNrgneiGownUKnovtkYdkY; nytimes_sec_token=259f2ea785300eff3bedc1c8b7e928da; NYTCN-MSS=a%3A5%3A%7Bs%3A10%3A%22session_id%22%3Bs%3A32%3A%223e8c9528cb18022f724f25a43101aa27%22%3Bs%3A10%3A%22ip_address%22%3Bs%3A10%3A%2210.9.151.4%22%3Bs%3A10%3A%22user_agent%22%3Bs%3A17%3A%22Amazon+CloudFront%22%3Bs%3A13%3A%22last_activity%22%3Bi%3A1533632468%3Bs%3A9%3A%22user_data%22%3Bs%3A0%3A%22%22%3B%7Da1884381a5309814835ac0176aa8713966a5aadf; trc_cookie_storage=taboola%2520global%253Auser-id%3D7bdffc74-8b80-48f3-b217-f8f162e08043-tuct1ebfeee; nyt-a=b6b646f1adfc9859637873dd3ca971c6; nytcn-gdpr=0; _ga=GA1.2.1089031137.1533302843; _gid=GA1.2.1974899024.1533567704; _cb=ehnPQD4NpQVCoy5AK; _chartbeat2=.1533302886655.1533633419168.10011.CD8SlYCoQ2QAHnpPOBa_SSuC2dAq1.3; _cb_svref=null; _SUPERFLY_lockout=1");
				conn.setRequestProperty("Host", "cn.nytimes.com");
				conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
				//��������false��������Զ�redirect���ض����ĵ�ַ
				conn.setInstanceFollowRedirects(false);
				conn.connect();
				/*����url��ȡ���ص�����*/
				
				StringBuffer buffer = new StringBuffer();
				//�����ص�������ת�����ַ���
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
					AddData addData = new AddData();
					if (list.size() % 2 == 0) {
						for (int i = 0; i < list.size(); i=i+2) {
							ChineseAndEnglishModel CE = new ChineseAndEnglishModel();
							CE.setChineseData(list.get(i+1));
							CE.setEnglishData(list.get(i));
							CE.setMemo(Memo);
							CE.setTitleID(100000+id);
							CE.setUrlType(7);
							CE.setURL(Url);
							CE.setTitle(EnglishTitle+"_"+ChineseTitle);
							addData.Add(CE);
							ChineseModel C = new ChineseModel();
							C.setChineseData(list.get(i+1));
							C.setMemo(Memo);
							C.setTitle(ChineseTitle);
							C.setTitleID(100000+id);
							C.setURL(Url);
							C.setUrlType(7);
							addData.Add(C);
							EnglishModel E = new EnglishModel();
							E.setEnglishData(list.get(i));
							E.setMemo(Memo);
							E.setChineseId(id);
							E.setTitle(EnglishTitle);
							E.setTitleID(100000+id);
							E.setURL(Url);
							E.setUrlType(7);
							addData.Add(E);
						}
					}
					else {
						System.out.println(Url+"���ݳ���");
						ErrorUrlModel errorUrlModel = new ErrorUrlModel();
						errorUrlModel.setURL( Url);
						errorUrlModel.setUrlType(7);
						addData.Add(errorUrlModel);
					}
					System.out.println(list.size()/2.0);
					bufferedReader.close();
					inputStreamReader.close();
					inputStream.close();
					spiderSleep(2000, 1000);
				}
				else {
					System.out.println(Url+"������");
					spiderSleep(1000, 2000);
				}
				conn.disconnect();
			} catch (IOException e) {
				System.out.println("IO����,��������--------------");
				spiderSleep(3000, 5000);
				GetChineseAndEnglish(url,urlLists.get(i).getId());
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			System.out.println("Malformed����,��������-----------");
			spiderSleep(3000, 5000);
			GetChineseAndEnglish(url,urlLists.get(i).getId());
			e.printStackTrace();
		}
	}
	
		 
	
}