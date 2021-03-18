import java.math.BigDecimal;
import java.util.*;
import java.io.Serializable;


public class Transaction implements Serializable {
  private BigDecimal amount;
  private Date date;
  private String description;
// Needs some accessor methods to return information about the transaction

public Transaction(BigDecimal amount,Date date,String description){
   this.amount = amount;
   this.date = date;
   this.description = description;

}
public BigDecimal getAmount(){
  return amount;
}
public Date getDate(){
return date;
}
public String Getdescription(){
  return description;
}
public String print(){
  String out = ("Date : " + date + " €"+amount+ " description : " + description);
  return out;
}


}
