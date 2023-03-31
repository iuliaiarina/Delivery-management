package DataLayer;

import BusinessLayer.BaseProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Order;
import BusinessLayer.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Serializator {

    public static void serializeOrders(HashMap<Order, ArrayList<MenuItem>> orders){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("orders.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(orders);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static HashMap<Order, ArrayList<MenuItem>> getAllOrders(){
        HashMap<Order, ArrayList<MenuItem>> orders = new HashMap<>();
        try {
            FileInputStream fileIn = new FileInputStream("orders.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            orders=(HashMap<Order, ArrayList<MenuItem>>)in.readObject();
            /*for(int i=0;i<orders.size();i++){
                System.out.println(orders.get(i).toString());
            }
            System.out.println("\n\n");*/
            in.close();
            fileIn.close();
        }catch (EOFException x) {
            return new HashMap<>();
        }
        catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Product class not found");
            c.printStackTrace();
            return null;
        }
        return orders;
    }

    public static void serializeUsers(ArrayList<User> users){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("users.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            users=(ArrayList<User>)in.readObject();
            for(int i=0;i<users.size();i++){
                System.out.println(users.get(i).toString());
            }
            System.out.println("\n\n");
            in.close();
            fileIn.close();
        }catch (EOFException x) {
            return new ArrayList<User>();
        }
        catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Product class not found");
            c.printStackTrace();
            return null;
        }
        return users;
    }


    public static void serializeMenuItems(ArrayList<MenuItem> m){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("MenuItem.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(m);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static ArrayList<MenuItem> getAllMenuItems(){
        ArrayList<MenuItem> m = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("MenuItem.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            m=(ArrayList<MenuItem>)in.readObject();
            /*for(int i=0;i<m.size();i++){
                System.out.println(m.get(i).toString());
            }
            System.out.println("\n\n");*/
            in.close();
            fileIn.close();
        } catch (IOException i) {
            //i.printStackTrace();
            return new ArrayList<MenuItem>();
        } catch (ClassNotFoundException c) {
            System.out.println("Product class not found");
            c.printStackTrace();
            return null;
        }
        return m;
    }




}
