
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;
import javax.servlet.*;
import java.util.Random;
import javax.servlet.http.*;
import java.util.*;
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

public class checkOutOrder extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
			
			//get the values from form
			
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String address = request.getParameter("address");
			int creditCard = Integer.parseInt(request.getParameter("creditCard"));
			
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			// If the collection does not exists, MongoDB will create it for you
			DBCollection myCheckOut = db.getCollection("myCheckOut");
			System.out.println("Collection myOrders selected successfully");
				
			
			BasicDBObject doc = new BasicDBObject("title", "myCheckOut").
				
				append("firstName", firstName).
				append("lastName", lastName).
				append("address", address).
				append("creditCard", creditCard);
					
				myCheckOut.insert(doc);
			
				System.out.println("Document inserted successfully");
			
			//Send the response back to the JSP
				PrintWriter out = response.getWriter();
				
				Random r = new Random( System.currentTimeMillis() );
				int rand= 900 + r.nextInt(4000);
				String randString=Integer.toString(rand);
				
				out.println("<html>");
				out.println("<head> </head>");
				out.println("<body background-color : 'red'>");
				
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date()); // Now use today date.
				cal.add(Calendar.DATE, 14); // Adding 14 days
				String date = sdf.format(cal.getTime());
				
				out.println("<center>");
				out.println("<h1> Order placed Successfully</h1>");
				out.println("<br>");
				out.println("<h3> Confirmation Number:  "+ randString + "</h3>");
				out.println("<br>");
				out.println("<h4> Delivery Date:   "+ date + "</h4>");
				out.println("<br>");
                out.println("First Name :   " +request.getParameter("firstName"));
				out.println("<br>");
				out.println("Last Name :    " +request.getParameter("lastName"));
				out.println("<br>");
				out.println("Address : "    +request.getParameter("address"));
				out.println("<br>");
				out.println("<br>");
				out.println("<br>");
				out.println("<form action = 'index.html'>");
				out.println("<input type = 'submit' name = 'home' value = 'Return Home'>");
				out.println("</form>");
				
				out.println("</center>");
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