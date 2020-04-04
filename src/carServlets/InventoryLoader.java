package carServlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import carSalesSystem.Car;
import carSalesSystem.SaleDetails;



@WebServlet("/InventoryLoader")
public class InventoryLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//Default constructor
    public InventoryLoader() {}
    
    //doGet statement
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		
		//Assign variables
		ArrayList<Car> inventory = new ArrayList<Car>();
        ArrayList<SaleDetails> sales = new ArrayList<SaleDetails>();
        
			//Path to inventory data text file
			final String path = "C:\\Users\\lsmlp\\eclipse-workspace\\CarDealershipProject\\WebContent\\InventoryData\\";
			String fileName = path+"inventory.txt";
			//Path to sale data text file
			final String salePath = "C:\\Users\\lsmlp\\eclipse-workspace\\CarDealershipProject\\WebContent\\SaleData\\";
			final String saleFile = salePath+"sales.txt";
			
			//Catch exceptions and load inventory
			try 
			{	
				//Read from the file
				Scanner inputEntry = new Scanner(new File(fileName));
					while (inputEntry.hasNextLine()) 
					{
						String info = inputEntry.nextLine();
						
						// Create a string array by separating the string by the pattern "  ::  "
						String[] data = info.split("  ::  ");
						
						// Assign each element to the corresponding data entry for the new car entry
						String purchaseDate = data[0];
						String imageName = data[1];
						String year = data[2];
						String make = data[3];
						String model = data[4];
						String vin = data[5];
						String mileage = data[6];
						String askingPrice = data[7];
						String description = data[8];
						
						
						Car add = new Car(purchaseDate, imageName, year, make, model, vin, mileage, askingPrice, description);										
						inventory.add(add);
						
					}					
			}
			catch(FileNotFoundException e) 
			{
				System.out.println("Error reading from file");
			}
			
			inventory.sort(new purchaseDateSorter());
			
			//Catch exceptions and load sales
			try 
			{	
				//Read from the file
				Scanner inputEntry = new Scanner(new File(saleFile));
					while (inputEntry.hasNextLine()) 
					{
						String info = inputEntry.nextLine();
						
						// Create a string array by separating the string by the pattern "  ::  "
						String[] data = info.split("  ::  ");
						
						// Assign each element to the corresponding data entry for the new car entry
						String saleDate = data[0];
						String buyerName = data[1];
						String salePrice = data[2];
						String purchaseDate = data[3];
						String imageName = data[4];
						String year = data[5];
						String make = data[6];
						String model = data[7];
						String vin = data[8];
						String mileage = data[9];
						String askingPrice = data[10];
						String description = data[11];
						
						
						Car temp = new Car(purchaseDate, imageName, year, make, model, vin, mileage, askingPrice, description);	
						SaleDetails addSale= new SaleDetails(saleDate, buyerName, salePrice, temp);
						sales.add(addSale);
						
					}					
			}
			catch(FileNotFoundException e) 
			{
				System.out.println("Error reading from file");
			}
			
		
		sales.sort(new saleDateSorter());
	
		//Get session and return the updated data to the inventory.jsp
		HttpSession session=request.getSession(true);
		session.setAttribute("inventory", inventory);
		session.setAttribute("sales", sales);
		RequestDispatcher rs = request.getRequestDispatcher("inventory.jsp");
		rs.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public class purchaseDateSorter implements Comparator<Car>{

		@Override
		public int compare(Car o2, Car o1) {
			return o2.getPurchaseDate().compareTo(o1.purchaseDate);
		}
		
	}
	
	public class saleDateSorter implements Comparator<SaleDetails>{

		@Override
		public int compare(SaleDetails o1, SaleDetails o2) {
			return o2.getSaleDate().compareTo(o1.saleDate);
		}
		
	}

}
