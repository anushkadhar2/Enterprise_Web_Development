import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;


public class Buy extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//Get the values from the form
			String productName = "";
			String imageLocation = " ";
			int productPrice = 0;
			
			//Get the product selected
			if (request.getParameter("XBox_Original") != null)
			{
				productName = "XBox_Original";
				imageLocation = "images/img_XBoxOriginal.jpg";
				
				productPrice = 80;
			}
			else if (request.getParameter("XBox_360") != null)
			{
				productName = "XBox_360";
				imageLocation = "images/img_XBox360.jpg";
				productPrice = 300;
			}
			/*
			else if (request.getParameter("XBox_One") != null)
			{
				productName = "X Box One";
				imageLocation = "images/img_XBoxOne.jpg";
				productPrice = 500;
			}
			
			else if (request.getParameter("PlayStation_2") != null)
			{
				productName = "PlayStation 2";
				imageLocation = "images/img_PlayStation2.jpg";
				productPrice = 60;
			}
			*/
			else if (request.getParameter("PlayStation_3") != null)
			{
				productName = "PlayStation_3";
				imageLocation = "images/img_PlayStation3.jpg";
				productPrice = 220;
			}
			else if (request.getParameter("PlayStation_4") != null)
			{
				productName = "PlayStation_4";
				imageLocation = "images/img_PlayStation4.jpg";
				productPrice = 400;
			}
			else if (request.getParameter("Wii") != null)
			{
				productName = "Wii";
				imageLocation = "images/img_wii1.jpg";
				productPrice = 250;
			}
			else if (request.getParameter("Wii_U") != null)
			{
				productName = "Wii_U";
				imageLocation = "images/img_wiiu.jpg";
				productPrice = 480;
		    }
		
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myReviews = db.getCollection("myReviews");
			// Find and display 
			BasicDBObject searchQuery = new BasicDBObject();
			//searchQuery.put(productName, imageLocation);

			DBCursor cursor = myReviews.find(searchQuery);
			
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head><title>Buy</title> </head>");
			out.println("<body>");	
			out.println("<h1>Place Order</h1>");	
					
					
			out.println("");
			//out.println("<td> </td>");
			out.println("<h3>" +productName+ "</h3>");
			out.println("<form method='get' action='SubmitOrder'>");
			out.println("<fieldset>");
			
			out.println("<legend>Product information:</legend>");
			out.println("<img src= "+imageLocation+" width = '200' height = '200' alt = 'Product Image'>");
			out.println("<table>");
			out.println("<tr>");
			out.println("<td> Product Name: </td><td><input type='text' name='productName' value="+productName+"></td></tr>");
			//out.println("<td>"+productName+"</td></tr>");
			out.println("<tr><td> Product Price: </td><td> <input type='text' name='productPrice' value="+productPrice+" readonly> </td></tr>");
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
				
					
		
		} catch (MongoException e) {
				e.printStackTrace();
		}
	}

	
	public void destroy(){
      // do nothing.
	}
}