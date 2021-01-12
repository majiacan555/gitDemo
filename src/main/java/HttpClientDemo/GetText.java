package HttpClientDemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class GetText implements Runnable{
	public GetText (String Url,String Date , String Hour,String referer){
		this.Url = Url;
		this.Date= Date;
		this.Hour = Hour;
		this.referer = referer;
	}
	
	private String Url ;
	private String Date;
	private String Hour;
	private String referer;
	@Override
	public void run() {
		
		//创建客户端
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		//创建请求Get实例
		HttpGet httpGet = new HttpGet(Url);
		
//		//设置代理IP，设置连接超时时间 、 设置 请求读取数据的超时时间 、 设置从connect Manager获取Connection超时时间、
//        HttpHost proxy = new HttpHost("60.194.11.179",3128);
//        
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setProxy(proxy)
//                .setConnectTimeout(20000)
//                .setSocketTimeout(20000)
//                .setConnectionRequestTimeout(20000)
//                .build();
//        httpGet.setConfig(requestConfig);
		
		//设置头部信息进行模拟登录（添加登录后的Cookie）
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Cookie", "SINAGLOBAL=3965505064345.769.1511100245786; login_sid_t=8b8485de00cd0651c660e687b903b3f8; cross_origin_proto=SSL; _s_tentry=passport.weibo.com; SWBSSL=usrmdinst_11; Apache=3547016758189.028.1520167812828; ULV=1520167812851:8:2:1:3547016758189.028.1520167812828:1519909434627; SWB=usrmdinst_6; UOR=www.baidu.com,weibo.com,login.sina.com.cn; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9Wh2fRxPITArLNhKK6N9Q2j-5JpX5K2hUgL.FoqXS0e41Kz4ehz2dJLoIE5LxK-L12zLB.-LxK-LB.2LB.-LxK-L12qLBoikMNiDd5tt; ALF=1551704087; SSOLoginState=1520168088; SCF=AohtYijDwf0SbRm51BP5FLYIAHLa7RL5mZModj5hhbJvxPoj3K8kUlmS0eS6P7lemVnjgLGemVywhn9pS3kgujU.; SUB=_2A253n5zIDeRhGeBK7FEY-SzFyz6IHXVU7IkArDV8PUNbmtBeLVDakW9NR4SOnzihvWck0c_1aOrq3sfhz0hlX3tB; SUHB=0gHS7OWmWNoVeq; un=13873755879; wvr=6");
		httpGet.setHeader("Host", "s.weibo.com");
		httpGet.setHeader("Referer", referer);
		httpGet.setHeader("Upgrade-Insecure-Requests", "1");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
		
		try {
			//客户端执行httpGet方法，返回响应
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
			
			//得到服务响应状态码
			if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader
						(closeableHttpResponse.getEntity().getContent(),"utf-8"));
				
				String inputLine;
				StringBuffer response = new StringBuffer();
				
				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				
				reader.close();
				Document doc = Jsoup.parse(response.toString(),"utf-8");
				
				Elements masthead = doc.select("script");//class等于masthead的div标签
				String textScript =  masthead.get(20).toString();
				
				int stridx = textScript.indexOf("{");
				int endidx = textScript.indexOf("}");
				String string = textScript.substring(stridx,endidx+1);
				closeableHttpResponse.close();
				closeableHttpClient.close();
				Gson json = new Gson();
				JsonModel model = json.fromJson(string,JsonModel.class);
				Document doc2 = Jsoup.parse(model.getHtml(),"utf-8");
				Elements masthead2 = doc2.select("p.comment_txt");
				Elements masthead3 = doc2.select("div.feed_from W_textb");
                FileOutputStream o = null;
        		byte[] nameBytes = new byte[]{};
        		File file = new File("D:\\"+Date+"\\"+Hour+".txt");
    			if(!file.exists())
    			{
    				file.createNewFile();
    			}
    			for(Element element : masthead3){
    				System.out.println(element.attr("title").toString());
    			}
    			for(Element element:masthead2){
    				System.out.println(element.attr("nick-name").toString());
                	nameBytes = element.text().getBytes();  
                	o=new FileOutputStream(file,true);
        			o.write(nameBytes);
        			o.flush();
        			
//        			AddData p = new AddData();
//        	        DataModel person0 = new DataModel();
//        	        person0.setData(element.text().toString());
//        	        p.Add(person0);
                }
    			o.close();
        			
			}
			else {
				System.out.println("222222222222222222");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				closeableHttpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
