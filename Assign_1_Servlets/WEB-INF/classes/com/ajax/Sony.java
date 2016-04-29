import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Sony extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int i;
		String nextToken1="";
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel='stylesheet' href='styles1.css' type='text/css' />");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Sony</h1>");
		out.println("<hr/>");
		//out.println("<table>");
		BufferedReader br = new BufferedReader(new FileReader("C:/My programs/apache-tomcat-8.0.26-windows-x86/apache-tomcat-8.0.26/webapps/Assignment1/itemdetails.txt"));
		//PrintWriter out = response.getWriter();
		String currLine = "";
		while((currLine=br.readLine())!=null ){
			
			String temp[] = currLine.split(":");
			String temp1[] = temp[1].split(",|\\[|\\]");
			
			//out.println("<hr/>");			
			String nextToken = temp[0];
			
			String s = nextToken.substring(0,2);
			
			if(s.equals("So")){
				
				out.println("<table>");	
					out.println("<tr>");
						out.println("<td>");
						out.println("<h4>ItemID:</h4>");
						out.println("</td>");
						out.println("<td>");
						out.println("<input type='text'  name='itemId' value="+nextToken+" readonly>");
						out.println("</td>");
					out.println("</tr>");
					out.println("<tr>");
						out.println("<td>");
						out.println("<h4>Item Name:</h4>");
						out.println("</td>");
						out.println("<td>");
						out.println("<input type='text'  name='itemName' value="+temp1[1]+" readonly>");
						out.println("</td>");
					out.println("</tr>");	
					out.println("<tr>");
						out.println("<td>");
						out.println("<h4>Item Price: </h4>");
						out.println("</td>");
						out.println("<td>");
						out.println("<input type='text' name='itemPrice' value="+temp1[2]+" readonly>");
						out.println("</td>");
					out.println("</tr>");	
					out.println("<tr>");	
						out.println("<td>");
						out.println("<h4>Item Type:</h4>");
						out.println("</td>");
						out.println("<td>");
						out.println("<input type='text' name='itemType' value="+temp1[3]+" readonly>");
						out.println("</td>");
					out.println("</tr>");
					out.println("<tr>");	
						out.println("<td>");
						out.println("<h4>Manufacturer Rebate:</h4>");
						out.println("</td>");
						out.println("<td>");
						out.println("<input type='text' name='itemType' value="+temp1[4]+" readonly>");
						out.println("</td>");
					out.println("</tr>");
					out.println("<tr>");	
						out.println("<td>");
						out.println("<h4>Retailer Discount:</h4>");
						out.println("</td>");
						out.println("<td>");
						out.println("<input type='text' name='itemType' value="+temp1[5]+" readonly>");
						out.println("</td>");
					out.println("</tr>");
				out.println("</table>");		
					out.print("<form action=\"addToCart\" method=\"get\" class = \"submit-button\">");
					out.print("<input class = \"submit-button\" type = \"submit\" name = temp1[1] value = \"Add To Cart\">");
					out.print("<input type='hidden' name='itemId' value="+nextToken+" readonly>");
					out.print("<input type='hidden' name='itemName' value="+temp1[1]+" readonly>");
					out.print("<input type='hidden' name='itemPrice' value="+temp1[2]+" readonly>");
					out.print("<input type='hidden' name='itemType' value="+temp1[3]+" readonly>");
					out.print("<input type='hidden' name='manufacturerRebate' value="+temp1[4]+" readonly>");
					out.print("<input type='hidden' name='retailerDiscount' value="+temp1[5]+" readonly>");
					out.print("</form>");
					out.print("<form action=\"Reviews\" method=\"get\" class = \"submit-button\">");
					out.print("<input class = \"submit-button\" type = \"submit\" name = +temp1[2]+ value = \"Write Review\">");
					out.print("<input type='hidden' name='itemId' value="+nextToken+" readonly>");
					out.print("<input type='hidden' name='itemName' value="+temp1[1]+" readonly>");
					out.print("<input type='hidden' name='itemPrice' value="+temp1[2]+" readonly>");
					out.print("<input type='hidden' name='itemType' value="+temp1[3]+" readonly>");
					out.print("<input type='hidden' name='manufacturerRebate' value="+temp1[4]+" readonly>");
					out.print("<input type='hidden' name='retailerDiscount' value="+temp1[5]+" readonly>");
					out.print("</form>");
					out.print("<form action=\"ViewReviews\" method=\"get\" class = \"submit-button\">");
					out.print("<input class = \"submit-button\" type = \"submit\" name = temp1[3] value = \"View Review\">");
					out.println("<hr/>");
					out.print("<input type='hidden' name='itemId' value="+nextToken+" readonly>");
					out.print("<input type='hidden' name='itemName' value="+temp1[1]+" readonly>");
					out.print("<input type='hidden' name='itemPrice' value="+temp1[2]+" readonly>");
					out.print("<input type='hidden' name='itemType' value="+temp1[3]+" readonly>");
					out.print("<input type='hidden' name='manufacturerRebate' value="+temp1[4]+" readonly>");
					out.print("<input type='hidden' name='retailerDiscount' value="+temp1[5]+" readonly>");
					out.print("</form>");
					
			}
			//out.println("<hr/>");	
		}	
		
		out.println("</body>");
		out.println("</html>");
			
	}
	
}