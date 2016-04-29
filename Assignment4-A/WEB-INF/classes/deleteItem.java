import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;



public class deleteItem extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("name");
         HttpSession session = request.getSession();
        cart shoppingCart;
        shoppingCart = (cart) session.getAttribute("cart");
        shoppingCart.deleteFromCart(name);
        session.setAttribute("cart", shoppingCart);
         shoppingCart = (cart) session.getAttribute("cart");
        try (PrintWriter out = response.getWriter()) {
          
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet deleteItem</title>");            
            out.println("</head>");
            out.println("<body>");
            HashMap<String, Integer> items = shoppingCart.getCartItems();
            out.println("<table border='1px'>");
             
            for(String key: items.keySet()){
                out.println("<form method = 'get' action='deleteItem'><input type='hidden' name='name' value='"+key+"'><tr><td>"+key+" - </td><td>"+"$"+items.get(key)+"</td><td><input type='submit' value='delete'></td></tr></form>");
            }
            out.println("<table>");
            out.println("</body>");
            out.println("</html>");
        }
}
}