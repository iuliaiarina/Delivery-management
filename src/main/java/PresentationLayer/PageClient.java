package PresentationLayer;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.DeliveryService;
import BusinessLayer.MenuItem;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PageClient extends JFrame {

    private JPanel contentPane;
    private JTextField title;
    private JTextField rating;
    private JTextField calories;
    private JTextField protein;
    private JTextField fat;
    private JTextField sodium;
    private JTextField price;
    private JTable table1;
    private JTable table2;
    private JButton search;
    private JButton back;
    private JButton addItem;
    private JButton addOrder;
    private JButton delete;
    private JScrollPane scrollPane;
    private String[] columnNames = {
            "Combo",
            "Title",
            "Rating",
            "Calories",
            "Protein",
            "Fat",
            "Sodium",
            "Price"};

    public PageClient(List<MenuItem> menius ,ArrayList<MenuItem> order) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 710, 538);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        title = new JTextField();
        title.setColumns(10);
        title.setBounds(79, 25, 80, 20);
        contentPane.add(title);

        rating = new JTextField();
        rating.setColumns(10);
        rating.setBounds(300, 25, 30, 20);
        contentPane.add(rating);

        calories = new JTextField();
        calories.setColumns(10);
        calories.setBounds(340, 25, 30, 20);
        contentPane.add(calories);

        protein = new JTextField();
        protein.setColumns(10);
        protein.setBounds(381, 25, 30, 20);
        contentPane.add(protein);

        fat = new JTextField();
        fat.setColumns(10);
        fat.setBounds(425, 25, 30, 20);
        contentPane.add(fat);

        sodium = new JTextField();
        sodium.setColumns(10);
        sodium.setBounds(465, 25, 30, 20);
        contentPane.add(sodium);

        price = new JTextField();
        price.setColumns(10);
        price.setBounds(510, 25, 30, 20);
        contentPane.add(price);

        search = new JButton("SEARCH");
        search.setBounds(580, 24, 89, 23);
        contentPane.add(search);


        List<MenuItem> item = menius;
        int n=0;
        for (MenuItem m: menius) {
            if(m instanceof BaseProduct) n++;
            else{
                n=n+((CompositeProduct)m).getMenuItems().size();
            }
        }
        Object[][] data1 =new Object[n][10];
        int i=0;
        int row=0;
        while(i<item.size()){
            if(item.get(i) instanceof BaseProduct) {
                data1[row][0] = "";
                data1[row][1] = ((BaseProduct) item.get(i)).getTitle();
                data1[row][2] = ((BaseProduct) item.get(i)).getRating();
                data1[row][3] = ((BaseProduct) item.get(i)).getCalories();
                data1[row][4] = ((BaseProduct) item.get(i)).getProtein();
                data1[row][5] = ((BaseProduct) item.get(i)).getFat();
                data1[row][6] = ((BaseProduct) item.get(i)).getSodium();
                data1[row][7] = ((BaseProduct) item.get(i)).getPrice();
                row++;
            }
            else if (item.get(i) instanceof CompositeProduct){
                ArrayList<MenuItem> menuItems=((CompositeProduct)item.get(i)).getMenuItems();
                for(MenuItem m: menuItems) {
                    data1[row][0] = ((CompositeProduct) item.get(i)).getTitle();
                    data1[row][1] = ((BaseProduct) m).getTitle();
                    data1[row][2] = ((BaseProduct) m).getRating();
                    data1[row][3] = ((BaseProduct) m).getCalories();
                    data1[row][4] = ((BaseProduct) m).getProtein();
                    data1[row][5] = ((BaseProduct) m).getFat();
                    data1[row][6] = ((BaseProduct) m).getSodium();
                    data1[row][7] = ((BaseProduct) m).getPrice();
                    row++;
                }
            }
            i++;
        }
        table1 = new JTable(data1, columnNames);
        table1.getColumnModel().getColumn(1).setPreferredWidth(510);
        scrollPane = new JScrollPane(table1);
        scrollPane.setBounds(10, 56, 551, 279);
        contentPane.add(scrollPane);

        n=0;
        for (MenuItem m: order) {
            if(m instanceof BaseProduct) n++;
            else{
                n=n+((CompositeProduct)m).getMenuItems().size();
            }
        }
        Object[][] data2 =new Object[n][10];
        i=0;
        row=0;
        while(i<order.size()){
            if(order.get(i) instanceof BaseProduct) {
                data2[row][0] = "";
                data2[row][1] = ((BaseProduct) order.get(i)).getTitle();
                data2[row][2] = ((BaseProduct) order.get(i)).getRating();
                data2[row][3] = ((BaseProduct) order.get(i)).getCalories();
                data2[row][4] = ((BaseProduct) order.get(i)).getProtein();
                data2[row][5] = ((BaseProduct) order.get(i)).getFat();
                data2[row][6] = ((BaseProduct) order.get(i)).getSodium();
                data2[row][7] = ((BaseProduct) order.get(i)).getPrice();
                row++;
            }
            else if (order.get(i) instanceof CompositeProduct){
                ArrayList<MenuItem> menuItems=((CompositeProduct)order.get(i)).getMenuItems();
                for(MenuItem m: menuItems) {
                    data2[row][0] = ((CompositeProduct) order.get(i)).getTitle();
                    data2[row][1] = ((BaseProduct) m).getTitle();
                    data2[row][2] = ((BaseProduct) m).getRating();
                    data2[row][3] = ((BaseProduct) m).getCalories();
                    data2[row][4] = ((BaseProduct) m).getProtein();
                    data2[row][5] = ((BaseProduct) m).getFat();
                    data2[row][6] = ((BaseProduct) m).getSodium();
                    data2[row][7] = ((BaseProduct) m).getPrice();
                    row++;
                }
            }
            i++;
        }
        table2 = new JTable(data2, columnNames);
        table2.getColumnModel().getColumn(1).setPreferredWidth(510);
        JScrollPane scrollPane_1 = new JScrollPane(table2);
        scrollPane_1.setBounds(108, 346, 453, 144);
        contentPane.add(scrollPane_1);


        back = new JButton("BACK");
        back.setBounds(580, 449, 89, 23);
        contentPane.add(back);

        JLabel lblNewLabel = new JLabel("ORDER:");
        lblNewLabel.setFont(new Font("umbxti10", Font.ITALIC, 14));
        lblNewLabel.setBounds(10, 346, 129, 14);
        contentPane.add(lblNewLabel);


        addItem = new JButton("ADD");
        addItem.setBounds(10, 371, 89, 23);
        contentPane.add(addItem);

        delete = new JButton("DELETE");
        delete.setBounds(10, 436, 89, 23);
        contentPane.add(delete);

        addOrder = new JButton("ADD ORDER");
        addOrder.setFont(new Font("Tahoma", Font.PLAIN, 9));
        addOrder.setBounds(10, 403, 89, 23);
        contentPane.add(addOrder);
    }

    public JTable getTable1() {
        return table1;
    }
    public JTable getTable2() {
        return table2;
    }

    public String getTitle() {
        return title.getText();
    }
    public String getSodium() {
        return sodium.getText();
    }
    public String getPrice() {
        return price.getText();
    }
    public String getRating() {
        return rating.getText();
    }
    public String getCalories() {
        return calories.getText();
    }
    public String getProtein() {
        return protein.getText();
    }
    public String getFat() {
        return fat.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
    public void setRating(String rating) {
        this.rating.setText(rating);
    }
    public void setCalories(String calories) {
        this.calories.setText(calories);
    }
    public void setProtein(String protein) {
        this.protein.setText(protein);
    }
    public void setFat(String fat) {
        this.fat.setText(fat);
    }
    public void setSodium(String sodium) {
        this.sodium.setText(sodium);
    }
    public void setPrice(String price) {
        this.price.setText(price);
    }

    public void addSearchListener(ActionListener x) {search.addActionListener(x);}
    public void addBackListener(ActionListener x) { back.addActionListener(x);}
    public void addAddItemListener(ActionListener x) {addItem.addActionListener(x);}
    public void addAddOrderListener(ActionListener x) {addOrder.addActionListener(x);}
    public void addDeleteListener(ActionListener x) {delete.addActionListener(x);}
}
