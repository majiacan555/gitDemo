package luceneIK;

public class CEData {
	private String chinesedata;
	private String englishdata;
	private String chinesenlp;
	private String englishnlp;
	private String url;
	private String title;
	private int resultcount;
	private int allcount;
	private int ccount;
	public int getCcount() {
		return ccount;
	}
	public void setCcount(int ccount) {
		this.ccount = ccount;
	}
	public int getEcount() {
		return ecount;
	}
	public void setEcount(int ecount) {
		this.ecount = ecount;
	}
	private int ecount;
	public String getChinesedata() {
		return chinesedata;
	}
	public void setChinesedata(String chinesedata) {
		this.chinesedata = chinesedata;
	}
	public String getEnglishdata() {
		return englishdata;
	}
	public void setEnglishdata(String englishdata) {
		this.englishdata = englishdata;
	}
	public String getChinesenlp() {
		return chinesenlp;
	}
	public void setChinesenlp(String chinesenlp) {
		this.chinesenlp = chinesenlp;
	}
	public String getEnglishnlp() {
		return englishnlp;
	}
	public void setEnglishnlp(String englishnlp) {
		this.englishnlp = englishnlp;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String Title) {
		this.title = Title;
	}
	public int getResultcount() {
		return resultcount;
	}
	public void setResultcount(int resultcount) {
		this.resultcount = resultcount;
	}
	public int getAllcount() {
		return allcount;
	}
	public void setAllcount(int allcount) {
		this.allcount = allcount;
	}
	
	
}
