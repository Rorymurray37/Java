package MovingSquaresApplication;
import java.awt.*;
import javax.swing.*;

//import MyApplication.MyApplication;

public class MovingSquaresApplication extends JFrame implements Runnable{

	private static final Dimension WindowSize = new Dimension(600,600);
    private static final int NUMGAMEOBJECTS = 30;
    private GameObject[] GameObjectArray = new GameObject[NUMGAMEOBJECTS];
    
	public MovingSquaresApplication() {
		
		this.setTitle("MovingSquaresApplication");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); int x = screensize.width/2 - WindowSize.width/2;
		int y = screensize.height/2 - WindowSize.height/2;
		setBounds(x, y, WindowSize.width, WindowSize.height);
		setVisible(true); 
		
		  for (int i=0; i<GameObjectArray.length; i++){        //fills array with GameObjects
               GameObject square = new GameObject();
               GameObjectArray[i] = square;
           }
           
		
		Thread t = new Thread(this);        
        t.start();
		
	}
	
	
	public void run() {
		
  while(true) {
		
		try {
			
			   Thread.sleep(20);
	
		   }
		   catch(InterruptedException e)
		  {

	      }
		 for (GameObject GameObject : GameObjectArray) {
			
			  GameObject.move();
			 repaint();
			  }
  }

	}
	
	public static void main(String [ ] args) {
		MovingSquaresApplication w = new MovingSquaresApplication();
		
		
	}
	
	public void paint(Graphics g) {
	
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WindowSize.width, WindowSize.height);
		
		for (int i=0; i<GameObjectArray.length; i++){
		
		        GameObjectArray[i].paint(g);
		       
		    }
		    
		
	}


	
}
