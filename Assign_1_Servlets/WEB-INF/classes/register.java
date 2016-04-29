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

public class register extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
			//Get the values from the form
			String FirstName = request.getParameter("firstname");
			String LastName = request.getParameter("lastname");
			String userid = request.getParameter("userid");
			String password = request.getParameter("password");
			String Confirm_Password = request.getParameter("cpassword");
			String Phone = request.getParameter("phone");	
										
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
				
			// If the collection does not exists, MongoDB will create it for you
			DBCollection myregister = db.getCollection("myregister");
			System.out.println("Collection myregister selected successfully");
				
			BasicDBObject doc = new BasicDBObject("title", "myregister").
				append("firstname", FirstName).
				append("lastname", LastName).
				append("userid", userid).
				append("password", password).
				append("cpassword", Confirm_Password).
				append("phone", Phone);
									
			myregister.insert(doc);
				
			System.out.println("Document inserted successfully");
			
			
			PrintWriter out = response.getWriter();
						
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Welcome "+ FirstName + "</h1>");
			out.println("<br/>");
			out.println("<a href='welcome.html'> Click To Proceed </a>");
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