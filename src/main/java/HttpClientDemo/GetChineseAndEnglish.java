package HttpClientDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Model.DataModel;
import TransLationModel.ErrorUrlModel;

import com.google.gson.Gson;

public class GetChineseAndEnglish {
	// public GetChineseAndEnglish (String Url)
	// {
	// this.Url = Url;
	// }

	// private String Url ;

	public void GetChineseAndEnglish(String Url)
			throws ClientProtocolException, IOException, SQLException {

		// �����ͻ���
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		// ��������Getʵ��
		HttpGet httpGet = new HttpGet(Url);

		// ����ͷ����Ϣ����ģ���¼����ӵ�¼���Cookie��
		httpGet.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader(
				"Cookie",
				"safedog-flow-item=2D1BF001984381DB9F4F5C2685FE66A6; UM_distinctid=164c9c710b519b-0b7f63e306181a-5e4f2b18-1fa400-164c9c710b67a1; bdshare_firstime=1532393623875; CNZZDATA5427419=cnzz_eid%3D1583836901-1532393313-%26ntime%3D1532436775");
		httpGet.setHeader("Host", "www.qqenglish.com");
		httpGet.setHeader("Upgrade-Insecure-Requests", "1");
		httpGet.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

		try {
			// �ͻ���ִ��httpGet������������Ӧ
			CloseableHttpResponse closeableHttpResponse = closeableHttpClient
					.execute(httpGet);

			// �õ�������Ӧ״̬��
			if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(closeableHttpResponse.getEntity()
								.getContent(), "utf-8"));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				Document doc = Jsoup.parse(response.toString(), "utf-8");

				Elements masthead = doc.select("p");// class����masthead��div��ǩ

				for (int i = 2; i < masthead.size() - 1; i = i + 2) {
					String EnglishText = masthead.get(i).toString();
					String ChineseText = masthead.get(i + 1).toString();
					int stridx = EnglishText.indexOf("<p>");
					int endidx = EnglishText.indexOf("</p>");
					String string = EnglishText.substring(stridx + 3, endidx);
					System.out.println(string);
				}
				reader.close();
				closeableHttpResponse.close();
				closeableHttpClient.close();

			} else {
				System.out.println(Url);
			}
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
