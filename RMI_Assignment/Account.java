import java.math.BigDecimal;
import java.util.*;

public class Account{
private int accountnum;
private String user,password;
private BigDecimal balance;
private ArrayList<Transaction> transactions;


public Account(String user,String password,int accountnum ,BigDecimal balance){
  this.user = user;
  this.password = password;
  this.accountnum = accountnum;
  this.balance = balance;
  transactions= new ArrayList<Transaction>();
}

public BigDecimal getBalance(){
  return balance;
}
public int getAccountnum(){
  return accountnum;
}
public String getUser(){
  return user;
}
public String getPassword(){
  return password;
}
public void deposit(BigDecimal amount){
  balance = balance.add(amount);
  Date date = new Date();
  Transaction t = new Transaction(amount,date,"deposit");
  transactions.add(t);
}
public void withdraw(BigDecimal amount){
  balance = balance.subtract(amount);
  Date date = new Date();
  Transaction t = new Transaction(amount,date,"withdrawal");
  transactions.add(t);
}
public Transaction getTransaction(int index){
  return transactions.get(index);
}
public int NumTransactions(){
  return transactions.size();
}



}
