import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;

public class deleteProduct extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		String productId = request.getParameter("productId");
		
		BufferedReader buffer_reader = new BufferedReader(new FileReader("C:/apache-tomcat-7.0.34/webapps/Assignment/itemDetails.txt"));
		File f = new File("C:/apache-tomcat-7.0.34/webapps/Assignment/deletedProducts.txt");
		
		FileWriter fstream = new FileWriter(f,true);
	    BufferedWriter buffer_writer = new BufferedWriter(fstream);
		
		
		
		//String token_new = " " ;
		String currentLine = " ";
		
		while((currentLine=buffer_reader.readLine())!=null ){
			
			String temp[] = currentLine.split("-");
			//String temp1[] = temp[1].split(",|\\[|\\]");
			String token_new = temp[0];
			//out.println(token_new);
			//out.println("Product Id is" +productId);
			if(token_new.equals(productId))
			{
				//out.println(currentLine);
			}
			else
			{
				buffer_writer.write(currentLine);
				buffer_writer.write("\r\n");
			}
		}
		
		
			buffer_writer.close();
			fstream.close();
			buffer_reader.close();
		
		   File f1 = new File("C:/apache-tomcat-7.0.34/webapps/Assignment/itemDetails.txt");
		   f1.delete();
		   File f2 = new File("C:/apache-tomcat-7.0.34/webapps/Assignment/deletedProducts.txt");
		   f2.renameTo(f1);
		
		 
		  
		        out.println("<html>");
				out.println("<head>");
				out.println("<meta http-equiv= 'Content-Type' content='text/html; charset=utf-8' />");
	            out.println("<title>Deleted Product Page</title>");
	            out.println("<link rel='stylesheet' href='webPage.css' type='text/css' />");
				out.println("</head>");
				
				out.println("<body>");
				out.println("<h1>Product Deleted Successfully</h1>");
				out.println("<a href = 'index.html'>Home</a>");
				out.println("<body>");
		  
		  
		  
		
		
	}
	
	
}