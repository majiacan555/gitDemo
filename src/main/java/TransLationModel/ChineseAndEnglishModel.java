package TransLationModel;

public class ChineseAndEnglishModel {
	private int Id;
	private String ChineseData;
	private String EnglishData;
	private String URL;
	private String title;
	private String Memo;
	private int urlType;  //对应哪一种数据来源（文章类型）  
	private int titleID;  //同属一篇文章的为相同titleID
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getChineseData() {
		return ChineseData;
	}
	public void   setChineseData(String chineseData) {
		ChineseData = chineseData;
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
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMemo() {
		return Memo;
	}
	public void setMemo(String memo) {
		Memo = memo;
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
