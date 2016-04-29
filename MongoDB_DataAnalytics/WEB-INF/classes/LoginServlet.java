/*
 * LoginServlet.java
 *
 */
 

import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

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

public class LoginServlet extends HttpServlet {
   
   private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
		
		try{
		
		// for manager 
		String managerEmail = "manager";
		String managerPassword = "manager";
		
		
		// for salesmen
		String salesmenEmail = "salesmen";
		String salesmenPassword = "salesmen";
		
		DB db = mongo.getDB("CSP595Tutorial");
		DBCollection myRegister = db.getCollection("myRegister");
		
		String userEmail = " ";
		String userPassword = " ";
		
        String email = request.getParameter("email");
        String password = request.getParameter("password");
		String userType = request.getParameter("userType");
		
		PrintWriter out = response.getWriter();
		// checking condition
		if(userType.equals("Manager"))
		{
		if((email.equals(managerEmail))&&(password.equals(managerPassword)))
		{   
	        response.sendRedirect("storemanager.html");
			
		}
		else
			{
			response.setContentType("text/html");
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Login Failed</h1>");
			out.println("</body>");
			out.println("</html>");
			}
		}
		else if(userType.equals("Salesmen"))
		{
			if((email.equals(salesmenEmail))&&(password.equals(salesmenPassword)))
			{
				 response.sendRedirect("salesmen.html");
			
			}
			else
			{
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Login Failed</h1>");
			out.println("</body>");
			out.println("</html>");
			}
		}
		else if(userType.equals("Customer"))
		{
         BasicDBObject searchQuery = new BasicDBObject();
		 searchQuery.put("email", email);

			DBCursor cursor = myRegister.find(searchQuery);
			while(cursor.hasNext())
			{
				BasicDBObject bdo = (BasicDBObject)cursor.next();
				userEmail =  bdo.getString("email");
				userPassword = bdo.getString("password");
				
				if((userEmail.equals(email))&&(userPassword.equals(password)))
			{
				 response.sendRedirect("welcomeCustomer.html");
			
			}
			else
			{
				
				out.println("<html>");
				out.println("<head> </head>");
				out.println("<body>");
				out.println("<h1>'Login Failed'</h1>");
				out.println("</body>");
				out.println("</html>");
			}
				
			}
		
		} 
		
		}
		catch (MongoException e) {
				e.printStackTrace();
		}
		
	}	
		
	
    } 
    
   