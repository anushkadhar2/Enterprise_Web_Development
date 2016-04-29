import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;

public class Sony extends HttpServlet {
	
				public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		        PrintWriter out = response.getWriter();
			
				
				
				out.println("<html>");
				out.println("<head>");
				out.println("<meta http-equiv= 'Content-Type' content='text/html; charset=utf-8' />");
	            out.println("<title>SONY GAME PRODUCTS</title>");
	            out.println("<link rel='stylesheet' href='webPage.css' type='text/css' />");
				out.println("</head>");
				
				out.println("<body>");
			    out.println("<div id='container'>");
				out.println(" <header>");
				out.println(" <h2>Welcome To SONY GAME PRODUCTS</h2>");
				out.println(" </header>");
				out.println("<nav  id='navigation_wrap'>");
				out.println("<ul>");
				out.println("<li class='current-menu-item'><a href='index.html'>Home</a></li>");
				out.println("<li><a>Console Manufacturer</a>");
				out.println("<ul>");
				out.println("<li><a href='Microsoft'>Microsoft</a></li>");
				out.println("<li><a href='Sony'>Sony</a></li>");
				out.println("<li><a href='Nintendo'>Nintendo</a></li>");
				out.println("</ul>");
				out.println("</li>");
				out.println("<li><a>Game Makers</a>");
				out.println("<ul>");
				out.println("<li><a href='ElectronicArts'>Electronic Arts</a></li>");
				out.println("<li><a href='Activision'>Activision</a></li>");
				out.println("<li><a href='TakeTwoInteractive'>Take Two Activision</a></li>");
				out.println("</ul>");
				out.println("</li>");
				out.println("<li><a href='Accessories'>Accessories</a></li>");
				out.println("</ul>");
				out.println("</nav>");
				out.println("<img class='header-image' src='images/img_PlayStation.jpg' width = '100%' height = '100%' alt='Wii' />");
				out.println("<div id='body'>");
				out.println("<section id='content'>");
				out.println("<article>");
				out.println("<h2>XBox</h2>");
				out.println("<h1>Types Of PlayStation</h1>");
				out.println("<p>Click on a model to purchase, write review or check the reviews.</p>");
				out.println("</article>");
				out.println("<article class='expanded'>");
				
				
				String searchParameter = "Sony";
				String searchField = "productType";
				
				MongoClient mongo;
				mongo = new MongoClient("localhost", 27017);
				
				// if database doesn't exists, MongoDB will create it for you
				DB db = mongo.getDB("CSP595Tutorial");
			
				DBCollection myProduct = db.getCollection("myProduct");
				
				// Find and display 
				BasicDBObject searchQuery = new BasicDBObject();
				searchQuery.put(searchField, searchParameter);
			
				DBCursor cursor = myProduct.find(searchQuery);
				
				while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject) cursor.next();
			
				out.println("<table>");
			    out.println("<tr>");
				out.println("<td> Product Id: </td><td><input type='text' name='productId' value ="+obj.getString("productId")+" readonly ></td></tr>");
				
				out.println("<tr>");
				out.println("<td> Product Name: </td><td><input type='text' name='productName' value ="+obj.getString("productName")+" readonly ></td></tr>");
				
				out.println("<tr>");
				out.println("<td> Product Price: </td><td><input type='text' name='productPrice' value ="+obj.getString("productPrice")+" readonly ></td></tr>");
				
				out.println("<tr>");
				out.println("<td> Product Discount: </td><td><input type='text' name='productDiscount' value ="+obj.getString("productDiscount")+" readonly ></td></tr>");
				
				out.println("<tr>");
				out.println("<td> Manufacturer Rebate: </td><td><input type='text' name='productManufacturerRebate' value ="+obj.getString("productManufacturerRebate")+" readonly ></td></tr>");
				
				out.println("<tr>");
				out.println("<td> Product Type: </td><td><input type='text' name='productType' value ="+obj.getString("productType")+" readonly ></td></tr>");
			
				out.println("<form class = 'submit-button' method = 'post' action = 'Reviews'>");
				out.println("<input class = 'submit-button' type = 'submit' name = 'writeReview' value = 'Write Review'>");
				out.println("<input  type = 'hidden' name = 'productId' value = "+obj.getString("productId")+" readonly>");
				out.println("<input  type = 'hidden' name = 'productName' value = "+obj.getString("productName")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productPrice' value = "+obj.getString("productPrice")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productType' value = "+obj.getString("productType")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productDiscount' value = "+obj.getString("productDiscount")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productManufacturerRebate' value = "+obj.getString("productManufacturerRebate")+"readonly>");
				out.println("</form>");
				
				out.println("<form class = 'submit-button' method = 'get' action = 'ViewReviews'>");
				out.println("<input class = 'submit-button' type = 'submit' name = 'viewReviews' value = 'View Reviews'>");
				out.println("<input  type = 'hidden' name = 'productId' value = "+obj.getString("productId")+" readonly>");
				out.println("<input  type = 'hidden' name = 'productName' value = "+obj.getString("productName")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productPrice' value = "+obj.getString("productPrice")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productType' value = "+obj.getString("productType")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productDiscount' value = "+obj.getString("productDiscount")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productManufacturerRebate' value = "+obj.getString("productManufacturerRebate")+"readonly>");
				out.println("</form>");
				
				out.println("<form class = 'submit-button' method = 'get' action = 'addToCart'>");
				out.println("<input class = 'submit-button' type = 'submit' name = 'addToCart' value= 'Add to Cart '>");
				out.println("<input  type = 'hidden' name = 'productId' value = "+obj.getString("productId")+" readonly>");
				out.println("<input  type = 'hidden' name = 'productName' value = "+obj.getString("productName")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productPrice' value = "+obj.getString("productPrice")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productType' value = "+obj.getString("productType")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productDiscount' value = "+obj.getString("productDiscount")+"readonly>");
				out.println("<input  type = 'hidden' name = 'productManufacturerRebate' value = "+obj.getString("productManufacturerRebate")+"readonly>");
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
		