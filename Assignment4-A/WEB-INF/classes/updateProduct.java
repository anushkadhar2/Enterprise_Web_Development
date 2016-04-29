import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class updateProduct extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String itemId = request.getParameter("itemId");
		String itemPrice = request.getParameter("itemPrice");
	
		BufferedReader br = new BufferedReader(new FileReader("C:/My programs/apache-tomcat-8.0.26-windows-x86/apache-tomcat-8.0.26/webapps/Project/itemdetails.txt"));
		File file1 = new File("C:/My programs/apache-tomcat-8.0.26-windows-x86/apache-tomcat-8.0.26/webapps/Project/itemdetails1.txt");
		FileWriter fw = new FileWriter(file1,true);
		BufferedWriter bw = new BufferedWriter(fw);
		String currLine = null;
		PrintWriter out = response.getWriter();
		while((currLine=br.readLine())!=null ){
			
			String temp[] = currLine.split(":");	
			String nextToken = temp[0];
			String temp1[] = temp[1].split(",|\\[|\\]");
			if(nextToken.equals(itemId)){
				currLine = temp[0]+":"+"["+temp1[1]+","+itemPrice+","+temp1[3]+"]";
				bw.write(currLine);
				bw.write("\r\n");
			} 
			else{
				bw.write(currLine);
				bw.write("\r\n");
			}	
			//out.println(currLine);	
		} 
		bw.close();
		br.close();
		fw.close();
	
		
		File old = new File("C:/My programs/apache-tomcat-8.0.26-windows-x86/apache-tomcat-8.0.26/webapps/Assignment1/itemdetails.txt");
		old.delete();
	
		File newFile = new File("C:/My programs/apache-tomcat-8.0.26-windows-x86/apache-tomcat-8.0.26/webapps/Assignment1/itemdetails1.txt");
		newFile.renameTo(old);
			
    
				out.println("<html>");
				out.println("<head>");
				out.println("<title></title>");  
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>"+ "Item Details Updated Successfully !!" +"</h1>");
				out.println("<a href='storemanager.html'>Go back</a>");
				out.println("</body>");
				out.println("</html>");
				
	} 		
} 