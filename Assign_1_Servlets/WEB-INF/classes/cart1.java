import java.util.HashMap;

public class cart1 {
    HashMap<String, Integer> cartItems;
    public cart1(){
     cartItems = new HashMap<>();
      
    }
    public HashMap getCartItems(){
        return cartItems;
    }
    public void addToCart(String itemId, int price){
        cartItems.put(itemId, price);
    }
	public void deleteFromCart(String itemId){
        cartItems.remove(itemId);
    }
     
}