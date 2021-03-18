
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.registry.LocateRegistry;
import java.util.*;
import java.math.BigDecimal;
//import java.util.Random;

public class Bank implements BankInterface {
private ArrayList<Account> accounts; // users accounts
private Account a1;
private Account a2;
private Account a3;

private BigDecimal balance;
private long sessionID;
private String username,password;

public Bank() throws RemoteException
{
  super();

  accounts= new ArrayList<Account>();
  a1 = new Account("user1","secret",1234567,new BigDecimal("869.13"));
  a2 = new Account("user2","secret",1234545,new BigDecimal("5211.70"));
  a3 = new Account("user3","secret",2355663,new BigDecimal("17230.20"));
  accounts.add(a1);
  accounts.add(a2);
  accounts.add(a3);
}


public long login(String user, String pass) throws RemoteException{

  for (Account account : accounts){
   username = account.getUser();
   password = account.getPassword();


  if (username.equals(user) && password.equals(pass)){
    Random rnd = new Random();
    sessionID = 100000 + rnd.nextInt(900000);
    return sessionID;
  }
}

    String err = "Username and password do not match";
    System.out.println(err);
    sessionID = 0;
    return sessionID;

}

public Account checkAccount(int usernum){
  for (Account account : accounts){
    int accountnum = account.getAccountnum();

    if(usernum == accountnum){
      return account;
      }
    }
    System.out.println("Account does not exist");
    return null;
  }

public long getSessionID() throws RemoteException {
  return sessionID;
}


public void deposit(int accountnum, BigDecimal amount,long sID) throws RemoteException {
  if (sID == sessionID){
    Account a = checkAccount(accountnum);
    a.deposit(amount);
    System.out.println(a.getBalance());
  }
  else{
    System.out.println("Invalid sessionID");
  }
}
public void withdraw(int accountnum, BigDecimal amount,long sID) throws RemoteException {
  if (sID == sessionID){
    Account a = checkAccount(accountnum);
    a.withdraw(amount);
  }
  else{
    System.out.println("Invalid sessionID");
  }
}
public BigDecimal getBalance(long sID,int accountnum) throws RemoteException {
  if (sID == sessionID){
   System.out.println("getting balance");
   Account a = checkAccount(accountnum);
   balance = a.getBalance();

   return balance;
  }
  else{
    System.out.println("Invalid sessionID");
    return null;
  }
 }

public Statement getStatement(int accountnum,Date from, Date to,long sID) throws RemoteException {
if (sID == sessionID){
   Statement statement = new Statement(from,to,accountnum);
   Account a = checkAccount(accountnum);

   for (int i = 0;i < a.NumTransactions();i++){
     Transaction transaction = a.getTransaction(i);


     if (transaction.getDate().after(from)  && transaction.getDate().before(to)){
       statement.addTransaction(transaction);
     }
   }

   return statement;
}
else{
  System.out.println("Invalid sessionID");
  return null;
 }
}

//---------------------------------------------------------------------//

public static void main(String args[]) throws Exception {
// initialise Bank server - see sample code in the notes and online RMI tutorials for details

int registryport = 20345;

if (args.length > 0)
   registryport = Integer.parseInt(args[0]);

if (System.getSecurityManager() == null) {
      System.setSecurityManager(new SecurityManager());
      System.out.println("Security manager set");
      }

try
{

  // Create an instance of the local object
  BankInterface bank = new Bank();
  System.out.println("Instance of bank Server created");
  BankInterface stub = (BankInterface) UnicastRemoteObject.exportObject(bank, 0);

  // Put the server object into the Registry

  Registry registry = LocateRegistry.getRegistry(registryport);
  registry.rebind("Bank", stub);
  System.out.println("Bank rebind completed");

}
catch(Exception exc)
{
  System.out.println("Error in main - " + exc.toString());
}



}

}
