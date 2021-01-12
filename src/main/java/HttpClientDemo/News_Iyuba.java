package HttpClientDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

public class News_Iyuba {
	static int p = 0;
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException, InterruptedException {
		
//		int begin = 25263; end = 78713
		
		
		for (int j = 69; j < 99; j++) {
			String Url = "http://news.iyuba.com/essay_category/122/"+j+".html";
			System.out.println(Url);
			GetChineseAndEnglish(Url);
		}
		for (int j = 1; j < 45; j++) {
			String Url = "http://news.iyuba.com/essay_category/123/"+j+".html";
			System.out.println(Url);
			GetChineseAndEnglish(Url);
		}
		for (int j = 1; j <= 20; j++) {
			String Url = "http://news.iyuba.com/essay_category/124/"+j+".html";
			System.out.println(Url);
			GetChineseAndEnglish(Url);
		}
		for (int j = 1; j <= 185; j++) {
			String Url = "http://news.iyuba.com/essay_category/125/"+j+".html";
			System.out.println(Url);
			GetChineseAndEnglish(Url);
		}
		for (int j = 1; j <= 92; j++) {
			String Url = "http://news.iyuba.com/essay_category/126/"+j+".html";
			System.out.println(Url);
			GetChineseAndEnglish(Url);
		}
		for (int j = 1; j <= 66; j++) {
			String Url = "http://news.iyuba.com/essay_category/127/"+j+".html";
			System.out.println(Url);
			GetChineseAndEnglish(Url);
		}
		for (int j = 1; j <= 364; j++) {
			String Url = "http://news.iyuba.com/essay_category/128/"+j+".html";
			System.out.println(Url);
			GetChineseAndEnglish(Url);
		}
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000)+"秒");
		Thread.sleep(Random);
		
	}
	public static void GetChineseAndEnglish(String Url) throws ClientProtocolException, IOException, SQLException, InterruptedException 
	{
		
		//创建客户端
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		//创建请求Get实例
		HttpGet httpGet = new HttpGet(Url);
		
		//设置头部信息进行模拟登录（添加登录后的Cookie）
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Cookie", "UM_distinctid=164c9c6ba2a53c-0de0a9b1d72db4-5e4f2b18-1fa400-164c9c6ba2b794; CNZZDATA1253159226=2089847591-1532390366-%7C1533561265");
		httpGet.setHeader("Host", "news.iyuba.com");
		httpGet.setHeader("Upgrade-Insecure-Requests", "1");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		
		try 
		{
			//客户端执行httpGet方法，返回响应
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
			
			//得到服务响应状态码
			if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) 
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader
						(closeableHttpResponse.getEntity().getContent(),"UTF-8"));
				
				String inputLine;
				StringBuffer response = new StringBuffer();
				
				while ((inputLine = reader.readLine()) != null) 
				{
					response.append(inputLine);
				}
				Document doc = Jsoup.parse(response.toString(),"UTF-8");
				Elements pp = doc.getElementsByClass("c_left2");
				AddData addData = new AddData();
				int ll = 0;
				for (int i = 0; i < pp.size(); i++) {
					for (int j = 0; j < pp.get(i).childNodeSize(); j++) {
						if (!pp.get(i).childNode(j).toString().contains("text-center")) {
							if (!pp.get(i).childNode(j).attr("href").isEmpty()) {
								String urlString ="http://news.iyuba.com"+  pp.get(i).childNode(j).attr("href");
								UrlList urlList = new UrlList();
								urlList.setURL(urlString);
								urlList.setUrlType(5);
								addData.Add(urlList);
								ll++;
							}
						}
					}
					
				}
				System.out.println(ll);
				reader.close();
				closeableHttpResponse.close();
				closeableHttpClient.close();
				spiderSleep(3000, 1000);
			}
			else {
				System.out.println(Url+"无数据");
				spiderSleep(1800, 1000);
			}
		}
		finally 
		{
			try 
			{
				closeableHttpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
		 
	

}