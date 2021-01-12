package Elegislation;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import LiberyUtils.HttpClient;
import LiberyUtils.LiberyCache;

public class elegislation_gov_hkGetUrl {
	public static void main(String[] args) throws KeyManagementException, Exception {
		String Url = "https://www.elegislation.gov.hk/grid";
		String BasePath = "E:\\数据分析\\Elegislation\\";
		String UrlSavePath = BasePath + "Url\\";
		List<String> GrounpList = new ArrayList<String>();
		GrounpList.add("1-100");
		GrounpList.add("101-200");
		GrounpList.add("201-300");
		GrounpList.add("301-400");
		GrounpList.add("401-500");
		GrounpList.add("501-600");
		GrounpList.add("601-700");
		GrounpList.add("1001-1100");
		GrounpList.add("1101-1200");
		GrounpList.add("0");
		for (String GROUPING : GrounpList) {
			System.out.println("Begin GROUPING:  " + GROUPING);
			String fileName = UrlSavePath + GROUPING + ".txt";
			System.out.println(fileName);
			File file = new File(UrlSavePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			List<String> urlList = GetUrlList(Url, GROUPING);
			LiberyCache.WriteStringToFile(fileName, urlList);
		}
	}

	private static List<String> GetUrlList(String url, String grouping) throws KeyManagementException, Exception {
		int Page = 1;
		int LastPage = 1;
		List<String> UrlList = new ArrayList<String>();
		Map<String, String> mapRequestProperty = new HashMap<String, String>();
		mapRequestProperty.put("Accept", "application/json, text/javascript, */*; q=0.01");
		mapRequestProperty.put("Accept-Encoding", "gzip, deflate, br");
		mapRequestProperty.put("Accept-Language", "zh-CN,zh;q=0.9");
		mapRequestProperty.put("Connection", "keep-alive");
		mapRequestProperty.put("Content-Length", "1266");
		mapRequestProperty.put("Content-Type", "application/json");
		mapRequestProperty.put("Cookie",
				"TaJM6jvsmH6aaGI39F7o7inO=v1KsIxJQSDcIB; CLIENT_URL_FORWARD=UBIeSAUCHhxHERwTWFQDDVEFVFBHWQkFE1pXEERQHRdYXVQDExJeUAcaTBNKX0ZdBA5PAkhWVwlQbGhjdVtaG2lhNi8FRB5lamAjVg4bdCckfyN5dnYNIw==; TaJM6jvsmH6aaGE39F7o7inO=v1LMI1JQSDxFt; SESSION_LOCALE_ATTRIBUTE=en_US; CLIENT_CONFIG_RESULT_ATTRIBUTE=%7B%22isOSSupported%22%3Atrue%2C%22isJvmVersionSupported%22%3Afalse%2C%22isBrowserSupported%22%3Atrue%2C%22isJvmSupported%22%3Afalse%2C%22isOSVersionSupported%22%3Atrue%2C%22isBrowserVersionSupported%22%3Atrue%7D; CLIENT_CONFIG_ATTRIBUTE=%7B%22branchCode%22%3A%2200%22%2C%22jvmVendor%22%3A%22%22%2C%22osVersion%22%3A%22Windows+10%22%2C%22jvmVersion%22%3A%22%22%2C%22browserVersion%22%3A%2278.0%22%2C%22isJavascriptEnabled%22%3Atrue%2C%22browserName%22%3A%22Chrome%22%2C%22userAgent%22%3A%22Mozilla%2F5.0+%28Windows+NT+10.0%3B+Win64%3B+x64%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F78.0.3904.97+Safari%2F537.36%22%2C%22applicationId%22%3A%22RA001%22%2C%22osName%22%3A%22Windows%22%2C%22isJvmEnabled%22%3Afalse%2C%22isCookieEnabled%22%3Atrue%7D; CLIENT_REDIRECT_URL_ATTRIBUTE=https://www.elegislation.gov.hk/client-check; clientCheckStatus=S; fontSize=default; JSWP1=7F5A1DC5EE9C85DF52E90E0F83380428; JSTP2=4099EA07F1CE87F28287CD1E95AAE810");
		mapRequestProperty.put("Host", "www.elegislation.gov.hk");
		mapRequestProperty.put("Origin", "https://www.elegislation.gov.hk");
		mapRequestProperty.put("Referer",
				"https://www.elegislation.gov.hk/index/chapternumber?p0=1&TYPE=1&TYPE=2&TYPE=3&LANGUAGE=E");
		mapRequestProperty.put("Sec-Fetch-Mode", "cors");
		mapRequestProperty.put("Sec-Fetch-Site", "same-origin");
		mapRequestProperty.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");
		mapRequestProperty.put("X-Requested-With", "XMLHttpRequest");
		List<String> alignment = new ArrayList<String>();
		alignment.add("left");
		alignment.add("left");
		alignment.add("center");
		alignment.add("center");
		alignment.add("center");
		List<String> columnWidths = new ArrayList<String>();
		columnWidths.add("15%");
		columnWidths.add("*");
		columnWidths.add("10%");
		columnWidths.add("10%");
		columnWidths.add("10%");
		List<String> columns = new ArrayList<String>();
		columns.add("CAP_NO");
		columns.add("TITLE");
		columns.add("PDF_RTF");
		columns.add("PDF_RTF");
		columns.add("PDF_RTF");
		List<String> controls = new ArrayList<String>();
		controls.add("generateCapNo");
		controls.add("generateTitle");
		controls.add("generatePDFB");
		controls.add("generatePDFE");
		controls.add("generatePDFC");
		List<String> labels = new ArrayList<String>();
		labels.add("Chapter / Instrument No.");
		labels.add("Chapter / Instrument Title");
		labels.add("Bilingual");
		labels.add("English");
		labels.add("Chinese");
		List<String> queryParams = new ArrayList<String>();
		queryParams.add("CAP_NO_FR=");
		queryParams.add("CAP_NO_TO=");
		queryParams.add("TYPE=1");
		queryParams.add("TYPE=2");
		queryParams.add("TYPE=3");
		queryParams.add("TITLE=");
		queryParams.add("STATUS=");
		queryParams.add("LANGUAGE=E");
		queryParams.add("GROUPING=" + grouping); // 主要---------------------
		List<Boolean> sortable = new ArrayList<Boolean>();
		sortable.add(false);
		sortable.add(false);
		sortable.add(false);
		sortable.add(false);
		sortable.add(false);

		JSONObject JasonObj = new JSONObject();
		JasonObj.put("MODE", "1");
		JasonObj.put("alignment", alignment);
		JasonObj.put("checkBoxKey", "");
		JasonObj.put("checkBoxKeyFormat", "");
		JasonObj.put("columnGenerator", new ArrayList<String>());
		JasonObj.put("columnWidths", columnWidths);
		JasonObj.put("columns", columns);
		JasonObj.put("controls", controls);
		JasonObj.put("criteria", new HashMap<String, String>());
		JasonObj.put("csrfToken",
				"Un+6FsG3OHYkapjZRrnQrPr34LQ5Awe3hXSSKlR++ZgpHP5boAGm58XMWPVWOQp7R7cO5HgqFR621OlcthyJOw==");
		JasonObj.put("dataItems", new ArrayList<String>());
		JasonObj.put("dataType", new ArrayList<String>());
		JasonObj.put("defaultSortColumn", "");
		JasonObj.put("defaultSortOrder", "");
		JasonObj.put("footable", true);
		JasonObj.put("format", new ArrayList<String>());
		JasonObj.put("functionId", "ECRS01");
		JasonObj.put("gridBd", "hk.gov.doj.hkel.grid.ecr.LCRS1005GridBd");
		JasonObj.put("gridId", "CHAPTER_NO_INDEX_GRID");
		JasonObj.put("gridIndex", "0");
		JasonObj.put("groupColumn", "");
		JasonObj.put("gsId", "CHAPTER_NO_INDEX_GRID");
		JasonObj.put("link", new ArrayList<String>());
		JasonObj.put("lookup", new ArrayList<String>());
		JasonObj.put("minColumnWidths", new ArrayList<String>());
		JasonObj.put("namespace", "hk.gov.doj.hkel.bd.ecr.ECRS0105Bd");
		JasonObj.put("pkFields", "");
		JasonObj.put("queryId", "CHAPTER_NO_INDEX_GRID_QRY");
		JasonObj.put("queryOrder", "");
		JasonObj.put("labels", labels);
		JasonObj.put("queryParams", queryParams);
		JasonObj.put("requiredRights", new ArrayList<String>());
		JasonObj.put("screenId", "ECRS0105");
		JasonObj.put("sortColRefName", new ArrayList<String>());
		JasonObj.put("sortColumn", "");
		JasonObj.put("sortOrder", "");
		JasonObj.put("sortable", sortable);
		JasonObj.put("template", "");
		while (Page <= LastPage) {
			JasonObj.put("pageNo", String.valueOf(Page)); // 主要----------------
			String PostString = JasonObj.toString();
			String result = "";
			try {
				result = HttpClient.HttpsPostString(url, mapRequestProperty, "utf-8", true, false, PostString);
			} catch (Exception e) {
				System.out.println(e);
				result = HttpClient.HttpsPostString(url, mapRequestProperty, "utf-8", false, false, PostString);
			}
			System.out.println("PostResult: " + result);
			JSONObject jason = new JSONObject(result);
			JSONArray rowData = (JSONArray) jason.get("rowData");
			String dataString = rowData.toString();
			String currentPage = (String) jason.get("currentPage");
			LastPage = (int) jason.get("lastPage");
			System.out.println("LastPage: " + LastPage);
			System.out.println("currentPage: " + currentPage);
			System.out.println("rowData: " + dataString);
			UrlList.add(dataString);
			Page++;
			LiberyCache.spiderSleep(2000, 2000);
		}
		return UrlList;
	}
}
