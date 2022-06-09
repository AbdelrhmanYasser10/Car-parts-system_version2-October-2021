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

public class UpdateProductPage extends JPanel {
    JFrame frame = new JFrame();

    String oldProductName;

    Font typingFont = new Font("Arial",Font.PLAIN,14);
    Font labelFont = new Font("Arial",Font.BOLD,14);

    JTextField searchField = new JTextField();
    JButton searchBtn = new JButton("Search");

    JButton backBtn = new JButton("Back");

    JLabel productNameLbl = new JLabel("-   Product Name : ");
    JTextField productName = new JTextField();


    JLabel originalPriceLbl = new JLabel("-   Original Price : ");
    JTextField originalPriceValueLbl = new JTextField();

    JLabel sellingPriceLbl = new JLabel("-   Selling Price : ");
    JTextField sellingPriceValueLbl = new JTextField();

    JLabel quantityLbl = new JLabel("-   Quantity : ");
    JTextField quantityValueLbl = new JTextField();

    JLabel minQuantityLbl = new JLabel("-   Min-Quantity : ");
    JTextField minQuantityValue = new JTextField();


    JButton updateBtn = new JButton("Update");

    JButton DeleteBtn = new JButton("Delete");


    JList list = new JList(databaseOperations.allCategories.toArray());
    JScrollPane pane = new JScrollPane(list);

    // create a button and add action listener
    JButton btnGet = new JButton("Add Category");

    JButton btnDel = new JButton("Delete Category");


    JLabel msg = new JLabel();
    String oldCategory;


    public UpdateProductPage(int productNumber){

        if(productNumber != -1){
            System.out.println(productNumber);
            quickUpdate(productNumber);
        }

        typingRules();
        Design();
        frameSettings();
        buttonsAction(productNumber);

    }


    private void Design(){


        backBtn.setBounds(35,25,80,20);
        searchField.setBounds(175,25,250,30);
        searchField.setMargin(new Insets(2,8,2,2));

        searchBtn.setBounds(260,65,80,20);

        productNameLbl.setBounds(45,95,140,15);
        productName.setBounds(190,90,190,25);

        sellingPriceLbl.setBounds(45,135,120,15);
        sellingPriceValueLbl.setBounds(190,130,190,25);

        originalPriceLbl.setBounds(45,175,160,15);
        originalPriceValueLbl.setBounds(190,170,190,25);


        quantityLbl.setBounds(45,215,120,15);
        quantityValueLbl.setBounds(190,210,70,25);

        minQuantityLbl.setBounds(45,255,120,15);
        minQuantityValue.setBounds(190,250,70,25);


        updateBtn.setBounds(175,330,100,35);
        DeleteBtn.setBounds(300,330,100,35);
        msg.setBounds(190,370,160,20);

        pane.setBounds(450,160,100,160);
        btnGet.setBounds(420,120,160,20);
        btnDel.setBounds(420,330,160,20);

        //Fonts
        productNameLbl.setFont(labelFont);
        originalPriceLbl.setFont(labelFont);
        sellingPriceLbl.setFont(labelFont);
        quantityLbl.setFont(labelFont);
        minQuantityLbl.setFont(labelFont);
        minQuantityValue.setFont(typingFont);
        originalPriceValueLbl.setFont(labelFont);

        //Text Color
        msg.setForeground(Color.red);

    }

    private void jTextFieldKeyTyped(java.awt.event.KeyEvent evt){
        char c = evt.getKeyChar();
        if(!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }

    private void buttonsAction(int productNumber){
        updateBtn.addActionListener((ActionEvent ae) ->{
            if(sellingPriceValueLbl.getText().isEmpty() || originalPriceValueLbl.getText().isEmpty() || quantityValueLbl.getText().isEmpty() || minQuantityValue.getText().isEmpty()) {

                msg.setBounds(255,370,200,20);
                msg.setText("Fill All Fields !");
            }else {
                try {
                    double d1 = Double.parseDouble(sellingPriceValueLbl.getText());
                    double d2 = Double.parseDouble(originalPriceValueLbl.getText());
                    int i = Integer.parseInt(quantityValueLbl.getText());
                    int j = Integer.parseInt(minQuantityValue.getText());
                    if (d1 < 0){
                        msg.setBounds(175, 370, 330, 20);
                        msg.setText("You Enter Selling Price with Negative Value");
                    }
                    else if (d2 < 0){
                        msg.setBounds(175, 370, 330, 20);
                        msg.setText("You Enter Buying Price with Negative Value");
                    }
                    else if (i < 0 || j < 0){
                        msg.setBounds(175, 370, 330, 20);
                        msg.setText("You Enter The Quantity with Negative Value");
                    }
                    else if(i==0){
                        msg.setBounds(175, 370, 330, 20);
                        msg.setText("You Enter Product Quantity :  0");
                    }
                    else {
                        ConfirmPopUpUpdateData pop = new ConfirmPopUpUpdateData(frame,oldProductName);
                        if (pop.getResult() == 0) {
                            updateProcess();
                            msg.setBounds(210, 370, 200, 20);
                            msg.setText("Updated Successfully!");
                        } else {
                            minQuantityValue.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                        }
                    }
                }
                catch (NumberFormatException nfe){
                    msg.setBounds(210, 370, 210, 20);
                    msg.setText("Check Your Data Again Please!!!");
                }
            }
        });

        backBtn.addActionListener((ActionEvent ae)->{
            new Menu();
            frame.dispose();
        });

        DeleteBtn.addActionListener((ActionEvent ae) ->{
            if(sellingPriceValueLbl.getText().isEmpty() || originalPriceValueLbl.getText().isEmpty() || quantityValueLbl.getText().isEmpty()) {

                msg.setBounds(255,370,200,20);
                msg.setText("Fill All Fields !");
            }else {
                try {
                    double d1 = Double.parseDouble(sellingPriceValueLbl.getText());
                    double d2 = Double.parseDouble(originalPriceValueLbl.getText());
                    int i = Integer.parseInt(quantityValueLbl.getText());
                    if (d1 < 0){
                        msg.setBounds(175, 370, 330, 20);
                        msg.setText("You Enter Selling Price with Negative Value");
                    }
                    else if (d2 < 0){
                        msg.setBounds(175, 370, 330, 20);
                        msg.setText("You Enter Buying Price with Negative Value");
                    }
                    else if (i < 0){
                        msg.setBounds(175, 370, 330, 20);
                        msg.setText("You Enter Product Quantity with Negative Value");
                    }
                    else if(i==0){
                        msg.setBounds(175, 370, 330, 20);
                        msg.setText("You Enter Product Quantity :  0");
                    }
                    else {
                        ConfirmDeletePopUp pop = new ConfirmDeletePopUp(frame,oldProductName);
                        if (pop.getResult() == 0) {
                            deleteProcess();
                            msg.setBounds(210, 370, 200, 20);
                            msg.setText("Deleted Successfully!");
                        } else {
                            minQuantityValue.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                        }
                    }
                }
                catch (NumberFormatException nfe){
                    msg.setBounds(210, 370, 210, 20);
                    msg.setText("Check Your Data Again Please!!!");
                }
            }
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
                        msg.setText("");
                    }
                    else{
                        msg.setText(databaseOperations.searchForCategory(popUp.getResult()));
                    }
                }
            }// end actionPerformed
        });
        // end actionPerformed
        btnDel.addActionListener(e -> {
            if(list.getSelectedIndex()>=0) {
                ConfirmDeletePopUp popUp = new ConfirmDeletePopUp(frame, (String) list.getModel().getElementAt(list.getSelectedIndex()), 0);
                if (popUp.getResult() == 0) {
                    DatabaseHelper.deleteCategory((String) list.getModel().getElementAt(list.getSelectedIndex()));
                    list.setListData(databaseOperations.allCategories.toArray());
                    pane.repaint();
                    frame.revalidate();
                    frame.repaint();
                }
                else{
                    msg.setText("");

                }
            }
            else{
                msg.setBounds(235, 370, 200, 20);
                msg.setText("Select A Category !");
            }
        });

        searchBtn.addActionListener(e -> {
            if(list.getSelectedIndex() != -1) {
                DatabaseHelper.searchForProductsInStoke(searchField.getText(),(String) list.getModel().getElementAt(list.getSelectedIndex()));
                ConfirmPopUp pop = new ConfirmPopUp(frame);
                if (pop.getProductName().equals("null")) {
                    searchField.setText("");
                    msg.setBounds(235, 370, 200, 20);
                    msg.setText("Enter Product Name !");
                } else if (pop.getProductName().equals(" ")) {
                    searchField.setText("");
                    msg.setBounds(235, 370, 200, 20);
                    msg.setText("Enter Product Name !");
                } else {
                    searchField.setText(pop.getProductName());
                    msg.setText("");
                    searchProcess();
                }
            }
            else{
                msg.setBounds(235, 370, 200, 20);
                msg.setText("Select A Category !");
            }
        });
    }

    public void quickUpdate(int productNumber){

        msg.setText("");
        searchField.setText(String.valueOf(databaseOperations.data.get(productNumber).getName()));
        list.setSelectedIndex(getCategoryIndex(databaseOperations.data.get(productNumber)));
        productName.setText(searchField.getText());
        originalPriceValueLbl.setText(String.valueOf(databaseOperations.data.get(productNumber).getBuyPrice()));
        sellingPriceValueLbl.setText(String.valueOf(databaseOperations.data.get(productNumber).getSoldPrice()));
        quantityValueLbl.setText(String.valueOf(databaseOperations.data.get(productNumber).getQuantity()));
        minQuantityValue.setText(String.valueOf(databaseOperations.data.get(productNumber).getMinimumQuantity()));
        oldProductName = String.valueOf(databaseOperations.data.get(productNumber).getName());
        oldCategory = (String)list.getModel().getElementAt(list.getSelectedIndex());

    }

    private void frameSettings(){


        frame.add(searchField);
        frame.add(searchBtn);
        frame.add(productName);
        frame.add(productNameLbl);
        frame.add(sellingPriceValueLbl);
        frame.add(sellingPriceLbl);
        frame.add(originalPriceLbl);
        frame.add(originalPriceValueLbl);
        frame.add(quantityValueLbl);
        frame.add(quantityLbl);
        frame.add(updateBtn);
        frame.add(minQuantityLbl);
        frame.add(minQuantityValue);
        frame.add(msg);
        frame.add(backBtn);
        frame.add(pane);
        frame.add(btnGet);
        frame.add(DeleteBtn);
        frame.add(btnDel);
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Update Product Data");
        frame.setSize(600,460);
        frame.setLocation(560,80);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void typingRules(){
        quantityValueLbl.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                enterAction(e);
            }
        });
        minQuantityValue.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                enterAction(e);
            }
        });
        productName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                enterAction(e);
            }
        });
        originalPriceValueLbl.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                enterAction(e);
            }
        });
        sellingPriceValueLbl.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                enterAction(e);
            }
        });


        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER && !searchField.getText().isEmpty()) {
                    if (list.getSelectedIndex() != -1) {
                        DatabaseHelper.searchForProductsInStoke(searchField.getText(),(String)list.getModel().getElementAt(list.getSelectedIndex()));
                        ConfirmPopUp pop = new ConfirmPopUp(frame);
                        if (pop.getProductName().equals("null")) {
                            searchField.setText("");
                            msg.setBounds(235, 370, 200, 20);
                            msg.setText("Enter Product Name !");
                        } else if (pop.getProductName().equals(" ")) {
                            searchField.setText("");
                            msg.setBounds(235, 370, 200, 20);
                            msg.setText("Enter Product Name !");
                        } else {
                            searchField.setText(pop.getProductName());
                            msg.setText("");
                            searchProcess();
                        }
                    }
                }
                else{
                    msg.setBounds(235, 370, 200, 20);
                    msg.setText("Select A Category!");
                }
            }
        });

    }

    private void enterAction(KeyEvent e){
        jTextFieldKeyTyped(e);
        if (e.getKeyChar() == KeyEvent.VK_ENTER ) {
            if(!(searchField.getText().isEmpty() ||
                    quantityValueLbl.getText().isEmpty() || productName.getText().isEmpty() ||
                    sellingPriceValueLbl.getText().isEmpty() || originalPriceValueLbl.getText().isEmpty() ||
                    quantityValueLbl.getText().isEmpty() || minQuantityValue.getText().isEmpty())) {
                System.out.println("ENTER");
                updateProcess();
                msg.setBounds(210, 370, 200, 20);
                msg.setText("Updated Successfully!");
            }else{
                msg.setBounds(255,370,200,20);
                msg.setText("Fill All Fields !");
            }
        }
    }


    private void searchProcess(){

        String searchWord = searchField.getText();
        int productNumber = databaseOperations.search(searchWord,(String)list.getModel().getElementAt(list.getSelectedIndex()));
        if(productNumber != -1){
            list.setSelectedIndex(getCategoryIndex(databaseOperations.data.get(productNumber)));
            oldCategory = (String)list.getModel().getElementAt(list.getSelectedIndex());
            productName.setText(String.valueOf(databaseOperations.data.get(productNumber).getName()));
            originalPriceValueLbl.setText(String.valueOf(databaseOperations.data.get(productNumber).getBuyPrice()));
            sellingPriceValueLbl.setText(String.valueOf(databaseOperations.data.get(productNumber).getSoldPrice()));
            quantityValueLbl.setText(String.valueOf(databaseOperations.data.get(productNumber).getQuantity()));
            minQuantityValue.setText(String.valueOf(databaseOperations.data.get(productNumber).getMinimumQuantity()));
            oldProductName = String.valueOf(databaseOperations.data.get(productNumber).getName());
        }else{
            msg.setBounds(260,370,150,20);
            msg.setText("Not Found !");
        }
    }

    private int getCategoryIndex(Product product){
        System.out.println(product.getCategory());
        for (int i = 0 ; i < databaseOperations.allCategories.toArray().length;i++){
            System.out.println(databaseOperations.allCategories.get(i));
            if(product.getCategory().equals(databaseOperations.allCategories.get(i))){
                return i;
            }
        }
        return -1;
    }

    private void updateProcess(){
        Product newProduct = new Product();
        newProduct.setName(productName.getText());
        newProduct.setQuantity(Integer.parseInt(quantityValueLbl.getText()));
        newProduct.setMinimumQuantity(Integer.parseInt(minQuantityValue.getText()));
        newProduct.setBuyPrice(Double.parseDouble(originalPriceValueLbl.getText()));
        newProduct.setSoldPrice(Double.parseDouble(sellingPriceValueLbl.getText()));
        newProduct.setCategory((String)list.getModel().getElementAt(list.getSelectedIndex()));
        DatabaseHelper.updateProductValues(newProduct,oldProductName,oldCategory);
    }

    private void deleteProcess(){
        DatabaseHelper.deleteData(searchField.getText(),(String)list.getModel().getElementAt(list.getSelectedIndex()));
    }
}
