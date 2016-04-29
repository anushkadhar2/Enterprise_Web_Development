<html>
	<head>
		<%@page language="java" %>
		<%@page import="java.util.*" %>
		<%@page import="java.io.*" %>
		<%@page import="javax.servlet.*" %>
		<%@page import="java.util.Date" %>
		<%@page import="com.mongodb.*"%>
		
		<meta http-equiv= "Content-Type" content="text/html; charset=utf-8" />
		<title>Submit Review</title> 
	</head>
	<body>

<%
	
	MongoClient mongo;

		mongo = new MongoClient("localhost", 27017);

			String productName = request.getParameter("productName");
			String userName = request.getParameter("userName");
			String reviewRating = request.getParameter("reviewRating");	
			String reviewDate = request.getParameter("reviewDate");
			String reviewText = request.getParameter("reviewText");
										
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
				
			// If the collection does not exists, MongoDB will create it for you
			DBCollection Reviews = db.getCollection("Reviews");
			System.out.println("Collection myReviews selected successfully");
				
			BasicDBObject doc = new BasicDBObject("title", "Reviews").
				append("productName", productName).
				append("userName", userName).
				append("reviewRating", reviewRating).
				append("reviewDate", reviewDate).
				append("reviewText", reviewText);
									
			Reviews.insert(doc);
				
			System.out.println("Document inserted successfully");
			%>
			<html>
			<head> </head>
			<body>
			<h1> Review submitted for:<%=productName%></h1>
			
			<table>
			
			<tr>
			<td>
			<a href="welcomeCustomer.html"> Index </a>
			</td>
			</tr>
			
			<tr>
			<td>
			<a href="Microsoft.jsp"> Microsoft </a>
			</td>
			</tr>
			
			<tr>
		    <td>
			<a href='Sony.jsp'> Play Station </a>
		    </td>
			</tr>
			
			<tr>
			<td>
			<a href='Nintendo.jsp'> Nintendo </a>
			</td>
			</tr>
			
			</table>
			
		    </body>
			</html>

	
