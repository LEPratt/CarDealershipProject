package carServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import carSalesSystem.Car;

@WebServlet("/CheckBid")
public class CheckBid extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public CheckBid() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		double bidAmount = Double.parseDouble(request.getParameter("bidAmount"));
		double price = Double.parseDouble(request.getParameter("askingPrice"));
		
		if(bidAmount>=(price*.9)){
			request.setAttribute("price", bidAmount);
			request.setAttribute("message3","Bid Accepted, Procede to Purchase");
		}else{
			request.setAttribute("price", price);
			request.setAttribute("message3","Bid Not Accepted, Please Place a Higher Bid");
			}


		RequestDispatcher rs = request.getRequestDispatcher("placeBid.jsp");
		rs.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
