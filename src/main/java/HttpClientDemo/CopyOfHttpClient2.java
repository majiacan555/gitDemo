package HttpClientDemo;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import TransLationModel.UrlList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


//2015.02.03-2018.07后的就标题对齐  标题无特征  只有一个标题

//2013-09-18 -2015.02.03都是只有一个标题  标题用**
//2013-09-18之前都是两个标题两篇文章
public class CopyOfHttpClient2 {
    public static void main(String[] args) throws InterruptedException, SQLException{
    	Calendar start =  Calendar.getInstance();
		start.set(2005, Calendar.SEPTEMBER,5);
		Calendar end =  Calendar.getInstance();
		end.set(2018, Calendar.JULY,25);
		while(start.getTime().getTime() <= end.getTime().getTime()){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/");
			String Date = simpleDateFormat.format(start.getTime());
			getUrl(Date);
			start.add(Calendar.MONTH, 1);
		}
        
    }
    
	private static void getUrl(String date) throws InterruptedException, SQLException {
		// TODO Auto-generated method stub
		//创建客户端
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        String Url  = "http://www.listeningexpress.com/voa/bilingual-news/"+date;

        //创建请求Get实例
        HttpGet httpGet = new HttpGet(Url);

        //添加头部信息模拟浏览器访问
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", "ASPSESSIONIDSQQCRQTA=HJKECFKDIMGJEEFLAKNBGILE; _ga=GA1.2.1845759156.1532395590; _gid=GA1.2.212967814.1532395590; __tins__805018=%7B%22sid%22%3A%201532512248638%2C%20%22vd%22%3A%2045%2C%20%22expires%22%3A%201532515603828%7D; __51cke__=; __51laig__=56");
        httpGet.setHeader("Host", "www.listeningexpress.com");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);

            //得到服务响应状态码
            if(closeableHttpResponse.getStatusLine().getStatusCode() == 200) {  
//                //得到响应实体
//                String entity = EntityUtils.toString (closeableHttpResponse.getEntity(), "GBK");
//                System.out.println(entity);
            	BufferedReader reader = new BufferedReader(new InputStreamReader
						(closeableHttpResponse.getEntity().getContent(),"utf-8"));
				
				String inputLine;
				StringBuffer response = new StringBuffer();
				
				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				Document doc = Jsoup.parse(response.toString(),"utf-8");
				
				Element masthead = doc.getElementById("list");//class等于masthead的div标签
				
//				for (int i =0;i<masthead.size();i++) {
					
//					String textScript =  masthead.get(i).toString();
//					int stridx = textScript.indexOf("<p>");
//					int endidx = textScript.indexOf("</p>");
//					String string = textScript.substring(stridx+3,endidx);
//					System.out.println(masthead.get(i).toString());
//				}
				Elements masthead2  = masthead.select("li");
				for (int i = masthead2.size()-1; i >= 0; i--) {
					String urlString = masthead2.get(i).select("a").attr("href").toString();
					UrlList urlList = new UrlList();
					urlList.setURL(Url+urlString);
					urlList.setUrlType(2);
					AddData addURLlist = new AddData();
					addURLlist.Add(urlList);
				}
				reader.close();
				closeableHttpResponse.close();
				closeableHttpClient.close();
				spiderSleep(3000, 1000);
            }
            else{
                System.out.println("------------");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000)+"秒");
		Thread.sleep(Random);
		
	}
}
