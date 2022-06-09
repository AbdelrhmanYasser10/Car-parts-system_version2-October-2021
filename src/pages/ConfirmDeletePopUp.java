package pages;

import javax.swing.*;

public class ConfirmDeletePopUp {
    int result;
    public ConfirmDeletePopUp(JFrame parent, String productName){
        JDialog.setDefaultLookAndFeelDecorated(true);
        this.result = JOptionPane.showConfirmDialog(parent, "Are You Sure You Want to Delete this product " + productName +" From System ?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.NO_OPTION) {
            System.out.println("No button clicked");
        } else if (result == JOptionPane.YES_OPTION) {
            System.out.println("Yes button clicked");
        } else if (result == JOptionPane.CLOSED_OPTION) {
            System.out.println("JOptionPane closed");
        }
    }

    public ConfirmDeletePopUp(JFrame parent,String categoryName,int number){
        if(number == 0) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            this.result = JOptionPane.showConfirmDialog(parent, "Are You Sure You Want to Delete this category " + categoryName + " From System ?\n Make Sure that all products from this category will be deleted ,too", "Confirm",
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

    public int getResult() {
        return result;
    }
}
