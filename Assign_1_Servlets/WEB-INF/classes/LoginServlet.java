import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

import java.net.UnknownHostException;
import java.util.Date;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

public class LoginServlet extends HttpServlet {
   
    //protected Map users = new HashMap();
    /** 
     * Initializes the servlet with some usernames/password
    */  
    
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
	try{	
	
		String storeManagerId = "smanager";
		String storeManagerPassword = "abc"; 
		String salesmanId = "salesman";
		String salesmanPassword = "def"; 
		
		//data saved in database
		String userName = "";
		String Password = "";
		
		//data coming from form
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
        
		//MongoClient mongo;
		//mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("CSP595Tutorial");
		DBCollection myregister = db.getCollection("myregister");
		
		if(usertype.equals("STOREMANAGER")){
			if(userid.equals(storeManagerId)&&password.equals(storeManagerPassword)){
				response.sendRedirect("storemanager.html");
			}
			else{
			response.setContentType("text/html");
				java.io.PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Login Servlet Result</title>");  
				out.println("</head>");
				out.println("<body>");
				out.println("<h2>"+ "Login Failed!!" +"</h2>");
				out.println("</body>");
				out.println("</html>");
				out.close();
			}
		}
		else if(usertype.equals("SALESMAN")){
			if(userid.equals(salesmanId)&&password.equals(salesmanPassword)){
				response.sendRedirect("salesman.html");
			}
			else{
			response.setContentType("text/html");
				java.io.PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Login Servlet Result</title>");  
				out.println("</head>");
				out.println("<body>");
				out.println("<h2>"+ "Login Failed!!" +"</h2>");
				out.println("</body>");
				out.println("</html>");
				out.close();
			}
		}
		else if(usertype.equals("CUSTOMER")){
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("userid", userid);
			DBCursor cursor = myregister.find(searchQuery);
		
			while(cursor.hasNext())
			{
				BasicDBObject ob = (BasicDBObject)cursor.next();
				userName = ob.getString("userid");
				Password = ob.getString("password");
				if(userName.equals(userid)&&Password.equals(password)){
					response.sendRedirect("welcome.html");
				}
				else{
					response.setContentType("text/html");
					java.io.PrintWriter out = response.getWriter();
					out.println("<html>");
					out.println("<head>");
					out.println("<title>Login Servlet Result</title>");  
					out.println("</head>");
					out.println("<body>");
					out.println("<h2>"+ "Login Failed!!" +"</h2>");
					out.println("</body>");
					out.println("</html>");
					out.close();
				}
			}
		}
		else{
			response.setContentType("text/html");
				java.io.PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Login Servlet Result</title>");  
				out.println("</head>");
				out.println("<body>");
				out.println("<h2>"+ "Login Failed!!" +"</h2>");
				out.println("</body>");
				out.println("</html>");
				out.close();
		}				
	}catch (MongoException e) {
				e.printStackTrace();
	}
	}
}