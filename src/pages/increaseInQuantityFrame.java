package pages;

import Database.*;
import javax.swing.*;
import java.awt.event.*;



public class increaseInQuantityFrame extends JPanel {

    JFrame frame = new JFrame();
    JLabel productNameLabel = new JLabel("");
    JLabel productCategoryLabel = new JLabel("");
    JLabel oldQuantityLabel = new JLabel("");
    JLabel newQuantityLabel = new JLabel("");
    JTextField newQuantityTextField = new JTextField("");
    JButton submitBtn = new JButton("Submit");
    JButton backBtn = new JButton("Back");
    JLabel successLabel = new JLabel("");


    int recordNumber, oldQuantity;
    String productName, productCategory;

    public increaseInQuantityFrame(String productName, String productCategory, int oldQuantity, int recordNumber){
        this.recordNumber = recordNumber;
        this.productName = productName;
        this.productCategory = productCategory;
        this.oldQuantity = oldQuantity;
        Design();


        buttonsAction();
        frameSettings();
    }

    private void Design(){
        productNameLabel.setBounds(50,20,400,20);
        productCategoryLabel.setBounds(50,60,400,20);
        oldQuantityLabel.setBounds(50,100,400,20);
        newQuantityLabel.setBounds(50,140,100,20);
        newQuantityTextField.setBounds(160,140,50,25);
        submitBtn.setBounds(100,210,80,20);
        backBtn.setBounds(100,250,80,20);
        successLabel.setBounds(80,290,150,20);

        productNameLabel.setText("Name  :    " + productName);
        productCategoryLabel.setText("Category  :    " + productCategory);
        oldQuantityLabel.setText("Old Quantity  =    " + oldQuantity);
        newQuantityLabel.setText("New Quantity  =");

    }

    private void jTextFieldKeyTyped(java.awt.event.KeyEvent evt){
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }

    private void buttonsAction(){
        submitBtn.addActionListener((ActionEvent ae) -> {
            if(!newQuantityTextField.getText().isEmpty()) {
                successLabel.setText("");
                submittingProcess();
            }else {
                successLabel.setText("New Quantity ??");
            }
        });

        backBtn.addActionListener((ActionEvent ae) -> {
            new wantedProductsPage();
            frame.dispose();
        });

        newQuantityTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                jTextFieldKeyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if(!newQuantityTextField.getText().isEmpty()) {
                        submittingProcess();
                    }else{
                        successLabel.setText("New Quantity ??");
                    }
                }
            }
        });

    }

    private void submittingProcess(){

        DatabaseHelper.updateProductQuantity(productName,Integer.parseInt(newQuantityTextField.getText()),productCategory);

        successLabel.setText("Submitted Successfully");

    }

    private void frameSettings(){


        frame.add(productNameLabel);
        frame.add(productCategoryLabel);
        frame.add(oldQuantityLabel);
        frame.add(newQuantityLabel);
        frame.add(newQuantityTextField);
        frame.add(submitBtn);
        frame.add(backBtn);
        frame.add(successLabel);
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.setSize(300,370);
        frame.setLocation(500,250);
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
