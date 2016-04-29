<html>
	<head>
			<%@page language="java" %>
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
		
		<meta http-equiv= "Content-Type" content="text/html; charset=utf-8" />
		<title>PlACE ORDER</title>
		<link rel="stylesheet" href="webPage.css" type="text/css" />
	</head>
	<body>

		<%
		MongoClient mongo;
	
		mongo = new MongoClient("localhost", 27017);
	
	
			
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String address = request.getParameter("address");
			String creditCard = request.getParameter("creditCard");
			String productManufacturerRebate = request.getParameter("productManufacturerRebate");
			String productDiscount = request.getParameter("productDiscount");
			
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			// If the collection does not exists, MongoDB will create it for you
			DBCollection myCheckOut = db.getCollection("myCheckOut");
			System.out.println("Collection myOrders selected successfully");
			
			
			Random r = new Random( System.currentTimeMillis() );
				int rand= 900 + r.nextInt(4000);
				String orderId=Integer.toString(rand);
				
				
				Date systemDate = new Date();
				String orderDate = systemDate.toString();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				Calendar cal = Calendar.getInstance();
				
				cal.add(Calendar.DATE, 14); // Adding 14 days
				String deliveryDate = sdf.format(cal.getTime());
				
				
			BasicDBObject doc = new BasicDBObject("title", "myCheckOut").
				append("orderId", orderId).
				append("orderDate", orderDate).
				append("deliveryDate",deliveryDate).
				append("firstName", firstName).
				append("lastName", lastName).
				append("address", address).
				append("productManufacturerRebate", productManufacturerRebate).
				append("productDiscount", productDiscount).
				append("creditCard", creditCard);
					
				myCheckOut.insert(doc);
			
				System.out.println("Document inserted successfully");
			
			
				
				
				
				%>
				
				<center>
				<h1> Order placed Successfully</h1>
				<br>
				<h3> Confirmation Number: <%=orderId%></h3>
				<br>
				<h4> Order Date:   <%=orderDate %></h4>
				<br>
				<h4> Delivery Date:   <%=deliveryDate %></h4>
				<br>
                First Name :   <%=firstName%>
				<br>
				Last Name :  <%=lastName%>
				<br>
				Address : <%=address%>
				<br>
				
				<br>
				<form action = "welcomeCustomer.html">
				<input type = "submit" name = "home" value = "Return Home">
				</form>
				
				</center>
				</body>
				</html>
				
		
		
	