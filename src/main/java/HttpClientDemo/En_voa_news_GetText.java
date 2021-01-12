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

import LiberyUtils.AddData;
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

public class En_voa_news_GetText {
	static int p = 0;
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException, InterruptedException {
		AddData search = new AddData();
		List<UrlList> list = search.Search("6");
		for (int j = 923; j <= list.size(); j++) {
			if (list.get(j).getId() >= 21619) {
				
				String Url = list.get(j).getURL();
				System.out.println(j+":"+Url);
				GetChineseAndEnglish(Url,list.get(j).getId());
			}
		}
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("��Ϣ:"+(Random/1000)+"��");
		Thread.sleep(Random);
		
	}
	public static void GetChineseAndEnglish(String Url,int id) throws ClientProtocolException, IOException, SQLException, InterruptedException 
	{
		
		//�����ͻ���
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		//��������Getʵ��
		HttpGet httpGet = new HttpGet(Url);
		
		//����ͷ����Ϣ����ģ���¼����ӵ�¼���Cookie��
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Cookie", "__cfduid=daeabc51b81a049cd83352f9939d0f6681532393608; cf_clearance=a4a587ca59a443ab0533dec4959be76802a7ac0e-1532393612-31536000; UM_distinctid=164c9c6e66c175-00a726c1f7122f-5e4f2b18-1fa400-164c9c6e66e27f; pgv_pvi=7031943168; pgv_si=s6301718528; CNZZDATA1262303005=78955421-1532393551-%7C1533565626; CNZZDATA1262078731=898970868-1532392949-null%7C1533561862; Hm_lvt_2890fcd2a8c509cb0942f1565c9ad8d4=1532393613,1532414230,1533302508,1533565437; Hm_lpvt_2890fcd2a8c509cb0942f1565c9ad8d4=1533566556; Hm_lvt_b647f8f5c5745b916ff6f8fd8d80419c=1532393613,1532414230,1533302507,1533565437; Hm_lpvt_b647f8f5c5745b916ff6f8fd8d80419c=1533566556");
		httpGet.setHeader("Host", "www.24en.com");
		httpGet.setHeader("Upgrade-Insecure-Requests", "1");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		
		try 
		{
			//�ͻ���ִ��httpGet������������Ӧ
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
			
			//�õ�������Ӧ״̬��
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
				Element pp = doc.getElementById("tab_1");
				Elements titleElements = doc.getElementsByClass("info-title");
				String title = titleElements.text();
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < pp.children().size(); i++) {
					for (int j = 0; j < pp.child(i).childNodeSize(); j++) {
						String text = pp.child(i).childNode(j).toString();
						if (text.contains("<br />")) {
							text = text.replace("<br />", "");
						}
						if (text.contains("&nbsp;")) {
							text = text.replace("&nbsp;", "");
						}
						if (text.length() > 3) {
							list.add(text);
						}
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
						CE.setUrlType(6);
						CE.setURL(Url);
						CE.setTitle(title);
						addData.Add(CE);
						ChineseModel C = new ChineseModel();
						C.setChineseData(list.get(i+1));
						C.setMemo("");
						C.setTitle(title);
						C.setTitleID(100000+id);
						C.setURL(Url);
						C.setUrlType(6);
						addData.Add(C);
						EnglishModel E = new EnglishModel();
						E.setEnglishData(list.get(i));
						E.setMemo("");
						E.setChineseId(id);
						E.setTitle(title);
						E.setTitleID(100000+id);
						E.setURL(Url);
						E.setUrlType(6);
						addData.Add(E);
					}
				}
				else {
					System.out.println(Url+"���ݳ���");
					ErrorUrlModel errorUrlModel = new ErrorUrlModel();
					errorUrlModel.setURL( Url);
					errorUrlModel.setUrlType(6);
					addData.Add(errorUrlModel);
				}
				System.out.println(list.size());
				
				reader.close();
				closeableHttpResponse.close();
				spiderSleep(1500, 500);
			}
			else {
				System.out.println(Url+"������");
				spiderSleep(1800, 1000);
			}
			closeableHttpClient.close();
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