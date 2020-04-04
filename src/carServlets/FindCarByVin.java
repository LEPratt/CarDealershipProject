package carServlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import carSalesSystem.Car;
import carServlets.InventoryLoader.purchaseDateSorter;

@WebServlet("/FindCarByVin")
public class FindCarByVin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FindCarByVin() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session=request.getSession(true);
		ArrayList<Car> inventory = (loadInventory());
		
		String vin = request.getParameter("vin");		
		Car tempCar = new Car();
		for (Car find : inventory) 
		{
			
			if (find.getVin().equals(vin)) 
			{
				tempCar=find;
				Date today = new Date();
				Calendar cal = new GregorianCalendar();
				cal.setTime(today);
				cal.add(Calendar.DAY_OF_MONTH, -120);
				Date over120 = cal.getTime();
			
				Date checkDate = null;
				try {
					checkDate = new SimpleDateFormat("yyyy-MM-dd").parse(find.purchaseDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				 if(checkDate.before(over120)){
			           session.setAttribute("bid", "true");
			       }else {session.setAttribute("bid", "false");
}
				break;
			}
		}

		session.setAttribute("car", tempCar);

		RequestDispatcher rs = request.getRequestDispatcher("moreInfo.jsp");
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
