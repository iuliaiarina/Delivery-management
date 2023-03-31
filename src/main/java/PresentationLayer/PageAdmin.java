package PresentationLayer;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.DeliveryService;
import BusinessLayer.MenuItem;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PageAdmin extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTextField CompP;
    private JButton back;
    private JButton generate;
    private JButton importData;
    private JButton editProduct;
    private JButton newProduct;
    private JButton deleteProduct;
    private JButton create;
    private JButton addToCompP;

    public PageAdmin(DeliveryService deliveryService) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 710, 538);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("MENIU:");
        lblNewLabel.setFont(new Font("Yu Gothic UI Light", Font.ITALIC, 20));
        lblNewLabel.setBounds(10, 15, 155, 19);
        contentPane.add(lblNewLabel);

        back = new JButton("BACK");
        back.setBounds(574, 11, 89, 23);
        contentPane.add(back);

        generate = new JButton("REPORT");
        generate.setBounds(574, 45, 89, 23);
        contentPane.add(generate);

        importData = new JButton("IMPORT");
        importData.setBounds(574, 75, 89, 23);
        contentPane.add(importData);

        String[] columnNames = {"Combo","Title",//Title,Rating,Calories,Protein,Fat,Sodium,Price
                "Rating",
                "Calories",
                "Protein",
                "Fat",
                "Sodium",
                "Price"};

        ArrayList<MenuItem> item = deliveryService.getMenuItems();
        Object[][] data =new Object[deliveryService.rowNumber()+1][10];
        int i=0;
        int row=0;
        while(i<item.size()){
            if(item.get(i) instanceof BaseProduct) {
                data[row][0] = "";
                data[row][1] = ((BaseProduct) item.get(i)).getTitle();
                data[row][2] = ((BaseProduct) item.get(i)).getRating();
                data[row][3] = ((BaseProduct) item.get(i)).getCalories();
                data[row][4] = ((BaseProduct) item.get(i)).getProtein();
                data[row][5] = ((BaseProduct) item.get(i)).getFat();
                data[row][6] = ((BaseProduct) item.get(i)).getSodium();
                data[row][7] = ((BaseProduct) item.get(i)).getPrice();
                row++;
            }
            else if (item.get(i) instanceof CompositeProduct){
                ArrayList<MenuItem> menuItems=((CompositeProduct)item.get(i)).getMenuItems();
               // System.out.println(((CompositeProduct) item.get(i)).getTitle());
                for(MenuItem m: menuItems) {
                  //  System.out.println(((CompositeProduct) item.get(i)).getTitle());
                    data[row][0] = ((CompositeProduct) item.get(i)).getTitle();
                    data[row][1] = ((BaseProduct) m).getTitle();
                    data[row][2] = ((BaseProduct) m).getRating();
                    data[row][3] = ((BaseProduct) m).getCalories();
                    data[row][4] = ((BaseProduct) m).getProtein();
                    data[row][5] = ((BaseProduct) m).getFat();
                    data[row][6] = ((BaseProduct) m).getSodium();
                    data[row][7] = ((BaseProduct) m).getPrice();
                    row++;
                }
            }
            i++;
        }

        table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(1).setPreferredWidth(510);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 45, 479, 445);
        contentPane.add(scrollPane);

        editProduct = new JButton("EDIT PRODUCT");
        editProduct.setBounds(525, 158, 138, 23);
        contentPane.add(editProduct);

        newProduct = new JButton("NEW PRODUCT");
        newProduct.setBounds(525, 124, 138, 23);
        contentPane.add(newProduct);

        deleteProduct = new JButton("DELETE");
        deleteProduct.setBounds(525, 192, 138, 23);
        contentPane.add(deleteProduct);

        create = new JButton("Create comp product");
        create.setBounds(509, 337, 176, 19);
        contentPane.add(create);

        CompP = new JTextField();
        CompP.setBounds(574, 306, 96, 20);
        contentPane.add(CompP);
        CompP.setColumns(10);

        addToCompP = new JButton("ADD");
        addToCompP.setBounds(596, 367, 89, 23);
        contentPane.add(addToCompP);

        JLabel lblNewLabel_1 = new JLabel("NAME:");
        lblNewLabel_1.setBounds(515, 309, 49, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("ADD ITEM:");
        lblNewLabel_2.setBounds(519, 371, 100, 14);
        contentPane.add(lblNewLabel_2);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public String getCompP() {
        return CompP.getText();
    }
    public void setCompP(String t) {
        CompP.setText(t);
    }


    public void addBackAdminListener(ActionListener x) {
        back.addActionListener(x);
    }
    public void addGenerateListener(ActionListener x) {
        generate.addActionListener(x);
    }
    public void addImportDataListener(ActionListener x) {
        importData.addActionListener(x);
    }
    public void addEditProductListener(ActionListener x) {
        editProduct.addActionListener(x);
    }
    public void addNewProductListener(ActionListener x) {
        newProduct.addActionListener(x);
    }
    public void addDeleteProductListener(ActionListener x) {
        deleteProduct.addActionListener(x);
    }
    public void addCreateListener(ActionListener x) {
        create.addActionListener(x);
    }
    public void addToCompPListener(ActionListener x) {
        addToCompP.addActionListener(x);
    }
}
