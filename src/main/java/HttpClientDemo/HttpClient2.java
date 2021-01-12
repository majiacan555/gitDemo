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

import sun.font.CreatedFontTracker;

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

//2015.02.03-2018.07��ľͱ������  ����������  ֻ��һ������

//2013-09-18 -2015.02.03����ֻ��һ������  ������**
// 2012.6.11 - 2013-09-17 ��ƪ���£��������� 
//2010-02-12  -  2012-6-7 ��ƪ����û�б���
//2010-02-11 ֮ǰ����ƪ������������
public class HttpClient2 {
	private static void spiderSleep(int random , int sleep) throws InterruptedException {
		int Random = (int) (Math.random() * random) + sleep;
		System.out.println("��Ϣ:"+(Random/1000)+"��");
		Thread.sleep(Random);
		
	}
    public static void main(String[] args) throws SQLException, InterruptedException{
    	AddData searchUrl = new AddData();
    	List<UrlList> urllist = searchUrl.Search("2");
    	for (int i = 0; i < urllist.size(); i++) {
    		if (urllist.get(i).getId() >= 12466) {
				
    			CreateClient(urllist.get(i).getURL(),urllist.get(i).getId());
			}
		}
    }

	private static void CreateClient(String url,int id) throws SQLException, InterruptedException {
		//�����ͻ���
        CloseableHttpClient  closeableHttpClient = HttpClients.createDefault();
        //��������Getʵ��
        HttpGet httpGet = new HttpGet(url);
        //���ͷ����Ϣģ�����������
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", "ASPSESSIONIDSQQCRQTA=HJKECFKDIMGJEEFLAKNBGILE; __tins__805018=%7B%22sid%22%3A%201532529670000%2C%20%22vd%22%3A%201%2C%20%22expires%22%3A%201532531470000%7D; __51cke__=; __51laig__=91; ASPSESSIONIDSSRDQRTB=NCELABHACDDAEEKAGDDGBHON; _ga=GA1.2.1845759156.1532395590; _gid=GA1.2.212967814.1532395590");
        httpGet.setHeader("Host", "www.listeningexpress.com");
        httpGet.setHeader("Referer", "http://www.listeningexpress.com/voa/bilingual-news/2018/03/");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

        try {
            //�ͻ���ִ��httpGet������������Ӧ
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
            

            //�õ�������Ӧ״̬��
            if(closeableHttpResponse.getStatusLine().getStatusCode() == 200) {  
//                //�õ���Ӧʵ��
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
				Element masthead = doc.getElementById("srcWinText");//class����masthead��div��ǩ
//				for (int i =0;i<masthead.size();i++) {
//					String textScript =  masthead.get(i).toString();
//					int stridx = textScript.indexOf("<p>");
//					int endidx = textScript.indexOf("</p>");
//					String string = textScript.substring(stridx+3,endidx);
//					System.out.println(masthead.get(i).toString());
//				}
				if (masthead==null) {
					System.out.println(url+"�����ݣ�mastheadΪnull");
					return;
				}
				List<String> list = new ArrayList<String>();
				
				for (int i = 0; i < masthead.childNodeSize(); i++) {
					if (!masthead.childNode(i).toString().contains("<br />")) {
//						System.out.println(masthead.childNode(i).toString());
						list.add(masthead.childNode(i).toString());
					}
				}
				//2015.02.03-2018.07��ľͱ������  ����������  ֻ��һ������
				
				//2013-12-30

				//2013-09-18 -2015.02.03����ֻ��һ������  ������**
				// 2012.6.11 - 2013-09-17 ��ƪ���£��������� 
				//2010-02-12  -  2012-6-7 ��ƪ����û�б���
				//2010-02-11 ֮ǰ����ƪ������������
				AddData addData = new AddData();
				if (list.size() % 2 == 0) {
					if (id<=10509 || (id>11018 && id <= 11333)) {  //��ƪ������������
						
					}
					if (id>10509 && id<=11018) {// ��ƪ����û�б���
						
					}
					if (id>11333 ) {  //һƪ����һ������
						for (int i = 1; i < list.size()/2; i++) {
							ChineseAndEnglishModel CE = new ChineseAndEnglishModel();
							CE.setChineseData(list.get(i));
							CE.setEnglishData(list.get(i+list.size()/2));
							CE.setMemo("");
							CE.setTitleID(100000+id);
							CE.setUrlType(2);
							CE.setURL(url);
							CE.setTitle(list.get(list.size()/2)+"_"+list.get(0));
							addData.Add(CE);
							ChineseModel C = new ChineseModel();
							C.setChineseData(list.get(i));
							C.setMemo("");
							C.setTitle(list.get(0));
							C.setTitleID(100000+id);
							C.setURL(url);
							C.setUrlType(2);
							addData.Add(C);
							EnglishModel E = new EnglishModel();
							E.setEnglishData(list.get(i+list.size()/2));
							E.setMemo("");
							E.setChineseId(id);
							E.setTitle(list.get(list.size()/2));
							E.setTitleID(100000+id);
							E.setURL(url);
							E.setUrlType(2);
							addData.Add(E);
						}
					}
				}
				else {
					ErrorUrlModel error = new ErrorUrlModel();
					error.setURL(url);
					error.setUrlType(2);
					addData.Add(error);
					System.out.println(url+"���ݳ���");
					
				}
				System.out.println(url);
				System.out.println(list.size());
				spiderSleep(2000, 1000);
				reader.close();
				closeableHttpResponse.close();
				closeableHttpClient.close();
            }
            else{
                System.out.println(url+"û������");
                spiderSleep(1000, 1000);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}
