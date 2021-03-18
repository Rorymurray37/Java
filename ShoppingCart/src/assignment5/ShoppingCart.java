package assignment5;
//Rory Murray 17395111
import java.util.ArrayList;


public class ShoppingCart {
  
	private String CustomerName,date;
	private ArrayList<Inventory> cart;
	
	public ShoppingCart(String Name,String date) {
		
		cart = new ArrayList<Inventory>();
		this.CustomerName = Name;
		this.date = date;
		
	}
	
	public void addItem(ArrayList<Inventory> list,String product,int quantity) {
		
		Inventory temp;//temporary Inventory object 
		int index = 0;
	    temp = searchInventory(list,product);//find product and return
	    
	    int current_q = temp.getQuantity();//get current quantity level
	    
	    for (int i = 0; i<list.size();i ++) { // loop to get index of the product 
	    	Inventory n;
	    	n = list.get(i);
	    	if(product == n.getName()) {
	    		index = i;
	    	}
	    }
	    
	    if(current_q-quantity < 0) { // if there is not enough quantity in stock
	    	System.out.println("Unable to add " + product + "order to cart as there are only "+current_q+" remaining");
	    	System.out.println("Remainder has been added to cart");
	   
	    	temp.setQuantity(0);//add remainder to cart
	    	temp.orderQuantity(quantity);
	    	list.set(index, temp);
	     	cart.add(temp);
	    	
	    }
	    else if(current_q == 0) {
	    	System.out.println("Product is not available at this minute");
	    }
	    
	    else {
	    	temp.setQuantity(current_q-quantity);//update quantity in stock
	    	temp.orderQuantity(quantity);//set order quantity 
	    	list.set(index, temp);//update list
	    	cart.add(temp);//add object to cart array list
	    
	    		
	    	
	    }
	    
	    
	    
		
	}
	public void deleteItem(ArrayList<Inventory> list,String product,int quantity) {
		
		Inventory remove;//temporary Inventory object
		int index = 0;
		remove = searchInventory(list,product);//find object to be removed 
		int orderQuantity = remove.getOrderQuantity();//get current order quantity
		int current_q =remove.getQuantity();
	
		
	if(orderQuantity == quantity) { // if the quantity to be removed is equal to the current order quantity then remove the whole object
		for (int i = 0;i<cart.size();i++) {
			Inventory temp2;
			temp2 = cart.get(i);
			if(temp2.getName() ==product) {
			   index = i;
			}
		}
		remove.setQuantity(current_q+quantity);
       cart.remove(index);
	}
	 else {
		remove.orderQuantity(orderQuantity-quantity);// if the quantity to be removed is less than the current order quantity update order quantity 
		
	  }
		
	}
	
	public Inventory searchInventory(ArrayList<Inventory>List,String item) {
	//loop to find item based on name
		
		for (int i = 0;i<List.size();i++) {
			Inventory temp;
			temp = List.get(i);
			if(temp.getName() ==item) {
				return temp;
			}
			
			
			
		}
		System.out.println("Item not found");
		return null;
		
		  
	
		
	}
	
	public void print() { // print out shopping cart details
		System.out.println(date+ " Name : "+CustomerName);
		double total = 0;
		
		for (int i = 0;i<cart.size();i++) {
			Inventory temp;
			temp = cart.get(i);
			double cost = temp.getPrice()*temp.getOrderQuantity();
			total += cost;
			System.out.println(temp.getOrderQuantity()+" "+temp.getName()+"        €"+ cost);
			
	;
			
		}
		
		System.out.println("Total : €" +total);
	}
}
