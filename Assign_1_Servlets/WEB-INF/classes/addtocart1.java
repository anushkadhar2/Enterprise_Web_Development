import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;



	public class addtocart1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        cart1 shoppingCart;
        shoppingCart = (cart1) session.getAttribute("cart");
        if(shoppingCart == null){
          shoppingCart = new cart1();
          session.setAttribute("cart", shoppingCart);
        }
        String name = request.getParameter("name");
        Integer price = Integer.parseInt(request.getParameter("price"));
        shoppingCart.addToCart(name, price);
        session.setAttribute("cart", shoppingCart);
		
		// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
				
			// If the collection does not exists, MongoDB will create it for you
			DBCollection myReviews = db.getCollection("myReviews");
			System.out.println("Collection myReviews selected successfully");
			
			BasicDBObject doc = new BasicDBObject("title", "myReviews").
				append("name", name).
				append("price", price);
				myReviews.insert(doc);
				
			System.out.println("Document inserted successfully");
			
		
        PrintWriter out = response.getWriter();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>result</title>");
			out.println("<link rel='stylesheet' href='loginstyle.css' type='text/css' />");             
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Item successfully added to cart </h1>");
            out.println("<form action='welcome.html'>Add more items <input type='submit' value='go'></form>");
            out.println("<hr>");
            out.println("<h2>Cart</h2>");
            HashMap<String, Integer> items = shoppingCart.getCartItems();
            out.println("<table border='1px'>");
             
           for(String key: items.keySet())
			{
			out.println("<form action='deleteItem'><input type='hidden' name='name' value='"+key+"'><tr><td>"+key+" - </td><td>"+"$"+items.get(key)+"</td><td><input type='submit' value='delete'></td></tr></form>");
			}
			
			out.println("</table>");
			out.println("</br></br></br></br>");
			//String checkoutURL =
            response.encodeURL("OrderForm.html");
			out.println("<form action= 'buyfromcart' method='get'><input type='submit' value='Proceed To Checkout'>");
			
			
			out.println("</body>");
			out.println("</html>");
             
        
    }
}
	
	
	
	
	
	
	
	
	
	
	
	
	