
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


public class checkout extends HttpServlet {
	
			private static final long serialVersionUID = 1L;
	
			MongoClient mongo;
	
			public void init() throws ServletException{
			// Connect to Mongo DB
			mongo = new MongoClient("localhost", 27017);
			}
			
	
				public void doGet(HttpServletRequest request,HttpServletResponse response)
						throws ServletException, IOException {
							
							try
							{
								DB db = mongo.getDB("CSP595Tutorial");
								
								DBCollection myCheckOut = db.getCollection("myCheckOut");
								// Find and display 
								BasicDBObject searchQuery = new BasicDBObject();
								//searchQuery.put(productName, imageLocation);

								DBCursor cursor = myCheckOut.find(searchQuery);
								
								PrintWriter out = response.getWriter();
								
								out.println("<html>");
								out.println("<head><title>CheckOut</title> </head>");
								out.println("<body>");	
								out.println("<h1>Check Out</h1>");
								out.println("<form method='get' action='checkOutOrder'>");
								out.println("<fieldset>");
								out.println("<legend>Enter Your Details:</legend>");
								out.println("<table>");
								out.println("<tr>");
								out.println("<td> First Name: </td><td><input type='text' name='firstName'></td></tr>");
								out.println("<tr><td> Last Name: </td><td><input type='text' name='lastName'></td></tr>");
								out.println("<tr><td> Address: </td><td><input type='text' name='address'></td></tr>");
								out.println("<td> Credit Card: </td><td><input type='password' name='creditCard'></td></tr>");
			                    out.println("</table>");
								out.println("<br><br>");
								out.println("<input type='submit' value='Submit Order'>");
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