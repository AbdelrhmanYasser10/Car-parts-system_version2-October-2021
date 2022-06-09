package pages;

import javax.swing.*;

public class InputPopUp {

    String result;
    public InputPopUp(JFrame frame){
        result = JOptionPane.showInputDialog(frame, "Enter new Category:");
    }

    public String getResult() {
        return result;
    }
}
