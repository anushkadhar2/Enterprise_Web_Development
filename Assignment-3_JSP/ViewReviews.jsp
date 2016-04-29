<html>
	<head>
		<%@page language="java" %>
		<%@page import="java.util.*" %>
		<%@page import="java.io.*" %>
		<%@page import="javax.servlet.*" %>
		<%@page import="java.util.Date" %>
		<%@page import="com.mongodb.*"%>
		
		<meta http-equiv= "Content-Type" content="text/html; charset=utf-8" />
		<title>View Review</title> 
	</head>
	<body>
	
	<%
		MongoClient mongo;
	    mongo = new MongoClient("localhost", 27017);
	    String searchField = "productName";
	    String searchParameter = request.getParameter("productName");
			
			
		DB db = mongo.getDB("CSP595Tutorial");
			
		DBCollection Reviews = db.getCollection("Reviews");
			
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put(searchField, searchParameter);

			
		DBCursor cursor = Reviews.find(searchQuery);
			
				%>		
			
			<h1> Reviews For:<%=searchParameter%></h1>
			
			<table>
			
			<tr>
			<td>
			<a href="welcomeCustomer.html"> Home </a>
			</td>
			</tr>
			
			<tr>
			<td>
			<a href="Microsoft.jsp"> Microsoft </a>
			</td>
			</tr>
			
			<tr>
			<td>
			<a href="Sony.jsp"> Play Station </a>
			</td>
			</tr>
			
			
			<tr>
			<td>
			<a href="Nintendo.jsp"> Wii </a>
			</td>
			</tr>
			
			</table>
			<br><br><hr>
			
			<%
			if(cursor.count() == 0){
				out.println("There are no reviews for this product.");
			}else
			{
			%>
				<table>
				
				<%
				String productName = "";
				String userName = "";
				String reviewRating = "";
				String reviewDate =  "";
				String reviewText = "";
				
				while (cursor.hasNext()) {
					//out.println(cursor.next());
					BasicDBObject obj = (BasicDBObject) cursor.next();				
					
					%>
					<tr>
					<td> Product Name: </td>
					
					
					<td><%=obj.getString("productName")%></td>
					</tr>
					
					<tr>
					<td> User Name: </td>
					
					<td> <%=obj.getString("userName")%></td>
					</tr>
					
					<tr>
					<td> Review Rating: </td>
					
					<td><%=obj.getString("reviewRating")%></td>
					</tr>
					
					<tr>
					<td> Review Date: </td>
					
					<td><%=obj.getString("reviewDate")%></td>
					</tr>
					
					<tr>
					<td> Review Text: </td>
					
					<td> <%=obj.getString("reviewText")%></td>
					</tr>
					
				<%} %>
			<%}	
			%>
				</table>
				</body>
			    </html>
		
