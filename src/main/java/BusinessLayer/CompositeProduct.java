package BusinessLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class CompositeProduct extends MenuItem implements Serializable {
    private String title;
    private ArrayList<MenuItem> menuItems;

    public CompositeProduct(String title) {
        this.title = title;
        this.menuItems = new ArrayList<MenuItem>();
    }

    public int computePrice(){
        int price=0;
        for(MenuItem m: menuItems){
            price= price+ m.computePrice();
        }
        return price;
    }

    @Override
    public String toString() {
        String text= "";
        for(MenuItem m : menuItems){
            text +="    "+ m.toString()+"\n";
        }
        return "CompositeProduct{" +
                "title='" + title + "'\n" +
                "menuItems="+"\n" + text +
                "endMenuItems}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeProduct that = (CompositeProduct) o;
        return Objects.equals(title, that.title) && Objects.equals(menuItems, that.menuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, menuItems);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void addBaseProduct(BaseProduct baseProduct){
        this.menuItems.add(baseProduct);
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
