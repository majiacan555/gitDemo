package HttpClientDemo;

import java.io.BufferedReader; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader; 
import java.net.URL;  
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext; 
import javax.net.ssl.TrustManager; 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetTaiwan_Panorama {
	static int p = 0;
	public static void main(String[] args) throws Exception { 
		 
		String FirstURL = "https://www.taiwan-panorama.com/Articles/Index_wa?page=";
		String FileName = "D:\\Taiwan_Panorama\\Taiwan_PanoramaURL.txt";
	for (int i = 73; i <= 73; i++) {
		String url = FirstURL + i;
		GetChineseAndEnglish(url,FileName); 
	}
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000)+"秒");
		Thread.sleep(Random); 
	}
	
	public static void GetChineseAndEnglish(String Url,String FileName) throws Exception 
	{ 
		System.out.println("Begin Analysis URL:"+Url);
		SSLContext sslcontext = SSLContext.getInstance("SSL","SunJSSE");  
        sslcontext.init(null, new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());  
        URL serverUrl = new URL(Url);
        HttpsURLConnection conn = (HttpsURLConnection) serverUrl.openConnection();
        conn.setSSLSocketFactory(sslcontext.getSocketFactory());  
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Cookie", "_ga=GA1.2.1709870200.1560135701; _gid=GA1.2.1087710269.1560671403; _gat=1");
        conn.setRequestProperty("Host", "www.taiwan-panorama.com");
        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();
	    /*请求url获取返回的内容*/
	    
        StringBuffer buffer = new StringBuffer();
        //将返回的输入流转换成字符串
        
		InputStream inputStream = conn.getInputStream();
//    		GZIPInputStream gis = new GZIPInputStream(inputStream); 
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close(); 
        String result = buffer.toString();
        Document doc = Jsoup.parse(result,"UTF-8");
        Elements pp = doc.getElementsByClass("table");  
        Elements td = pp.select("a");
		String Memo = pp.text(); 
        System.out.println(result);
		int ll = 0;
		List<String> strList = new ArrayList<String>();
		for (int i = 0; i < td.size(); i++) { 
			if (!td.get(i).attr("href").isEmpty()) {
				String urlString ="https://www.taiwan-panorama.com"+  td.get(i).attr("href"); 
				strList.add(urlString);
				System.out.println(urlString);
				ll++;
			}  
		} 
		appendStringToFile(FileName,strList);  
		conn.disconnect();
		spiderSleep(15000, 10000); 
	} 
	public static void appendStringToFile(String fileName, List<String> contentList) { 
		try {  
		//打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件 
		File file = new File(fileName);
		if (!file.exists()) { 
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs(); 
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(fileName, true);
		for (String string : contentList) {
			string +="\r\n";
			writer.write(string); 
		}
		writer.close(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		} 
}