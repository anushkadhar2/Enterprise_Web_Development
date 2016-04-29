import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.io.*;


public class deleteProduct extends HttpServlet {
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
    
   // public void removeLineFromFile(String file, String lineToRemove) {
	    String itemId = request.getParameter("itemId");
		
		BufferedReader br = new BufferedReader(new FileReader("C:/myproject/tomcat/webapps/Assign_1Copy/productdetails.txt"));
		
		File file = new File("C:/myproject/tomcat/webapps/Assign_1Copy/productdetails1.txt");
		FileWriter fw = new FileWriter(file,true);
		BufferedWriter bw = new BufferedWriter(fw);
		String currLine = null;
		
		
		while((currLine=br.readLine())!=null ){
				
			String temp[] = currLine.split(":");	
			String nextToken = temp[0];
			if(nextToken.equals(itemId)){
				//do nothing
			} 
			else{
				bw.write(currLine);
				bw.write("\r\n");
		}
		
			
}
		bw.close();
		br.close();
		fw.close();
		
		File old = new File("C:/myproject/tomcat/webapps/Assign_1Copy/productdetails.txt");
		old.delete();
	
		File newFile = new File("C:/myproject/tomcat/webapps/Assign_1Copy/productdetails1.txt");
		newFile.renameTo(old); 	
		java.io.PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title></title>");  
				out.println("</head>");
				out.println("<body>");
				out.println("<h2>"+ "Item deleted !!" +"</h2>");
				out.println("<a href='storemanager.html'>Go back</a>");
				out.println("</body>");
				out.println("</html>");
				out.close();
}	
	
}