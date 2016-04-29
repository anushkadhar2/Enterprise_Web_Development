import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.mongodb.*;

public class addProduct extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
{                
            try{
			// first fetch the values from the form
			String itemId = request.getParameter("itemId");
			String itemName = request.getParameter("itemName");	
			String itemPrice = request.getParameter("itemPrice");
			String itemType = request.getParameter("itemType");
			
			
			//creating the hashmap for storing the data values
			
			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
			
			DB db = mongo.getDB("Database");
			
			DBCollection myProduct = db.getCollection("myProduct");
			System.out.println("Collection registerDetails selected successfully");
			
			BasicDBObject doc = new BasicDBObject("title", "myProduct").
				append("itemId", itemId).
				append("itemName", itemName).
				append("itemPrice", itemPrice).
				append("itemType", itemType).
				append("itemType", itemType);
				
				
				myProduct.insert(doc);
				}

				catch (Exception e) {
				e.printStackTrace();
		}
}
}