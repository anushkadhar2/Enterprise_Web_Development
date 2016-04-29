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

public class ViewReviews1 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//Get the values from the form
			String searchField = "productName";
			
			//Get the product selected
			String searchParameter = "";
			if (request.getParameter("XBox_Original") != null){
			searchParameter = "XBox_Original";
			}else if (request.getParameter("XBox_360") != null){
			searchParameter = "XBox_360";
			}else if (request.getParameter("XBox_One") != null){
			searchParameter = "XBox_One";
			}else if (request.getParameter("PlayStation_2") != null){
			searchParameter = "PlayStation_2";
			}else if (request.getParameter("PlayStation_3") != null){
			searchParameter = "PlayStation_3";
			}else if (request.getParameter("PlayStation_4") != null){
			searchParameter = "PlayStation_4";
			}else if (request.getParameter("wii") != null){
			searchParameter = "wii";
			}else if (request.getParameter("WiiU") != null){
			searchParameter = "WiiU";
			}
			
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myReviews = db.getCollection("myReviews");
			
			// Find and display 
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put(searchField, searchParameter);

			DBCursor cursor = myReviews.find(searchQuery);
			
			PrintWriter out = response.getWriter();
			//out.println(cursor);
						
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Reviews For:"+ searchParameter+ "</h1>");
			
			out.println("<table>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='index.html'> Index </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='Microsoft.html'> Microsoft </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='Sony.html'> Sony </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='Nintendo.html'> Nintendo </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("</table>");
			out.println("<br><br><hr>");
			
			if(cursor.count() == 0){
				out.println("There are no reviews for this product.");
			}else{
			
				out.println("<table>");
				
				String productName = "";
				String productCategory = "";
				String productPrice = "";
				String manufacturerName =  "";
				String userName = "";
				String userAge = "";
				String userGender = "";
				String city = "";
				String state = "";
				String zip = "";
				String reviewRating = "";
				String retailerName = "";
				String reviewDate = "";
				String reviewText = "";
				
				while (cursor.hasNext()) {
					//out.println(cursor.next());
					BasicDBObject obj = (BasicDBObject) cursor.next();				
					
					out.println("<tr>");
					out.println("<td> Product Name: </td>");
					productName = obj.getString("productName");
					out.println("<td>" +productName+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Product Category:</td>");
					productCategory = obj.getString("productCategory");
					out.println("<td>" +productCategory+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Product Price: </td>");
					productPrice = obj.getString("productPrice").toString();
					out.println("<td>" +productPrice+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Retailer Name : </td>");
					retailerName = obj.getString("retailerName");
					out.println("<td>" +retailerName+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Manufacturer Name: </td>");
					manufacturerName = obj.getString("manufacturerName");
					out.println("<td>" +manufacturerName+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Product Name: </td>");
					productName = obj.getString("productName");
					out.println("<td>" +productName+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> User Name: </td>");
					userName = obj.getString("userName");
					out.println("<td>" +userName+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> User Age: </td>");
					userAge = obj.getString("userAge");
					out.println("<td>" +userAge+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> User Age: </td>");
					userGender = obj.getString("userGender");
					out.println("<td>" +userGender+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> City: </td>");
					city = obj.getString("city");
					out.println("<td>" +city+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> state: </td>");
					state = obj.getString("state");
					out.println("<td>" +state+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> zip: </td>");
					zip = obj.getString("zip");
					out.println("<td>" +zip+ "</td>");
					out.println("</tr>");
					
					
					out.println("<tr>");
					out.println("<td> Review Rating: </td>");
					reviewRating = obj.getString("reviewRating").toString();
					out.println("<td>" +reviewRating+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Review Date: </td>");
					reviewDate = obj.getString("reviewDate");
					out.println("<td>" +reviewDate+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Review Text: </td>");
					reviewText = obj.getString("reviewText");
					out.println("<td>" +reviewText+ "</td>");
					out.println("</tr>");

				}
			}	
				out.println("</table>");
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