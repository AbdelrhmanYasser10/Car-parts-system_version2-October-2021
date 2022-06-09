package pages;

import javax.swing.*;

public class ConfirmRemovePopUp {
    private int result;

    public ConfirmRemovePopUp(JFrame parent, int number,String option) {
        switch (number) {
            case 1 -> {
                JDialog.setDefaultLookAndFeelDecorated(true);
                this.result = JOptionPane.showConfirmDialog(parent, "Are You Sure That You Want To " + option + " this product from/to returns products?", "Confirm",
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
                this.result = JOptionPane.showConfirmDialog(parent, "Are You Sure That You Want To " + option + " this product from/to wanted products?", "Confirm",
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
    public int getResult() {
        return result;
    }
}
