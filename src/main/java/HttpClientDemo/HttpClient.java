package HttpClientDemo;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class HttpClient {
    public static void main(String[] args){
        //�����ͻ���
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //��������Getʵ��
        HttpGet httpGet = new HttpGet("https://www.baidu.com");

        //���ͷ����Ϣģ�����������
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml," +
                "application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36" +
                " (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

        try {
            //�ͻ���ִ��httpGet������������Ӧ
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);

            //�õ�������Ӧ״̬��
            if(closeableHttpResponse.getStatusLine().getStatusCode() == 200) {  
                //�õ���Ӧʵ��
                String entity = EntityUtils.toString (closeableHttpResponse.getEntity(), "utf-8");
                System.out.println(entity);
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
}
