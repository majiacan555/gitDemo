package HttpClientDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sun.org.apache.xml.internal.serializer.utils.StringToIntTable;

import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;

public class MainClass {
	static int p = 0;
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException, InterruptedException {
		for (int i =31205; i < 32202; i++) {
			String Url = "http://www.qqenglish.com/bn/"+i+".htm";
			System.out.println(Url);
			GetChineseAndEnglish(Url,i);
			
		}
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000)+"秒");
		Thread.sleep(Random);
		
	}
	public static void GetChineseAndEnglish(String Url,int k) throws ClientProtocolException, IOException, SQLException, InterruptedException 
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
		httpGet.setHeader("Cookie", "UM_distinctid=164c9c710b519b-0b7f63e306181a-5e4f2b18-1fa400-164c9c710b67a1; bdshare_firstime=1532393623875; safedog-flow-item=394CEA9EBBC1E8900FC7A5E9CC789C5E; CNZZDATA5427419=cnzz_eid%3D1583836901-1532393313-%26ntime%3D1532607739");
		httpGet.setHeader("Host", "www.qqenglish.com");
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
						(closeableHttpResponse.getEntity().getContent(),"GBK"));
				
				String inputLine;
				StringBuffer response = new StringBuffer();
				
				while ((inputLine = reader.readLine()) != null) 
				{
					response.append(inputLine);
				}
				Document doc = Jsoup.parse(response.toString(),"utf-8");
				
				Elements masthead = doc.select("p");//class等于masthead的div标签
				AddData addCE = new AddData();
				if (masthead.size() > 1 && masthead.size() % 2 == 1) {
					int titleidx = masthead.get(1).toString().indexOf("</p>");
					String CEtitle = masthead.get(1).toString().substring(3,titleidx);
					if (! CEtitle.contains("<br")) {
						ErrorUrlModel err = new ErrorUrlModel();
						err.setURL(Url);
						err.setUrlType(0);
						addCE.Add(err);
						return;
					}
					String[] title = CEtitle.split("<br />");
					String Etitle = title[0];
					String Ctitle = title[1];
					for (int i = 2; i < masthead.size()-1; i = i+2) 
					{
						String EnglishText =  masthead.get(i).toString();
						String ChineseText =  masthead.get(i+1).toString();
						int Eendidx = EnglishText.indexOf("</p>");
						int Cendidx = ChineseText.indexOf("</p>");
						String Estring = EnglishText.substring(3,Eendidx);
						String Cstring = ChineseText.substring(3,Cendidx);
//						System.out.println(Estring);
//						System.out.println(Cstring);
						ChineseAndEnglishModel CE = new ChineseAndEnglishModel();
						CE.setChineseData(Cstring);
						CE.setEnglishData(Estring);
						CE.setMemo("");
						CE.setTitleID(k);
						CE.setUrlType(0);
						CE.setURL(Url);
						CE.setTitle(Etitle+"_"+Ctitle);
						addCE.Add(CE);
						ChineseModel C = new ChineseModel();
						C.setChineseData(Cstring);
						C.setMemo("");
						C.setTitle(Ctitle);
						C.setTitleID(k);
						C.setURL(Url);
						C.setUrlType(0);
						addCE.Add(C);
						p++;
						EnglishModel E = new EnglishModel();
						E.setEnglishData(Estring);
						E.setMemo("");
						E.setChineseId(p);
						E.setTitle(Etitle);
						E.setTitleID(k);
						E.setURL(Url);
						E.setUrlType(0);
						addCE.Add(E);
					}
				}
				else {
					ErrorUrlModel errUrl = new ErrorUrlModel();
					errUrl.setURL(Url);
					errUrl.setUrlType(0);
					addCE.Add(errUrl);
					System.out.println(Url+"数据格式错误");
					
				}
				System.out.println(masthead.size());
				reader.close();
				closeableHttpResponse.close();
				closeableHttpClient.close();
				spiderSleep(2000, 1000);
				
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
