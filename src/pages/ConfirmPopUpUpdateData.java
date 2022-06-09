package pages;

import javax.swing.*;

public class ConfirmPopUpUpdateData {

    int result;
    public ConfirmPopUpUpdateData(JFrame parent, String productName){
        JDialog.setDefaultLookAndFeelDecorated(true);
        this.result = JOptionPane.showConfirmDialog(parent, "Are You Sure You Want to Update this product " + productName +" Data ?", "Confirm",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.NO_OPTION) {
            System.out.println("No button clicked");
        } else if (result == JOptionPane.YES_OPTION) {
            System.out.println("Yes button clicked");
        } else if (result == JOptionPane.CLOSED_OPTION) {
            System.out.println("JOptionPane closed");
        }
    }

    public int getResult() {
        return result;
    }
}
