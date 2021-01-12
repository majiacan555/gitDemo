package HttpClientDemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

/**
 * Created by paranoid on 17-3-26.
 */
public class HttpClientCookie {
	
    public static void main(String[] args) throws IOException, InterruptedException, SQLException{
    	System.out.println("11111111111111111111111111111");
    	String Url ="";
    	
    	for (int y = 2017; y <= 2017; y++) {
    		for (int m = 12; m <= 12; m++) {
				for (int d = 27; d <= 27; d++) {
					for (int h = 8; h < 10; h++) {
						List<String> list = new ArrayList<>();
						String Date = y+"_"+m+"_"+d;
						Url = "http://s.weibo.com/weibo/%25E7%2599%258C%25E7%2597%2587&typeall=1&suball=1&timescope=custom:" +
								y +
								"-" +
								m +
								"-" +
								d +
								"-" +
								h +
								":" +
								y +
								"-" +
								m +
								"-" +
								d +
								"-" +
								11 +
								"&Refer=g";
						System.out.println(Url);
						Get get = new Get();
						list = get.GetUrl(Url,Date,String.valueOf(h));
						for (int i = 1; i < list.size(); i++) {
							Url="http://s.weibo.com"+list.get(i);
							if (i==1) {
								String referer="http://s.weibo.com/weibo/%25E7%2599%258C%25E7%2597%2587?topnav=1&wvr=6";
								Thread thread = new Thread(new GetText(Url,Date,String.valueOf(h),referer));
								thread.start();
								int Random = (int)(Math.random()*500)+500;
								Thread.sleep(Random);
								
							}
							else {
								String referer="http://s.weibo.com"+list.get(i-1);
								Thread thread = new Thread(new GetText(Url,Date,String.valueOf(h),referer));
								thread.start();
								int Random = (int)(Math.random()*300)+500;
								Thread.sleep(Random);
							}
						}
						int dom = (int)(Math.random()*10)+2;
						TimeUnit.SECONDS.sleep(dom);//Ãë
						
							
						
					}
				}
			}
		}
    }
}
