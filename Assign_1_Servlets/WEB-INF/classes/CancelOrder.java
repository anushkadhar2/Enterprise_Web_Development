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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CancelOrder extends HttpServlet {
	long diffDays=0;
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//Get the values from the form
			//String orderNo = "";
			int searchParameter=Integer.parseInt(request.getParameter("itemId"));
			//int search= Integer.valueOf(request.getParameter("searchParameter"));

			DB db = mongo.getDB("CSP595Tutorial");
			//PrintWriter out = response.getWriter();
			DBCollection myOrders = db.getCollection("myOrders");
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("abc", searchParameter);
			DBCursor cursor1 = myOrders.find(whereQuery);
			
			//out.println(searchParameter);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			if(searchParameter <= 5 )
			{
							
				out.println("<html>");
				out.println("<head> </head>");
				out.println("<body>");
			   out.println("<h3> You can't cancel your order now. It should be before 5 days</h3>");
			}
			else {
				out.println("<h3>We have cancelled your Order</h3>");
			}
				
				out.println("<a href='welcome.html'>Go back</a>");	//PrintWriter out = response.getWriter();
			
		} catch (MongoException e) {
				e.printStackTrace();
		}
	}

	
	public void destroy(){
      // do nothing.
	}
}