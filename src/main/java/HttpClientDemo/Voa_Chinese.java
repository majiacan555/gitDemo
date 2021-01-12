package HttpClientDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.SQLException; 

import org.apache.http.client.ClientProtocolException; 
public class Voa_Chinese {
	static int p = 0;
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException, InterruptedException, KeyManagementException, NoSuchAlgorithmException {
		
		for (int j = 185; j <= 573; j++) {
			String Key = "/world/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 146; j++) {
			String Key = "/opinion/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 170; j++) {
			String Key = "/culture/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 73; j++) {
			String Key = "/style/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 27; j++) {
			String Key = "/travel/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 34; j++) {
			String Key = "/real-estate/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 66; j++) {
			String Key = "/health/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 46; j++) {
			String Key = "/education/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 79; j++) {
			String Key = "/technology/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 48; j++) {
			String Key = "/science/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 9; j++) {
			String Key = "/lens/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 382; j++) {
			String Key = "/china/"+j+"/";
			GetChineseAndEnglish(Key);
		}
		for (int j = 1; j <= 207; j++) {
			String Key = "/business/"+j+"/";
			GetChineseAndEnglish(Key);
		}
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("��Ϣ:"+(Random/1000)+"��");
		Thread.sleep(Random);
		
	}
	public static void GetChineseAndEnglish(String Key) throws ClientProtocolException, IOException, SQLException, InterruptedException, KeyManagementException, NoSuchAlgorithmException 
	{
		String  Url = "https://cn.nytimes.com"+ Key;
		System.out.println(Url);
		 URL serverUrl = new URL(Url);
	 
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Cookie", "nyt-a=f06674b6fae678fe807ca1b3be32d7b7; trc_cookie_storage=taboola%2520global%253Auser-id%3D6d7f9429-6e95-47b3-a56d-67818cafd744-tuct4a20087; _ga=GA1.2.830449563.1571670621; _gid=GA1.2.22581638.1571670621; _fbp=fb.1.1571670621073.1773381911; nytimes_sec_token=11f2c9c0aa8da613f2534a760788358b; NYTCN-MSS=a%3A5%3A%7Bs%3A10%3A%22session_id%22%3Bs%3A32%3A%22e5dba245bc1a3f768d72b6f79b93d5f0%22%3Bs%3A10%3A%22ip_address%22%3Bs%3A11%3A%2210.9.152.34%22%3Bs%3A10%3A%22user_agent%22%3Bs%3A17%3A%22Amazon+CloudFront%22%3Bs%3A13%3A%22last_activity%22%3Bi%3A1571751770%3Bs%3A9%3A%22user_data%22%3Bs%3A0%3A%22%22%3B%7Da47e513733a0e6f79bec82a35c6a3785f240eb9f; nytcn-gdpr=0; _gat=1; AWSALB=3qD0TEJUJu/EG7Kz68oidJpm1AuXst3dz9ei+v2e4Q4oKry/JlaiqtH5usaDdOkyUpAcQhyj/H8pxJhmom+6R+wl31yHQG1vWzT/rHznLA73PQSJiowKm8sSvQ6E; __gads=ID=0a835da3c2eb3c53:T=1571751773:S=ALNI_MYiBm3tYhdUkazP0u3lJ_0FIV01xg; _cb_ls=1; _cb=BTPuuXCBx9fWBj4EE2; _chartbeat2=.1571751773299.1571751773299.1.BeIptRBlXDIHDtc12SC52h3DFje4c.1; _cb_svref=null");
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
        	try(
				InputStream inputStream = conn.getInputStream();
				
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);){
	            String str = null;
	            while ((str = bufferedReader.readLine()) != null) {
	                buffer.append(str);
	            }
	            String result = buffer.toString();
	            System.out.println(result);
//	            Document doc = Jsoup.parse(result,"UTF-8");
//				Elements pp = doc.getElementsByClass("autoList");
//				Elements kkElements = pp.select("a");
//				Map map = new HashMap();
//				for (int i = 0; i < kkElements.size(); i++) {
//					String urlString = kkElements.get(i).attr("href");
//					map.put(urlString, 1);
//				}
//				AddData addData = new AddData();
//				if (map.size()>0) {
//					SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//�������ڸ�ʽ
//			        System.out.println(df.format(new Date())); ;// new Date()Ϊ��ȡ��ǰϵͳʱ��
//					List<UrlList> list = addData.Search("7");
//					for (Object key : map.keySet()) {
//						String NewUrl = "https://cn.nytimes.com"+key;
//						Boolean isContains = false;
//						for (int i = 0; i < list.size(); i++) {
//							if (list.get(i).getURL().contains(NewUrl) ) {
//								isContains=true;
//								System.out.println(NewUrl+"�Ѵ���");
//								break;
//							}
//						}
//						if (! isContains) {			
//							UrlList urlList = new UrlList();
//							urlList.setURL(NewUrl);
//							urlList.setUrlType(7);
//							addData.Add(urlList);
//						}
//					}
//					System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
//				}
//				System.out.println(map.size());
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
				spiderSleep(3000, 1000);
        	}
        }
        else {
			System.out.println(Url+"������");
			spiderSleep(3000, 1000);
			conn.disconnect();
		}
		conn.disconnect();
	}
}