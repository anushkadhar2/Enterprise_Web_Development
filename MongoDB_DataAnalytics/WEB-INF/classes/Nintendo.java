import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Nintendo extends HttpServlet {
	
				public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		        PrintWriter out = response.getWriter();
			
				
				
				out.println("<html>");
				out.println("<head>");
				out.println("<meta http-equiv= 'Content-Type' content='text/html; charset=utf-8' />");
	            out.println("<title>NINTENDO GAME PRODUCTS</title>");
	            out.println("<link rel='stylesheet' href='webPage.css' type='text/css' />");
				out.println("</head>");
				
				out.println("<body>");
			    out.println("<div id='container'>");
				out.println(" <header>");
				out.println(" <h2>Welcome To NINTENDO GAME PRODUCTS</h2>");
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
				out.println("<li><a href='TakeTwoActivision'>Take Two Activision</a></li>");
				out.println("</ul>");
				out.println("</li>");
				out.println("<li><a href='Accessories'>Accessories</a></li>");
				out.println("</ul>");
				out.println("</nav>");
				out.println("<img class='header-image' src='images/img_wii.jpg' width = '100%' height = '100%' alt='xbox' />");
				out.println("<div id='body'>");
				out.println("<section id='content'>");
				out.println("<article>");
				out.println("<h2>XBox</h2>");
				out.println("<h1>Types Of Nintendo</h1>");
				out.println("<p>Click on a model to purchase, write review or check the reviews.</p>");
				out.println("</article>");
				out.println("<article class='expanded'>");
				
				
				
				String token_new = " " ;
				BufferedReader buffer_reader = new BufferedReader(new FileReader("C:/apache-tomcat-7.0.34/webapps/Assignment/itemDetails.txt"));
		
					String currentLine = "";
					while((currentLine=buffer_reader.readLine())!=null ){
			
				String temp[] = currentLine.split("-");
				String temp1[] = temp[1].split(",|\\[|\\]");
			
					
				token_new = temp[0];
			
				String str  = token_new.substring(0,3);
			
				if(str.equals("Nin")){
				
				
			    out.println("<h4>Type :</h4>");
	            out.println("<table>");
			    out.println("<tr>");
				out.println("<td> Product Id: </td><td><input type='text' name='productId' value ="+token_new+" readonly ></td></tr>");
				out.println("<tr><td> Product Name: </td><td><input type='text' name = 'productName' value="+temp1[1]+" readonly></td></tr>");
				out.println("<tr><td> Product Price: </td><td><input type='text' name = 'productPrice' value="+temp1[2]+" readonly></td></tr>");
				
				
				
				
				out.println("<form class = 'submit-button' method = 'post' action = 'Reviews'>");
				out.println("<input class = 'submit-button' type = 'submit' name = 'writeReview' value = 'Write Review'>");
				out.println("<input  type = 'hidden' name = 'productId' value = "+token_new+">");
				out.println("<input  type = 'hidden' name = 'productName' value = "+temp1[1]+">");
				out.println("<input  type = 'hidden' name = 'productPrice' value = "+temp1[2]+">");
				out.println("<input  type = 'hidden' name = 'productType' value = "+temp1[3]+">");
				out.println("</form>");
				
				out.println("<form class = 'submit-button' method = 'get' action = 'ViewReviews'>");
				out.println("<input class = 'submit-button' type = 'submit' name = 'viewReviews' value = 'View Reviews'>");
				out.println("<input  type = 'hidden' name = 'productId' value = "+token_new+">");
				out.println("<input  type = 'hidden' name = 'productName' value = "+temp1[1]+">");
				out.println("<input  type = 'hidden' name = 'productPrice' value = "+temp1[2]+">");
				out.println("</form>");
				
				out.println("<form class = 'submit-button' method = 'get' action = 'addToCart'>");
				out.println("<input class = 'submit-button' type = 'submit' name = 'addToCart' value= 'Add to Cart '>");
				out.println("<input  type = 'hidden' name = 'productId' value = "+token_new+">");
				out.println("<input  type = 'hidden' name = 'productName' value = "+temp1[1]+">");
				out.println("<input  type = 'hidden' name = 'productPrice' value = "+temp1[2]+">");
				out.println("</form>");
				out.println("</table>");
				
				
				out.println("</article>");
				
				
			}
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
		