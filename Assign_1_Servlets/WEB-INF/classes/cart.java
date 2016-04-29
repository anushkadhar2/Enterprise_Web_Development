import java.util.HashMap;

public class cart {
    HashMap<String, String> cartItems;
    public cart(){
     cartItems = new HashMap<>();
      
    }
    public HashMap getCartItems(){
        return cartItems;
    }
    public void addToCart(String itemName, String itemPrice){
        cartItems.put(itemName, itemPrice);
    }
	public void deleteFromCart(String itemName){
        cartItems.remove(itemName);
    }
     
}