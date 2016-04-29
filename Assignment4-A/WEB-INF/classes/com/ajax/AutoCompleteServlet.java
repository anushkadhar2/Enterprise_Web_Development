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


public class AutoCompleteServlet extends HttpServlet {

    private ServletContext context;
    private ProductData productData = new ProductData();
    private HashMap products = productData.getProducts();

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
		
		
        String action = request.getParameter("action");
        String targetId = request.getParameter("id");
		
        StringBuffer sb = new StringBuffer();
		

        if (targetId != null) {
            targetId = targetId.trim().toLowerCase();
			
        } else {
            context.getRequestDispatcher("/error.jsp").forward(request, response);
        }

        boolean namesAdded = false;
        if (action.equals("complete")) {
			
            if (!targetId.equals("")) {

                Iterator it = products.keySet().iterator();

                while (it.hasNext()) {
                    String productId = (String) it.next();
                    Product product = (Product) products.get(productId);

                    if (product.getProductName().toLowerCase().startsWith(targetId)) 
                         
					{

                        sb.append("<product>");
                        sb.append("<productId>" + product.getProductId() + "</productId>");
                        sb.append("<productName>" + product.getProductName() + "</productName>");
                        sb.append("<productPrice>" + product.getProductPrice() + "</productPrice>");
						sb.append("<productType>" + product.getProductType() + "</productType>");
                        sb.append("</product>");
						
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
			
            // put the target composer in the request scope to display 
            if ((targetId != null) || products.containsKey(targetId.trim())) {
                request.setAttribute("product", products.get(targetId));
				
				if((targetId.substring(0,2)).equals("mi")){
					context.getRequestDispatcher("/Microsoft").forward(request, response);		
				}else if((targetId.substring(0,2)).equals("so")){
					context.getRequestDispatcher("/Sony").forward(request, response);
				}else if((targetId.substring(0,1)).equals("w")){
					context.getRequestDispatcher("/Nintendo").forward(request, response);
				} 
				
			
            }
        }
    }
}
