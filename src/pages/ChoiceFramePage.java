package pages;

import Database.databaseOperations;

import javax.swing.*;
import java.awt.*;


public class ChoiceFramePage extends JPanel {

    int productNumber;
    String sProductName, category;

    JFrame frame = new JFrame();
    JButton updateProductData = new JButton("Update Product Data"),
            sellProduct = new JButton("Sell Product"),
            cancel = new JButton("Cancel");
    JLabel productName = new JLabel();

    public ChoiceFramePage(String sProductName,  String category){

        this.sProductName = sProductName;
        this.category = category;
        productNumber = databaseOperations.search(sProductName,category);
        buttonsActions();
        Design();
        frameSettings();
    }

    private void buttonsActions() {
        updateProductData.addActionListener(e -> {
            frame.dispose();
            new UpdateProductPage(productNumber);
        });
        sellProduct.addActionListener(e -> {
            frame.dispose();
            new SellPage(productNumber);
        });

        cancel.addActionListener(e -> {
            frame.dispose();
            new showProductsPage(400,60);
        });

    }
    private void Design(){

        productName.setText("Product name: "+sProductName);
        productName.setForeground(Color.BLACK);
        productName.setBounds(130,20,750,35);
        updateProductData.setBounds(120,90,160,35);
        sellProduct.setBounds(120,180,160,35);
        cancel.setBounds(120,270,160,35);

    }
    private void frameSettings(){

        frame.add(sellProduct);
        frame.add(updateProductData);
        frame.add(cancel);
        frame.add(productName);
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Choice");
        int width = sProductName.length();
        if(width >= 50){
            width = 500;
        }else{
            width = 400;
        }
        frame.setSize(width,420);
        frame.setLocation(500,50);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
