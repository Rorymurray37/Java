import java.util.*;
import java.math.BigDecimal;
import java.io.Serializable;



public class Statement implements Serializable {
private Date from,to;
private int account;
private ArrayList<Transaction> transactions;

public Statement(Date from,Date to,int account){
  this.from = from;
  this.to = to;
  this.account = account;
  transactions = new ArrayList<Transaction>();
}

public void getAccountnum(){

} // returns account number associated with this statement
public void getStartDate(){

} // returns start Date of Statement
public void getEndDate(){

} // returns end Date of Statement
public void getAccoutName(){

} // returns name of account holder
public void getTransactions(){

} // return list of transactions included in this statement
public void addTransaction(Transaction t){
  transactions.add(t);
}
public void print(){
  System.out.println("Statement of account "+account +" from " + from + " to "+ to );
  for (Transaction transaction : transactions){
    System.out.println(transaction.print());
  }

}

}
