<html>
	<head>
		<%@page language="java" %>
		<%@page import="java.util.*" %>
		<%@page import="java.io.*" %>
		<%@page import="javax.servlet.*" %>
		<%@page import="java.util.Date" %>
		<%@page import="com.mongodb.*"%>
		
		<meta http-equiv= "Content-Type" content="text/html; charset=utf-8" />
		<title>TAKETWOACTIVISION GAME PRODUCTS</title>
		<link rel="stylesheet" href="webPage.css" type="text/css" />
	</head>
	<body>
			<div id="container">
			<header>
			<h1> TakeTwoActivision Products </h1>
			<h2>Welcome To TakeTwoActivision GAME PRODUCTS</h2>
			</header>
			<nav  id="navigation_wrap">
			<ul>
			<li class="current-menu-item"><a href="welcomeCustomer.html">Home</a></li>
			<li><a>Console Manufacturer</a>
			<ul>
			<li><a href="Microsoft.jsp">Microsoft</a></li>
			<li><a href="Sony.jsp">Sony</a></li>
			<li><a href="Nintendo.jsp">Nintendo</a></li>
			</ul>
			</li>
			<li><a>Game Makers</a>
			<ul>
			<li><a href="ElectronicArts.jsp">Electronic Arts</a></li>
			<li><a href="Activision.jsp">Activision</a></li>
			<li><a href="TakeTwoActivision.jsp">Take Two Activision</a></li>
			</ul>
			</li>
			<li><a href="Accessories.jsp">Accessories</a></li>
			</ul>
			</nav>
			<img class="header-image" src="images/img_takeTwoInteractive.jpg" width = "100%" height = "100%" alt="xbox" />
			<div id="body">
			<section id="content">
			<article>
			<h2>TAKETWOACTIVISION</h2>
			<h1>Types Of TAKETWOACTIVISION Products</h1>
			<p>Click on a model to purchase, write review or check the reviews.</p>
			</article>
			<article class="expanded">
				
				<%
		
			String searchParameter = "TakeTwoActivision";
			String searchField = "productType";
	
			MongoClient mongo;
			mongo = new MongoClient("localhost", 27017);
	
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myProduct = db.getCollection("myProduct");
			
			// Find and display 
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put(searchField, searchParameter);
			
			DBCursor cursor = myProduct.find(searchQuery);
			
			while(cursor.hasNext()){
				BasicDBObject obj = (BasicDBObject) cursor.next();
				%>
				<table>
				<tr>
				<td><h4>Product ID:</h4></td>
				<td><input type="text"  name="productId" value=<%=obj.getString("productId")%> readonly></td>
				</tr>
				
				<tr>
				<td><h4>Product Name:</h4></td>
				<td><input type= "text"  name="productName" value= <%=obj.getString("productName")%> readonly></td>
			   </tr>
					
				<tr>
				<td>
				<h4>Product Price: </h4>
				</td>
				<td>
				<input type="text" name="productPrice" value=<%=obj.getString("productPrice")%> readonly>
				</td>
				</tr>
				<tr>
				<td>
				<h4>Product Type:</h4>
				</td>
				<td>
			    <input type="text" name="productType" value= <%=obj.getString("productType")%> readonly>
				</td>
				</tr>
					
			
				<form class = "submit-button" method = "post" action = "Reviews.jsp">
				<input class = "submit-button" type = "submit" name = "writeReview" value = "Write Review">
				<input type="hidden" name="productId" value=<%=obj.getString("productId")%> readonly>
				<input type="hidden" name="productName" value=<%=obj.getString("productName")%> readonly>
				<input type="hidden" name="productPrice" value=<%=obj.getString("productPrice")%> readonly>
			    <input type="hidden" name="productType" value=<%=obj.getString("productType")%> readonly>
				<input type="hidden" name="productDiscount" value=<%=obj.getString("productDiscount")%> readonly>
				<input type="hidden" name="productManufacturerRebate" value=<%=obj.getString("productManufacturerRebate")%> readonly>
				</form>
				
				<form class = "submit-button" method ="get" action = "ViewReviews.jsp">
				<input class = "submit-button" type = "submit" name = "viewReviews" value = "View Reviews">
				<input type="hidden" name="productId" value=<%=obj.getString("productId")%> readonly>
				<input type="hidden" name="productName" value=<%=obj.getString("productName")%> readonly>
				<input type="hidden" name="productPrice" value=<%=obj.getString("productPrice")%> readonly>
			    <input type="hidden" name="productType" value=<%=obj.getString("productType")%> readonly>
				<input type="hidden" name="productDiscount" value=<%=obj.getString("productDiscount")%> readonly>
				<input type="hidden" name="productManufacturerRebate" value=<%=obj.getString("productManufacturerRebate")%> readonly>
				</form>
				
				<form class = "submit-button" method = "get" action = "Addtocart.jsp">
				<input class = "submit-button" type = "submit" name = "addToCart" value= "Add to Cart">
				<input type="hidden" name="productId" value=<%=obj.getString("productId")%> readonly>
				<input type="hidden" name="productName" value=<%=obj.getString("productName")%> readonly>
				<input type="hidden" name="productPrice" value=<%=obj.getString("productPrice")%> readonly>
			    <input type="hidden" name="productType" value=<%=obj.getString("productType")%> readonly>
				<input type="hidden" name="productDiscount" value=<%=obj.getString("productDiscount")%> readonly>
				<input type="hidden" name="productManufacturerRebate" value=<%=obj.getString("productManufacturerRebate")%> readonly>
				</form>
				</table>
				
				
					
			</article>
			<%}
			%>
			</div>
				
				
		   <footer>
	           
		   <div class='footer-bottom'>
		   <p>@CopyRight GameSpeed WebSite</p>
		  </div>
		
		</footer>
				
		


</body>
</html>

