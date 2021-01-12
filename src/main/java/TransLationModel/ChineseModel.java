package TransLationModel;

public class ChineseModel {
	private int ChineseId;
	private String ChineseData;
	private String URL;
	private String title;
	private String Memo;
	private int urlType;
	private int titleID;
	public int getChineseId() {
		return ChineseId;
	}
	public void setChineseId(int chineseId) {
		ChineseId = chineseId;
	}
	public String getChineseData() {
		return ChineseData;
	}
	public void setChineseData(String chineseData) {
		ChineseData = chineseData;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String url) {
		URL = url;
	}
	public String getMemo() {
		return Memo;
	}
	public void setMemo(String memo) {
		Memo = memo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getUrlType() {
		return urlType;
	}
	public void setUrlType(int urlType) {
		this.urlType = urlType;
	}
	public int getTitleID() {
		return titleID;
	}
	public void setTitleID(int titleID) {
		this.titleID = titleID;
	}
	

}
