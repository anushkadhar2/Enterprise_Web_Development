package com.ajax;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author nbuser
 */
public class AutoCompleteServlet extends HttpServlet {

    private ServletContext context;
    private ProductData prodData = new ProductData();
    private HashMap products = prodData.getProducts();

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
		
		//PrintWriter out = response.getWriter();
		
        String action = request.getParameter("action");
        String targetId = request.getParameter("id");
		//System.out.println("ID value:"+targetId);
		//out.println("Value:"+targetId);
        StringBuffer sb = new StringBuffer();
		//String prodType = "";

        if (targetId != null) {
            targetId = targetId.trim().toLowerCase();
			//System.out.println("ID value:"+targetId);
        } else {
            context.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        boolean namesAdded = false;
        if (action.equals("complete")) {
			System.out.println("ID1 value:"+targetId);
            // check if user sent empty string
            if (!targetId.equals("")) {

                Iterator it = products.keySet().iterator();

                while (it.hasNext()) {
                    String itemId = (String) it.next();
                    Product product = (Product) products.get(itemId);

                    if (product.getItemName().toLowerCase().startsWith(targetId)) 
                         
					{

                        sb.append("<product>");
                        sb.append("<itemId>" + product.getItemId() + "</itemId>");
                        sb.append("<itemName>" + product.getItemName() + "</itemName>");
                        sb.append("<itemPrice>" + product.getItemPrice() + "</itemPrice>");
						sb.append("<itemType>" + product.getItemType() + "</itemType>");
                        sb.append("</product>");
						//prodType = product.getItemType();
                        namesAdded = true;
                    }
                }
            } 
				
            if (namesAdded) {
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write("<products>" + sb.toString() + "</products>");
            } else {
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }

        if (action.equals("lookup")) {
			//String prodId = request.getParameter("id");
			System.out.println("ID2 value:"+targetId);
			//System.out.println("ID3 value:"+prodId);
            // put the target composer in the request scope to display 
            if ((targetId != null) || products.containsKey(targetId.trim())) {
                request.setAttribute("product", products.get(targetId));
				System.out.println("ID3 value:"+targetId);
				if((targetId.substring(0,2)).equals("mi")){
					context.getRequestDispatcher("/Microsoft").forward(request, response);		
				}else if((targetId.substring(0,2)).equals("so")){
					context.getRequestDispatcher("/Sony").forward(request, response);
				}else if((targetId.substring(0,2)).equals("ni")){
					context.getRequestDispatcher("/Nintendo").forward(request, response);
				}else if((targetId.substring(0,2)).equals("ac")){
					context.getRequestDispatcher("/Accessories").forward(request, response);
				}else if((targetId.substring(0,2)).equals("ea")){
					context.getRequestDispatcher("/EA").forward(request, response);
				}else if((targetId.substring(0,2)).equals("ni")){
					context.getRequestDispatcher("/Nintendo").forward(request, response);
				}else if((targetId.substring(0,2)).equals("at")){
					context.getRequestDispatcher("/Activision").forward(request, response);	
				}
				
				
                //context.getRequestDispatcher("/composer.jsp").forward(request, response);
            }
        }
    }
}
