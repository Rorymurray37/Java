package MovingSquaresApplication;
import java.awt.*;



public class GameObject  {

  private double x,y;
  private int width,height;
  private Color c;
  private int[] movearray = new int[] {1,2,3,4,5,6,7,8,-1,-2,-3,-4,-5,-6,-7,-8};

public GameObject() {
	  
	
	width = (int) (Math.random( )*560);
	height = (int) (Math.random( )*560);
	
	int R = (int) (Math.random( )*256);
	int G = (int)(Math.random( )*256);
	int B= (int)(Math.random( )*256);
	
	c = new Color(R,G,B);
}

  public void move() {
	int random = (int)(Math.random()*16);
	int border1 = 40;
	int border2 = -40;
	if(width > 560) {
		width += border1;
	}
	else if (width < 40) {
		width += border1;
	}
	else if(height > 560) {
		height += border2;
	}
	else if(height < 40 ) {
		height += border1;
	}
	else {
	
	width += movearray[random];
	height += movearray[random];
	}
  }

 public void paint(Graphics g) {

   g.setColor(c);
   g.fillRect(width, height, 40, 40);
  
   
 }


}