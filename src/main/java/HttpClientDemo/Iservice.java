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

public class Iservice {
	static int p = 0;
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException, InterruptedException {
		AddData searchUrl = new AddData();
		List<UrlList> urllist = searchUrl.Search("4");
//		int begin = 25263; end = 78713
		for (int j = 3347; j < urllist.size(); j++) {
			if (urllist.get(j).getId() > 16729) {
				System.out.println("当前进度："+j);
				GetChineseAndEnglish(urllist.get(j).getURL(),urllist.get(j).getId());
			}
		}
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000)+"秒");
		Thread.sleep(Random);
		
	}
	public static void GetChineseAndEnglish(String Url,int id) throws ClientProtocolException, IOException, SQLException, InterruptedException 
	{
		System.out.println(Url);
		
		//创建客户端
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		//创建请求Get实例
		HttpGet httpGet = new HttpGet(Url);
		
		//设置头部信息进行模拟登录（添加登录后的Cookie）
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Cookie", "__gads=ID=fb103957a2b50692:T=1533302936:S=ALNI_MaTVi8D-c3m9UnmNMdo8fCd5Kji_Q; ReaderInfo=1830000786664; ltnTagDate=20180803; __asc=a4eaa466165180d474a7153dfb0; __auc=3f618c7e164fff8ec8b0ee3c3be; _ga=GA1.3.1340141295.1533302861; _gid=GA1.3.1235466363.1533542531");
		httpGet.setHeader("Host", "iservice.ltn.com.tw");
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
				Element pp = doc.getElementById("newsContent");
				Elements kk = doc.getElementsByClass("title");
				String title = kk.text();
				List<String> list = new ArrayList<>();
				for (int i = 1; i < pp.childNodeSize(); i++) {
					if (pp.child(i).toString().contains("新聞辭典")) {
						break;
					}
					String text = pp.child(i).text();
					if (text.length()>2) {
						list.add(text);
//						System.out.println(text);
					}
					
				}
				AddData addData = new AddData();
				if (list.size() % 2 == 0) {
					for (int i = 0; i < list.size(); i=i+2) {
						ChineseAndEnglishModel CE = new ChineseAndEnglishModel();
						CE.setChineseData(list.get(i+1));
						CE.setEnglishData(list.get(i));
						CE.setMemo("");
						CE.setTitleID(100000+id);
						CE.setUrlType(4);
						CE.setURL(Url);
						CE.setTitle(title);
						addData.Add(CE);
						ChineseModel C = new ChineseModel();
						C.setChineseData(list.get(i+1));
						C.setMemo("");
						C.setTitle(title);
						C.setTitleID(100000+id);
						C.setURL(Url);
						C.setUrlType(4);
						addData.Add(C);
						EnglishModel E = new EnglishModel();
						E.setEnglishData(list.get(i));
						E.setMemo("");
						E.setChineseId(id);
						E.setTitle(title);
						E.setTitleID(100000+id);
						E.setURL(Url);
						E.setUrlType(4);
						addData.Add(E);
					}
				}
				else {
					System.out.println(Url+"数据出错");
					ErrorUrlModel errorUrlModel = new ErrorUrlModel();
					errorUrlModel.setURL( Url);
					errorUrlModel.setUrlType(4);
					addData.Add(errorUrlModel);
				}
				System.out.println(list.size());
//				CopyOfAddData addData = new CopyOfAddData();
//				for (int i = 0; i < aa.size(); i++) {
//					String urlString = "http://iservice.ltn.com.tw/Service/english/"+aa.get(i).attr("href").toString();
//					UrlList urlList = new UrlList();
//					urlList.setURL(urlString);
//					urlList.setUrlType(4);
//					addData.Add(urlList);
////					System.out.println(urlString);
//				}
//				for (int i = 0; i < pp.size(); i++) {
//					
//					System.out.println(pp.get(i).toString());
////					System.out.println(pp.get(i).select("a").attr("href").toString());
//				}
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