package SimHashService;

public class SimHashModel {
	private int NumID;
	private String yChineseData;
	private String yEnglishData;
	private String hChineseData;
	private String hEnglishData;
	private double SimilarPercent;
	public double getSimilarPercent() {
		return SimilarPercent;
	}
	public void setSimilarPercent(double similarPercent) {
		SimilarPercent = similarPercent;
	}
	public int getNumID() {
		return NumID;
	}
	public void setNumID(int num) {
		NumID = num;
	}
	public String getyChineseData() {
		return yChineseData;
	}
	public void setyChineseData(String yChineseData) {
		this.yChineseData = yChineseData;
	}
	public String getyEnglishData() {
		return yEnglishData;
	}
	public void setyEnglishData(String yEnglishData) {
		this.yEnglishData = yEnglishData;
	}
	public String gethChineseData() {
		return hChineseData;
	}
	public void sethChineseData(String hChineseData) {
		this.hChineseData = hChineseData;
	}
	public String gethEnglishData() {
		return hEnglishData;
	}
	public void sethEnglishData(String hEnglishData) {
		this.hEnglishData = hEnglishData;
	}
	
	 
}
