package carSalesSystem;


public class SaleDetails 
{	
//Assign variables
	public String saleDate;
	public String buyerName;
	public String salePrice;
	public Car car = new Car();
	
	
//Default Constructor
	public SaleDetails() 
	{
	}
//Field Constructor
	
	public SaleDetails(String saleDate, String buyerName, String salePrice, Car car) {
		super();
		this.saleDate = saleDate;
		this.buyerName = buyerName;
		this.salePrice = salePrice;
		this.car = car;
	}

	
//Getters and setters
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}

	
	@Override
	public String toString() {
		return "SaleTransactionDetails [saleDate=" + saleDate + ", buyerName=" + buyerName + ", salePrice=" + salePrice
				+ ", car=" + car + "]";
	}
	
	public String toStringForFile() {
		return saleDate + "  ::  " + buyerName + "  ::  " + salePrice
				+ "  ::  " + car.toStringForFile();
	}

	
	
}
