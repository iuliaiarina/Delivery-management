package BusinessLayer;

import PresentationLayer.PageClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IDeliveryServiceProcessing {


    //Administrator:
    /**
     * imports the products from file.cvs into menuItems
     * @pre true
     * @post !isEmpty()
     */
    public void importProducts();

    /**
     * create a new BaseProduct with string argument
     * @pre string != null
     * @post  menuItems.size()++; !empty()
     * @throws ArithmeticException
     */
    public void newBaseProduct(String[] string) throws ArithmeticException;

    /**
     *
     * @pre title != null
     * @post MenuItems.   getSize() == getSize()@pre + 1
     * @post ! MenuItems.get(MenuItem.getTitle().equals(title)) == null
     */
    public void deleteMenuItem(String title);

    /**
     *
     * @pre title != null
     * @post MenuItems.   getSize() == getSize()@pre - 1
     * @post  menuItem[row][col].get() =value
     * @throws Exception
     */
    public void editMenuItem(int row,int col,String value)throws Exception;

    /**
     * @pre 0<=startHour<=23
            * 0<=endHour<=23
            * startHour< endHour
            * 0 < day < 32
            * 0 < month < 13
            * year >1900
     * @post @nochange
     */
    public void  generateReports(int startHour,int endHour,int minNrOrderP,int minNrOrderC,int minPriceC, int day , int month , int year);
   //Client:

    /**
     *
     * @pre !items.isEmpty() and !user.isNULL():
     * @post menuItems.size()++;
     */
    public void createNewOrder(ArrayList<MenuItem> items , User user);//which implies computing the price for an order and generating a bill in .txt format,

    /**
     *
     * @pre !pageClient.isNULL()
     * @post @nochange
     * @throws Exception
     */
    public List<MenuItem> searchProduct(PageClient pageClient) throws Exception;// searching for products based on several criteria.

}
