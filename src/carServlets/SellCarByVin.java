package carServlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import carSalesSystem.Car;
import carSalesSystem.SaleDetails;

@WebServlet("/SellCarByVin")
public class SellCarByVin extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SellCarByVin() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//Path to store the sale data text file
				final String salePath = "C:\\Users\\lsmlp\\eclipse-workspace\\CarDealershipProject\\WebContent\\SaleData\\";
				final String saleFile = salePath+"sales.txt";
				// Path to store updated inventory text file
				final String inventoryPath = "C:\\Users\\lsmlp\\eclipse-workspace\\CarDealershipProject\\WebContent\\InventoryData\\";
				final String inventoryFile = inventoryPath+"inventory.txt";
				// Path to store car image file uploaded
				final String imagePath = "C:\\Users\\lsmlp\\eclipse-workspace\\CarDealershipProject\\WebContent\\InventoryData\\carImages\\";
				

		HttpSession session=request.getSession(true);
		 @SuppressWarnings("unchecked")
		ArrayList<Car> inventory = (ArrayList<Car>) session.getAttribute("inventory");
		 @SuppressWarnings("unchecked")
		ArrayList<SaleDetails> sales = (ArrayList<SaleDetails>)session.getAttribute("sales");
		 InventoryLoader temp = new InventoryLoader();
		
		String vin = request.getParameter("vin");

		for (Car find : inventory) 
		{			
			if (find.getVin().equals(vin)) 
			{
				//Record the sale
				
		// Create sale sale details object to add to sale text file	    
				SaleDetails sold = new SaleDetails(				
						request.getParameter("saleDate"), 
						request.getParameter("buyerName"), 
						request.getParameter("askingPrice"), 
						find);
				
			  //Add new sale to the text file        		

			    sales.add(sold);
				sales.sort(temp.new  saleDateSorter());
			    session.setAttribute("sales", sales);	
			    
			  		try {	 
			  	    FileWriter fileWriter = new FileWriter(saleFile);
			  	    PrintWriter printWriter = new PrintWriter(fileWriter);
			  	    	for(SaleDetails sale:sales) {			
			  			printWriter.println(sale.toStringForFile());
			  	    	}
			  	    	    printWriter.close();
			  	    		request.setAttribute("message2","Inventory Updated");   	
			  	    }
			  	    catch(FileNotFoundException e) 
			  		{
			    		request.setAttribute("message2","ERROR: Inventory file not found");
			  		} catch (IOException e) {
			    		request.setAttribute("message2","ERROR: Could not write to inverntor file");
			  		}
				
				//Remove the car from the inventory
				inventory.remove(find);
				File image=new File(imagePath+find.imageName);
				image.delete();
				break;
			}
		}
		try {	 
	  	    FileWriter fileWriter = new FileWriter(inventoryFile);
	  	    PrintWriter printWriter = new PrintWriter(fileWriter);
	  	    	for(Car car:inventory) {			
	  			printWriter.println(car.toStringForFile());
	  	    	}
	  	    	    printWriter.close();
	  	    		request.setAttribute("message2","Inventory Updated");	   	
	  	    		request.setAttribute("message1",""); 
	  	    }
	  	    catch(FileNotFoundException e) 
	  		{
	    		request.setAttribute("message2","ERROR: Inventory file not found");
	  		} catch (IOException e) {
	    		request.setAttribute("message2","ERROR: Could not write to inverntor file");
	  		}

	    session.setAttribute("inventory", inventory);	

		RequestDispatcher rs = request.getRequestDispatcher("results.jsp");
		rs.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	
}
