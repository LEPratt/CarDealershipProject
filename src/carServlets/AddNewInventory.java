package carServlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import carSalesSystem.Car;


@WebServlet("/AddNewInventory")
@MultipartConfig
public class AddNewInventory extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   	
    public AddNewInventory() 
    {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
// Path to store car image file uploaded
	final String imagePath = "C:\\Users\\lsmlp\\eclipse-workspace\\CarDealershipProject\\WebContent\\InventoryData\\carImages\\";
	
// Path to store updated inventory text file
		final String inventoryPath = "C:\\Users\\lsmlp\\eclipse-workspace\\CarDealershipProject\\WebContent\\InventoryData\\";
		final String inventoryFile = inventoryPath+"inventory.txt";
		
//Get image filename combine with path
		Part filePart = request.getPart("file"); 
	    String imageName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	    final String imageFile = imagePath+imageName; 
	    
// Create Car object to add to inventory text file	    
		Car add = new Car(				
				request.getParameter("purchaseDate"), 
				imageName,
				request.getParameter("year"), 
				request.getParameter("make"), 
				request.getParameter("model"), 
				request.getParameter("vin"), 
				request.getParameter("mileage"),
				request.getParameter("askingPrice"), 
				request.getParameter("description"));
		
		
		
// Save the image file to carImages folder
		OutputStream out = null;
	    InputStream fileContent = null;
	    final PrintWriter writer = new PrintWriter(new File(imageFile));

	    try {
	        out = new FileOutputStream(new File(imageFile));
	        fileContent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = fileContent.read(bytes)) != -1) 
	        {
	            out.write(bytes, 0, read);	         
	        }    
    		request.setAttribute("message1","Image Uploaded");	   	

	    	} 
	    	catch (FileNotFoundException fne) 
	    	{	    	
	    		request.setAttribute("message1","file not found");	    	
	    	} 
	    
	  //Add new car to the text file        		

	    HttpSession session=request.getSession(true);
	    @SuppressWarnings("unchecked")
		ArrayList<Car> inventory=(ArrayList<Car>) session.getAttribute("inventory");
		InventoryLoader temp = new InventoryLoader();

	    
	    inventory.add(add);
		inventory.sort(temp.new purchaseDateSorter());
	    session.setAttribute("inventory", inventory);	
	    
	  		try {	 
	  	    FileWriter fileWriter = new FileWriter(inventoryFile);
	  	    PrintWriter printWriter = new PrintWriter(fileWriter);
	  	    	for(Car car:inventory) {			
	  			printWriter.println(car.toStringForFile());
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
	       
	    if (out != null) 
	    {
	       out.close();
	    }
	    if (fileContent != null) 
	    {
	        fileContent.close();           
	    }
	    if (writer != null) 
	    {
	        writer.close();
	    }
		
		RequestDispatcher rs = request.getRequestDispatcher("results.jsp");
		rs.forward(request, response);	    
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
