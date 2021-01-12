package HttpClientDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

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

public class Ft_Chinese {
	static String Url;
	static int i;
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException, InterruptedException {
		for (i =73555; i < 78713; i++) {
//		int begin = 25263; end = 78713  33234
			Url = "http://www.ftchinese.com/story/0010"+i+"/ce?ccode=LanguageSwitch&archive";
			System.out.println(Url);
			GetChineseAndEnglish(Url,i);
			
		}
	}
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000.0)+"秒");
		Thread.sleep(Random);
		
	}
	public static void GetChineseAndEnglish(String Url,int id) throws SQLException, InterruptedException
	{
		URL serverUrl;
		try {
			serverUrl = new URL(Url);
			HttpURLConnection conn;
			try {
				conn = (HttpURLConnection) serverUrl.openConnection();
				conn.setRequestMethod("GET");
		        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
		        conn.setRequestProperty("Connection", "keep-alive");
		        conn.setRequestProperty("Cookie", "null=undefined; ftn_cookie_id=1532695698.621919884; uniqueVisitorId=09cb233f-eb5f-57db-4aef-67aa7496a8c5; faid=adec1f4aed966b52090855def728c837; USER_ID=eb72cc79-371d-44e3-834b-ff46c3c11438; USER_ID_FT=eb72cc79-371d-44e3-834b-ff46c3c11438; USER_NAME=616846283; USER_NAME_FT=616846283; USER_KV=ver%7C201808%3Bsex%7C101%3Bcs%7C0%3Bcsp%7C0%3Bhi%7C0%3Bin%7C17; ft=FjDwFkGtm2RZo4KsjzUIjs9yiuVd%2BRwiS2GjiYeA%2FwUJyuNEZDUWrY3dtPiXtQchl%2BMP7EY1hpUQkg%2FzW3y1Eo5WNEHBrCF0aq7f6Xguim21xzR6kzZfV%2Fs6Ow3IQfy8BDjK9%2BYvXBQUaJf%2BNg7GN7yDZmw8hbgGV7WfPixUj7lkbbpaJrrJc5YCBT%2BBjISK; ccode=LanguageSwitch; FTSTAT_ok_pages=1; FTSTAT_ok_times=12; Hm_lvt_6f808c64c1308274ef22324b340e40af=1533213427,1533285348,1533567699,1533903783; Hm_lpvt_6f808c64c1308274ef22324b340e40af=1533903783; _ga=GA1.2.402975821.1532695700; _gid=GA1.2.2021615387.1533903783; _gat=1");
		        conn.setRequestProperty("Host", "www.ftchinese.com");
		        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
		        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
				
		       conn.setInstanceFollowRedirects(false);
		       conn.connect();
			    /*请求url获取返回的内容*/
		       StringBuffer buffer = new StringBuffer();
		       //将返回的输入流转换成字符串
		       if (conn.getResponseCode() == 200) {
					InputStream inputStream = conn.getInputStream();
				    GZIPInputStream gis = new GZIPInputStream(inputStream); 
		            InputStreamReader inputStreamReader = new InputStreamReader(gis,"utf-8");
		            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		            String str = null;
		            while ((str = bufferedReader.readLine()) != null) {
		                buffer.append(str);
		            }
		            String result = buffer.toString();
		            Document doc = Jsoup.parse(result,"UTF-8");Elements pp = doc.getElementsByClass("story-headline");
					String EnglishTitle = "";
					String chineseTitle = "";
					for (int i = 0; i < pp.size(); i++) {
//							for (int j = 0; j < pp.get(i).childNodeSize(); j++) {
//								System.out.println(pp.get(i).childNode(j));
//							}
						if (pp.get(i).childNodeSize() == 3) {
							EnglishTitle = pp.get(i).childNode(0).toString();
							chineseTitle = pp.get(i).childNode(2).toString();
						}
						else {
							System.out.println(pp.get(i));
						}
					}
					Element masthead = doc.getElementById("story-body-container");//class等于masthead的div标签
					if (masthead != null) {
						Elements aaElements = masthead.getElementsByClass("leftp");
						Elements bbElements = masthead.getElementsByClass("rightp");
						List<String> leftList = new ArrayList<String>();
						List<String> RightList = new ArrayList<String>();
						for (int i = 0; i < aaElements.size(); i++) {
							for (int j = 0; j < aaElements.get(i).childNodeSize(); j++) {
								for (int j2 = 0; j2 < aaElements.get(i).childNode(j).childNodeSize(); j2++) {
									String leftText = aaElements.get(i).childNode(j).childNode(j2).toString();
									if (leftText.contains("<b>")) {
										leftText = leftText.replace("<b>", "");
									}
									if (leftText.contains("</b>")) {
										leftText = leftText.replace("</b>", "");
									}
									if (leftText.contains("<br />")) {
										leftText = leftText.replace("<br />", "");
									}
									if (leftText.contains("&nbsp;")) {
										leftText = leftText.replace("&nbsp", "");
									}
									if (leftText.length()>2) {
										leftList.add(leftText);
									}
								}
							}
						}
						for (int i = 0; i < bbElements.size(); i++) {
							for (int j = 0; j < bbElements.get(i).childNodeSize(); j++) {
								for (int j2 = 0; j2 < bbElements.get(i).childNode(j).childNodeSize(); j2++) {
									String rightText = bbElements.get(i).childNode(j).childNode(j2).toString();
									if (rightText.contains("<b>")) {
										rightText = rightText.replace("<b>", "");
									}
									if (rightText.contains("</b>")) {
										rightText = rightText.replace("</b>", "");
									}
									if (rightText.contains("<br />")) {
										rightText = rightText.replace("<br />", "");
									}
									if (rightText.contains("&nbsp;")) {
										rightText = rightText.replace("&nbsp", "");
									}
									if (rightText.length()>2) {
										RightList.add(rightText);
									}
								}
							}
						}
						System.out.println("leftp.size="+leftList.size()+"____right.size="+RightList.size());
						AddData addData = new AddData();
						if (leftList.size() == RightList.size()) {
							for (int i = 0; i < leftList.size(); i++) {
								ChineseAndEnglishModel CE = new ChineseAndEnglishModel();
								CE.setChineseData(RightList.get(i));
								CE.setEnglishData(leftList.get(i));
								CE.setMemo("财经新闻");
								CE.setTitleID(1000000+id);
								CE.setUrlType(3);
								CE.setURL(Url);
								CE.setTitle(EnglishTitle+"_"+chineseTitle);
								addData.Add(CE);
								ChineseModel C = new ChineseModel();
								C.setChineseData(RightList.get(i));
								C.setMemo("财经新闻");
								C.setTitle(chineseTitle);
								C.setTitleID(1000000+id);
								C.setURL(Url);
								C.setUrlType(3);
								addData.Add(C);
								EnglishModel E = new EnglishModel();
								E.setEnglishData(leftList.get(i));
								E.setMemo("财经新闻");
								E.setChineseId(id);
								E.setTitle(EnglishTitle);
								E.setTitleID(1000000+id);
								E.setURL(Url);
								E.setUrlType(3);
								addData.Add(E);
							}
						}
						else {
							System.out.println(Url+"格式出错");
							ErrorUrlModel errorUrlModel = new ErrorUrlModel();
							errorUrlModel.setURL(Url);
							errorUrlModel.setUrlType(3);
							addData.Add(errorUrlModel);
						}
					}
					else {
						System.out.println(Url+"没有数据");
					}
					bufferedReader.close();
					inputStreamReader.close();
					inputStream.close();
					spiderSleep(3000, 3000);
				}
		       
		       else {
					System.out.println(Url+"访问失败");
					spiderSleep(3000, 3000);
				}
				conn.disconnect();
			} catch (IOException e) {
				System.out.println("IO出错,重新链接--------------");
				spiderSleep(5000, 10000);
				GetChineseAndEnglish(Url,i);
				e.printStackTrace();
			}
	        
		} catch (MalformedURLException e) {
			
			System.out.println("Malformed出错,重新链接-----------");
			spiderSleep(3000, 10000);
			GetChineseAndEnglish(Url,i);
			e.printStackTrace();
		}
		//得到服务响应状态码
		
		
	}
}