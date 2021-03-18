package assignment5;
//Rory Murray 17395111

public class Item {

	private String ItemName;
	private int quantity;
	private double price;
	
	public Item(String name,int quan,double price){
		this.ItemName = name;
		this.quantity = quan;
		this.price = price;
	}
	
	 public String toString() {
		 
		String output = ItemName+"    " + quantity+"     " + price;
		return output;
		
	}
	 public void  setQuantity(int q) {
		 this.quantity = q;
	 }
}
