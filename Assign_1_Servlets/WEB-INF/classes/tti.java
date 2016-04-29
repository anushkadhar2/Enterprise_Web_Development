import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;

public class tti extends HttpServlet {
	
				public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		        PrintWriter out = response.getWriter();
			
				
				
				out.println("<html>");
				out.println("<head>");
				out.println("<meta http-equiv= 'Content-Type' content='text/html; charset=utf-8' />");
	            out.println("<title>MICROSOFT GAME PRODUCTS</title>");
	            out.println("<link rel='stylesheet' href='styles.css' type='text/css' />");
				out.println("</head>");
				
				out.println("<body>");
			    out.println("<div id='container'>");
				out.println(" <header>");
				out.println(" <h2>Welcome To MICROSOFT GAME PRODUCTS</h2>");
				out.println(" </header>");
				out.println("<nav  id='navigation_wrap'>");
				out.println("<ul>");
				out.println("<li class='current-menu-item'><a href='index.html'>Home</a></li>");
				//out.println("<li><a>Console Manufacturer</a>");
				out.println("<ul>");
				out.println("<li><a href='Microsoft'>Microsoft</a></li>");
				out.println("<li><a href='Sony'>Sony</a></li>");
				out.println("<li><a href='Nintendo'>Nintendo</a></li>");
				out.println("<li><a href='Accessories'>Accessories</a></li>");
				out.println("</ul>");
				out.println("</li>");
				//out.println("<li><a>Game Makers</a>");
				out.println("<ul>");
				
				out.println("</ul>");
				out.println("</li>");
				
				out.println("</ul>");
				out.println("</nav>");
				out.println("<img class='header-image' src='images/img_XBox.jpg' width = '100%' height = '100%' alt='xbox' />");
				out.println("<div id='body'>");
				out.println("<section id='content'>");
				out.println("<article>");
				
				
				out.println("</article>");
				out.println("<article class='expanded'>");
				
				
				
				String searchParameter = "tti";
				String searchField = "itemType";
				
				MongoClient mongo;
				mongo = new MongoClient("localhost", 27017);
				
				// if database doesn't exists, MongoDB will create it for you
				DB db = mongo.getDB("Database");
			
				DBCollection myProduct = db.getCollection("myProduct");
				
				// Find and display 
				BasicDBObject searchQuery = new BasicDBObject();
				searchQuery.put(searchField, searchParameter);
			
				DBCursor cursor = myProduct.find(searchQuery);
				
				while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject) cursor.next();
			
				out.println("<table>");
			    out.println("<tr>");
				out.println("<td> Item Id: </td><td><input type='text' name='itemId' value ="+obj.getString("itemId")+" readonly ></td></tr>");
				
				out.println("<tr>");
				out.println("<td> Item Name: </td><td><input type='text' name='itemName' value ="+obj.getString("itemName")+" readonly ></td></tr>");
				
				out.println("<tr>");
				out.println("<td> Item Price: </td><td><input type='text' name='itemPrice' value ="+obj.getString("itemPrice")+" readonly ></td></tr>");
				
				out.println("<tr>");
				out.println("<td> Item Type: </td><td><input type='text' name='itemType' value ="+obj.getString("itemType")+" readonly ></td></tr>");
			
				out.println("<form class = 'submit-button' method = 'post' action = 'review'>");
				out.println("<input class = 'submit-button' type = 'submit' name = 'writeReview' value = 'Write Review'>");
				out.println("<input  type = 'hidden' name = 'itemId' value = "+obj.getString("itemId")+">");
				out.println("<input  type = 'hidden' name = 'itemName' value = "+obj.getString("itemName")+">");
				out.println("<input  type = 'hidden' name = 'itemPrice' value = "+obj.getString("itemPrice")+">");
				out.println("<input  type = 'hidden' name = 'itemType' value = "+obj.getString("itemType")+">");
				
				out.println("</form>");
				
				out.println("<form class = 'submit-button' method = 'get' action = 'ViewReviews'>");
				out.println("<input class = 'submit-button' type = 'submit' name = 'viewReviews' value = 'View Reviews'>");
				out.println("<input  type = 'hidden' name = 'itemId' value = "+obj.getString("itemId")+">");
				out.println("<input  type = 'hidden' name = 'itemName' value = "+obj.getString("itemName")+">");
				out.println("<input  type = 'hidden' name = 'itemPrice' value = "+obj.getString("itemPrice")+">");
				out.println("<input  type = 'hidden' name = 'itemType' value = "+obj.getString("itemType")+">");
				
				out.println("</form>");
				
				out.println("<form class = 'submit-button' method = 'get' action = 'addtocart'>");
				out.println("<input class = 'submit-button' type = 'submit' name = 'addToCart' value= 'Add to Cart '>");
				out.println("<input  type = 'hidden' name = 'itemId' value = "+obj.getString("itemId")+" readonly>");
				out.println("<input  type = 'hidden' name = 'itemName' value = "+obj.getString("itemName")+"readonly>");
				out.println("<input  type = 'hidden' name = 'itemPrice' value = "+obj.getString("itemPrice")+"readonly>");
				out.println("<input  type = 'hidden' name = 'itemType' value = "+obj.getString("itemType")+"readonly>");
				
				out.println("</form>");
				out.println("</table>");
				
				
				out.println("</article>");
				
				
			}
			
			   
			
				out.println("</div>");
				
				
				out.println("<footer>");
	           
				out.println("<div class='footer-bottom'>");
				out.println("<p>@CopyRight GameSpeed WebSite</p>");
				out.println("</div>");
		
				out.println("</footer>");
				
				
				out.println("</body>");
				out.println("</html>");
			
			
				
				}
			}
		