package BusinessLayer;

import java.io.Serializable;
import java.util.*;

public class Order implements Serializable{
    private int clientID;
    private Date date;


    public Order(int clientID) {
        this.clientID = clientID;
        this.date = new Date();
        //System.out.println(date.getHours());
        //System.out.println(date.getDate()+ "  "+ (date.getMonth()+1) + "  "+ (date.getYear()+1900));
    }

    public int getClientID() {
        return clientID;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Comanda clientului cu id-ul " + clientID +
                " la data de " + date.toString() + " este: \n" ;
    }

}
