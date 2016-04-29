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

public class SubmitReview1 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
			//Get the values from the form
			String productName = request.getParameter("productName");
			String productCategory = request.getParameter("productCategory");
			String productPrice = request.getParameter("productPrice");
			String retailerName = request.getParameter("retailerName");
			String manufacturerName = request.getParameter("manufacturerName");
			//String productName = request.getParameter("productName");
			String userName = request.getParameter("userName");
			String userAge = request.getParameter("userAge");
			String userGender = request.getParameter("userGender");
			String city = request.getParameter("city");
			String state = request.getParameter("state");
			String zip = request.getParameter("zip");
			String productonSale = request.getParameter("productonSale");
			String reviewRating = request.getParameter("reviewRating");	
			String reviewDate = request.getParameter("reviewDate");
			String manufacturerRebate = request.getParameter("manufacturerRebate");
			String reviewText = request.getParameter("reviewText");
			
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
				
			// If the collection does not exists, MongoDB will create it for you
			DBCollection myReviews = db.getCollection("myReviews");
			System.out.println("Collection myReviews selected successfully");
				
			BasicDBObject doc = new BasicDBObject("title", "myReviews").
				append("productName", productName).
				append("productCategory", productCategory).
				append("productPrice", productPrice).
				append("retailerName", retailerName).
				append("manufacturerName", manufacturerName).
				append("productName", productName).
				append("userName", userName).
				append("userAge", userAge).
				append("userGender", userGender).
				append("city", city).
				append("state", state).
				append("zip", zip).
				append("productonSale", productonSale).
				append("reviewRating", reviewRating).
				append("reviewDate", reviewDate).
				append("manufacturerRebate", manufacturerRebate).
				append("reviewText", reviewText);
									
			myReviews.insert(doc);
				
			System.out.println("Document inserted successfully");
			
			//Send the response back to the JSP
			PrintWriter out = response.getWriter();
						
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Review submitted for:"+ productName + "</h1>");
			
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
			
			out.println("</body>");
			out.println("</html>");
		
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	
}