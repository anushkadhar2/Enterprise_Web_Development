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


public class writeReviews extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		
			String productName = request.getParameter("productName");
			String productPrice =request.getParameter("productPrice");
			
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection Reviews = db.getCollection("Reviews");
			
			PrintWriter out = response.getWriter();
			
			out.println("<html>");
			out.println("<head><title>Write Review</title> </head>");
			out.println("<body>");	
			out.println("<h1>Write Review</h1>");	
			
			out.println("");
			out.println("<h3>" +productName+ "</h3>");
			
			out.println("<form method='get' action='SubmitReview'>");
			out.println("<fieldset>");
			
			out.println("<legend>Product information:</legend>");
			
			out.println("<table>");
			out.println("<tr>");
			out.println("<td> Product Model Name: </td>");
			out.println("<td> <input type='text' name='productName' value=" +productName+ " readonly> </td></td></tr>");
			out.println("<tr><td> Product Price: </td><td><input type='text' name='productPrice' value=" +productPrice+ " readonly	> </td></tr>");
			out.println("<tr>");
			out.println("<td> Product Category: </td><td><select name='productCategory'><option value= 'GameMaker' selected >GameMaker</option><option value= 'ConsoleMaker'>ConsoleMaker</option><option value= 'Accessories'>Accessories</option></td></tr>");
			
			out.println("<tr><td> Retailer Name: </td><td><select name='retailerName'><option value= 'Walmart' selected >Walmart</option><option value= 'GameSpeed'>GameSpeed</option><option value= 'BestBuy'>BestBuy</option></td></tr>");
			
			out.println("</tr><tr><td> Retailer City: </td><td><input type='text' name='retailerCity'> </td></tr>");
			
			out.println("<tr><td> Retailer State: </td><td><input type='text' name='retailerState'> </td></tr>");
			
			out.println("<tr><td> Retailer Zip: </td><td><input type='text' name='retailerZip'> </td></tr>");
			
			out.println("<tr><td> Product On Sale: </td><td><select name='productOnSale' ><option value= 'Yes'>Yes</option><option value = 'No'>No</option> </td></tr>");
			
			out.println("<tr><td> Manufacturer Name: </td><td><select name='manufacturerName'><option value= 'Microsoft' selected >Microsoft</option><option value= 'Sony'>Sony</option><option value= 'Nintendo'>Nintendo</option></td></tr>");
			
	        out.println("<tr><td> Retailer Zip: </td><td><input type='text' name='retailerZip'> </td></tr>");
			
			out.println("<tr><td> Product On Sale: </td><td><select name='productOnSale' ><option value= 'Yes'>Yes</option><option value = 'No'>No</option> </td></tr>");
			
			out.println("<tr><td> Manufacturer Rebate: </td><td><select name='manufacturerRebate'> <option value= 'Yes'>Yes</option><option value = 'No'>No</option>  </td></tr></table>");
			
			out.println("</fieldset>");	
			
			out.println("<fieldset>");
			out.println("<legend>Reviews:</legend>");
			out.println("<table>");
			
			//User Id
			out.println("<tr>");
		    out.println("<td> User Name: </td>");
			out.println("<td> <input type='text' name='userName'> </td>	</tr>");
			
			//User Age
			out.println("<tr>");
		    out.println("<td> User Age: </td>");
			out.println("<td> <input type='text' name='userAge'> </td>	</tr>");
			
			//User Gender
			out.println("<tr>");
		    out.println("<td> User Gender: </td>");
			out.println("<td> <input type='text' name='userGender'> </td>	</tr>");
			
			//User Occupation
			out.println("<tr>");
		    out.println("<td> User Occupation: </td>");
			out.println("<td> <input type='text' name='userOccupation'> </td>	</tr>");
			
			//Review Ratings
			out.println("<tr>");
		    out.println("<td> Review Rating: </td>");
			out.println("<td>");
			out.println("<select name = 'reviewRating'>");
			out.println("<option value='1' selected>1</option>");
			out.println("<option value='2'>2</option>");
			out.println("<option value='3'>3</option>");
			out.println("<option value='4'>4</option>");
			out.println("<option value='5'>5</option>");
			out.println("</td>");
			out.println("</tr>");
			
			//Review Date
			out.println("<tr>");
		    out.println("<td> Review Date: </td>");
			out.println("<td> <input type='date' name='reviewDate'> </td>	</tr>");
			
			//Review Text
			out.println("<tr>");
		    out.println("<td> Review Text: </td>");
			out.println("<td> <textarea type name='reviewText' rows = '5' columns = '50'></textarea> </td>	</tr>");
            
			out.println("</table>");
			out.println("<br><br>");
			out.println("<input type='submit' value='Send Review'>");
			out.println("</fieldset>");
			
			
		    out.println("</form>");
			out.println("</body>");
			out.println("</html>");
			
			
		}
		 catch (MongoException e) {
				e.printStackTrace();
		}
	}
}