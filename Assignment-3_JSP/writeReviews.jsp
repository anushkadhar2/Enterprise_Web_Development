<html>
	<head>
		<%@page language="java" %>
		<%@page import="java.util.*" %>
		<%@page import="java.io.*" %>
		<%@page import="javax.servlet.*" %>
		<%@page import="java.util.Date" %>
		<%@page import="com.mongodb.*"%>
		
		<meta http-equiv= "Content-Type" content="text/html; charset=utf-8" />
		<title>Write Review</title> 
	</head>
	<body>
			
	<%
		MongoClient mongo;
		mongo = new MongoClient("localhost", 27017);
		String productName = request.getParameter("productName");
		String productPrice =request.getParameter("productPrice");
			
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection Reviews = db.getCollection("Reviews");
			
			
			%>
				
			<h1>Write Review</h1>	
			<br>
	        <h3><%=productName%></h3>
		
			<form method="get" action="SubmitReview.jsp">
			<fieldset>
			
			<legend>Product information:</legend>
			<table>
			<tr>
		    <td> Product Model Name: </td>
			<td> <input type="text" name="productName" value= "<%=productName%>" readonly> </td>
			</tr>
			
			<tr>
			<td> Product Price: </td>
			<td><input type="text" name="productPrice" value= "<%=productPrice%>"readonly> </td>
			</tr>
			
			<tr>
			<td> Product Category: </td>
			<td><select name="productCategory">
			<option value= 'GameMaker' selected >GameMaker</option>
			<option value= 'ConsoleMaker'>ConsoleMaker</option>
			<option value= 'Accessories'>Accessories</option>
			</td>
			</tr>
			
			 
			<tr>
			<td> Retailer Name: </td>
			<td><select name='retailerName'>
			<option value= 'Walmart' selected >Walmart</option>
			<option value= 'GameSpeed'>GameSpeed</option>
			<option value= 'BestBuy'>BestBuy</option>
			</td></tr>
			
			</tr>
			<tr><td> Retailer City: </td>
			<td><input type="text" name="retailerCity"> </td>
			</tr>
			
			<tr>
			<td> Retailer State: </td>
			<td><input type="text" name="retailerState"> </td>
			</tr>
			
	        <tr>
			<td> Retailer Zip: </td>
			<td><input type="text" name="retailerZip"> </td>
			</tr>
			
			<tr>
			<td> Product On Sale: </td>
			<td><select name='productOnSale' >
			<option value= "Yes">Yes</option>
			<option value = "No">No</option> </td>
			</tr>
			
			<tr>
			<td> Manufacturer Name: </td>
			<td><select name="manufacturerName">
			<option value= "Microsoft" selected >Microsoft</option>
			<option value= "Sony">Sony</option>
			<option value= "Nintendo">Nintendo</option>
			</td>
			</tr>
		
			<tr>
			<td> Manufacturer Rebate: </td>
			<td><select name="manufacturerRebate"> 
			<option value= "Yes">Yes</option>
			<option value = "No">No</option>  </td>
			</tr>
			</table>
			
			</fieldset>	
			
			<fieldset>
			<legend>Reviews:</legend>
			<table>
			
			<!--User Name-->
			<tr>
		    <td> User Name: </td>
			<td> <input type="text" name="userName"> </td>	</tr>
			
			<!--User Age-->
			<tr>
		    <td> User Age: </td>
			<td> <input type="text" name="userAge"> </td>	</tr>
			
			<!--User Gender-->
			<tr>
		    <td> User Gender: </td>
			<td> <input type="text" name="userGender"> </td></tr>
			
			<!--User Occupation-->
			<tr>
		    <td> User Occupation: </td>
			<td> <input type="text" name="userOccupation"> </td>	
			</tr>
			
			<!--Review Ratings-->
			<tr>
		    <td> Review Rating: </td>
			<td>
			<select name = "reviewRating">
			<option value="1" selected>1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			</td>
			</tr>
			
			<tr>
		    <td> Review Date: </td>
			<td> <input type="date" name="reviewDate"> </td>	</tr>
			
			<tr>
		    <td> Review Text: </td>
			<td> <textarea type name="reviewText" rows = "5" columns = "50"></textarea> </td>	</tr>
            
			</table>
			<br><br>
			<input type="submit" value="Send Review">
			</fieldset>
			
			
		    </form>
			</body>
			</html>
		 
