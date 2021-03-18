package assignment5;
//Rory Murray 17395111

import java.util.ArrayList;

public class Test {

	
	
	 public static void main(String args[]) {
		ArrayList<Inventory>List = new ArrayList<Inventory>();
		
		 Inventory a = new Inventory("1000","Apple",30,2.50);
	     Inventory b = new Inventory("1001", "Orange", 40, 2);
	     Inventory c = new Inventory("2001", "Milk", 10, 2.39);
	     Inventory d = new Inventory("2002", "Orange Juice", 20, 1.99);
	     Inventory e = new Inventory("3001", "Blue Cheese", 10, 2.25);
	     Inventory f = new Inventory("3002", "Cheddar", 20, 2.79);
	     Inventory g = new Inventory("4001", "Chocolate", 40, 2.99);
	     Inventory h = new Inventory("4002", "Candy", 30, 0.99);
	     Inventory i = new Inventory("5001", "Beef", 10, 5.00);
	     Inventory j = new Inventory("5002", "Chicken", 10, 4.00);
	     
	     List.add(a);
	     List.add(b);
	     List.add(c);
	     List.add(d);
	     List.add(e);
	     List.add(f);
	     List.add(g);
	     List.add(h);
	     List.add(i);
	     List.add(j);
	     
	
	     
	     System.out.println("---------------");
	     
	    
	     ShoppingCart cart = new ShoppingCart("Rory","29/10/19");
	     ShoppingCart cart2 = new ShoppingCart("Dan","29/10/19");
	     
	    cart.addItem(List, "Apple", 2);
	     cart.addItem(List,"Orange", 5); 
	     cart.addItem(List,"Milk", 2);
	     cart.addItem(List,"Blue Cheese", 4);
	     cart.addItem(List,"Candy", 25); 
	     cart.deleteItem(List,"Candy", 5); 
	     cart.print();
	     
	     System.out.println("---------------");
	    
	     cart2.addItem(List,"Apple", 2); 
	     cart2.addItem(List,"Orange", 5);
	     cart2.addItem(List,"Milk", 2); 
	     cart2.addItem(List,"Blue Cheese", 4); 
	     cart2.addItem(List,"Cheddar", 3); 
	     cart2.addItem(List,"Beef", 6); 
	     cart2.addItem(List,"Candy", 20);
	     cart2.addItem(List,"Chocolate", 10); 
	     cart2.addItem(List,"Chicken", 2);
	     cart2.deleteItem(List, "Chocolate", 5);
	     cart2.deleteItem(List, "Blue Cheese", 1);
	     cart2.print();
	     
	     System.out.println("---------------");
	     
	  
	    
	 
	
	   
		 
	 }
}
