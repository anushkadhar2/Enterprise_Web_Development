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


public class buyfromcart extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//Get the values from the form
			String name = request.getParameter("itemName");
			String DiscountedPrice = request.getParameter("price");
			//String imageLocation = " ";
			//int price = 0;
			int Warranty = 2;
			
			//Get the product selected
			/*if (request.getParameter("XBox_Original") != null)
			{
				name = "X_Box_Original";
				//imageLocation = "images/img_XBoxOriginal.jpg";
				price = 80;
				Warranty = 2;
			}
			else if (request.getParameter("XBox_360") != null)
			{
				name = "X Box 360";
				//imageLocation = "images/img_XBox360.jpg";
				price = 300;
				Warranty = 1;
			}
			else if (request.getParameter("XBox_One") != null)
			{
				name = "X Box One";
				//imageLocation = "images/img_XBoxOne.jpg";
				price = 500;
				Warranty = 3;
			}
			else if (request.getParameter("PlayStation_2") != null)
			{
				name = "PlayStation 2";
				//imageLocation = "images/img_PlayStation2.jpg";
				price = 60;
				Warranty = 2;
			}
			else if (request.getParameter("PlayStation_3") != null)
			{
				name = "PlayStation 3";
				//imageLocation = "images/img_PlayStation3.jpg";
				price = 220;
				Warranty = 2;
			}
			else if (request.getParameter("PlayStation_4") != null)
			{
				name = "PlayStation 4";
				//imageLocation = "images/img_PlayStation4.jpg";
				price = 400;
				Warranty = 2;
				
			}
			else if (request.getParameter("wii") != null)
			{
				name = "Wii";
				//imageLocation = "images/wii.jpg";
				price = 250;
				Warranty = 2;
			}
			else if (request.getParameter("WiiU") != null)
			{
				name = "WiiU";
				//imageLocation = "images/WiiU.jpg";
				price = 480;
				Warranty = 2;
		    }
			else if (request.getParameter("xbox-360-accessories") != null)
			{
				name = "xbox-360-accessories";
				//imageLocation = "images/xbox-360-accessories.jpg";
				price = 480;
				Warranty = 1;
		    }*/
		
			
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection mycarts = db.getCollection("mycarts");
			
			// Find and display 
			BasicDBObject searchQuery = new BasicDBObject();
			//searchQuery.put(productName, imageLocation);

			DBCursor cursor = mycarts.find(searchQuery);
			//String Warranty = "2 yr";
			//String name = request.getParameter("name");
			//String price = request.getParameter("price");
			//String FirstName = request.getParameter("firstname");
			
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head><title>Buy</title> </head>");
			out.println("<body>");	
			out.println("<h1>Place Order</h1>");	
					
						
			out.println("");
			//out.println("<td> </td>");
			
			
			out.println("<form method='get' action='SubmitOrder'>");
			out.println("<fieldset>");

			
			out.println("<legend>Product information:</legend>");
			out.println("<table>");
			out.println("<tr>");
			
			out.println("<tr><td> Discounted Price: </td><td> <input type='text' name='discountedPrice' value="+DiscountedPrice+" readonly> </td></tr>");
			out.println("<tr><td> Warranty: </td><td> <input type='text' name='Warranty' value="+Warranty+" readonly> </td></tr>");
			out.println("</table></fieldset>");
			out.println("<fieldset>");
			out.println("<legend>Personal information:</legend>");
			out.println("<table>");
			out.println("<tr><td> First name: </td><td> <input type='text' name='firstName'> </td></tr>");
			out.println("<tr><td> Last name: </td><td> <input type='text' name='lastName'> </td></tr>");
			out.println("<tr><td> Address: </td><td> <input type='text' name='address'> </td></tr>");
			out.println("<tr><td> Phone: </td><td> <input type='text' name='phoneNumber'> </td></tr>");
			out.println("<tr><td> Credit Card No: </td><td> <input type='password' name='cardNum'> </td></tr>");
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