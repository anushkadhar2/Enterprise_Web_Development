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

public class Register extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//Get the values from the form
			String FirstName = request.getParameter("firstName");
			String LastName = request.getParameter("lastName");
			String Password = request.getParameter("password");
			String ConfirmPassword = request.getParameter("confirmPassword");
			String Email = request.getParameter("email");
			String UserId = request.getParameter("userId");
			
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myRegister = db.getCollection("myRegister");
			System.out.println("Collection registerDetails selected successfully");
			
			
			BasicDBObject doc = new BasicDBObject("title", "myRegister").
				append("firstName", FirstName).
				append("lastName", LastName).
				append("password", Password).
				append("confirmPassword", ConfirmPassword).
				append("email", Email);
				
				
				
				myRegister.insert(doc);
				
				System.out.println("Document inserted successfully");
				
				//Send the response back to the JSP
			PrintWriter out = response.getWriter();
		
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Welcome "+ FirstName + "</h1>");
			out.println("<br/>");
			out.println("<a href='index.html'> Click for Home Page</a>");
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