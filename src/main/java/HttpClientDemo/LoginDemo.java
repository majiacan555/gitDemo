package HttpClientDemo;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by paranoid on 17-3-27.
 * 
 * ģ���¼������
 *
 * ��ʾ��������ģ���¼֮ǰ��Ҫ���ֶ���¼Ȼ��ͨ��ץ���鿴��¼�ɹ���Ҫ���Է�������������Щ������Ȼ�����ǽ���Щ����������ȡ��ͨ��Post�������͸��Է�������
 */

public class LoginDemo {
    public static void main(String[] args){
        //����Ĭ�Ͽͻ���
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        //����Post����ʵ��
        HttpPost httpPost = new HttpPost("http://www.renren.com/");

        //���������б�
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("domain", "renren.com"));
        nvps.add(new BasicNameValuePair("isplogin", "true"));
        nvps.add(new BasicNameValuePair("submit", "��¼"));
        nvps.add(new BasicNameValuePair("email", ""));
        nvps.add(new BasicNameValuePair("passwd", ""));

        //��Է�����������Post����
        try {
            //���������з�װ���ύ����������
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF8"));
            CloseableHttpResponse httpResponse = closeableHttpClient.execute(httpPost);

            //���ģ���¼�ɹ�
            if(httpResponse.getStatusLine().getStatusCode() == 200) {
                Header[] headers = httpResponse.getAllHeaders();
                for (Header header : headers) {
                    out.println(header.getName() + ": " + header.getValue());
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();      //�ͷ���Դ
        }
    }
}
