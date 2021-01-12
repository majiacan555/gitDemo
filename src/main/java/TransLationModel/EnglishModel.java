package TransLationModel;

public class EnglishModel {
	private int EnglishId;
	private int ChineseId;
	private String EnglishData;
	private String URL;
	private String title;
	private String Memo;
	private int urlType;
	private int titleID;
	public int getEnglishId() {
		return EnglishId;
	}
	public void setEnglishId(int englishId) {
		EnglishId = englishId;
	}
	public String getEnglishData() {
		return EnglishData;
	}
	public void setEnglishData(String englishData) {
		EnglishData = englishData;
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
	public int getChineseId() {
		return ChineseId;
	}
	public void setChineseId(int chineseId) {
		ChineseId = chineseId;
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
