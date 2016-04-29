<html>
	<head>
		<%@page language="java"%>
		<%@page import = "java.util.*"%>
		<%@page import = "java.io.*"%>
		<%@page import = "java.text.*"%>
		<%@page import = "java.util.Date"%>
		<%@page import = "java.io.IOException"%>
		<%@page import = "com.mongodb.*"%>
		

		
	</head>
	<body>
<%
	        String productId = request.getParameter("productId");
			String productName = request.getParameter("productName");	
			String productPrice = request.getParameter("productPrice");
			String productType = request.getParameter("productType");
			String productDiscount = request.getParameter("productDiscount");
			String productManufacturerRebate = request.getParameter("productManufacturerRebate");
			
			//creating the hashmap for storing the data values
			
			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
			
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myProduct = db.getCollection("myProduct");
			System.out.println("Collection registerDetails selected successfully");
			
			BasicDBObject doc = new BasicDBObject("title", "myProduct").
				append("productId", productId).
				append("productName", productName).
				append("productPrice", productPrice).
				append("productType", productType).
				append("productDiscount", productDiscount).
				append("productManufacturerRebate", productManufacturerRebate);
				
				myProduct.insert(doc);
			
			%>
			<h1> Product Added Successfully</h1>
			<br>
		    <h3>To add more products</h1>
			<a href = "addProduct.html">Click here</a>
			<h3>To go back</h1>
			<a href = "storemanager.html">Click here</a>
			</body>
			</html>
				

		
