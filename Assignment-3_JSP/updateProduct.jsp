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
			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
    
		   	String productId = request.getParameter("productId");
			
			DB db = mongo.getDB("CSP595Tutorial");
			DBCollection myProduct = db.getCollection("myProduct");
			System.out.println("Collection registerDetails selected successfully");
			
			BasicDBObject query = new BasicDBObject();
			query.append("productId",productId);
			myProduct.remove(query);
			
			String productName = request.getParameter("productName");	
			String newProductPrice = request.getParameter("newProductPrice");
			String newProductDiscount = request.getParameter("newProductDiscount");
			String newProductManufacturerRebate = request.getParameter("newProductManufacturerRebate");
			String productType = request.getParameter("productType");
			
			BasicDBObject doc = new BasicDBObject("title", "myProduct").
				append("productId", productId).
				append("productName", productName).
				append("productDiscount", newProductDiscount).
				append("productManufacturerRebate", newProductManufacturerRebate).
				append("productPrice", newProductPrice).
				append("productType", productType);
				
				myProduct.insert(doc);
			
			%>
			<h1> Product Update Successfully</h1>
			<br>
		    <h3>To update more products</h1>
			<a href = 'addProduct.html'>Click here</a>
			<h3>To go back</h1>
			<a href = 'storemanager.html'>Click here</a>
			</body>
			</html>
				

		
