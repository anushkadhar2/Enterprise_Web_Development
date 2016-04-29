import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;



public class addToCart extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
		response.setContentType("text/html;charset=UTF-8");
        
		HttpSession session = request.getSession();
        
		cart shoppingCart;
       
	   shoppingCart = (cart) session.getAttribute("cart");
        
		if(shoppingCart == null){
          shoppingCart = new cart();
          session.setAttribute("cart", shoppingCart);
        }
        String name = request.getParameter("productName");
        Integer price = Integer.parseInt(request.getParameter("productPrice"));
        shoppingCart.addToCart(name, price);
        session.setAttribute("cart", shoppingCart);
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>result</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Product has been added to  cart </h1>");
            out.println("<form action='index.html'>Add more items<input type='submit' value='go'></form>");
            out.println("<hr>");
            out.println("<h2>Cart</h2>");
            HashMap<String, Integer> items = shoppingCart.getCartItems();
            out.println("<table border='1px'>");
             
		
			for(String key: items.keySet()){
               out.println("<form method = 'get' action='deleteItem'><input type='hidden' name='name' value='"+key+"'><tr><td>"+key+" - </td><td>"+"$"+items.get(key)+"</td><td><input type='submit' value='delete'></td></tr></form>");
           }
            out.println("</table>");
			out.println("<br>>");
			out.println("<br>");
			out.println("<center>");
			out.println("<form action = 'checkout'><input type = 'submit' name = 'checkout' value = 'checkout'></form> ");
			out.println("</center>");
            out.println("</body>");
            out.println("</html>");
             
        }
    }
}