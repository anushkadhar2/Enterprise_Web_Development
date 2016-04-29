import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import java.util.Random;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SubmitOrder extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
			
			PrintWriter out = response.getWriter();
			Random r = new Random( System.currentTimeMillis() );
			int rand= 10000 + r.nextInt(20000);
			String randString=Integer.toString(rand);
			Enumeration paramNames = request.getParameterNames();

			//ServletContext sc = request.getSession().getServletContext();
			
			//File fname= new File(sc.getRealPath("Temp1234.txt"));
			//FileWriter fileWriter = new FileWriter(fname,true);
			//BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			


			String storeorder=randString+"-";
			
			//Get the values from the form
			//String DiscountedPrice = request.getParameter("DiscountedPrice");
			int DiscountedPrice = Integer.parseInt(request.getParameter("discountedPrice"));
			int Warranty = Integer.parseInt(request.getParameter("Warranty"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String address = request.getParameter("address");
			String phoneNumber = request.getParameter("phoneNumber");		
			storeorder+=request.getParameter("firstName")+"-";
			storeorder+=request.getParameter("lastName")+"-";	
			
			
			String title = "Your Order Placed Successfully";
            out.println("<HTML>\n" +
                "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n" +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
				"<H3 ALIGN=CENTER>Your Order Number :" + randString + "</H3>\n" +
                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                "<TR BGCOLOR=\"#FFAD00\">\n" +
                "<TH>Parameter Name</TH>");
			
			
                
			
			
			
			out.print("<TR><TD> Discount Price:"  + request.getParameter("discountedPrice") + "\n</TD></TR>");
			
			
			out.print("<TR><TD> First Name:"  + request.getParameter("firstName") + "\n</TD></TR>");
			//out.print("<TD>" + request.getParameter("firstName") + "\n</TD></TR>");
				
				
	    	out.print("<TR><TD> Last Name:"  + request.getParameter("lastName") + "\n</TD></TR>");
			//out.print("<TD>" + request.getParameter("lastName") + "\n</TD></TR>");
				
					
			out.print("<TR><TD> Address :" + request.getParameter("address") + "\n</TD></TR>");
			//out.print("<TD>" + request.getParameter("address") + "\n</TD></TR>");
			
			out.print("<TR><TD> Phone Number:"  + request.getParameter("phoneNumber") + "\n</TD></TR>");
			
			out.print("<TR><TD> Warranty:"  + request.getParameter("Warranty") + "\n</TD></TR>");
			//out.print("<TD>" + request.getParameter("phoneNumber") + "\n</TD></TR>");
			
				
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				Calendar c = Calendar.getInstance();
				c.setTime(new Date()); // Now use today date.
				c.add(Calendar.DATE, 14); // Adding 14 days
				String output = sdf.format(c.getTime());
				
				out.print("<TR><TD> Delivery Date:"  + output + "\n</TD></TR>");
				//out.print("<TD>" + output + "\n</TD></TR>");
				out.println("</TABLE>\n");

				
				storeorder+=output;
				//storeorder+="\n";			
										
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
				
			// If the collection does not exists, MongoDB will create it for you
			DBCollection mycarts = db.getCollection("mycarts");
			System.out.println("Collection myOrders selected successfully");
				
		BasicDBObject doc = new BasicDBObject("title", "mycarts").
		
				append("DiscountedPrice", DiscountedPrice).
				append("randString",randString).
				append("firstName", firstName).
				append("lastName", lastName).
				append("address", address).
				append("Warranty", Warranty).
				append("phoneNumber", phoneNumber).
				append("Deliverydate",output);
					
			mycarts.insert(doc);
				
			System.out.println("Document inserted successfully");
			
			//Send the response back to the JSP
			
			//String title = "Your Order Placed Successfully";
			
			/*out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Order placed for:"+ productName + "</h1>");*/
			
			//out.println("<tr>");
			//out.println("<td>");
			String backURL =
            response.encodeURL("/Assign_1Copy/welcome.html");
       
        // "Proceed to Checkout" button below table
			 out.println
			  ("</TABLE>\n" +
			   "<FORM ACTION=\"" + backURL + "\">\n" +
			   "<BIG><CENTER>\n" +
			   "<p>Thank you for shopping with us.</p>"+
			   "<INPUT TYPE=\"SUBMIT\"\n" +
			   "       VALUE=\"Home\">\n" +
			   
			   "</CENTER></BIG></FORM>");

			//out.println("<a href='index.html'> Index </a>");
			//out.println("</td>");
			//out.println("</tr>");
			
		
			//out.println("</table>");
			
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