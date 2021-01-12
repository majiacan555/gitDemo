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

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import TransLationModel.ChineseAndEnglishModel;
import TransLationModel.ChineseModel;
import TransLationModel.EnglishModel;
import TransLationModel.ErrorUrlModel;
import TransLationModel.UrlList;
import junit.framework.Test;

public class GetSaShiBiYaText {
	public static void main(String[] args) {
		test();
	}
	  public static void test() {
	        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//�½�һ��ģ��ȸ�Chrome�������������ͻ��˶���

	        webClient.getOptions().setThrowExceptionOnScriptError(false);//��JSִ�г����ʱ���Ƿ��׳��쳣, ����ѡ����Ҫ
	        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//��HTTP��״̬��200ʱ�Ƿ��׳��쳣, ����ѡ����Ҫ
	        webClient.getOptions().setActiveXNative(false);
	        webClient.getOptions().setCssEnabled(false);//�Ƿ�����CSS, ��Ϊ����Ҫչ��ҳ��, ���Բ���Ҫ����
	        webClient.getOptions().setJavaScriptEnabled(true); //����Ҫ������JS
	        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//����Ҫ������֧��AJAX

	        HtmlPage page = null;
	        try {
	            page = webClient.getPage("https://www.baidu.com/");//���Լ�������ͼƬ���Ӹ�������ҳ
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            webClient.close();
	        }

	        webClient.waitForBackgroundJavaScript(30000);//�첽JSִ����Ҫ��ʱ,���������߳�Ҫ����30��,�ȴ��첽JSִ�н���

	        String pageXml = page.asXml();//ֱ�ӽ�������ɵ�ҳ��ת����xml��ʽ���ַ���

	        //TODO ����Ĵ�����Ƕ��ַ����Ĳ�����,������������,�õ��˱ȽϺ��õ�Jsoup��

	        Document document = Jsoup.parse(pageXml);//��ȡhtml�ĵ�
	        List<Element> infoListEle = document.getElementById("feedCardContent").getElementsByAttributeValue("class", "feed-card-item");//��ȡԪ�ؽڵ��
	        for(Element element : infoListEle) {
	            System.out.println(element.text()); 
	        } ;
	    }
}