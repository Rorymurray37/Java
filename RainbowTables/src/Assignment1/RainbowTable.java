package Assignment1;
//RainbowTable

/**
 *  This class provides functionality to build rainbow tables (with a different reduction function per round) for 8 character long strings, which
    consist of the symbols "a .. z", "A .. Z", "0 .. 9", "!" and "#" (64 symbols in total).
    Properly used, it creates the following value pairs (start value - end value) after 10,000 iterations of hashFunction() and reductionFunction():
          start value  -  end value
          Kermit12        lsXcRAuN
          Modulus!        L2rEsY8h
          Pigtail1        R0NoLf0w
          GalwayNo        9PZjwF5c
          Trumpets        !oeHRZpK
          HelloPat        dkMPG7!U
          pinky##!        eDx58HRq
          01!19!56        vJ90ePjV
          aaaaaaaa        rLtVvpQS
          036abgH#        klQ6IeQJ
          
          
 *
 * @author Michael Schukat
 * @version 1.1
 * Includes bugfix in reduction method by Cian McSweeney 
 */
public class RainbowTable
{
    /**
     * Constructor, not needed for this assignment
     */
    public RainbowTable() {

    }

    public static void main(String[] args) {
    	String[] inputs = new String[] {"Kermit12","Modulus!","Pigtail1","GalwayNo", //declare array of passwords
    			"Trumpets","HelloPat","pinky##!","01!19!56","aaaaaaaa","036abgH#"};
		
		String start,function,input;
		long hashval,inputhash;

		if (args != null && args.length > 0) { // Check for <input> value
			function = args[0];
			
			if (function.equals("hash")){ //determine which function is to be run 
				start = args[1];
				input = start;
				
			    if (start.length() != 8) {
				     System.out.println("Input " + start + " must be 8 characters long - Exit");
			    }
			    else {
			    	
					
				   for (int i = 0; i<10000; i++) { //hash and reduce string 10000 times 
					
					   hashval = hashFunction(start);
					   start = reductionFunction(hashval,i);
					
				}
				System.out.println(input);
				System.out.println("10000 hashes and reductions later...");
				System.out.println(start); // print out final value 
	      	   }
			}
			else if(function.equals("search")) {
				 inputhash = Long.parseLong(args[1]); //parse hash value input
				 long hashvalue;
				 int j =0;
				
				 while(j<inputs.length) {  //loop to generate each chain 
					 String password = inputs[j];
					 for (int i = 0; i<10000; i++) { 
						   
						   hashvalue = hashFunction(password);	
						   if (hashvalue == inputhash) {   //check if hash value is found within chain
							   System.out.println("match found");
							   System.out.println("Password : "+password+" hash value : "+hashvalue);
							   break;
						   }
						   password = reductionFunction(hashvalue,i);
						   			  
						  
					 }
					 j++;
				 }			
			}
		}
		else { // No <input> 
			System.out.println("Use: CT437_RainbowTable <Input>");
		} 
	}
    
        
    private static long hashFunction(String s){
        long ret = 0;
        int i;
        long[] hashA = new long[]{1, 1, 1, 1};
        
        String filler, sIn;
        
        int DIV = 65536;
        
        filler = new String("ABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGH");
        
        sIn = s + filler; // Add characters, now have "<input>HABCDEF..."
        sIn = sIn.substring(0, 64); // // Limit string to first 64 characters

        for (i = 0; i < sIn.length(); i++) {
            char byPos = sIn.charAt(i); // get i'th character
            hashA[0] += (byPos * 17111); // Note: A += B means A = A + B
            hashA[1] += (hashA[0] + byPos * 31349);
            hashA[2] += (hashA[1] - byPos * 101302);
            hashA[3] += (byPos * 79001);
        } 
           
        ret = (hashA[0] + hashA[2]) + (hashA[1] * hashA[3]);
        if (ret < 0) ret *= -1;
        return ret;
    } 
    
    private static String reductionFunction(long val, int round) {  // Note that for the first function call "round" has to be 0, 
        String car, out;                                            // and has to be incremented by one with every subsequent call. 
        int i;                                                      // I.e. "round" created variations of the reduction function.
        char dat;                                                  
        
        car = new String("0123456789ABCDEFGHIJKLMNOPQRSTUNVXYZabcdefghijklmnopqrstuvwxyz!#");
        out = new String("");
      
        for (i = 0; i < 8; i++) {
            val -= round;
			
	        // This fix addresses the problem of negative remainders
		    long temp = val % 63;
            if (temp < 0) temp += 63;
            dat = (char) temp;	
            
	        // Old version
            // dat = (char) (val % 63);
            val = val / 83;
            out = out + car.charAt(dat);
        }
        
        return out;
    }
}