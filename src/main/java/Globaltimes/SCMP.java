package Globaltimes;
 
import java.io.IOException; 
import java.sql.SQLException; 
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import LiberyUtils.HttpClient;
import LiberyUtils.LiberyCache; 

public class SCMP {
	public static void main(String[] args) throws Exception {
		List<String> allUrlList = new ArrayList<String>();
		String searchWord = "hong+kong+protest";
		String fileName = "E:\\数据分析\\urlList\\SCMP_"+searchWord+".txt";
		for (int id = 1; id < 999999; id++) { 
			String  Url = "https://search-proquest-com.lib-ezproxy.hkbu.edu.hk/internationalnews1/results/6079C8BAF173497DPQ/"+id+"?accountid=11440#scrollTo";
			System.out.println("开始获取url："+Url);
			List<String> urlList= GetChineseAndEnglish(Url);
			urlList = GetIsHadSameUrl(urlList,allUrlList);
			if (urlList.size()<=0) {
				break;
			}
			LiberyCache.appendStringToFile(fileName, urlList); 
			LiberyCache.spiderSleep(15000, 15000); 
		}
	} 

	public static List<String> GetChineseAndEnglish(String Url) throws InterruptedException, SQLException, IOException {
		List<String> urlList = new ArrayList<String>();
		Map<String, String> mapRequestProperty = new HashMap<String, String>();
		mapRequestProperty.put("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		mapRequestProperty.put("Accept-Encoding", "gzip, deflate, br");
		mapRequestProperty.put("Accept-Language", "zh-CN,zh;q=0.9");
		mapRequestProperty.put("Connection", "keep-alive");
		mapRequestProperty.put("Cookie",
				"authenticatedBy=IP; _vwo_uuid_v2=DC023D0F68EC85A8D681A9709C6BB5A76|822db4d3b1c94159f60405d3e3c3c050; _vwo_uuid=DC023D0F68EC85A8D681A9709C6BB5A76; _vwo_ds=3%3Aa_0%2Ct_0%3A0%241572354617%3A81.8492431%3A%3A%3A340_0%2C305_0%3A0; _ga=GA1.3.377517271.1572354620; _ga=GA1.5.377517271.1572354620; fulltextShowAll=YES; _vis_opt_exp_358_combi=1; OS_PERSISTENT=\"mxU/A1V+VhQ0rHO7F9yK/i7gyYV8nIsIZLVHYdHKMxA=\"; ezproxy=2XIIE81WRx56EM9; JSESSIONID=4A4581E4C7E86B62A1BBD5082EA0B589.i-0bb81728c07dcfae6; OS_VWO_COUNTRY=HK; OS_VWO_INSTITUTION=11440; OS_VWO_LANGUAGE=eng; OS_VWO_MY_RESEARCH=false; OS_VWO_REFERRING_URL=N; OS_VWO_REQUESTED_URL=\"https://search.proquest.com/internationalnews1/docview/2216778506/37C20B1356C24441PQ/1001?accountid=11440\"; OS_VWO_VISITOR_TYPE=returning; AWSELB=211F3B2F14E486D8303965AD2DFCA61E745238FF8279BD8E274BDDFE9FB1EB942065584C15C0B0480FECA41398E2931AAC3CF8B1F18F7300B01ABB00A8577AE72114A051709C75CF956595BED427C4233754F4162D; AppVersion=r20191.9.0.272.4209; oneSearchTZ=480; _vis_opt_s=2%7C; _vis_opt_test_cookie=1; _gid=GA1.3.923841031.1572615889; _gid=GA1.5.923841031.1572615889; _vwo_sn=265601%3A2; osTimestamp=1572620227.054; _gat_UA-61126923-3=1; availability-zone=us-east-1a");
		mapRequestProperty.put("Referer", "https://search-proquest-com.lib-ezproxy.hkbu.edu.hk/internationalnews1/results/18AAF35E574742E7PQ/1?accountid=11440");
		mapRequestProperty.put("Host", "search-proquest-com.lib-ezproxy.hkbu.edu.hk");
		mapRequestProperty.put("Sec-Fetch-Mode", "navigate");
		mapRequestProperty.put("Sec-Fetch-Site", "none");
		mapRequestProperty.put("Sec-Fetch-User", "?1");
		mapRequestProperty.put("Upgrade-Insecure-Requests", "1");
		mapRequestProperty.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586");
		String result = HttpClient.HttpsGetString(Url, mapRequestProperty, "UTF-8", false); 
		Document doc = Jsoup.parse(result, "UTF-8");
		Elements memoElements = doc.getElementsByClass("resultHeader");
		 if (memoElements.size()>0) {
			 for (Element element : memoElements) {
				Elements esElements = element.select("a");
				String url = "";
				String tltle = "";
				if (esElements.size()>0) {
					  tltle = esElements.get(0).attr("title");
					  url = esElements.get(0).attr("href");
				}
				Elements Elements = element.getElementsByClass("titleAuthorETC");
				String author = "";
				String timeString= "";
				if (Elements.size()>0) {
					author = Elements.get(0).text();
					timeString = Elements.get(1).text();
				}
				String resultString=tltle+"_"+timeString +"_"+author +"_"+ url;
				urlList.add(resultString); 
			} 
		}
		return urlList;
	}
	private static List<String> GetIsHadSameUrl(List<String> urlList, List<String> allUrlList) {
		List<String> resultList=new ArrayList<String>();
		for (String string : urlList) {
			if (!allUrlList.contains(string)) {
				resultList.add(string);
				allUrlList.add(string);
			}
		}
		return resultList;
	}
}