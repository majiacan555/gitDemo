package HttpClientDemo;

/**
 * Created by paranoid on 17-1-19.
 */
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpCLientDemo {

    public static void main (String[] args) throws Exception {
        // ����Ĭ�ϵĿͻ���ʵ��
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // ����get����ʵ��
        HttpGet httpget = new HttpGet("http://www.baidu.com");

        System.out.println("executing request " + httpget.getURI());

        try {
            // �ͻ���ִ��get���󷵻���Ӧ
            CloseableHttpResponse response = httpClient.execute(httpget);

            // ��������Ӧ״̬��
            System.out.println(response.getStatusLine().toString());

            Header[] heads = response.getAllHeaders();
            System.out.println(response.getHeaders("Content-Type"));
            // ��ӡ������Ӧͷ
            for(Header h:heads){
                System.out.println(h.getName()+":"+h.getValue());
            }
        } finally {
            httpClient.close();
        }
    }
}
