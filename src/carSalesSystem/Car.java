package carSalesSystem;

public class Car
{
	//Assign class variables	
	public String purchaseDate;
	public String imageName;
	public String year;
	public String make;
	public String model;
	public String vin;
	public String mileage;
	public String askingPrice;	
	public String description;
	
	//Default Constructor
	public Car() 
	{
	}
	
	// Parameterized Constructors
	public Car(String purchaseDate, String imageName, String year, String make, String model, String vin, String mileage, String askingPrice, String description) {
		this.purchaseDate = purchaseDate;
		this.imageName = imageName;
		this.year = year;
		this.make = make;
		this.model = model;
		this.vin = vin;
		this.mileage = mileage;
		this.askingPrice = askingPrice;		
		this.description = description;
	}
	

	//Getters and setters
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate)  {
		this.purchaseDate = purchaseDate;		
	}

	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}	

	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}	

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getAskingPrice() {
		return askingPrice;
	}
	public void setAskingPrice(String askingPrice) {
		this.askingPrice = askingPrice;
	}	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toStringForFile() {
		return purchaseDate+"  ::  "+imageName+"  ::  "+year+"  ::  "+make+"  ::  "+model+"  ::  "+vin+"  ::  "+mileage+"  ::  "+askingPrice+"  ::  "+description;
	}

	@Override
	public String toString() {
		return "Purchase Date=" + purchaseDate + ", Year=" + year + ", Make=" + make + ", Model=" + model + ", VIN="
				+ vin + ", Mileage=" + mileage + ", AskingPrice=" + askingPrice;
	}

	
	
	

}
