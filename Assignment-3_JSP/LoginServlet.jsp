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
   
   String userEmail = " ";
   String userPassword = " ";
   
   String email = request.getParameter("email");
   String password = request.getParameter("password");
   String userType = request.getParameter("userType");
   
   MongoClient mongo;
   mongo = new MongoClient("localhost", 27017);
   DB db = mongo.getDB("CSP595Tutorial");
   DBCollection myRegistration = db.getCollection("myRegistration");
   
   
   String managerEmail = "manager";
   String managerPassword = "manager";
   
   String salesmenEmail = "salesmen";
   String salesmenPassword = "salesmen";
		
		if(userType.equals("Manager"))
		{
		if((email.equals(managerEmail))&&(password.equals(managerPassword)))
		{   
	        response.sendRedirect("storemanager.html");
			
		}
		else
			{
			response.setContentType("text/html");
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Login Failed</h1>");
			out.println("</body>");
			out.println("</html>");
			}
		}
		else if(userType.equals("Salesmen"))
		{
			if((email.equals(salesmenEmail))&&(password.equals(salesmenPassword)))
			{
				 response.sendRedirect("salesmen.html");
			
			}
			else
			{ %>
			<html>
			<head>
			</head>
			<body>
			<h1> Login Failed</h1>
			</body>
			</html>
			<%}
		}
		else if(userType.equals("Customer"))
		{
         BasicDBObject searchQuery = new BasicDBObject();
		 searchQuery.put("email", email);

			DBCursor cursor = myRegistration.find(searchQuery);
			while(cursor.hasNext())
			{
				BasicDBObject bdo = (BasicDBObject)cursor.next();
				userEmail =  bdo.getString("email");
				userPassword = bdo.getString("password");
				
				if((userEmail.equals(email))&&(userPassword.equals(password)))
			{
				 response.sendRedirect("welcomeCustomer.html");
			
			}
			else
			{ 
		%>
				
					<html>
					<head> </head>
					<body>
					<h1>'Login Failed'</h1>
					</body>
					</html>
		<%	}
			}
		}
			%>	
		
    
   