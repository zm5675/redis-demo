package domain;

public class Good {
	private int id;
    private String GoodTypeName;
    private String GoodName;
    private String GoodCount;
    private String GoodUnitPrice;
    private String Content;
    private String GoodPicUrl;
    private String addDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGoodTypeName() {
		return GoodTypeName;
	}
	public void setGoodTypeName(String goodTypeName) {
		GoodTypeName = goodTypeName;
	}
	public String getGoodName() {
		return GoodName;
	}
	public void setGoodName(String goodName) {
		GoodName = goodName;
	}
	public String getGoodCount() {
		return GoodCount;
	}
	public void setGoodCount(String goodCount) {
		GoodCount = goodCount;
	}
	public String getGoodUnitPrice() {
		return GoodUnitPrice;
	}
	public void setGoodUnitPrice(String goodUnitPrice) {
		GoodUnitPrice = goodUnitPrice;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getGoodPicUrl() {
		return GoodPicUrl;
	}
	public void setGoodPicUrl(String goodPicUrl) {
		GoodPicUrl = goodPicUrl;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
}
