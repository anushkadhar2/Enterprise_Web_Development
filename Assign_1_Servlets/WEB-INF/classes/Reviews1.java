import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

public class Reviews1 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
     // Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try{
		String productModelName = "";
		String imageLocation = " ";
		String productCatagory = "";
		double productPrice = 0.0;
		String retailerName = "";
		String retailerZip = "";
		String retailerCity = "";
		String retailerState = "";
		String productOnSale = "";
		String manufacturarName = "";
		String manufacturarRebate = "";
		
		//Get the selected product details
		if (request.getParameter("XBox_Original") != null){
			productModelName = "X_Box_Original";
			imageLocation = "images/img_XBoxOriginal.jpg";
			productCatagory = "Gaming Console";
			productPrice = 80;
			retailerName = "GameSpeed";
			retailerZip = "01818";
			retailerCity = "Boston";
			retailerState = "MA";
			productOnSale = "yes";
			manufacturarName = "Hasbro";
			manufacturarRebate = "no";
		}else if (request.getParameter("XBox_360") != null){
			productModelName = "X_Box_360";
			imageLocation = "images/img_XBox360.jpg";
			productCatagory = "Gaming Console";
			productPrice = 300;
			retailerName = "Gamers";
			retailerZip = "60616";
			retailerCity = "Chicago";
			retailerState = "IL";
			productOnSale = "no";
			manufacturarName = "Kosmos";
			manufacturarRebate = "yes";
		}else if (request.getParameter("XBox_One") != null){
			productModelName = "X Box One";
			imageLocation = "images/img_XBoxOne.jpg";
			productCatagory = "Gaming Console";
			productPrice = 500;
			retailerName = "GameSpeed";
			retailerZip = "01818";
			retailerCity = "Boston";
			retailerState = "MA";
			productOnSale = "no";
			manufacturarName = "Hasbro";
			manufacturarRebate = "yes";
		}else if (request.getParameter("PlayStation_2") != null){
			productModelName = "PlayStation 2";
			imageLocation = "images/img_PlayStation2.jpg";
			productCatagory = "Gaming Console";
			productPrice = 60;
			retailerName = "Gamers";
			retailerZip = "60616";
			retailerCity = "Chicago";
			retailerState = "IL";
			productOnSale = "no";
			manufacturarName = "Kosmos";
			manufacturarRebate = "no";
		}else if (request.getParameter("PlayStation_3") != null){
			productModelName = "PlayStation 3";
			imageLocation = "images/img_PlayStation3.jpg";
			productCatagory = "Gaming Console";
			productPrice = 220;
			retailerName = "PinkGorilla";
			retailerZip = "92012";
			retailerCity = "NewYork";
			retailerState = "NY";
			productOnSale = "no";
			manufacturarName = "MatrixGames";
			manufacturarRebate = "no";
		}else if (request.getParameter("PlayStation_4") != null){
			productModelName = "PlayStation 4";
			imageLocation = "images/img_PlayStation4.jpg";
			productCatagory = "Gaming Console";
			productPrice = 400;
			retailerName = "PinkGorilla";
			retailerZip = "92012";
			retailerCity = "NewYork";
			retailerState = "NY";
			productOnSale = "no";
			manufacturarName = "MatrixGames"; 
		    manufacturarRebate = "no";
		}
		else if (request.getParameter("Wii") != null){
			productModelName = "Wii";
			imageLocation = "images/Wii.jpg";
			productCatagory = "Gaming Console";
			productPrice = 350;
			retailerName = "GameSpeed";
			retailerZip = "01818";
			retailerCity = "Boston";
			retailerState = "MA";
			productOnSale = "yes";
			manufacturarName = "Hasbro";
			manufacturarRebate = "no";
		}
		else if (request.getParameter("WiiU") != null){
			productModelName = "PlayStation 4";
			imageLocation = "images/Wiiu.jpg";
			productCatagory = "Gaming Console";
			productPrice = 360;
			retailerName = "Gamers";
			retailerZip = "60616";
			retailerCity = "Chicago";
			retailerState = "IL";
			productOnSale = "no";
			manufacturarName = "Kosmos";
			manufacturarRebate = "no";
		}
		
		// if database doesn't exists, MongoDB will create it for you
		DB db = mongo.getDB("CSP595Tutorial");
			
		DBCollection myReviews = db.getCollection("myProducts");
			
		// Find and display 
		BasicDBObject searchQuery = new BasicDBObject();
			
		//searchQuery.put(productName, imageLocation);
		DBCursor cursor = myReviews.find(searchQuery);
		
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head><title>Product Reviews</title> </head>");
		out.println("<body>");	
		out.println("<h1>Product Reviews</h1>");	
					
					
		out.println("");
		out.println("<h3>" +productModelName+ "</h3>");
		out.println("<form method='get' action='SubmitOrder'>");
		out.println("<fieldset>");
		out.println("<legend>Product information:</legend>");
		out.println("<img src= "+imageLocation+" width = '200' height = '200' alt = 'Product Image'>");
		out.println("<table>");
		
		out.println("<tr>");
		out.println("<td> Product Model Name: </td>");
		out.println("<td> <input type='text' name='productName' value="+productModelName+" readonly> </td></tr>");
		out.println("<tr>");
		out.println("<td> Product Category: </td>");
		out.println("<td> <input type='text' name='productCatagory' value="+productCatagory+" readonly> </td></tr>");
		out.println("<tr><td> Product Price: </td>");
		out.println("<td> <input type='text' name='productPrice' value="+productPrice+" readonly> </td></tr>");
		out.println("<tr><td> Retailer Name: </td>");
		out.println("<td> <input type='text' name='productCatagory' value="+retailerName+" readonly> </td></tr>");
		out.println("<tr><td> Retailer Zip: </td>");
		out.println("<td> <input type='text' name='productCatagory' value="+retailerZip+" readonly> </td></tr>");
		out.println("<tr><td> Retailer City: </td>");
		out.println("<td> <input type='text' name='productCatagory' value="+retailerCity+" readonly> </td></tr>");
		out.println("<tr><td> Retailer State: </td>");
		out.println("<td> <input type='text' name='productCatagory' value="+retailerState+" readonly> </td></tr>");
		out.println("<tr><td> Product On Sale: </td>");
		out.println("<td> <input type='text' name='productCatagory' value="+productOnSale+" readonly> </td></tr>");
		out.println("<tr><td> Manufacturer Name: </td>");
		out.println("<td> <input type='text' name='productCatagory' value="+manufacturarName+" readonly> </td></tr>");
		out.println("<tr><td> Manufacturer Rebate: </td>");
		out.println("<td> <input type='text' name='productCatagory' value="+manufacturarRebate+" readonly> </td></tr>");
		out.println("</table></fieldset>");
		out.println("<fieldset>");
		out.println("<legend>Personal information:</legend>");
		out.println("<table>");
		out.println("<tr><td> First name: </td><td> <input type='text' name='firstName'> </td></tr>");
		out.println("<tr><td> Last name: </td><td> <input type='text' name='lastName'> </td></tr>");
		out.println("<tr><td> Address: </td><td> <input type='text name='address> </td></tr>");
		out.println("<tr><td> Phone: </td><td> <input type='text' name='phoneNumber'> </td></tr>");
		out.println("</table>");
		out.println("<br><br>");
		out.println("<input type='submit' value='Place Order'>");
		out.println("</fieldset>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}			
	catch (MongoException e) {
			e.printStackTrace();
	} 
	}

	
	public void destroy(){
      // do nothing.
	}
}