package com.link.assignment;
import java.util.HashMap;
import java.util.*;




	public class Cart {
    HashMap<String, List<String>> cartItems;
    public Cart(){
     cartItems = new HashMap<String, List<String>>();
      
    }
    public HashMap getCartItems(){
        return cartItems;
    }
    public void deleteFromCart(String productId){
        cartItems.remove(productId);
    }
	public void addToCart(String productId, List<String> value){
        cartItems.put(productId, value);
    }
   
}
     	
