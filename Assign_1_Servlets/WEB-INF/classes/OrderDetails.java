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


public class OrderDetails extends HttpServlet {
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
			
			//Get the product selected
			String searchParameter = request.getParameter("orderNo");
			//String seachParameter = request.getParameter("randString");
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			PrintWriter out = response.getWriter();
			DBCollection mycarts = db.getCollection("mycarts");
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("randString", searchParameter);
			DBCursor cursor1 = mycarts.find(whereQuery);
			//String s="";
			String ddate="";
			while(cursor1.hasNext()) {
				 //s = (cursor1.next()).toString();		
			 BasicDBObject searchObj=(BasicDBObject)cursor1.next();
				ddate=searchObj.getString("Deliverydate");
			}
			//String a[] = s.split(",");
		    //String b[] = a[9].split(":");
			/*for(int i=0;i<a.length;i++){
				out.println("<h1>"+a[i]+"</h1>");
			}*/
			//int c=0;
			//for(int i=0;i<b.length;i++){
				//out.println("<h1>"+b.length+"</h1>");
				//out.println(b[i]);
				
				
				//String tdate=dateFormat.format(date);//2014/08/06 15:59:48
				//out.println(b[1].substring(2,12));
				//String ddate = b[1].substring(1,10);
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				String today=dateFormat.format(date);
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
 
					Date d1 = null;
					Date d2 = null;
					
					try {
			d1 = format.parse(ddate);
			d2 = format.parse(today);
			
			out.println("Delivery date:"+d1);
			out.println("Today's date:"+d2);
 
			
			long diff = d1.getTime() - d2.getTime();
			diffDays = diff / (24 * 60 * 60 * 1000);
				//String abc= Long.toString(diffDays);
			out.println("Difference in days:"+diffDays);
		
			//String abc= diffDays.toString();
						//PrintWriter out = response.getWriter();
						
			
			BasicDBObject doc = new BasicDBObject("title", "mycarts").
				append("diffDays", diffDays);	
			mycarts.insert(doc);
				
			System.out.println("Document inserted successfully");
							
			//out.println(cursor);
			response.setContentType("text/html");			
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Item shipped</h1>");
			//out.println("<h1>order shipped</h1>");
			out.println("<form action='CancelOrder'>");
			out.println("<input type = 'submit' name = 'XBox_Original' value = 'Cancel Order'>");
			out.println("<input type='hidden' name='itemId' value="+diffDays+" readonly></a>");
			out.println("</form>");
				out.println("</body>");
				out.println("</html>");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				//SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

				/*Date d1 = null;
					Date d2 = null;
					
					try {
			d1 = format.parse(ddate);
			d2 = format.parse(today);
			out.println(d1);
			out.println(d2);
			
			
			long diff = d1.getTime() - d2.getTime();
			out.println(diff);
			diffDays = diff / (24 * 60 * 60 * 1000);
			out.println(diffDays);
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/


	


  
				//out.println(c);
				//String m = b[1].substring(2,12);
				//out.println("<h1>"+b[0]+"</h1>");
			//}
			
			
		} catch (MongoException e) {
				e.printStackTrace();
		}
	}

	
	public void destroy(){
      // do nothing.
	}
}