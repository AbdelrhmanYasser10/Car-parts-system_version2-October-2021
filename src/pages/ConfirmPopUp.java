package  pages;

import Database.DatabaseHelper;
import Database.databaseOperations;
import classes.Product;

import javax.swing.*;

public class ConfirmPopUp {

    private int result;
    private String productName;
    public ConfirmPopUp (){ }
    public ConfirmPopUp(JFrame parent,String price,String productName , String quantity){
        JDialog.setDefaultLookAndFeelDecorated(true);
        this.result = JOptionPane.showConfirmDialog(parent, "Are You Sure That You Want To Sell " + quantity + " Products From "+ productName+" For " + price +" L.E ?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.NO_OPTION) {
            System.out.println("No button clicked");
        } else if (result == JOptionPane.YES_OPTION) {
            System.out.println("Yes button clicked");
        } else if (result == JOptionPane.CLOSED_OPTION) {
            System.out.println("JOptionPane closed");
        }
    }

    public ConfirmPopUp(JFrame parent,String productName){
        JDialog.setDefaultLookAndFeelDecorated(true);
        this.result = JOptionPane.showConfirmDialog(parent, "Are You Sure You Want to add this product " + productName +" ?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.NO_OPTION) {
            System.out.println("No button clicked");
        } else if (result == JOptionPane.YES_OPTION) {
            System.out.println("Yes button clicked");
        } else if (result == JOptionPane.CLOSED_OPTION) {
            System.out.println("JOptionPane closed");
        }
    }

    public ConfirmPopUp(JFrame parent,String productName,String proCategory,int x, boolean wanted){
        JDialog.setDefaultLookAndFeelDecorated(true);
        this.result = JOptionPane.showConfirmDialog(parent, "Are You Sure You Want to Remove " + productName +" ?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.NO_OPTION) {
            System.out.println("No button clicked");
        } else if (result == JOptionPane.YES_OPTION) {
            System.out.println("Yes button clicked");
            DatabaseHelper.setWantedValue(false,productName,proCategory);
            wantedProductsPage.wantedProductsArr[x][0].setText(" _ ");
            wantedProductsPage.wantedProductsArr[x][1].setText("");
            wantedProductsPage.wantedProductsArr[x][2].setText("");
        } else if (result == JOptionPane.CLOSED_OPTION) {
            System.out.println("JOptionPane closed");
        }
    }

    public ConfirmPopUp(JFrame parent , String day , int number){
        switch (number) {
            case 1 -> {
                JDialog.setDefaultLookAndFeelDecorated(true);
                this.result = JOptionPane.showConfirmDialog(parent, "Are You Sure You Want to Export Sales PDF For this " + day + " date ?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) {
                    System.out.println("No button clicked");
                } else if (result == JOptionPane.YES_OPTION) {
                    System.out.println("Yes button clicked");
                } else if (result == JOptionPane.CLOSED_OPTION) {
                    System.out.println("JOptionPane closed");
                }
            }
            case 2 -> {
                JDialog.setDefaultLookAndFeelDecorated(true);
                this.result = JOptionPane.showConfirmDialog(parent, "Are You Sure You Want to Export Sales PDF For All Sales That Happen in the system ?", "Confirm",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.NO_OPTION) {
                    System.out.println("No button clicked");
                } else if (result == JOptionPane.YES_OPTION) {
                    System.out.println("Yes button clicked");
                } else if (result == JOptionPane.CLOSED_OPTION) {
                    System.out.println("JOptionPane closed");
                }
            }
        }
    }

    public ConfirmPopUp(JFrame frame) {

        productName = "";
        if (databaseOperations.searchProducts.toArray().length == 0) {
            JOptionPane.showMessageDialog(frame, "Sorry not found product match your search option , please try again");
        } else {
                productName = String.valueOf(JOptionPane.showInputDialog(frame, "Select The Product That You Search For", "Results", JOptionPane.QUESTION_MESSAGE,
                        null, databaseOperations.searchProducts.toArray(), " "));
        }
    }

    public String getProductName() {
        return productName;
    }

    public int getResult() {
        return result;
    }
}
