package pages;

import Database.DatabaseHelper;
import Database.databaseOperations;
import classes.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProductPage extends JPanel{
    JFrame frame = new JFrame();

    Font typingFont = new Font("Arial",Font.PLAIN,14);
    Font labelFont = new Font("Arial",Font.BOLD,14);

    JButton backBtn = new JButton("Back");

    JTextField productName = new JTextField();
    JTextField sellingPrice = new JTextField();
    JTextField buyingPrice = new JTextField();
    JTextField quantity = new JTextField();
    JTextField minQuantityField = new JTextField();

    JLabel productNameLbl = new JLabel("-   Product Name : ");

    JLabel sellingPriceLbl = new JLabel("-   Selling Price : ");

    JLabel buyingPriceLbl = new JLabel("-   Buying Price : ");

    JLabel quantityLbl = new JLabel("-   Quantity : ");

    JLabel dateLbl = new JLabel(" -     Date : ");
    JLabel dateLblValue = new JLabel(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

    JLabel minQuantityLbl = new JLabel("-   Min-Quantity : ");


    JButton insertBtn = new JButton("Enter Product");
    JLabel msg = new JLabel();




    JList list = new JList(databaseOperations.allCategories.toArray());
    JScrollPane pane = new JScrollPane(list);

    // create a button and add action listener
    JButton btnGet = new JButton("Add Category");

    int extraYForMSG = 40;

    public AddProductPage(){
        typingRules();
        Design();
        frameSettings();
        buttonsAction();

    }


    private void Design(){


        backBtn.setBounds(35,30,80,20);
        productNameLbl.setBounds(45,95,140,15);
        productName.setBounds(190,90,190,25);

        sellingPriceLbl.setBounds(45,135,120,15);
        sellingPrice.setBounds(190,130,190,25);

        buyingPriceLbl.setBounds(45,175,120,15);
        buyingPrice.setBounds(190,170,190,25);


        quantityLbl.setBounds(45,215,120,15);
        quantity.setBounds(190,210,70,25);


        minQuantityLbl.setBounds(45,255,120,15);
        minQuantityField.setBounds(190,250,70,25);

        dateLbl.setBounds(45,295,120,15);
        dateLblValue.setBounds(172,290,160,25);


        insertBtn.setBounds(175,360,220,35);
        msg.setBounds(190,430 + extraYForMSG,160,20);

        pane.setBounds(450,80,100,160);
        btnGet.setBounds(420,260,160,20);

        //Fonts
        productNameLbl.setFont(labelFont);
        sellingPriceLbl.setFont(labelFont);
        quantityLbl.setFont(labelFont);
        dateLbl.setFont(labelFont);
        dateLblValue.setFont(typingFont);
        buyingPriceLbl.setFont(labelFont);
        minQuantityLbl.setFont(labelFont);

        //Text Color
        msg.setForeground(Color.red);

    }

    private void buttonsAction(){
        insertBtn.addActionListener((ActionEvent ae) ->{
            if(productName.getText().isEmpty() || sellingPrice.getText().isEmpty() || buyingPrice.getText().isEmpty() || quantity.getText().isEmpty()) {

                msg.setBounds(255,370 + extraYForMSG,200,20);
                msg.setText("Fill All Fields !");
            }else {
                try {
                    double d1 = Double.parseDouble(sellingPrice.getText());
                    double d2 = Double.parseDouble(buyingPrice.getText());
                    int i = Integer.parseInt(quantity.getText());
                    if (d1 < 0){
                        msg.setBounds(175, 370 + extraYForMSG, 330, 20);
                        msg.setText("You Enter Selling Price with Negative Value");
                    }
                    else if (d2 < 0){
                        msg.setBounds(175, 370 + extraYForMSG, 330, 20);
                        msg.setText("You Enter Buying Price with Negative Value");
                    }
                    else if (i < 0){
                        msg.setBounds(175, 370 + extraYForMSG, 330, 20);
                        msg.setText("You Enter Product Quantity with Negative Value");
                    }
                    else if(i==0){
                        msg.setBounds(175, 370 + extraYForMSG, 330, 20);
                        msg.setText("You Enter Product Quantity :  0");
                    }
                    else {
                        ConfirmPopUp pop = new ConfirmPopUp(frame,productName.getText());
                        if (pop.getResult() == 0) {
                            msg.setBounds(210, 370 + extraYForMSG, 200, 20);
                            msg.setText(insertProcess());
                        } else {
                            dateLblValue.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                        }
                    }
                }
                catch (NumberFormatException nfe){
                    msg.setBounds(210, 370 + extraYForMSG, 210, 20);
                    msg.setText("Check Your Data Again Please!!!");
                }
            }



        });

        backBtn.addActionListener((ActionEvent ae)->{
            new Menu();
            frame.dispose();
        });

        btnGet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               InputPopUp popUp= new InputPopUp(frame);
               if(!popUp.getResult().isEmpty()){
                   if(databaseOperations.searchForCategory(popUp.getResult()).equals("")){
                       DatabaseHelper.insertNewCategory(popUp.getResult());
                       list.setListData(databaseOperations.allCategories.toArray());
                       pane.repaint();
                       frame.revalidate();
                       frame.repaint();
                   }
                   else{
                       msg.setText(databaseOperations.searchForCategory(popUp.getResult()));
                   }
               }
            }// end actionPerformed
        });
    }

    private void frameSettings(){


        frame.add(productName);
        frame.add(productNameLbl);
        frame.add(sellingPrice);
        frame.add(sellingPriceLbl);
        frame.add(buyingPrice);
        frame.add(buyingPriceLbl);
        frame.add(quantity);
        frame.add(quantityLbl);
        frame.add(insertBtn);
        frame.add(dateLbl);
        frame.add(dateLblValue);
        frame.add(minQuantityLbl);
        frame.add(minQuantityField);
        frame.add(msg);
        frame.add(backBtn);
        frame.add(pane);
        frame.add(btnGet);
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Adding Product Page");
        frame.setSize(600,500);
        frame.setLocation(560,80);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    private void typingRules() {

        sellingPrice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER ) {
                    insertBtnChecking();
                }
            }
        });
        buyingPrice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER ) {
                    insertBtnChecking();
                }
            }

        });
        quantity.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                jTextFieldKeyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER ) {
                    insertBtnChecking();
                }
            }
        });

        minQuantityField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                jTextFieldKeyTyped(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER ) {
                    insertBtnChecking();
                }
            }
        });

        
    }

    private void insertBtnChecking(){

        try {
            double d1 = Double.parseDouble(sellingPrice.getText());
            double d2 = Double.parseDouble(buyingPrice.getText());
            int i = Integer.parseInt(quantity.getText());
            int j = Integer.parseInt(minQuantityField.getText());

            if (d1 < 0 || d2 < 0) {
                msg.setBounds(175, 370 + extraYForMSG, 330, 20);
                msg.setText("You Enter The Price with Negative Value");
            } else if (i < 0 || j < 0) {
                msg.setBounds(175, 370 + extraYForMSG, 330, 20);
                msg.setText("You Enter The Quantity with Negative Value");
            } else {
                ConfirmPopUp pop = new ConfirmPopUp(frame, productName.getText());
                if (pop.getResult() == 0) {
                    msg.setBounds(210, 370 + extraYForMSG, 200, 20);
                    msg.setText(insertProcess());
                } else {
                    dateLblValue.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                }
            }
        } catch (NumberFormatException nfe) {
            msg.setBounds(210, 370 + extraYForMSG, 210, 20);
            msg.setText("Check Your Data Again Please!!!");
        }
    }

    private void jTextFieldKeyTyped(java.awt.event.KeyEvent evt){
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }

    private String insertProcess(){
        if(list.getSelectedIndex()>=0) {
            boolean wanted = false;
            if(Integer.parseInt(minQuantityField.getText()) > Integer.parseInt(quantity.getText())){
                wanted = true;
            }
            return DatabaseHelper.insertData(
                    new Product(
                            productName.getText(),
                            Double.parseDouble(sellingPrice.getText()),
                            Double.parseDouble(buyingPrice.getText()),
                            Integer.parseInt(quantity.getText()),
                            new Date(),
                            (String) list.getModel().getElementAt(list.getSelectedIndex()),
                            Integer.parseInt(minQuantityField.getText()),
                            wanted
                    )
            );
        }
        return "Select A Category";
    }
}
