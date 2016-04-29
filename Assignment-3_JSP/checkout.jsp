<html>
	<head>
		<%@page language="java" %>
		<%@page import="java.util.*" %>
		<%@page import="java.io.*" %>
		<%@page import="javax.servlet.*" %>
		<%@page import="java.util.Date" %>
		<%@page import="com.mongodb.*"%>
		
		<meta http-equiv= "Content-Type" content="text/html; charset=utf-8" />
		<title>CHECK OUT PRODUCTS</title>
		<link rel="stylesheet" href="table.css" type="text/css" />
	</head>
	<body>

	<%
	
			MongoClient mongo;
            mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("CSP595Tutorial");
			DBCollection myCheckOut = db.getCollection("myCheckOut");
            BasicDBObject searchQuery = new BasicDBObject();
								
           DBCursor cursor = myCheckOut.find(searchQuery);
		 
				BasicDBObject obj = (BasicDBObject) cursor.next();
			%>
		   <h1>Check Out</h1>
		 <form method="get" action="checkOutOrder.jsp">
		<fieldset>
		<legend>Enter Your Details:</legend>
		<table>
		<tr>
	    <td> First Name: </td><td><input type="text" name="firstName"></td></tr>
		<tr><td> Last Name: </td><td><input type="text" name="lastName"></td></tr>
		<tr><td> Address: </td><td><input type="text" name="address"></td></tr>
		<td> Credit Card: </td><td><input type="password" name="creditCard"></td></tr>
	    </table>
		<br><br>
		<input type="submit" value="Submit Order">
		<input type="hidden" name="productDiscount" value=<%=obj.getString("productDiscount")%> readonly>
	    <input type="hidden" name="productManufacturerRebate" value=<%=obj.getString("productManufacturerRebate")%> readonly>
		</fieldset>
		</form>
		  
		</body>
		</html>
							
							