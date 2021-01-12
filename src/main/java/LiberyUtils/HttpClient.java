package LiberyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class HttpClient {
	public static String HttpsGetString(String Url, Map<String, String> mapRequestProperty, String strType,
			boolean isGizp) throws IOException {
		String proxyHost = "127.0.0.1";
		String proxyPort = "10809";
//		System.setProperty("http.proxyHost", proxyHost);
//		System.setProperty("http.proxyPort", proxyPort); 
//		// 对https也开启 代理
//		System.setProperty("https.proxyHost", proxyHost);
//		System.setProperty("https.proxyPort", proxyPort);
		String resultString = "";
//		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 10809);
//        Proxy proxy = new Proxy(Proxy.Type.SOCKS, address); // http代理协议类型
		URL serverUrl = new URL(Url);
		HttpsURLConnection conn = (HttpsURLConnection) serverUrl.openConnection();
		conn.setRequestMethod("GET");
		for (String key : mapRequestProperty.keySet()) {
			String value = mapRequestProperty.get(key);
			conn.setRequestProperty(key, value);
		} 
		conn.setInstanceFollowRedirects(false);
		conn.connect();
		StringBuffer buffer = new StringBuffer();
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = null;
			if (isGizp) {
				GZIPInputStream gis = new GZIPInputStream(inputStream);
				inputStreamReader = new InputStreamReader(gis, strType);
			} else {
				inputStreamReader = new InputStreamReader(inputStream, strType);
			}
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			resultString = buffer.toString();
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
		}
		conn.disconnect();
		return resultString;
	}
	public static String HttpsPostString(String Url, Map<String, String> mapRequestProperty, String strType,
			boolean isGizp) throws IOException {
		String proxyHost = "127.0.0.1";
		String proxyPort = "10809";
		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyPort); 
		// 对https也开启 代理
		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", proxyPort);
		String resultString = "";
//		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 10809);
//        Proxy proxy = new Proxy(Proxy.Type.SOCKS, address); // http代理协议类型
		URL serverUrl = new URL(Url);
		HttpsURLConnection conn = (HttpsURLConnection) serverUrl.openConnection();
		conn.setDoOutput(true);
		//如果打算使用 URL 连接进行输入，则将 DoInput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 true
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		for (String key : mapRequestProperty.keySet()) {
			String value = mapRequestProperty.get(key);
			conn.setRequestProperty(key, value);
		} 
		conn.setInstanceFollowRedirects(false);
		conn.connect();
		StringBuffer buffer = new StringBuffer();
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = null;
			if (isGizp) {
				GZIPInputStream gis = new GZIPInputStream(inputStream);
				inputStreamReader = new InputStreamReader(gis, strType);
			} else {
				inputStreamReader = new InputStreamReader(inputStream, strType);
			}
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			resultString = buffer.toString();
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
		}
		conn.disconnect();
		return resultString;
	}

	public static String HttpGetString(String Url, Map<String, String> mapRequestProperty, String strType)
			throws IOException {
		String resultString = "";
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(Url);
		for (String key : mapRequestProperty.keySet()) {
			String value = mapRequestProperty.get(key);
			httpGet.setHeader(key, value);
		}
		CloseableHttpResponse closeableHttpResponse;
		closeableHttpResponse = closeableHttpClient.execute(httpGet);
		if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(closeableHttpResponse.getEntity().getContent(),strType));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			resultString = response.toString();
			reader.close();
		}
		closeableHttpResponse.close();
		closeableHttpClient.close();
		return resultString;
	}

}
