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
        // 创建默认的客户端实例
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建get请求实例
        HttpGet httpget = new HttpGet("http://www.baidu.com");

        System.out.println("executing request " + httpget.getURI());

        try {
            // 客户端执行get请求返回响应
            CloseableHttpResponse response = httpClient.execute(httpget);

            // 服务器响应状态行
            System.out.println(response.getStatusLine().toString());

            Header[] heads = response.getAllHeaders();
            System.out.println(response.getHeaders("Content-Type"));
            // 打印所有响应头
            for(Header h:heads){
                System.out.println(h.getName()+":"+h.getValue());
            }
        } finally {
            httpClient.close();
        }
    }
}
