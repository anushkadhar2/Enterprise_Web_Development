import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class addProduct extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
{                
            try{
			// first fetch the values from the form
	        String productId = request.getParameter("productId");
			String productName = request.getParameter("productName");	
			String productPrice = request.getParameter("productPrice");
			String productType = request.getParameter("productType");
			
			//creating the hashmap for storing the data values
			
			HashMap<String,List<String>> hashmap = new HashMap<String,List<String>>();
			List<String> list = new ArrayList<String>();
			
			list.add(productName);
			list.add(productPrice);
			list.add(productType);
			
			hashmap.put(productId,list);
			
			
				FileWriter fstream;
				BufferedWriter out;
			 
				fstream = new FileWriter("C:/apache-tomcat-7.0.34/webapps/Assignment/itemDetails.txt",true);
				out = new BufferedWriter(fstream);
			  
			   
			   
				for(Map.Entry<String, List<String>> entry:hashmap.entrySet()){
				String Key = entry.getKey();
				List<String> value = entry.getValue();
				out.write(Key+"-"+list);
				out.write("\r\n");
		
				   

        
		out.close();
		fstream.close();
		
				PrintWriter pw = response.getWriter();
				pw.println("<html>");
				pw.println("<head> </head>");
				pw.println("<body>");
				pw.println("<h1> Product Added Successfully</h1>");
				pw.println("<br>");
				pw.println("<h3>To add more products</h1>");
				pw.println("<a href = 'addProduct.html'>Click here</a>");
				pw.println("<h3>To go back</h1>");
				pw.println("<a href = 'index.html'>Click here</a>");
				pw.println("</body>");
				pw.println("</html>");
				}
}
				catch (Exception e) {
				e.printStackTrace();
		}
}
}