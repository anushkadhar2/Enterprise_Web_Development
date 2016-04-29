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

public class SubmitReview extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageHeading = "Submit Review";
		String pageContent =" ";
		String myPage = " ";
		String tableData = " ";
		int count = 0;
		
		response.setContentType("text/html");
		
		PrintWriter output = response.getWriter();
				
		try{
			//Get the values from the form
			String productCategory = request.getParameter("productCategory");
			String productName = request.getParameter("productName");
			int productPrice = Integer.parseInt(request.getParameter("productPrice"));
			String retailerName = request.getParameter("retailerName");
			String retailerZip = request.getParameter("retailerZip");
			String retailerCity = request.getParameter("retailerCity");
			String retailerState = request.getParameter("retailerState");
			String productOnSale = request.getParameter("productOnSale");
			//manufacturer name
			String manufacturerName = request.getParameter("manufacturerName");
			
			String manufacturerRebate = request.getParameter("manufacturerRebate");
			
			String userName = request.getParameter("userName");
			int userAge = Integer.parseInt(request.getParameter("userAge"));
			String userGender = request.getParameter("userGender");
			String userOccupation = request.getParameter("userOccupation");
			int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
			String reviewDate = request.getParameter("reviewDate");
			String reviewText = request.getParameter("reviewText");
										
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("Tutorial_3");
				
			// If the collection does not exists, MongoDB will create it for you
			DBCollection myReviews = db.getCollection("myReviews");
			System.out.println("Collection myReviews selected successfully");
				
			BasicDBObject doc = new BasicDBObject("title", "MongoDB").append("productCategory", productCategory)
					.append("productName", productName).append("productPrice", productPrice)
					.append("retailerName", retailerName).append("retailerZip", retailerZip).append("retailerCity", retailerCity)
					.append("retailerState", retailerState).append("productOnSale", productOnSale).append("userName", userName).append("userAge", userAge)
					.append("userGender", userGender).append("userOccupation", userOccupation)
					.append("manufacturerName", manufacturerName).append("manufacturerRebate", manufacturerRebate)
					.append("reviewRating", reviewRating).append("reviewDate", reviewDate)
					.append("reviewText", reviewText);
									
			myReviews.insert(doc);
				
			System.out.println("Document inserted successfully");
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("productName", productName);
	        searchQuery.put("userName", userName);

			DBCursor dbCursor = myReviews.find(searchQuery);
			
			while (dbCursor.hasNext()) {
				BasicDBObject bobj = (BasicDBObject) dbCursor.next();
				tableData =  "<tr><td>Category</td><td>" + bobj.getString("productCategory")
						+ "</td></tr>" + "<tr><td>Name</td><td>" + bobj.getString("productName") + "</td></tr>"
						+ "<tr><td>Price</td><td>" + bobj.getInt("productPrice") + "</td></tr>"
						+ "<tr><td>Retailer</td><td>" + bobj.getString("retailerName") + "</td></tr>"
						+ "<tr><td>Retailer Zipcode</td><td>" + bobj.getString("retailerZip") + "</td></tr>"
						+ "<tr><td>Retailer City </td><td>" + bobj.getString("retailerCity") + "</td></tr>"
						+ "<tr><td>Retailer State</td><td>" + bobj.getString("retailerState") + "</td></tr>"
						+ "<tr><td>Sale</td><td>" + bobj.getString("productOnSale") + "</td></tr>"
						+ "<tr><td>User ID</td><td>" + bobj.getString("userName") + "</td></tr>"
						+ "<tr><td>User Age</td><td>" + bobj.getString("userAge") + "</td></tr>"
						+ "<tr><td>User Gender</td><td>" + bobj.getString("userGender") + "</td></tr>"
						+ "<tr><td>User Occupation</td><td>" + bobj.getString("userOccupation") + "</td></tr>"
						+ "<tr><td>Manufacturer Name</td><td>" + bobj.getString("manufacturerName") + "</td></tr>"
						+ "<tr><td>Manufacturer Rebate</td><td>" + bobj.getString("manufacturerRebate") + "</td></tr>"
						+ "<tr><td>Rating</td><td>" + bobj.getString("reviewRating") + "</td></tr>"
						+ "<tr><td>Date</td><td>" + bobj.getString("reviewDate") + "</td></tr>"
						+ "<tr><td>Review Text</td><td>" + bobj.getString("reviewText") + "</td></tr>";
			}
			
			pageContent = "<table>"+tableData+"</table>"+"<h1>"+count+"</h1>";
			
			//Construct the page here
			myPage = "<!DOCTYPE html>" + "<html lang=\"en\">"
					+ "<head>	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
					+ "<title>GameZone</title>"
					+ "<link rel=\"stylesheet\" href=\"datastyles.css\" type=\"text/css\" />"
					+ "</head>"
					+ "<body>"
					+ "<div id=\"container\">"
					+ "<header>"
					+ "<h1><a href=\"/\">GameSpeed<span></span></a></h1><h2>Tutorial 3</h2>"
					+ "</header>"
					+ "<nav>"
					+ "<ul>"
					+ "<li class=\"\"><a href=\"index.html\">Home</a></li>"
					+ "<li class = \"\"><a href=\"Reviews\">Write Review</a></li>"
					+ "<li class = \"start selected\"><a href=\"DataAnalytics.html\">Data Analytics</a></li>"
					+ "</ul>"
					+ "</nav>"
					+ "<div id=\"body\">"
					+ "<section id=\"content\">"
					+ "<article>"
					+ "<h2 style=\"color:#DE2D3A;font-weight:700;\">"
					+ pageHeading + "</h2>" + pageContent + "</article>"
					+ "</section>"
					+ "<aside class=\"sidebar\">"
					+ "<ul>"	
					+ "<li>"
					+ "<h4>Options</h4>"
					+ "<ul>"
					+ "<li><a href=\"Reviews\">Write Reviews</a></li>"
					+ "<li><a href=\"DataAnalytics.html\">Data Analytics</a></li>"
					+ "</ul>"
					+ "</li>"
					+ "<li>"
					+ "<h4>About us</h4>"
					+ "<ul>"
					+ "<li class=\"text\">"
					+ "<p style=\"margin: 0;\">This is a sample website created to demonstrate a standard enterprise web page.</p>"
					+ "</li>"
					+ "</ul>"
					+ "</li>"
					+ "<li>"
					+ "<h4>Search site</h4>"
					+ "<ul>"
					+ "<li class=\"text\">"
					+ "<form method=\"get\" class=\"searchform\" action=\"#\" >"
					+ "<p>"
					+ "<input type=\"text\" size=\"25\" value=\"\" name=\"s\" class=\"s\" />"
					+ "</p>"
					+ "</form>"	
					+ "</li>"
					+ "</ul>"
					+ "</li>"
					+ "<li>"
					+ "<h4>Helpful Links</h4>"
					+ "<ul>"
					+ "<li><a href=\"http://www.w3schools.com/html/default.asp\" title=\"premium templates\">Learn HTML here</a></li>"
					+ "<li><a href=\"http://www.w3schools.com/css/default.asp\" title=\"web hosting\">Learn CSS here</a></li>"
					+ "</ul>"
					+ "</li>"
					+ "</ul>"
					+ "</aside>"
                    + "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<footer>"
					+ "<div class=\"footer-content\">"
					+ "<ul>"
                    + "<li>"
                    + "<h4>Dummy Link Section 1</h4>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link 1</a>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link 2</a>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link  3</a>"
                    + "</li>"
					+ "</ul>"
					+ "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<div class=\"footer-bottom\">"
					+ "<p>CSP 595 - Enterprise Web Application - Assignment#3</p>"
					+ "</div>"
					+ "</footer>"
					+ "</div>"
					+ "</body>"
					+ "</html>";

			output.println(myPage);

		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
	
	public void destroy()	{
      // do nothing.
	}
	
}