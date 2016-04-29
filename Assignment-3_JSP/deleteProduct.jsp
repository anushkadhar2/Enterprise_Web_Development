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

		   String searchParameter = request.getParameter("productId");	

		   DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myProduct = db.getCollection("myProduct");
			System.out.println("Collection registerDetails selected successfully");
			
			BasicDBObject query = new BasicDBObject();
				query.append("productId",searchParameter);
				myProduct.remove(query);
			
			%>
			<h1> Product Removed Successfully</h1>
			<br>
		    <h3>To remove more products</h1>
			<a href = 'deleteProduct.html'>Click here</a>
			<h3>To go back</h1>
			<a href = 'storemanager.html'>Click here</a>
			</body>
			</html>
				

		
