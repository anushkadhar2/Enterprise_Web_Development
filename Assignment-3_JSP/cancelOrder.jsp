<html>
	<head>
		<%@ page language="java" %>
		<%@page import="java.util.*" %>
		<%@page import="java.io.*" %>
		<%@page import="java.text.*" %>
		<%@page import="javax.servlet.*" %>
		<%@page import="javax.servlet.http.*" %>
		<%@page import="com.mongodb.*"%>
		<%@page import="java.io.IOException"%>
		<%@page import="java.util.*"%>
		<%@page import="java.io.*"%>
		<%@page import="java.util.HashMap"%>
		<%@page import="com.link.assignment.Cart"%>
		<link rel='stylesheet' href='table.css' type='text/css' />
	</head>
	<body>
	<%
		    MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
			
			String searchParameter = request.getParameter("orderId");
			
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			// If the collection does not exists, MongoDB will create it for you
			DBCollection myCheckOut = db.getCollection("myCheckOut");
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("orderId", searchParameter);
			
			DBCursor cursor = myCheckOut.find(searchQuery);
			String deliveryDate ="";
			while(cursor.hasNext()) {
				BasicDBObject searchObj = (BasicDBObject)cursor.next();
				deliveryDate = searchObj.getString("deliveryDate");	
				
			}
			
			
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date dateObj = new Date();
				
				Date dateDelivery = null;
				Date dateToday = null;
				
				String today=dateFormat.format(dateObj);
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
 		
		
		        dateDelivery= format.parse(deliveryDate);
				dateToday = format.parse(today);
			
				
			long difference=0;
			long cal = dateDelivery.getTime() - dateToday.getTime();
			difference = cal / (24 * 60 * 60 * 1000);
			String dateDifference= Long.toString(difference);
			%>
			<h1>CANCEL ORDER STATUS</h1>
			<table border="2">
		
			<tr>
			<td>Date Difference</td>
			<td><%=dateDifference%></td>
		     </tr>
			 <%
			int i = Integer.valueOf(dateDifference);%>
			<tr>
			<td>Shipment Status</td>
			<%
			if(i<5){
				out.println("<td>Item can't be deleted</td>");
			}
			
			else{
				myCheckOut.remove(searchQuery);
				out.println("<td>Order cancelled</td>");
			}
			%>
			</tr>
		
			</table>
			   
			<a href='welcomeCustomer.html'>Go Back</a>
			
			</body>
			</html>
		