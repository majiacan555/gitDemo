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

public class Kekenet_GetText {
	static String Url;
	static int i;
	static List<UrlList> list;
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException, InterruptedException {
		AddData search = new AddData();
		list = search.Search("8");
		for (i = 53260; i < list.size(); i++) {//8339
			if (list.get(i).getId() >111488) {
				
				Url = list.get(i).getURL();
				System.out.println(i+":"+Url);
				GetChineseAndEnglish(Url,list.get(i).getId());
			}
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
		        conn.setRequestProperty("Cookie", "UM_distinctid=164c9c62325463-0f8303943b5cb-5e4f2b18-1fa400-164c9c62326711; pgv_pvid=4014071455; Hm_lvt_9c68e8500a8dd6cbf338b9afc70517ec=1533548670,1533614061,1533720493,1534041729; Hm_lpvt_9c68e8500a8dd6cbf338b9afc70517ec=1534043139; CNZZDATA1037071=cnzz_eid%3D54743330-1532393234-%26ntime%3D1534041369");
		        conn.setRequestProperty("Host", "www.kekenet.com");
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
		            Document doc = Jsoup.parse(result,"UTF-8");
		            Element titleElement = doc.getElementById("nrtitle");
		            String title = "";
		            if (titleElement != null) {
						title = titleElement.text();
					}
		            if (result.contains("英中对照" )&& result.contains("中英对照")) {
		            	List<String> list = new ArrayList<>();
		            	Elements pp = doc.getElementsByClass("story-headline");
		            	Elements aaElements = doc.getElementsByClass("info-qh");
		            	if (aaElements.size()>0) {
		            		for (int i = 0; i < aaElements.get(0).children().size(); i++) {
		            			String Text = aaElements.get(0).child(i).text();
		            			if (!Text.contains("来源:可可英语") && Text.length()>2 && !Text.contains("来源：前十网")) {
		            		
		            				list.add(Text);
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
								CE.setUrlType(8);
								CE.setURL(Url);
								CE.setTitle(title);
								addData.Add(CE);
								ChineseModel C = new ChineseModel();
								C.setChineseData(list.get(i+1));
								C.setMemo("");
								C.setTitle(title);
								C.setTitleID(100000+id);
								C.setURL(Url);
								C.setUrlType(8);
								addData.Add(C);
								EnglishModel E = new EnglishModel();
								E.setEnglishData(list.get(i));
								E.setMemo("");
								E.setChineseId(id);
								E.setTitle(title);
								E.setTitleID(100000+id);
								E.setURL(Url);
								E.setUrlType(8);
								addData.Add(E);
							}
						}
						else {
							System.out.println(Url+"数据出错");
							ErrorUrlModel errorUrlModel = new ErrorUrlModel();
							errorUrlModel.setURL(Url);
							errorUrlModel.setUrlType(8);
							addData.Add(errorUrlModel);
						}
						
		            	System.out.println("中英---"+list.size()/2.0);
					}
		            else {
		            	List<String> noList =new ArrayList<>();
	            		Element bbElement = doc.getElementById("article_eng");
	            		for (int i = 0; i < bbElement.children().size(); i++) {
	            			for (int j = 0; j < bbElement.child(i).childNodeSize(); j++) {
	            				Document kkk = Jsoup.parse(bbElement.child(i).childNode(j).toString(),"UTF-8");
	            				for (int k = 0; k < kkk.children().size(); k++) {
	            					for (int k2 = 0; k2 <kkk.child(k).childNodeSize(); k2++) {
	            						Document qqq = Jsoup.parse(kkk.child(k).childNode(k2).toString(),"UTF-8");
										for (int l = 0; l < qqq.children().size(); l++) {
											String Text = qqq.child(l).text();
											if (Text.contains("<br />")) {
												Text = Text.replace("<br />", "");
											}
											if (Text.contains("<body>")) {
												Text = Text.replace("<body>", "");
											}
											if (Text.contains("</head>")) {
												Text = Text.replace("</head>", "");
											}
											if (Text.contains("<head>")) {
												Text = Text.replace("<head>", "");
											}
											if (Text.contains("</body>")) {
												Text = Text.replace("</body>", "");
											}
											if (!Text.contains("<img") && !Text.contains("<script>") &&!Text.contains("来源：前十网")) {
												
												if (!Text.contains("来源:可可英语") && Text.length()>3 && !Text.contains("www.kekenet.com")) {
													if (Text.length() >2) {
														noList.add(Text);
													}
												}
											}
										}
										
									}
								}
	            			}
	            		}
	            		CopyOfAddData addData = new CopyOfAddData();
	            		if (noList.size() % 2 == 0) {
							for (int i = 0; i < noList.size(); i=i+2) {
								ChineseAndEnglishModel CE = new ChineseAndEnglishModel();
								CE.setChineseData(noList.get(i+1));
								CE.setEnglishData(noList.get(i));
								CE.setMemo("");
								CE.setTitleID(100000+id);
								CE.setUrlType(8);
								CE.setURL(Url);
								CE.setTitle(title);
								addData.Add(CE);
								ChineseModel C = new ChineseModel();
								C.setChineseData(noList.get(i+1));
								C.setMemo("");
								C.setTitle(title);
								C.setTitleID(100000+id);
								C.setURL(Url);
								C.setUrlType(8);
								addData.Add(C);
								EnglishModel E = new EnglishModel();
								E.setEnglishData(noList.get(i));
								E.setMemo("");
								E.setChineseId(id);
								E.setTitle(title);
								E.setTitleID(100000+id);
								E.setURL(Url);
								E.setUrlType(8);
								addData.Add(E);
							}
						}
						else {
							System.out.println(Url+"数据出错");
							ErrorUrlModel errorUrlModel = new ErrorUrlModel();
							errorUrlModel.setURL(Url);
							errorUrlModel.setUrlType(8);
							addData.Add(errorUrlModel);
						}
	            		System.out.println(noList.size()/2.0);
	            	}
		            
//					for (int i = 0; i < aaElements.get(0).childNodeSize(); i++) {
//						System.out.println(aaElements.get(0).childNode(i));
//					}
					bufferedReader.close();
					inputStreamReader.close();
					inputStream.close();
					spiderSleep(1000, 1500);
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
	}
}