package PresentationLayer;

import BusinessLayer.*;
import DataLayer.Serializator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private PageLogin pageLogin;
    private PageAdmin pageAdmin;
    private PageClient pageClient;
    private PageReport pageReport;
    private PageEmployee pageEmployee;
    private ArrayList<User> users;
    private DeliveryService deliveryService;
    private ArrayList<MenuItem> actualOrder;
    private User actualUser;
    private List<MenuItem> menius ;


    public Controller(ArrayList<User> users) {
        this.users = users;
        this.deliveryService = new DeliveryService();
        pageEmployee = new PageEmployee();
        deliveryService.addObserver(pageEmployee);
        openLogin();
    }

    // pageLogin:
    public void openLogin(){
        this.pageLogin = new PageLogin();
        pageLogin.setVisible(true);
        pageLogin.addRegisterListener(new RegisterListener());
        pageLogin.addLoginListener(new LoginListener());
    }

    class RegisterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String password = pageLogin.getPassword().getText();
            String username = pageLogin.getUsername().getText();
            User newUser =new User(username,password, Type.CLIENT);
            users.add(newUser);
            Serializator.serializeUsers(users);
            Serializator.getAllUsers();
        }
    }
    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String password = pageLogin.getPassword().getText();
            String username = pageLogin.getUsername().getText();
            int found=0;
            for(User u: users){
                if(u.getPassword().equals(password) && u.getUsername().equals(username)){
                    actualUser = u;
                    found=1;
                    if(u.getType().equals(Type.CLIENT)){
                        deliveryService.setMenuItems(Serializator.getAllMenuItems());
                        pageLogin.dispose();
                        menius = deliveryService.getMenuItems();
                        actualOrder =new ArrayList<>();
                        openClient();
                    }
                    else if(u.getType().equals(Type.ADMINISTRATOR)){
                        pageLogin.dispose();
                       // Serializator.serializeMenuItems(deliveryService.getMenuItems());
                        deliveryService.setMenuItems(Serializator.getAllMenuItems());
                        pageAdmin= new PageAdmin(deliveryService);
                        pageAdmin.setVisible(true);
                        pageAdmin.addBackAdminListener(new BackAdminListener());
                        pageAdmin.addGenerateListener(new GenerateListener());
                        pageAdmin.addImportDataListener(new ImportDataListener());
                        pageAdmin.addEditProductListener(new EditProductListener());
                        pageAdmin.addNewProductListener(new NewProductListener());
                        pageAdmin.addDeleteProductListener(new DeleteProductListener());
                        pageAdmin.addCreateListener(new CreateListener());
                        pageAdmin.addToCompPListener(new ToCompPListener());
                    }
                    else {
                        pageLogin.dispose();
                    }
                }
            }
            if(found==0){
                String message = "\"Wrong password or username \"\n";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // pageReport:
    class GenerateActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int startHour = Integer.parseInt(pageReport.getStartTime());
                int endHour = Integer.parseInt(pageReport.getEndTime());
                if( startHour < 0 || startHour > 23 || endHour < 0 || endHour > 23)
                    throw new Exception();
                int minNrOrderP = Integer.parseInt(pageReport.getNrOrders());
                int minNrOrderC = Integer.parseInt(pageReport.getNrOrdersClient());
                int minPriceC = Integer.parseInt(pageReport.getPriceOrderClient());
                int day = Integer.parseInt(pageReport.getDay());
                int month = Integer.parseInt(pageReport.getMonth());
                int year = Integer.parseInt(pageReport.getYear());
                if( day < 1 || day > 31 || month < 1 || month > 12)
                    throw new Exception();
                deliveryService.generateReports(startHour,endHour,minNrOrderP,minNrOrderC,minPriceC,day,month,year);
            }
            catch(Exception e1){
                String message = "\"INVALID DATA\"\n";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // PageAdmin:
    public void openAdmin(){
        Serializator.serializeMenuItems(deliveryService.getMenuItems());
        deliveryService.setMenuItems(Serializator.getAllMenuItems());
        this.pageAdmin= new PageAdmin(deliveryService);
        this.pageAdmin.setVisible(true);

        pageAdmin.addBackAdminListener(new BackAdminListener());
        pageAdmin.addGenerateListener(new GenerateListener());
        pageAdmin.addImportDataListener(new ImportDataListener());
        pageAdmin.addEditProductListener(new EditProductListener());
        pageAdmin.addNewProductListener(new NewProductListener());
        pageAdmin.addDeleteProductListener(new DeleteProductListener());
        pageAdmin.addCreateListener(new CreateListener());
        pageAdmin.addToCompPListener(new ToCompPListener());
    }

    class BackAdminListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pageAdmin.dispose();
            openLogin();
        }
    }
    class GenerateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pageReport = new PageReport();
            pageReport.setVisible(true);
            pageReport.addGenerateActonListener(new GenerateActionListener());
        }
    }
    class ImportDataListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            deliveryService.importProducts();
            pageAdmin.dispose();
            openAdmin();
        }
    }
    class EditProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                JTable table = pageAdmin.getTable();
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                String value= (String) table.getValueAt(row,col);
                deliveryService.editMenuItem(row,col,value);
                pageAdmin.dispose();
                openAdmin();
            }
            catch (Exception e1){
                String message = "   INVALID EDIT DATA\n         OR\n  UNSELECT ROW\n";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    class NewProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                JTable table = pageAdmin.getTable();
                int row = deliveryService.rowNumber();
                String[] strings = new String[10];
                 for(int i=1;i<=7;i++) {
                    if(table.getValueAt(row,i).equals(null))throw new ArithmeticException();
                    strings[i-1] = (String) table.getValueAt(row, i);
                     //System.out.println(strings[i-1]);
                }
                deliveryService.newBaseProduct(strings);
                pageAdmin.dispose();
                openAdmin();
            }
            catch (ArithmeticException e1){
                String message = "\"EMPTY FILD!\"\n";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception e1){
                String message = "\"INVALID DATA. OR UNSELECT ROW\"\n";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    class DeleteProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JTable table = pageAdmin.getTable();
            int row = table.getSelectedRow();
            String title = null;
            if(((String)table.getValueAt(row,0)).equals(""))
                title = (String) table.getValueAt(row,1);
            else
                title = (String) table.getValueAt(row,0);
            deliveryService.deleteMenuItem(title);
            pageAdmin.dispose();
            openAdmin();
        }
    }
    class CreateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String name = pageAdmin.getCompP();
                CompositeProduct compositeProduct = new CompositeProduct(name);
                deliveryService.addMenuItem(compositeProduct);
                pageAdmin.dispose();
                openAdmin();
                pageAdmin.setCompP(name);
            } catch (ArithmeticException ex) {
                String message = "\"selection is not a composed item\"\n";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    class ToCompPListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String name=pageAdmin.getCompP();
                JTable tabel =pageAdmin.getTable();
                int row=tabel.getSelectedRow();
                BaseProduct p;
                p = deliveryService.getBaseProduct(row);
                deliveryService.addToCompositeProduct(name,p);
                pageAdmin.dispose();
                openAdmin();
                pageAdmin.setCompP(name);
            } catch (ArithmeticException ex) {
                String message = "\"selection is not a composed item\"\n";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex) {
                String message = "\"Cant add Composed item to another composed item \"\n";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }


    // PageClient

    public void openClient(){
        deliveryService.setMenuItems(Serializator.getAllMenuItems());
        this.pageClient= new PageClient(menius,actualOrder);
        this.pageClient.setVisible(true);

        pageClient.addSearchListener(new SearchListener());
        pageClient.addBackListener(new BackClientListener());
        pageClient.addAddItemListener(new AddItemListener());
        pageClient.addAddOrderListener(new AddOrderListener());
        pageClient.addDeleteListener(new DeleteListener());
    }

    class SearchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try{
               menius = deliveryService.searchProduct(pageClient);
               String a1,a2,a3,a4,a5,a6,a7;
               a1 = pageClient.getTitle();
               a2 = pageClient.getCalories();
               a3 = pageClient.getFat();
               a4 = pageClient.getProtein();
               a5 = pageClient.getRating();
               a6 = pageClient.getSodium();
               a7 = pageClient.getPrice();
               pageClient.dispose();
               openClient();
               pageClient.setTitle(a1);
               pageClient.setCalories(a2);
               pageClient.setFat(a3);
               pageClient.setProtein(a4);
               pageClient.setRating(a5);
               pageClient.setSodium(a6);
               pageClient.setPrice(a7);

            }catch(Exception e1){
                String message = "   INVALID DATA    ";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    class BackClientListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pageClient.dispose();
            actualOrder = null;
            openLogin();
        }
    }
    class AddItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try{
                JTable table1 = pageClient.getTable1();
                int row = table1.getSelectedRow();
                String title = null;
                if(((String)table1.getValueAt(row,0)).equals(""))
                    title = (String) table1.getValueAt(row,1);
                else
                    title = (String) table1.getValueAt(row,0);
                MenuItem target = null;
                for(MenuItem m : deliveryService.getMenuItems()){
                    if(m.getTitle().equals(title)){
                        target = m;
                        break;
                    }
                }
                actualOrder.add(target);
                pageClient.dispose();
                openClient();
            }catch (Exception e1){
                String message = "   SELECT MENU ITEM   ";
                JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    class AddOrderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            deliveryService.createNewOrder(actualOrder,actualUser);
            actualOrder = new ArrayList<>();
            pageClient.dispose();
            openClient();
        }
    }
    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            actualOrder = new ArrayList<>();
            pageClient.dispose();
            openClient();
        }
    }

    //pageEmployee




}
