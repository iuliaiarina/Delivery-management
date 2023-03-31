import BusinessLayer.BaseProduct;
import BusinessLayer.Type;
import BusinessLayer.User;
import DataLayer.Serializator;
import PresentationLayer.Controller;
import PresentationLayer.PageLogin;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
       ArrayList<User> users ;
        users = Serializator.getAllUsers();
        User.setMax(users);
        /*User u1= new User("admin","0000", Type.ADMINISTRATOR);
        users.add(u1);
        User u2= new User("client","0000", Type.CLIENT);
        users.add(u2);
        User u3= new User("e","", Type.EMPLOYEE);
        users.add(u3);
        User u4= new User("","", Type.ADMINISTRATOR);
        users.add(u4);

       // users.remove(u3);
       // System.out.println(users.get(0).toString());
        Serializator.serializeUsers(users);*/


        Controller controller =new Controller(users);

    }
}
