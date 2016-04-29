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
		<link rel="stylesheet" href="table.css" type="text/css" />
	</head>
	<body>
	 
	<%
		
		String productName = request.getParameter("productName");
		String productPrice = request.getParameter("productPrice");
		String productManufacturerRebate = request.getParameter("productManufacturerRebate");
		String productDiscount = request.getParameter("productDiscount");
		
		
        Cart shoppingCart;	
        shoppingCart = (Cart) session.getAttribute("Cart");
        if(shoppingCart == null){
          shoppingCart = new Cart();
          session.setAttribute("Cart", shoppingCart);
        }  
		List<String> c = new ArrayList();
		c.add(productPrice);
		c.add(productManufacturerRebate);
		c.add(productDiscount);
		shoppingCart.Addtocart(productName, c);
		session.setAttribute("Cart", shoppingCart);
		
		MongoClient mongo;
		mongo = new MongoClient("localhost", 27017);
		
		DB db = mongo.getDB("CSP595Tutorial");
		// If the collection does not exists, MongoDB will create it for you
		DBCollection myCart = db.getCollection("myCart");
		
		BasicDBObject doc = new BasicDBObject("title", "myCart").
			append("productName", productName).
			append("productPrice", productPrice).
			append("productManufacturerRebate", productManufacturerRebate).
			append("productDiscount", productDiscount);			
			myCart.insert(doc);
		
		System.out.println("Document inserted successfully");
		HashMap<String, List<String>> product = shoppingCart.getCartItems();
		%>
		
	<h1>Items in Cart</h1>
    <table border='2'>
   <tr>
	<th>Product Name</th>
	<th>Price</th>
	<th> Manufacturer Rebate</th>
    <th>Discount Price</th>
	<th></th>
	</tr>
	<%
		for(String key: product.keySet()){
			List<String> items = product.get(key);
			%>
			<tr>
			<td><%=key%></td>
		    <td><%=items.get(0)%></td>
			<td><%=items.get(1)%></td>
		    <td><%=items.get(2)%></td>
			<td><form method="get" action="deleteItem.jsp">
			<input type="hidden" name="key" value=<%=key%>>
			<input type="hidden" name="productPrice" value= <%=items.get(0)%>>
			<input type="hidden" name="rebate" value=<%=items.get(1)%>>
			<input type="hidden" name="discount" value=<%=items.get(2)%>>
			<input type="submit" value="delete"></form></td>
			</tr>
		<%} 	%>		
	    </table>
		<br/>
		<form action="welcomeCustomer.html"><input type="submit" value="Add More Items"></form>
		<form method="get" action="checkout.jsp"><input type="submit" value="Proceed to Checkout"></form>		
		</body>	
		</html>
