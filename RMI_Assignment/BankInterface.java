//import import java.util.*;
import java.rmi.*;
import java.util.Date;
import java.math.BigDecimal;



public interface BankInterface extends Remote {

// The login method returns a token that is valid for some time period that must be passed to the other methods as a session identifier
public long login(String username, String password) throws RemoteException;

public void deposit(int accountnum, BigDecimal amount, long sessionID) throws RemoteException;

public void withdraw(int accountnum, BigDecimal amount, long sessionID) throws RemoteException;

public long getSessionID() throws RemoteException;

public BigDecimal getBalance(long sessionID,int accountnum) throws RemoteException;
///Users/rorymurray/eclipse-workspace/RMI/src/App
public Statement getStatement(int accountnum,Date from, Date to, long sessionID) throws RemoteException;

}
