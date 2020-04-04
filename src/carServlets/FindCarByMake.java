package carServlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import carSalesSystem.Car;


@WebServlet("/FindCarByMake")
public class FindCarByMake extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public FindCarByMake() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(true);
		ArrayList<Car> inventory = (loadInventory());
		ArrayList<Car> makeFound = new ArrayList<Car>();		
		String searchMake = request.getParameter("searchMake");		
		
		for (Car find : inventory) 
		{
			
			if (find.getMake().equals(searchMake)) 
			{
				makeFound.add(find);
			}
		}

		session.setAttribute("makeFound", makeFound);
	
		RequestDispatcher rs = request.getRequestDispatcher("searchMake.jsp");
		rs.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

		public ArrayList<Car> loadInventory(){	
			//Assign variables
			ArrayList<Car> inventory = new ArrayList<Car>();
	        
				//Assign variables
				final String path = "C:\\Users\\lsmlp\\eclipse-workspace\\CarDealershipProject\\WebContent\\InventoryData\\";
				String fileName = path+"inventory.txt";
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

		return inventory;
		}

}
