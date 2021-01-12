package HttpClientDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


import org.apache.http.client.ClientProtocolException;

public class Cn_Nytimes {
	static int p = 0;
	public static void main(String[] args) throws Exception {
		
		String url = "https://www.voachinese.com/a/bilingual-news-201807310801/4509184.html";
		GetChineseAndEnglish(url);
		
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000)+"秒");
		Thread.sleep(Random);
		
	}
	
	public static void GetChineseAndEnglish(String Url) throws Exception 
	{
		
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
        conn.setRequestProperty("Cookie", "Pangea-NodeId=WD6CMnZlQMc/aj51E0+pPA==; _cb_ls=1; .ASPXANONYMOUS=mSfp3i_oFNIIT4_kEvr1D5wRIHNkQoKvPWOwIHBSRGzfgRbaDu5JGtWCy6o9tiI6Ze5XOeI5TOXkR0R89m0H36EZfyZy3ox80IcT8lA6Bjaldw35SKUpGZcmUwjuySLMJubWTA2; PangeaEnvironment=2; optimizelyEndUserId=oeu1533302859206r0.417917401485558; __qca=P0-1198235383-1533302862368; AMCVS_518ABC7455E462B97F000101%40AdobeOrg=1; AMCV_518ABC7455E462B97F000101%40AdobeOrg=1406116232%7CMCIDTS%7C17747%7CMCMID%7C23487147751837693252725007406726712066%7CMCAAMLH-1534296741%7C9%7CMCAAMB-1534296741%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1533699141s%7CNONE%7CMCAID%7CNONE%7CMCSYNCSOP%7C411-17754%7CvVersion%7C2.5.0; __utmt_gwt=1; utag_main=v_id:0164fff8cccd00585f13bef910b80506d002106500bd0$_sn:3$_ss:0$_st:1533693750836$vapi_domain:voachinese.com$ses_id:1533691938999%3Bexp-session$_pn:2%3Bexp-session; s_cc=true; __utma=245739139.1941137521.1533302860.1533548900.1533691944.3; __utmb=245739139.2.10.1533691944; __utmc=245739139; __utmz=245739139.1533302860.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); _cb=BAr6NBwl67KBSx0hD; _chartbeat2=.1533302859137.1533691951037.100101.BlAW_lRAxI9t8zsmCaATI9JP14f.2; _cb_svref=null; optimizelySegments=%7B%222317890156%22%3A%22false%22%2C%222326810159%22%3A%22direct%22%2C%222327980171%22%3A%22gc%22%7D; optimizelyBuckets=%7B%7D; _ceg.s=pd4ca8; _ceg.u=pd4ca8; optimizelyPendingLogEvents=%5B%5D; s_sq=bbgrvoamandarin2%252Cbbgprod%252Cbbgentityvoa%252Cbbgunitvoamandarin%3D%2526c.%2526a.%2526activitymap.%2526page%253Dvoa%25253Aman%25253Ar%25253Asection%25253A%2525E5%25258F%25258C%2525E8%2525AF%2525AD%2525E6%252596%2525B0%2525E9%252597%2525BB%2526link%253D%2525E5%25258F%25258C%2525E8%2525AF%2525AD%2525E6%252596%2525B0%2525E9%252597%2525BB%2525282018%2525E5%2525B9%2525B48%2525E6%25259C%2525881%2525E6%252597%2525A5%252529%252520%2525E7%2525BE%25258E%2525E5%25259B%2525BD%2525E7%2525A7%252591%2525E5%2525AD%2525A6%2525E5%2525AE%2525B6%2525E8%2525AE%2525A4%2525E5%2525AE%25259A%2525EF%2525BC%25258C%2525E5%2525A2%2525A8%2525E8%2525A5%2525BF%2525E5%252593%2525A5%2525E6%2525B9%2525BE%2525E9%252587%25258C%2525E7%252594%2525B1%2525E4%2525BA%25258E%2525E5%252590%2525AB%2525E6%2525B0%2525A7%2525E8%2525BF%252587%2525E4%2525BD%25258E%2525E5%2525AF%2525BC%2525E8%252587%2525B4%2525E9%2525B1%2525BC%2525E5%252592%25258C%2525E6%2525B5%2525B7%2525E6%2525B4%25258B%2525E7%252594%25259F%2525E7%252589%2525A9%2525E6%2525AD%2525BB%2525E4%2525BA%2525A1%2525E7%25259A%252584%2525E6%252589%252580%2525E8%2525B0%252593%2525E2%252580%25259C%2525E6%2525AD%2525BB%2525E4%2525BA%2525A1%2525E5%25258C%2525BA%2525E2%252580%25259D%2525E4%2525BB%25258A%2525E5%2525B9%2525B4%2525E6%252598%2525AF1985%2525E5%2525B9%2525B4%2525E5%2525BC%252580%2525E5%2525A7%25258B%2525E6%2525B5%25258B%2525E9%252587%25258F%2525E8%2525AE%2525B0%2525E5%2525BD%252595%2525E4%2525BB%2525A5%2525E6%25259D%2525A5%2525E7%2525AC%2525AC%2525E5%25259B%25259B%2525E5%2525B0%25258F%2525E7%25259A%252584%2525E9%25259D%2525A2%2525E7%2525A7%2525AF%2525E3%252580%252582U.S.%252520scientists%252520have%252520determined%252520that%252520the%252520Gulf%252520of%252520Mex%2526region%253DordinaryItems%2526pageIDType%253D1%2526.activitymap%2526.a%2526.c%2526pid%253Dvoa%25253Aman%25253Ar%25253Asection%25253A%2525E5%25258F%25258C%2525E8%2525AF%2525AD%2525E6%252596%2525B0%2525E9%252597%2525BB%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fwww.voachinese.com%25252Fa%25252Fbilingual-news-201807310801%25252F4509184.html%2526ot%253DA; _chartbeat5=139,600,%2Fz%2F2404,https%3A%2F%2Fwww.voachinese.com%2Fa%2Fbilingual-news-201807310801%2F4509184.html,Blgc0NvZRzeDC43xeCUF7haBYiJ62,,c,DBTRQVCWz2-XBd-QIQCycUVpQcRNY,voa.china,");
        conn.setRequestProperty("Host", "www.voachinese.com");
        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();
	    /*请求url获取返回的内容*/
	    
        StringBuffer buffer = new StringBuffer();
        //将返回的输入流转换成字符串
        try(
    		InputStream inputStream = conn.getInputStream();
    		GZIPInputStream gis = new GZIPInputStream(inputStream); 
            InputStreamReader inputStreamReader = new InputStreamReader(gis,"utf8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);){
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            String result = buffer.toString();
            System.out.println(result);
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        }
		System.out.println(Url);
		conn.disconnect();
		spiderSleep(3000, 1000);
//		//创建客户端
//		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
//		//创建请求Get实例
//		HttpGet httpGet = new HttpGet(Url);
//		
//////		
////		
//		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//		httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
//		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
//		httpGet.setHeader("Connection", "keep-alive");
//		httpGet.setHeader("Cookie", "Pangea-NodeId=WD6CMnZlQMc/aj51E0+pPA==; _cb_ls=1; .ASPXANONYMOUS=mSfp3i_oFNIIT4_kEvr1D5wRIHNkQoKvPWOwIHBSRGzfgRbaDu5JGtWCy6o9tiI6Ze5XOeI5TOXkR0R89m0H36EZfyZy3ox80IcT8lA6Bjaldw35SKUpGZcmUwjuySLMJubWTA2; PangeaEnvironment=2; optimizelyEndUserId=oeu1533302859206r0.417917401485558; __qca=P0-1198235383-1533302862368; AMCVS_518ABC7455E462B97F000101%40AdobeOrg=1; AMCV_518ABC7455E462B97F000101%40AdobeOrg=1406116232%7CMCIDTS%7C17747%7CMCMID%7C23487147751837693252725007406726712066%7CMCAAMLH-1534296741%7C9%7CMCAAMB-1534296741%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1533699141s%7CNONE%7CMCAID%7CNONE%7CMCSYNCSOP%7C411-17754%7CvVersion%7C2.5.0; __utmt_gwt=1; utag_main=v_id:0164fff8cccd00585f13bef910b80506d002106500bd0$_sn:3$_ss:0$_st:1533693750836$vapi_domain:voachinese.com$ses_id:1533691938999%3Bexp-session$_pn:2%3Bexp-session; s_cc=true; __utma=245739139.1941137521.1533302860.1533548900.1533691944.3; __utmb=245739139.2.10.1533691944; __utmc=245739139; __utmz=245739139.1533302860.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); _cb=BAr6NBwl67KBSx0hD; _chartbeat2=.1533302859137.1533691951037.100101.BlAW_lRAxI9t8zsmCaATI9JP14f.2; _cb_svref=null; optimizelySegments=%7B%222317890156%22%3A%22false%22%2C%222326810159%22%3A%22direct%22%2C%222327980171%22%3A%22gc%22%7D; optimizelyBuckets=%7B%7D; _ceg.s=pd4ca8; _ceg.u=pd4ca8; optimizelyPendingLogEvents=%5B%5D; s_sq=bbgrvoamandarin2%252Cbbgprod%252Cbbgentityvoa%252Cbbgunitvoamandarin%3D%2526c.%2526a.%2526activitymap.%2526page%253Dvoa%25253Aman%25253Ar%25253Asection%25253A%2525E5%25258F%25258C%2525E8%2525AF%2525AD%2525E6%252596%2525B0%2525E9%252597%2525BB%2526link%253D%2525E5%25258F%25258C%2525E8%2525AF%2525AD%2525E6%252596%2525B0%2525E9%252597%2525BB%2525282018%2525E5%2525B9%2525B48%2525E6%25259C%2525881%2525E6%252597%2525A5%252529%252520%2525E7%2525BE%25258E%2525E5%25259B%2525BD%2525E7%2525A7%252591%2525E5%2525AD%2525A6%2525E5%2525AE%2525B6%2525E8%2525AE%2525A4%2525E5%2525AE%25259A%2525EF%2525BC%25258C%2525E5%2525A2%2525A8%2525E8%2525A5%2525BF%2525E5%252593%2525A5%2525E6%2525B9%2525BE%2525E9%252587%25258C%2525E7%252594%2525B1%2525E4%2525BA%25258E%2525E5%252590%2525AB%2525E6%2525B0%2525A7%2525E8%2525BF%252587%2525E4%2525BD%25258E%2525E5%2525AF%2525BC%2525E8%252587%2525B4%2525E9%2525B1%2525BC%2525E5%252592%25258C%2525E6%2525B5%2525B7%2525E6%2525B4%25258B%2525E7%252594%25259F%2525E7%252589%2525A9%2525E6%2525AD%2525BB%2525E4%2525BA%2525A1%2525E7%25259A%252584%2525E6%252589%252580%2525E8%2525B0%252593%2525E2%252580%25259C%2525E6%2525AD%2525BB%2525E4%2525BA%2525A1%2525E5%25258C%2525BA%2525E2%252580%25259D%2525E4%2525BB%25258A%2525E5%2525B9%2525B4%2525E6%252598%2525AF1985%2525E5%2525B9%2525B4%2525E5%2525BC%252580%2525E5%2525A7%25258B%2525E6%2525B5%25258B%2525E9%252587%25258F%2525E8%2525AE%2525B0%2525E5%2525BD%252595%2525E4%2525BB%2525A5%2525E6%25259D%2525A5%2525E7%2525AC%2525AC%2525E5%25259B%25259B%2525E5%2525B0%25258F%2525E7%25259A%252584%2525E9%25259D%2525A2%2525E7%2525A7%2525AF%2525E3%252580%252582U.S.%252520scientists%252520have%252520determined%252520that%252520the%252520Gulf%252520of%252520Mex%2526region%253DordinaryItems%2526pageIDType%253D1%2526.activitymap%2526.a%2526.c%2526pid%253Dvoa%25253Aman%25253Ar%25253Asection%25253A%2525E5%25258F%25258C%2525E8%2525AF%2525AD%2525E6%252596%2525B0%2525E9%252597%2525BB%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fwww.voachinese.com%25252Fa%25252Fbilingual-news-201807310801%25252F4509184.html%2526ot%253DA; _chartbeat5=139,600,%2Fz%2F2404,https%3A%2F%2Fwww.voachinese.com%2Fa%2Fbilingual-news-201807310801%2F4509184.html,Blgc0NvZRzeDC43xeCUF7haBYiJ62,,c,DBTRQVCWz2-XBd-QIQCycUVpQcRNY,voa.china,");
//		httpGet.setHeader("Host", "www.voachinese.com");
//		httpGet.setHeader("Upgrade-Insecure-Requests", "1");
//		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
//		
//		try 
//		{
//			System.setProperty("https.protocols", "TLSv1.2");
//		    System.setProperty("https.cipherSuites", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384");
//			//客户端执行httpGet方法，返回响应
//			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
//			
//			//得到服务响应状态码
//			if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) 
//			{
//				BufferedReader reader = new BufferedReader(new InputStreamReader
//						(closeableHttpResponse.getEntity().getContent(),"UTF-8"));
//				
//				String inputLine;
//				StringBuffer response = new StringBuffer();
//				
//				while ((inputLine = reader.readLine()) != null) 
//				{
//					response.append(inputLine);
//				}
//				Document doc = Jsoup.parse(response.toString(),"UTF-8");
//				Elements pp = doc.getElementsByClass("autoList");
//				AddData addData = new AddData();
//				int ll = 0;
//				for (int i = 0; i < pp.size(); i++) {
//					for (int j = 0; j < pp.get(i).childNodeSize(); j++) {
//						if (!pp.get(i).childNode(j).attr("href").isEmpty()) {
//							String urlString ="https://cn.nytimes.com"+  pp.get(i).childNode(j).attr("href");
////							UrlList urlList = new UrlList();
////							urlList.setURL(urlString);
////							urlList.setUrlType(5);
////							addData.Add(urlList);
//							System.out.println(urlString);
//							ll++;
//						}
//					}
//				}
//				System.out.println(ll);
//				reader.close();
//				closeableHttpResponse.close();
//				closeableHttpClient.close();
//				spiderSleep(3000, 1000);
//			}
//			else {
//				System.out.println(Url+"无数据");
//				spiderSleep(1800, 1000);
//			}
//		}
//		finally 
//		{
//			try 
//			{
//				closeableHttpClient.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
		 
	
}