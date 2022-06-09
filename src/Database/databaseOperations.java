package Database;

import classes.Product;

import java.util.ArrayList;
import java.util.Date;

public class databaseOperations {
    /*The array list that will get data from database (please check getAllData function in DatabaseHelper Class)*/

    public static ArrayList<Product> data = new ArrayList<>();

    public static ArrayList<String>searchProducts = new ArrayList<>();

    public static ArrayList<String> allCategories = new ArrayList<>();

    public static ArrayList<Product> listOfProductsOfASpecificCategory = new ArrayList<>();

    public static  ArrayList<Product> salesData = new ArrayList<>();

    public static ArrayList<Product> wantedProducts = new ArrayList<>();

    public databaseOperations(){

        //try to run after deleting system.db
        DatabaseHelper.setName("system.db"); // db is extension for database file
        DatabaseHelper.createNewDatabase();
        DatabaseHelper.getAllData();
        DatabaseHelper.getAllCategories();
        DatabaseHelper.getAllSalesData();
        DatabaseHelper.getWantedProducts();
    }
    public static int search(String productName , String category){

        for (int i = 0; i < data.toArray().length; i++){
            if(data.get(i).getName().equals(productName) && data.get(i).getCategory().equals(category)){
                return i;
            }
        }

        return -1;
    }

    public static String searchForCategory(String category) {
        for (int i = 0; i < allCategories.toArray().length; i++) {
            if (allCategories.get(i).equals(category)) {
                return "This Category Already Exist";
            }
        }
        return "";
    }


}
