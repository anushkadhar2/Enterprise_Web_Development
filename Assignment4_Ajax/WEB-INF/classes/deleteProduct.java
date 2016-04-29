import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.io.*;

import com.mongodb.*;

public class deleteProduct extends HttpServlet {
   
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException{
	
	try{
		
			PrintWriter out = response.getWriter();
			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);

		   String searchParameter = request.getParameter("productId");	

		   DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myProduct = db.getCollection("myProduct");
			System.out.println("Collection registerDetails selected successfully");
			
			BasicDBObject query = new BasicDBObject();
				query.append("productId",searchParameter);
				myProduct.remove(query);
				out.println("<html>");
				out.println("<head>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1> Product Removed Successfully</h1>");
				out.println("<br>");
				out.println("<h3>To remove more products</h1>");
				out.println("<a href = 'deleteProduct.html'>Click here</a>");
				out.println("<h3>To go back</h1>");
				out.println("<a href = 'storemanager.html'>Click here</a>");
				out.println("</body>");
				out.println("</html>");
	}
	
		catch (Exception e) {
				e.printStackTrace();
		}
				
}	
}
	

	    	


