import java.rmi.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.*;
import java.text.SimpleDateFormat;
//java -cp /Users/rorymurray/Desktop/4BCT/Java/RMI_Assignment/joda-money-1.0.1.jar:. BankInterface.java

public class ATM {



    public static void main (String args[]) throws Exception {
      // get user’s input, and perform the operations
        int registryport = 0;
        long sessionID = 0L;

        if (args.length > 0)
           registryport = Integer.parseInt(args[0]);

        System.out.println("RMIRegistry port = " + registryport);
        //System.out.println(args[1]);

        if (System.getSecurityManager() == null) {
           System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Bank";
            Registry registry = LocateRegistry.getRegistry(registryport);
            BankInterface bank = (BankInterface) registry.lookup(name);

            if (args[1].equals("login")){
               String user = args[2];
               String pass = args[3];
               sessionID = bank.login(user,pass);

               if(sessionID != 0){
                  System.out.println("Successful login for "+user +" : session ID "+sessionID + " is valid for 5 minutes");
               }
               else{
                  System.out.println("Login failed");
               }
            }
            else{ //input is sessionID

               sessionID = Long.parseLong(args[1]);
               if (sessionID == bank.getSessionID()){ //check it is valid sessionID

                 String input = args[2];
                 if (input.equals("balance")){
                    int accountnum = Integer.parseInt(args[3]);
                    BigDecimal bal = bank.getBalance(sessionID,accountnum);
                    System.out.println("The current balance of account "+ accountnum + " is "+bal);
                 }
                 else if (input.equals("deposit")) {
                   int accountnum = Integer.parseInt(args[3]);
                   String amount = args[4];

                   BigDecimal a = new BigDecimal(amount);
                   bank.deposit(accountnum,a,sessionID);
                   System.out.println("Successfully deposited "+ a + " to account "+accountnum);
                 }
                 else if (input.equals("withdraw")) {
                   int accountnum = Integer.parseInt(args[3]);
                   String amount = args[4];
                   BigDecimal b = new BigDecimal(amount);
                   bank.withdraw(accountnum,b,sessionID);
                   System.out.println("Successfully withdrew "+ b + " from account "+accountnum);
                 }
                 else if (input.equals("statement")){
                   int accountnum = Integer.parseInt(args[3]);
                   Date from = new SimpleDateFormat("dd/MM/yyyy").parse(args[4]);
                   Date to = new SimpleDateFormat("dd/MM/yyyy").parse(args[5]);
                  
                   Statement s = bank.getStatement(accountnum,from,to,sessionID);
                   s.print();
                 }

               }
               else{
                 System.out.println("Invalid sessionID");
               }

              //BigDecimal bal = bank.getBalance(sessionID,account);
              //System.out.println("The current balance of account "+account + " is "+bal);
            }

            }
            catch (Exception e) {
              System.err.println("ATM exception:");
              e.printStackTrace();
            }

   }
}
