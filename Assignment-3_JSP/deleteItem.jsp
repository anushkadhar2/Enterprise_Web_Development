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
		<title> deleteItem</title>
	</head>
	<body>

	<%
        
        String name = request.getParameter("name");
        
        Cart shoppingCart;
        shoppingCart = (Cart) session.getAttribute("Cart");
        shoppingCart.deleteFromCart(name);
        session.setAttribute("Cart", shoppingCart);
         shoppingCart = (Cart) session.getAttribute("Cart");
      %>
          
            
			<%
            HashMap<String, Integer> items = shoppingCart.getCartItems();
			%>
			<h1> Items in Cart </h1>
            <table>
             
			 <%
            for(String key: items.keySet()){%>
			
                <form method = "get" action="deleteItem.jsp">
				<input type="hidden" name="name" value=<%=key%>>
				<tr><td><%=key%> </td>
				
				<td><input type="submit" value="delete"></td>
				</tr>
				
				</form>
          <%  }
			%>
			
            </table>
			<br/>
			<form action="index.html"><input type="submit" value="Add More Items"></form>
			<form method="get" action="checkout.jsp"><input type="submit" value="Proceed to Checkout"></form>	
            </body>
            </html>
        

