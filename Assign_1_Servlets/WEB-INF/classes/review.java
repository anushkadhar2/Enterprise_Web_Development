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

public class review extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//Get the values from the form
		String productName = request.getParameter("itemName");
		//String productCategory="Gaming Console";
		String imageLocation = " ";
		String productCategory = "Gaming_Console";
		String retailerName ="Game_Speed";
		String retailerZip ="60616";
		String retailerCity ="Chicago";
		String retailerState ="IL";
		String productonSale="Yes";
		String manufacturerName=request.getParameter("itemType");
		String manufacturerRebate="Yes";
		int productPrice =Integer.parseInt(request.getParameter("itemPrice"));	
		
		/*if (request.getParameter("XBox_Original") != null){
			productName = "XBox_Original";
			imageLocation = "images/img_XBoxOriginal.jpg";
			productCategory="Gaming_Console";
			retailerName = "Game_Speed";
			productonSale = "Yes";
			manufacturerRebate="Yes";
			manufacturerName = "Microsoft";
			productPrice = 80;
		}else if (request.getParameter("XBox_360") != null){
			productName = "XBox_360";
			productCategory="Gaming_Console";
			imageLocation = "images/img_XBox360.jpg";
			retailerName = "Game_Speed";
			productonSale = "Yes";
			manufacturerRebate="Yes";
			manufacturerName = "Microsoft";
			productPrice = 300;
		}else if (request.getParameter("XBox_One") != null){
			productName = "XBox_One";
			productCategory="Gaming_Console";
			imageLocation = "images/img_XBoxOne.jpg";
			retailerName = "Game_Speed";
			productonSale = "Yes";
			manufacturerRebate="Yes";
			manufacturerName = "Microsoft";
			productPrice = 500;
		}else if (request.getParameter("PlayStation_2") != null){
			productName = "PlayStation_2";
			productCategory="Gaming_Console";
			imageLocation = "images/img_PlayStation2.jpg";
			retailerName = "Game_Speed";
			productonSale = "Yes";
			manufacturerRebate="Yes";
			manufacturerName = "Sony";
			productPrice = 60;
		}else if (request.getParameter("PlayStation_3") != null){
			productName = "PlayStation_3";
			productCategory="Gaming_Console";
			imageLocation = "images/img_PlayStation3.jpg";
			retailerName = "Game_Speed";
			productonSale = "Yes";
			manufacturerRebate="Yes";
			manufacturerName = "Sony";
			productPrice = 220;
		}else if (request.getParameter("PlayStation_4") != null){
			productName = "PlayStation_4";
			productCategory="Gaming_Console";
			imageLocation = "images/img_PlayStation4.jpg";
			retailerName = "Game_Speed";
			productonSale = "Yes";
			manufacturerRebate="Yes";
			manufacturerName = "Sony";
			productPrice = 400;
		}
		else if (request.getParameter("wii") != null){
			productName = "wii";
			productCategory="Gaming_Console";
			imageLocation = "images/wii.jpg";
			retailerName = "Game_Speed";
			productonSale = "Yes";
			manufacturerRebate="Yes";
			manufacturerName = "Nintendo";
			productPrice = 250;
		}else if (request.getParameter("WiiU") != null){
			productName = "WiiU";
			productCategory="Gaming_Console";
			imageLocation = "images/WiiU.jpg";
			retailerName = "Game_Speed";
			productonSale = "Yes";
			manufacturerRebate="Yes";
			manufacturerRebate="Yes";
			manufacturerName = "Nintendo";
			productPrice = 480;
		}else if (request.getParameter("xbox-360-accessories") != null){
			productName = "xbox-360-accessories";
			productCategory="Gaming_Console";
			imageLocation = "images/xbox-360-accessories.jpg";
			retailerName = "Game_Speed";
			productonSale = "Yes";
			manufacturerRebate="Yes";
			manufacturerRebate="Yes"; 
			manufacturerName = "Xbox";
			productPrice = 480;
		}*/
		
		
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
			out.println("<h1>Product Reviews</h1>");	
			
			out.println("");
			//out.println("<td> </td>");
			out.println("<h3>" +productName+ "</h3>");
			out.println("<form method='get' action='SubmitReview'>");
			out.println("<fieldset>");
			
			out.println("<fieldset>");
			out.println("<legend>Product information:</legend>");
			//out.println("<img src= " + imageLocation + " width = '200' height = '200' alt = 'Product Image'>");
			out.println("<table>");
			out.println("<tr><td> Product Name: </td><td><input type='text' name='productName' value=" +productName+"></td></tr>");
			out.println("<tr><td> Product Category: </td><td><input type='text' name='productCategory' value=" +productCategory+"> </td></tr>");
			out.println("<tr><td> Product Price: </td><td><input type='text' name='productPrice' value=" +productPrice+"></td></tr>");
			out.println("<tr><td> Retailer Name: </td><td><input type='text' name='retailerName' value=" +retailerName+"></td></tr>");
			out.println("<tr><td> Retailer City: </td><td> <input type='text' name='city' value="+retailerCity+"> </td></tr>");
			out.println("<tr><td> Retailer State: </td><td> <input type='text' name='state' value="+retailerState+"> </td></tr>");
			out.println("<tr><td> Retailer ZIP: </td><td> <input type='text' name='zip' value="+retailerZip+"> </td></tr>");
			out.println("<tr><td> Product on sale: </td><td><input type='text' name='productonSale' value=" +productonSale+"></td></tr>");
			out.println("<tr><td> Manufacturer Name: </td><td><input type='text' name='manufacturerName' value=" +manufacturerName+"></td></tr>");
			out.println("<tr><td> Manufacturer Rebate : </td><td><input type='text' name='manufacturerRebate' value=" +manufacturerRebate+">`	</td></tr>");
			out.println("</table>");
			out.println("</fieldset>");
			out.println("<fieldset>");
			out.println("<legend>Reviews:</legend>");
			out.println("<table>");
			//out.println("<tr><td> Product Name: </td><td><input type='text' name='productName' value=" +productName+"></td></tr>");
			out.println("<tr><td> User Name: </td><td> <input type='text' name='userName'> </td></tr>");
			out.println("<tr><td> User Age: </td><td> <input type='text' name='userAge'> </td></tr>");
			out.println("<tr><td> User Gender: </td><td> <select name='userGender'><option>Male</option><option>Female</option></select></td></tr>");
			out.println("<tr><td> User Occupation: </td><td> <input type='text' name='userOccupation'> </td></tr>");
			out.println("<tr><td> Review Rating: </td><td><select name='reviewRating'><option value='1' selected>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option></td></tr>");
			out.println("<tr><td> Review Date: </td><td> <input type='date' name='reviewDate'> </td></tr>");
			out.println("<tr><td> Review Text: </td><td><textarea name='reviewText' rows='4' cols='50'> </textarea></td></tr>");
			out.println("</table>");
			out.println("<br><br>");
			out.println("<input type='submit' value='Submit Review'>");
			//out.println("</fieldset>");
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
			
			
			