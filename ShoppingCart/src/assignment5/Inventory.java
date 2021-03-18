package assignment5;
//Rory Murray 17395111
import java.util.ArrayList;

public class Inventory {
	
	private String Item_sku,name;
	private Item item;
	private double price;
	private int quantity, orderQuantity=0;
	

	
	public Inventory(String sku,String name,int quantity,double price ) {
		
	    item = new Item(name,quantity,price);
		this.Item_sku = sku;
		this.price = price;
		this.name = name;
		this.quantity = quantity;
		
	}
	
	public Item getItem() {
		return item;
	}
	
	public String getName() {
		return name;
	}
	public void orderQuantity(int q) {
		orderQuantity = q;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int q) {
	   quantity = q;
	    item.setQuantity(q);
	}
	
	public double getPrice() {
		return price;
	}
	
	public String toString() {
		String output = Item_sku + "  " + item.toString();
		return output;
	}

}
