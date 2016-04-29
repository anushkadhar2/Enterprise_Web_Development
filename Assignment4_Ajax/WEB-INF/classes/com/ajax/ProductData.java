package com.ajax;

import java.util.HashMap;
import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.text.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;

/**
 *
 * @author nbuser
 */
public class ProductData {
    
    private HashMap productList = new HashMap();
    

    public HashMap getProducts() {
        return productList;
    }
    
    public ProductData() {
        
    int i = 1;
	String productId="";
    String productName = " ";
	String productPrice = " ";
	String productType = " ";
	
	MongoClient mongo = new MongoClient("localhost", 27017);
						
	DB db = mongo.getDB("CSP595Tutorial");
			
	DBCollection myProduct = db.getCollection("myProduct");

	DBCursor cursor = myProduct.find();

	if(cursor.count() > 0)
	{
			while(cursor.hasNext())
			{
					BasicDBObject obj = (BasicDBObject) cursor.next();
					productId = obj.getString("productId");
					productName = obj.getString("productName");
					productType = obj.getString("productType");
					productPrice = obj.getString("productPrice");
					
					productList.put(productId,new Product(productId,productName,productPrice,productType));
					i++;
			}
	}
    }

}
