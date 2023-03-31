package PresentationLayer;

import BusinessLayer.DeliveryService;
import BusinessLayer.Order;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class PageEmployee extends JFrame implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Order) {
            Order order = ((Order) arg);
            System.out.println("Observer:"+
                    "changed to: "+order);
        } else System.out.println("PriceObserver:"+
                "other changes");
    }
}
