

import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class deleteItem extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("name");
		
          HttpSession session = request.getSession();
        cart shoppingCart;
        shoppingCart = (cart) session.getAttribute("cart");
		//Integer price = Integer.parseInt(request.getParameter("price"));
        shoppingCart.deleteFromCart(name);
        session.setAttribute("cart", shoppingCart);
         shoppingCart = (cart) session.getAttribute("cart");
		 int total=0;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet deleteItem</title>"); 
            out.println("<link rel='stylesheet' href='loginstyle.css' type='text/css' />"); 			
            out.println("</head>");
            out.println("<body>");
			out.println("<h1>Item successfully deleted from the cart </h1>");
			out.println("<form action='welcome.html'>Add more item <input type='submit' value='go'></form>");
			out.println("<hr>");
			out.println("<h2>Cart</h2>");
            HashMap<String, String> items = shoppingCart.getCartItems();
            out.println("<table border='1px'>");
             
            
			for(String key: items.keySet())
			{
			out.println("<form action='deleteItem'><input type='hidden' name='name' value='"+key+"'><tr><td>"+key+" - </td><td>"+"$"+items.get(key)+"</td><td><input type='submit' value='delete'></td></tr></form>");
			total=total+Integer.parseInt(items.get(key));
			}
			//out.println("<h1>Item deleted</h1>");
			
			//out.println("<form action='addtocart'><input type='submit' value='Go back'");
            out.println("</table>");
			out.println("</br></br></br></br>");
			//String checkoutURL =
            response.encodeURL("OrderForm.html");
			out.println("Total Price:"+total);
			int DiscountedPrice=total-10;
			out.println("</br></br>");
			out.println("Discounted Price:"+DiscountedPrice);
			out.println("<form action= 'buyfromcart' method='get'><input type='submit' value='Proceed To Checkout'><input type='hidden' name='price' value='"+DiscountedPrice+"'></form>");
        }
    }
}