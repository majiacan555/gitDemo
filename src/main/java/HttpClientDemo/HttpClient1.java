package HttpClientDemo;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class HttpClient1 {
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("休息:"+(Random/1000)+"秒");
		Thread.sleep(Random);
		
	}
    public static void main(String[] args) throws SQLException, InterruptedException{
    	AddData searchUrl = new AddData();
    	List<UrlList> urllist = searchUrl.Search("1");
    	for (int i = 0; i < urllist.size(); i++) {
    		if (urllist.get(i).getId()<=743) {
    			CreateClient(urllist.get(i).getURL(),urllist.get(i).getId());
			}
    		else {
				break;
			}
		}
        
    }
	private static void CreateClient(String Url, int id) throws SQLException, InterruptedException {
		String url = "http://www.51voa.com"+Url;
		System.out.println(url);
		//创建客户端
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = null ;
		try {
			httpGet = new HttpGet(url);
		} catch (Exception e) {
			return;
		}
        //创建请求Get实例

        //添加头部信息模拟浏览器访问
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", "ICIBA_HUAYI_COOKIE=1");
        httpGet.setHeader("Host", "www.51voa.com");
        httpGet.setHeader("Referer", "http://www.51voa.com/Voa_English_Learning/");
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
				if (doc == null) {
					System.out.println(url+"为null");
					return;
				}
				Element titlemasthead = doc.getElementById("title");//class等于masthead的div标签
				String title = titlemasthead.getElementsByTag("h1").first().childNode(0).toString();
				Element Textmasthead = doc.getElementById("content");//class等于masthead的div标签
				Elements masthead = Textmasthead.getElementsByTag("p");//class等于masthead的div标签
				
				List<String> list = new ArrayList<String>();
				if (masthead.size()>0) {
					for (int i =0;i<masthead.size();i++) {
						String textScript =  masthead.get(i).toString();
						if (textScript.contains("<font")) {
							Elements textScript1 =  masthead.get(i).getElementsByTag("font");
							for (int j = 0; j < textScript1.size(); j++) {
								for (int j2 = 0; j2 < textScript1.get(j).childNodeSize(); j2++) {
									String string =  textScript1.get(j).childNode(j2).toString();
									if ((!string.contains("<br />")) && (!string.contains("<span class=\"datetime\">"))) {
										if (string.contains("&nbsp;")) {
											string = string.replaceAll("&nbsp;", "");
										}
										if (string.contains("<i>&middot;</i>")) {
											string = string.replaceAll("<i>&middot;</i>", "");
										}
										if (!string.isEmpty()) {
											if (string.length()<=4) {
												boolean judge = false;
												char[] a = string.toCharArray();
												for (int k = 0; k < a.length; k++) {
													int p = a[k];
													if (p != 12288 ) {
														if (p != 32) {												
															judge = true;
														}
													}
												}
												if (judge) {
													list.add(string);
												}
												
											}
											else {
												list.add(string);
											}
										}
									}
								}
							}
						}
						else {
							for (int j = 0; j < masthead.get(i).childNodeSize(); j++) {
								String tt = masthead.get(i).childNode(j).toString();
								
								if ((!tt.contains("<br />")) && (!tt.contains("<span class=\"datetime\">"))) {
									if (tt.contains("&nbsp;")) {
										tt = tt.replaceAll("&nbsp;", "");
									}
									if (tt.contains("<i>&middot;</i>")) {
										tt = tt.replaceAll("<i>&middot;</i>", "");
									}
									if (!tt.isEmpty()) {
										if (tt.length()<=4) {
											boolean judge = false;
											char[] a = tt.toCharArray();
											for (int k = 0; k < a.length; k++) {
												int p = a[k];
												if (p != 12288 ) {
													if (p != 32) {												
														judge = true;
													}
												}
											}
											if (judge) {
												list.add(tt);
											}
										}
										else {
											list.add(tt);
											
										}
									}
								}
							}
						}
					}
				}
				else {
					for (int i = 0; i < Textmasthead.childNodeSize(); i++) {
						String kk = Textmasthead.childNode(i).toString();
						if (!(kk.contains("<br />")  )) {
							if (!kk.contains("<span class=\"datetime\">")) {
								
								if (kk.contains("&nbsp;")) {
									kk = kk.replaceAll("&nbsp;", "");
								}
								if (kk.contains("<i>&middot;</i>")) {
									kk = kk.replaceAll("<i>&middot;</i>", "");
								}
								if (!kk.isEmpty()) {
									if (kk.length()<=4) {
										boolean judge = false;
										char[] a = kk.toCharArray();
										for (int k = 0; k < a.length; k++) {
											int p = a[k];
											if (p != 12288 ) {
												if (p != 32) {												
													judge = true;
												}
											}
										}
										if (judge) {
											list.add(kk);
										}
										
									}
									else {
										list.add(kk);
										
									}
								}
							}
						}
					}
				}
				
				AddData addData = new AddData();
				if (list.size() % 2 == 0) {
					for (int i = 0; i < list.size()/2; i++) {
						ChineseAndEnglishModel CE = new ChineseAndEnglishModel();
						CE.setChineseData(list.get(i));
						CE.setEnglishData(list.get(i+list.size()/2));
						CE.setMemo("");
						CE.setTitleID(100000+id);
						CE.setUrlType(1);
						CE.setURL(url);
						CE.setTitle(title);
						addData.Add(CE);
						ChineseModel C = new ChineseModel();
						C.setChineseData(list.get(i));
						C.setMemo("");
						C.setTitle(title);
						C.setTitleID(100000+id);
						C.setURL(url);
						C.setUrlType(1);
						addData.Add(C);
						EnglishModel E = new EnglishModel();
						E.setEnglishData(list.get(i+list.size()/2));
						E.setMemo("");
						E.setChineseId(id);
						E.setTitle(title);
						E.setTitleID(100000+id);
						E.setURL(url);
						E.setUrlType(1);
						addData.Add(E);
					}
				}
				else {
					ErrorUrlModel errorUrlModel = new ErrorUrlModel();
					errorUrlModel.setURL(url);
					errorUrlModel.setUrlType(1);
					addData.Add(errorUrlModel);
					System.out.println(url+"数据出错");
				}
				System.out.println(list.size());
				spiderSleep(2000, 1000);
				reader.close();
				closeableHttpResponse.close();
				closeableHttpClient.close();
            }
            else{
                System.out.println(url+"没有数据");
                spiderSleep(2000, 1000);
            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally 
		{
			try 
			{
				closeableHttpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		for (int i =0;i<masthead.size();i++) {
//			
//			String textScript =  masthead.get(i).childNode(0).toString();
//
//			String textScript1 =  masthead.get(i).getElementsByTag("font").first().childNode(0).toString();
//			
////			int stridx = textScript.indexOf("<p>");
////			int endidx = textScript.indexOf("</p>");
////			String string = textScript.substring(stridx+3,endidx);
//			System.out.println(textScript);
//			System.out.println(textScript1);
//		}
	}
}
