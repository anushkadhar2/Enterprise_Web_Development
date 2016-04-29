<html>
	<head>
		<%@page language="java"%>
		<%@page import = "java.util.*"%>
		<%@page import = "java.io.*"%>
		<%@page import = "java.text.*"%>
		<%@page import = "java.util.Date"%>
		<%@page import = "java.io.IOException"%>
		<%@page import = "com.mongodb.*"%>
		<link rel="stylesheet" href="webPage.css" type="text/css" />
	</head>
	<body>

	<%
	MongoClient mongo;
	mongo = new MongoClient("localhost", 27017);

			String FirstName = request.getParameter("firstName");
			String LastName = request.getParameter("lastName");
			String Password = request.getParameter("password");
			String ConfirmPassword = request.getParameter("confirmPassword");
			String Email = request.getParameter("email");
			String UserId = request.getParameter("userId");
			
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myRegistration = db.getCollection("myRegistration");
			System.out.println("Collection registerDetails selected successfully");
			
			
			BasicDBObject doc = new BasicDBObject("title", "myRegistration").
				append("firstName", FirstName).
				append("lastName", LastName).
				append("password", Password).
				append("confirmPassword", ConfirmPassword).
				append("email", Email);
				
				
				
				myRegistration.insert(doc);
				
				System.out.println("Document inserted successfully");
				
				
		%>
			
			<h1> Welcome <%=FirstName%></h1>
			<br/>
			<a href="index.html"> Click for Home Page</a>
			</body>
			</html>
		
			
		
	


