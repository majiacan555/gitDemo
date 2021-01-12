package HttpClientDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

public class News_Iyuba_GetText {
	static String Url;
	static int j;
	static List<UrlList> list;
	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException, InterruptedException {
		AddData search = new AddData();
		list = search.Search("5");
		
		for (j = 16364; j < list.size(); j++) {
			if (list.get(j).getId() > 32165) {  //最后是     >29654
				Url = list.get(j).getURL();
				System.out.println(j+":"+Url);
				GetChineseAndEnglish(Url,list.get(j).getId());
//		String Url  = "http://news.iyuba.com/essay/2014/12/12/35736.html";
//		GetChineseAndEnglish(Url,1);
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
		
		//创建客户端
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		//创建请求Get实例
		HttpGet httpGet = new HttpGet(Url);
		
		//设置头部信息进行模拟登录（添加登录后的Cookie）
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Cookie", "UM_distinctid=164c9c6ba2a53c-0de0a9b1d72db4-5e4f2b18-1fa400-164c9c6ba2b794; CNZZDATA1253159226=2089847591-1532390366-%7C1533884074");
		httpGet.setHeader("Host", "news.iyuba.com");
		httpGet.setHeader("Upgrade-Insecure-Requests", "1");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
		
		
			//客户端执行httpGet方法，返回响应
			CloseableHttpResponse closeableHttpResponse;
			try {
				closeableHttpResponse = closeableHttpClient.execute(httpGet);
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
					Elements titleElements = doc.getElementsByClass("title_cn");
					String engString = titleElements.get(0).childNode(0).toString();
					Document engdoc = Jsoup.parse(engString,"UTF-8");
					Elements engElements = engdoc.select("h1");
					String EnglishTitle = engElements.text();
					String ChinaTitle = titleElements.get(0).childNode(1).toString();
					if (ChinaTitle.contains("<br />")) {
						ChinaTitle = titleElements.get(0).childNode(2).toString();
					}
					Elements pp = doc.getElementsByClass("w_center");
					
					for (int i = 0; i < pp.size(); i++) {
						Elements englishElements = pp.get(i).getElementsByClass("p1");
						Elements ChinaElements = pp.get(i).getElementsByClass("p2");
						AddData addData = new AddData();
						if ( englishElements.size() >0  ) {
							if (englishElements.size() == ChinaElements.size()) {
								for (int j = 0; j < englishElements.size(); j++) {
									ChineseAndEnglishModel CE = new ChineseAndEnglishModel();
									CE.setChineseData(ChinaElements.get(j).text());
									CE.setEnglishData(englishElements.get(j).text());
									CE.setMemo("");
									CE.setTitleID(100000+id);
									CE.setUrlType(5);
									CE.setURL(Url);
									CE.setTitle(EnglishTitle+"_"+ChinaTitle);
									addData.Add(CE);
									ChineseModel C = new ChineseModel();
									C.setChineseData(ChinaElements.get(j).text());
									C.setMemo("");
									C.setTitle(ChinaTitle);
									C.setTitleID(100000+id);
									C.setURL(Url);
									C.setUrlType(5);
									addData.Add(C);
									EnglishModel E = new EnglishModel();
									E.setEnglishData(englishElements.get(j).text());
									E.setMemo("");
									E.setChineseId(id);
									E.setTitle(EnglishTitle);
									E.setTitleID(100000+id);
									E.setURL(Url);
									E.setUrlType(5);
									addData.Add(E);
								}
							}
							else {
								ErrorUrlModel errorUrlModel = new ErrorUrlModel();
								errorUrlModel.setURL(Url);
								errorUrlModel.setUrlType(5);
//								addData.Add(errorUrlModel);
								System.out.println(Url+"格式出错");
							}
						}
						else {
							List<String> list = new ArrayList<String>();
							for (int p = 0; p < pp.get(0).children().size(); p++) {
								Elements spanElements = pp.get(0).child(p).select("span");
								for (int j = 0; j < spanElements.size(); j++) {
									String stylevalue = spanElements.get(j).attr("style");
									if (stylevalue.contains("font-size:16px;")) {
										if (stylevalue.contains("font-family:Times New Roman;")) {
											for (int k = 0; k < spanElements.get(j).childNodeSize(); k++) {
												String text = spanElements.get(j).childNode(k).toString();
												if (!text.contains("style=")) {
													if (text.contains("&nbsp;")) {
														text = text.replace("&nbsp;", "");
													}
													if (text.contains("<br />")) {
														text = text.replace("<br />", "");
													}
													if (text.contains("&middot")) {
														text = text.replace("&middot", "");
													}
													if (text.contains("&quot;")) {
														text = text.replace("&quot;", "");
													}
													if (text.length()>2) {
														if (!text.contains("<img")) {
															list.add(text);
														}
													}
												}
											}
										}
									}
								}
							}
							if (list.size() % 2 == 0) {
								for (int j = 0; j < list.size(); j=j+2) {
									ChineseAndEnglishModel CE = new ChineseAndEnglishModel();
									CE.setChineseData(list.get(j+1));
									CE.setEnglishData(list.get(j));
									CE.setMemo("");
									CE.setTitleID(100000+id);
									CE.setUrlType(5);
									CE.setURL(Url);
									CE.setTitle(EnglishTitle+"_"+ChinaTitle);
									addData.Add(CE);
									ChineseModel C = new ChineseModel();
									C.setChineseData(list.get(j+1));
									C.setMemo("");
									C.setTitle(ChinaTitle);
									C.setTitleID(100000+id);
									C.setURL(Url);
									C.setUrlType(5);
									addData.Add(C);
									EnglishModel E = new EnglishModel();
									E.setEnglishData(list.get(j));
									E.setMemo("");
									E.setChineseId(id);
									E.setTitle(EnglishTitle);
									E.setTitleID(100000+id);
									E.setURL(Url);
									E.setUrlType(5);
									addData.Add(E);
								}
							}
							else {
								System.out.println(Url+"数据出错");
								ErrorUrlModel errorUrlModel = new ErrorUrlModel();
								errorUrlModel.setURL( Url);
								errorUrlModel.setUrlType(7);
								addData.Add(errorUrlModel);
							}
							System.out.println(list.size());
						}
						
						
						System.out.println(englishElements.size()+"&&"+ChinaElements.size());
					}
					reader.close();
					closeableHttpResponse.close();
					spiderSleep(4000, 4000);
				}
				else {
					System.out.println(Url+"无数据");
					spiderSleep(1800, 1000);
				}
				closeableHttpClient.close();
			} catch (ClientProtocolException e) {
				System.out.println("链接失败，重新链接------------------");
				spiderSleep(2000, 5000);
				GetChineseAndEnglish(Url,list.get(j).getId());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("链接失败，重新链接------------------");
				spiderSleep(2000, 5000);
				GetChineseAndEnglish(Url,list.get(j).getId());
				
				e.printStackTrace();
			}
			
			//得到服务响应状态码
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