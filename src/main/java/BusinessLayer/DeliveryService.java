package BusinessLayer;

import DataLayer.FilesWriter;
import DataLayer.Serializator;
import PresentationLayer.PageClient;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing{
     private HashMap<Order, ArrayList<MenuItem>> orders;
     private ArrayList<MenuItem> menuItems;

    /**
     * @invariant isWellFormed()
     */
    public DeliveryService() {
        this.orders = new HashMap<>();
        this.menuItems = new ArrayList<>();
    }

    protected boolean isWellFormed() {
        int n = 0;
        if(menuItems.isEmpty())return true;
        if(orders.isEmpty())return true;
        List<Order> verOrder =  new ArrayList<Order>();
        List<ArrayList<MenuItem>> verMenuItem =  new ArrayList<ArrayList<MenuItem>>();
        orders.forEach((k,v) -> {
            verOrder.add(k);
            verMenuItem.add(v);
        });
        for(MenuItem m :menuItems) {
            if(m.equals(null))return false;
            else if(m instanceof CompositeProduct){
                for(MenuItem mcomp: ((CompositeProduct) m).getMenuItems()){
                    if(mcomp.equals(null))return false;
                }
            }
        }
        return n == menuItems.size();
    }

    /**
     * citeste din .csv produsele si adauga la meniu
     */
    @Override
    public void importProducts() {
        ArrayList<MenuItem> mi = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("products.csv"));
            br.readLine();   //
            while ((line = br.readLine()) != null)
            {
                String[] product = line.split(splitBy);    // use comma as separator
                BaseProduct baseProduct = null;
                try {
                    baseProduct = new BaseProduct(product[0], Double.parseDouble(product[1]), Integer.parseInt(product[2]), Integer.parseInt(product[3]), Integer.parseInt(product[4]), Integer.parseInt(product[5]), Integer.parseInt(product[6]));
                }
                catch (Exception e){
                    System.out.println("\n\n\n eroareee");
                }
                mi.add(baseProduct);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        List<String> titles = new ArrayList<>();
        for(MenuItem m:mi){
            titles.add(m.getTitle());
        }
        titles = titles.stream()
                .distinct()
                .collect( Collectors.toList() );
        menuItems = new ArrayList<MenuItem>();
        for(String s : titles){
            for(MenuItem m : mi){
                if(m.getTitle().equals(s)){
                    menuItems.add(m);
                    break;
                }
            }
        }
        Serializator.serializeMenuItems(this.getMenuItems());
        assert menuItems.isEmpty():" Not valid";
    }

    @Override
    public void newBaseProduct(String[] string) throws ArithmeticException {
        for(int i=0;i<=6;i++)
            assert string[i].equals(null):" Not valid newBaseProduct";
        String title = string[0];
        Double rating = Double.parseDouble(string[1]);
        int calories = Integer.parseInt(string[2]);
        int protein = Integer.parseInt(string[3]);
        int fat = Integer.parseInt(string[4]);
        int sodium = Integer.parseInt(string[5]);
        int price = Integer.parseInt(string[6]);
        BaseProduct b = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
        assert b.equals(null):" Not valid newBaseProduct";
        menuItems.add(b);
    }

    @Override
    public void deleteMenuItem(String title){
        assert title.equals(null):" Not valid deleteMenuItem";
        MenuItem target = null;
        for(MenuItem m : menuItems){
            if(m.getTitle().equals(title)){
                target = m;
                break;
            }
        }
        assert target.getTitle().equals(title):" Not valid deleteMenuItem";
        menuItems.remove(target);
        for(int i=0;i<menuItems.size();i++){
            if(menuItems.get(i).equals(target))
                assert true:" Not valid deleteMenuItem";
        }
    }

    @Override
    public void editMenuItem(int row,int col,String value)throws Exception{
        assert (value.equals(null)):" Not valid deleteMenuItem";
        int n=0;
        BaseProduct edit =null;
        for (MenuItem m: menuItems) {
            if(m instanceof BaseProduct) {
                if(n==row){
                    edit = (BaseProduct) m;
                    break;
                }
                n++;
            }
            else{
                for(MenuItem b : ((CompositeProduct)m).getMenuItems()){
                    if(n==row){
                        edit=(BaseProduct) b;
                        break;
                    }
                    n++;
                }
            }
        }
        if(col==1)
            edit.setTitle(value);
        else if(col == 2)
            edit.setRating(Double.parseDouble(value));
        else{
            int numb = Integer.parseInt(value);
            switch (col){
                case 3 :
                    edit.setCalories(numb);
                    break;
                case 4:
                    edit.setProtein(numb);
                    break;
                case 5:
                    edit.setFat(numb);
                    break;
                case 6:
                    edit.setSodium(numb);
                    break;
                case 7:
                    edit.setPrice(numb);
                    break;
            }

        }
    }

    public BaseProduct getBaseProduct(int i) throws Exception{
        BaseProduct result = null;
        int n=0;
        for (MenuItem m: menuItems) {
            if(m instanceof BaseProduct){
                if(n==i)
                    return  (BaseProduct) m;
                n++;
            }
            else{
                n=n+((CompositeProduct)m).getMenuItems().size();
                if(n>=i) throw new Exception() ;
            }
        }
        return result;
    }

    public void addToCompositeProduct(String nume, BaseProduct b) throws Exception{
        CompositeProduct result=null;
        for(MenuItem m: menuItems){
            if (m.getTitle().equals(nume)){
                if(m instanceof BaseProduct) throw new ArithmeticException();
                else{
                    result = (CompositeProduct) m;
                    result.addBaseProduct(b);
                    return;
                }
            }
        }
    }

    public int rowNumber(){
        int n=0;
        for (MenuItem m: menuItems) {
            if(m instanceof BaseProduct) n++;
            else{
                n=n+((CompositeProduct)m).getMenuItems().size();
            }
        }
        return n;
    }

    public void addMenuItem(MenuItem menuItem){
        menuItems.add(menuItem);
    }


    @Override
    public void generateReports(int startHour,int endHour,int minNrOrderP,int minNrOrderC,int minPriceC, int day , int month , int year) {
        assert (startHour < 0 || startHour >12 || endHour < 0 || endHour >12 || endHour>startHour || day < 0 || day >32 || month < 1 || month >13):" Not valid deleteMenuItem";

        orders=Serializator.getAllOrders(); // if not already done
        // first report: time interval of the orders – a report should be generated with the orders performed
        //between a given start hour and a given end hour regardless the date.


        List<Order> numbers =  new ArrayList<Order>();
        orders.forEach((k,v) -> {
            numbers.add(k);
        });
        List<Order> sortedOrders =
                numbers.stream()
                        .filter(n -> {
                            if(n.getDate().getHours() >= startHour && n.getDate().getHours() <= endHour){
                                return true;
                            }
                            return false;
                        })
                        .collect(toList());
        String text1 = "TOATE COMENZILE INTRE ORELE "+startHour+" SI "+ endHour + " SUNT :\n";
        for(Order o : sortedOrders){
            text1 = text1+"\n" + o.toString();
            text1 = text1 + printProducts(orders.get(o));
        }
        FilesWriter.writeReport(1,text1);




        //second report:the products ordered more than a specified number of times so far
        List<MenuItem> products =  new ArrayList<MenuItem>();
        orders.forEach((k,v) -> {
            for(MenuItem p:v) {
                //System.out.println(p.toString());
                products.add(p);   //aici avem toate produsle din toate comenzile
            }
        });

        Set<MenuItem> produseRep = products  // am creat un set cu produsele care se repeta de
                                                //cel putin minNrOrderP ori
                .stream()
                .filter(n -> products       // ele se filtreaza astfel: n este adaugat daca
                        .stream()               // in acesr stream interior avem un numar
                        .filter(x -> x.equals(n))   // corect de elemente la final
                        .count() >= minNrOrderP)
                .collect(Collectors.toSet());

        String text2 = "TOATE PRODUSELE CARE AU FOST CUMPARATE DE MAI MULT DE "+minNrOrderP+" DE ORI :\n";
        for(MenuItem p : produseRep){
            //System.out.println(p.toString());
            text2 = text2+"\n" + p.toString();
        }
        FilesWriter.writeReport(2,text2);



        //third report:the clients that have ordered more than a specified number of times so far and the
        //value of the order was higher than a specified amount.
        List<Order> comenzi =  new ArrayList<Order>();
        orders.forEach((k,v) -> {
            comenzi.add(k);   // aici is toate order
        });

        Set<Order> filtru = comenzi
                .stream()
                .filter(n -> comenzi
                        .stream()
                        .filter(x -> {
                            if(x.getClientID() == n.getClientID() && getPriceOfOrder(x)>minPriceC){
                                return true;
                            }
                            return false;
                        })
                        .count() >= minNrOrderC)
                .collect(Collectors.toSet());
        String text3 = "CLIENTII CARE AU MAI MULT DE "+minNrOrderC+" COMENZI CU PRETUL MAI MARE DE "+ minPriceC + " SUNT :\n";
        ArrayList<Integer> idClient= new ArrayList<>();
        for(Order o :filtru){
            idClient.add(o.getClientID());
        }
        Set<Integer> filtru2 = idClient
                .stream()
                .distinct()
                .collect(Collectors.toSet());
        for(Integer o : filtru2){
            text3 = text3+"\n" + "CLIENT ID "+o;
        }
        //System.out.println(text3);
        FilesWriter.writeReport(3,text3);
        // report 4: the products ordered within a specified day with the number of times they have
        //been ordered.

        List<Order> dataOrder=
                numbers.stream()
                        .filter(n -> {
                            if(n.getDate().getDate()== day && (n.getDate().getMonth()+1) == month && (n.getDate().getYear()+1900) == year){
                                return true; // filtrez dupa data
                            }
                            return false;
                        })
                        .collect(toList());
        // am toate comenzile din data resp in dataOrder
        // => scot produsele:
        List<MenuItem> dataProd =  new ArrayList<MenuItem>();
        orders.forEach((k,v) -> {
            for(MenuItem p:v) {
                dataProd.add(p);   //aici avem toate produsle din toate comenzile din data data
            }
        });
        String text4 = "TOATE PRODUSELE CARE AU FOST CUMPARATE IN DATA DE "+day+"/"+month+"/"+year+" SUNT :\n";
        final String[] aux = {""};
        Set<MenuItem> produseRep23 = dataProd
                .stream()
                .distinct()
                .collect(Collectors.toSet());
        Set<MenuItem> produseRep2 = produseRep23
                .stream()
                .map(n->{
                    aux[0]+=n.toString() + " a aparut de "+dataProd
                            .stream()
                            .filter(x -> x.equals(n))
                            .count()+ " ori \n";
                    return n;
                })
                .collect(Collectors.toSet());
        text4 =text4 + aux[0];
        FilesWriter.writeReport(4,text4);
    }

    public int getPriceOfOrder(Order o){
        int totalPrice = 0;
        for(MenuItem m : orders.get(o)){
            totalPrice = totalPrice + m.computePrice();
        }
        return totalPrice;
    }

    public String printProducts(ArrayList<MenuItem> products){
        String rezult = "";
        for(MenuItem p : products){
            rezult = rezult + p.toString() +"\n";
        }
        return rezult;
    }

    @Override
    public void createNewOrder(ArrayList<MenuItem>  items , User user) {
        assert (items.equals(null) || items.size()==0):" Not valid createNewOrder";
        assert (user.equals(null)):" Not valid createNewOrder";
        int size= orders.size();
        orders=Serializator.getAllOrders();
        Order newOrder = new Order(user.getId());
        int totalPrice = 0;
        String text = "";
        for(MenuItem m : items){
            text = text + m.toString() +"\n";
            totalPrice = totalPrice + m.computePrice();
        }
        System.out.println(newOrder.toString() + text +"Total price: "+ totalPrice +"\n");
        FilesWriter.writeBill(newOrder.toString() + text +"Total price: "+ totalPrice +"\n");
        orders.put(newOrder,items);
        Serializator.serializeOrders(orders);
        setChanged();
        notifyObservers(newOrder);
        assert (size+1==orders.size()):" Not valid reateNewOrder";
    }

    @Override
    public List<MenuItem> searchProduct(PageClient pageClient) throws Exception{
        assert (pageClient.equals(null)):" Not valid reateNewOrder";
        String title;
        List<MenuItem> menius = menuItems;
        double rating;
        int calories,protein,fat,sodium,price;
        if(!pageClient.getTitle().equals("")) {
            title = pageClient.getTitle();
            menius = menuItems.stream()
                    .filter(n -> {
                        if(n instanceof CompositeProduct){
                            if(n.getTitle().toLowerCase().indexOf(title) != -1) return true;
                            for(MenuItem p : ((CompositeProduct) n).getMenuItems()){
                                if(((BaseProduct)p).getTitle().toLowerCase().indexOf(title) != -1){
                                    return true;
                                }
                            }
                        }
                        else if(((BaseProduct)n).getTitle().toLowerCase().indexOf(title) != -1){
                            return true;
                        }
                        return false;
                    })
                    .collect(toList());
        }
        if(!pageClient.getRating().equals("")) {
            rating = Double.parseDouble(pageClient.getRating());
            menius = menius.stream()
                    .filter(n -> {
                        if(n instanceof CompositeProduct){
                            for(MenuItem p : ((CompositeProduct) n).getMenuItems()){
                                if(((BaseProduct)p).getRating()==rating){
                                    return true;
                                }
                            }
                        }
                        else if(((BaseProduct)n).getRating()==rating){
                            return true;
                        }
                        return false;
                    })
                    .collect(toList());
        }
        if(!pageClient.getCalories().equals("")) {
            calories = Integer.parseInt(pageClient.getCalories());
            menius = menius.stream()
                    .filter(n -> {
                        if(n instanceof CompositeProduct){
                            for(MenuItem p : ((CompositeProduct) n).getMenuItems()){
                                if(((BaseProduct)p).getCalories()==calories){
                                    return true;
                                }
                            }
                        }
                        else if(((BaseProduct)n).getCalories()==calories){
                            return true;
                        }
                        return false;
                    })
                    .collect(toList());
        }
        if(!pageClient.getProtein().equals("")) {
            protein = Integer.parseInt(pageClient.getProtein());
            menius = menius.stream()
                    .filter(n -> {
                        if(n instanceof CompositeProduct){
                            for(MenuItem p : ((CompositeProduct) n).getMenuItems()){
                                if(((BaseProduct)p).getProtein()==protein){
                                    return true;
                                }
                            }
                        }
                        else if(((BaseProduct)n).getProtein()==protein){
                            return true;
                        }
                        return false;
                    })
                    .collect(toList());
        }
        if(!pageClient.getFat().equals("")) {
            fat = Integer.parseInt(pageClient.getFat());
            menius = menius.stream()
                    .filter(n -> {
                        if(n instanceof CompositeProduct){
                            for(MenuItem p : ((CompositeProduct) n).getMenuItems()){
                                if(((BaseProduct)p).getFat()==fat){
                                    return true;
                                }
                            }
                        }
                        else if(((BaseProduct)n).getFat()==fat){
                            return true;
                        }
                        return false;
                    })
                    .collect(toList());
        }
        if(!pageClient.getSodium().equals("")) {
            sodium = Integer.parseInt(pageClient.getSodium());
            menius = menius.stream()
                    .filter(n -> {
                        if(n instanceof CompositeProduct){
                            for(MenuItem p : ((CompositeProduct) n).getMenuItems()){
                                if(((BaseProduct)p).getSodium()==sodium){
                                    return true;
                                }
                            }
                        }
                        else if(((BaseProduct)n).getSodium()==sodium){
                            return true;
                        }
                        return false;
                    })
                    .collect(toList());
        }
        if(!pageClient.getPrice().equals("")) {
            price = Integer.parseInt(pageClient.getPrice());
            menius = menius.stream()
                    .filter(n -> {
                        if(n instanceof CompositeProduct){
                            for(MenuItem p : ((CompositeProduct) n).getMenuItems()){
                                if(((BaseProduct)p).getPrice()==price){
                                    return true;
                                }
                            }
                        }
                        else if(((BaseProduct)n).getPrice()==price){
                            return true;
                        }
                        return false;
                    })
                    .collect(toList());
        }
        return menius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryService that = (DeliveryService) o;
        return Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders);
    }


    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
