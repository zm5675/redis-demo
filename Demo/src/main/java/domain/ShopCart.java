package domain;

public class ShopCart {
	private int id;
    private String UserID;
    private String UserName;
    private String GoodName;
    private String GoodUnitPrice;
    private String GoodNum;
    private String addDate;
    private String OrderID;
    private String GoodID;

	public String getGoodID() {
		return GoodID;
	}

	public void setGoodID(String goodID) {
		GoodID = goodID;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getGoodName() {
		return GoodName;
	}
	public void setGoodName(String goodName) {
		GoodName = goodName;
	}
	public String getGoodNum() {
		return GoodNum;
	}
	public void setGoodNum(String goodNum) {
		GoodNum = goodNum;
	}
	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getGoodUnitPrice() {
		return GoodUnitPrice;
	}
	public void setGoodUnitPrice(String goodUnitPrice) {
		GoodUnitPrice = goodUnitPrice;
	}
}
