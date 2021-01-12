package TransLationModel;

public class CEDataNLP2 {
	private int Id;
	private int DataId;
	private String ChineseData;
	private String ChineseNLP;
	private String EnglishData;
	private String EnglishNLP;
	private String URL;
	private String title;
	private int urlType;  //对应哪一种数据来源（文章类型）  
	private int titleID;
	private String Memo;
	private String Memo2;
	private String Memo3;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getDataId() {
		return DataId;
	}
	public void setDataId(int dataId) {
		DataId = dataId;
	}
	public String getChineseData() {
		return ChineseData;
	}
	public void setChineseData(String chineseData) {
		ChineseData = chineseData;
	}
	public String getChineseNLP() {
		return ChineseNLP;
	}
	public void setChineseNLP(String chineseNLP) {
		ChineseNLP = chineseNLP;
	}
	public String getEnglishData() {
		return EnglishData;
	}
	public void setEnglishData(String englishData) {
		EnglishData = englishData;
	}
	public String getEnglishNLP() {
		return EnglishNLP;
	}
	public void setEnglishNLP(String englishNLP) {
		EnglishNLP = englishNLP;
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
	public String getMemo() {
		return Memo;
	}
	public void setMemo(String memo) {
		Memo = memo;
	}
	public String getMemo2() {
		return Memo2;
	}
	public void setMemo2(String memo2) {
		Memo2 = memo2;
	}
	public String getMemo3() {
		return Memo3;
	}
	public void setMemo3(String memo3) {
		Memo3 = memo3;
	}

}
