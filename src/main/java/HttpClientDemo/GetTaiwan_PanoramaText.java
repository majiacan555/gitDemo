package HttpClientDemo;

import java.io.BufferedReader; 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader; 
import java.net.URL;  
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext; 
import javax.net.ssl.TrustManager; 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import LiberyUtils.LiberyCache;

public class GetTaiwan_PanoramaText {
	static int p = 0;
	public static void main(String[] args) throws Exception { 
		 String filePath = "D:\\Taiwan_Panorama\\Taiwan_PanoramaURL.txt";
		 File file = new File(filePath);
		 if (file.exists()) {
			List<String> urlList=  LiberyCache.ReadTextFromTxt(file, "utf-8");
			int Count = 0;
			for (String url : urlList) {
				Count++;
				String FileName = "D:\\Taiwan_Panorama\\text\\"+Count+".txt";
				GetChineseAndEnglish(url,FileName); 
			}
		 } 
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000)+"秒");
		Thread.sleep(Random); 
	}
	
	public static void GetChineseAndEnglish(String Url,String FileName) throws Exception 
	{  
        String result =  GetResult(Url);
        if (result.contains("&amp;CatId=")) {
			int index = result.indexOf("&amp;CatId=");
			String value = result.substring(index+11, index+12);
			System.out.println(value);
			Url = Url+"&CatId="+value;
		    result =  GetResult(Url);
		}
        result+="@#@#"+Url;
		System.out.println(result); 
		List<String> strList = new ArrayList<String>();
		strList.add(result);
		LiberyCache.appendStringToFile(FileName,strList);   
		spiderSleep(15000, 10000); 
	}
	private static String GetResult(String Url) throws KeyManagementException, Exception {
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
        String result = buffer.toString();  
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close(); 
        conn.disconnect();
        return result;
	} 
	}
