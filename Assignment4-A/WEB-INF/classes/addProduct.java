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
			String productId = request.getParameter("productId");
			String productName = request.getParameter("productName");	
			String productPrice = request.getParameter("productPrice");
			String productType = request.getParameter("productType");
			String productDiscount = request.getParameter("productDiscount");
			String productManufacturerRebate = request.getParameter("productManufacturerRebate");
			
			//creating the hashmap for storing the data values
			
			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
			
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myProduct = db.getCollection("myProduct");
			System.out.println("Collection registerDetails selected successfully");
			
			BasicDBObject doc = new BasicDBObject("title", "myProduct").
				append("productId", productId).
				append("productName", productName).
				append("productPrice", productPrice).
				append("productType", productType).
				append("productDiscount", productDiscount).
				append("productManufacturerRebate", productManufacturerRebate);
				
				myProduct.insert(doc);
				}

				catch (Exception e) {
				e.printStackTrace();
		}
}
}