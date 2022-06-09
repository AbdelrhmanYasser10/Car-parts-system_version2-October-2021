package pages;

import Database.*;
import classes.Product;
import com.itextpdf.text.BadElementException;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;


public class showProductsPage extends JPanel{

    int numberOfPages;
    int currentPageNumber = 1;
    int productNumber;
    int numOfRows = 0, length;

    JFrame frame = new JFrame();
    Font labelFont = new Font("Arial",Font.PLAIN,15);
    Font headFont = new Font("Arial",Font.BOLD,12);

    JTextField[][] productsArr = new JTextField[10][8];
    JTextField[] headsTF = new JTextField[8];

    JButton backBtn = new JButton("Back");
    JButton nextBtn = new JButton("->");
    JButton previousBtn = new JButton("<-");
    JTextField pageNumberTF = new JTextField();
    JComboBox<String> catList = new JComboBox<>();
    JButton filterBtn = new JButton("Filter");

    JButton exportBtn = new JButton("Export");


    public showProductsPage(double xFrame, double yFrame){
        length = databaseOperations.data.toArray().length;

        numberOfPages = length / 10;
        if(length%10 != 0){
            numberOfPages++;
        }


        dataTableDesign();
        dataTable(1);   //initial page

        Design();
        buttonsAction();
        frameSettings(xFrame,yFrame);
    }

    private void dataTableDesign(){

        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++) {
                productsArr[i][j] = new JTextField("");
                productsArr[i][j].setHorizontalAlignment(0);
                productsArr[i][j].setEditable(false);
                //Fonts
                productsArr[i][j].setFont(labelFont);
                switch (j) {
                    case 0 -> productsArr[i][j].setBounds(0, 51 * (i + 1), 149 + 200, 48);
                    case 1 -> productsArr[i][j].setBounds(150 + 200, 51 * (i + 1), 92, 48);
                    case 2 -> productsArr[i][j].setBounds(240+ 200, 51 * (i + 1), 92, 48);
                    case 3 -> productsArr[i][j].setBounds(330 + 200, 51 * (i + 1), 92, 48);
                    case 4 -> productsArr[i][j].setBounds(420 + 200, 51 * (i + 1), 165, 48);
                    case 5 -> productsArr[i][j].setBounds(585 + 200, 51 * (i + 1), 160, 48);
                    case 6 -> productsArr[i][j].setBounds(945 , 51 * (i + 1), 92,48);
                    case 7 -> productsArr[i][j].setBounds(1037 , 51 * (i + 1), 92,48);
                }

            }

        }
    }

    private void dataTable(int currentPageNumber){

        productNumber = (currentPageNumber -1) *10 - 1;

        if(length >= currentPageNumber*10){
            numOfRows = 10;
        }else{
            numOfRows = length - (currentPageNumber - 1) * 10;
        }

        deleteData();
        for(int i = 0; i < numOfRows;i++){
            productNumber++;
            for(int j = 0; j < 8;j++) {

                    switch (j) {
                        case 0 -> productsArr[i][j].setText(/*databaseOperations.data.get(productNumber).getName().length() > 50 ? databaseOperations.data.get(productNumber).getName().substring(0,9).concat("...") :*/ databaseOperations.data.get(productNumber).getName());
                        case 1 -> productsArr[i][j].setText(String.valueOf(databaseOperations.data.get(productNumber).getBuyPrice()));
                        case 2 -> productsArr[i][j].setText(String.valueOf(databaseOperations.data.get(productNumber).getSoldPrice()));
                        case 3 -> productsArr[i][j].setText(String.valueOf(databaseOperations.data.get(productNumber).getQuantity()));
                        case 4 -> productsArr[i][j].setText(String.valueOf(databaseOperations.data.get(productNumber).getAddingToSystemDate()));
                        case 5 -> productsArr[i][j].setText(String.valueOf(databaseOperations.data.get(productNumber).getCategory()));
                        case 6 -> productsArr[i][j].setText(String.valueOf(databaseOperations.data.get(productNumber).getMinimumQuantity()));
                        case 7 -> {
                            if(databaseOperations.data.get(productNumber).isWanted()){
                                productsArr[i][j].setBackground(Color.RED);
                            }else{
                                productsArr[i][j].setBackground(Color.GREEN);
                            }
                        }
                    }

            }

        }
    }

    private void deleteData(){      //when its less than 10 rows
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++) {
                productsArr[i][j].setText("");
                productsArr[i][j].setBackground(SystemColor.text);
            }

        }
    }

    private void Design(){

        backBtn.setBounds(30 + 50,572,80,20);
        exportBtn.setBounds(120 + 50,572,75,20);

        nextBtn.setBounds(355 + 200,572,50,20);
        previousBtn.setBounds(255 + 200,572,50,20);
        pageNumberTF.setBounds(310 + 200,572,40,20);
        pageNumberTF.setText(currentPageNumber + " / " + numberOfPages);
        pageNumberTF.setEditable(false);
        pageNumberTF.setHorizontalAlignment(0);

        catList.setBounds(470 + 300,572,150,20);
        filterBtn.setBounds(640 + 300,572,75,20);

        int border = 0;
        nextBtn.setBorder(BorderFactory.createEmptyBorder(border,border,border,border));
        previousBtn.setBorder(BorderFactory.createEmptyBorder(border,border,border,border));
        //backBtn.setForeground(Color.BLUE);

        for(int i = 0; i < 8; i++) {
            switch (i) {
                case 0 -> {
                    headsTF[i] = new JTextField("Name");
                    headsTF[i].setBounds(0, 0, 151 + 200, 50);
                }
                case 1 -> {
                    headsTF[i] = new JTextField("Original Price");
                    headsTF[i].setBounds(150 + 200, 0, 92, 50);
                }
                case 2 -> {
                    headsTF[i] = new JTextField("Selling Price");
                    headsTF[i].setBounds(240 + 200, 0, 92, 50);
                }
                case 3 -> {
                    headsTF[i] = new JTextField("Quantity");
                    headsTF[i].setBounds(330 + 200, 0, 92, 50);
                }
                case 4 -> {
                    headsTF[i] = new JTextField("Date");
                    headsTF[i].setBounds(420 + 200, 0, 165, 50);
                }
                case 5 -> {
                    headsTF[i] = new JTextField("Category");
                    headsTF[i].setBounds(585 + 200, 0, 160, 50);
                }
                case 6 -> {
                    headsTF[i] = new JTextField("Min-Quantity");
                    headsTF[i].setBounds(945, 0, 92, 50);
                }
                case 7 -> {
                    headsTF[i] = new JTextField("Wanted");
                    headsTF[i].setBounds(1037, 0, 92, 50);
                }
            }
            headsTF[i].setBackground(Color.gray);
            headsTF[i].setHorizontalAlignment(0);
            headsTF[i].setEditable(false);
            //Fonts
            if(i != 1 && i != 2 && i != 3) {
                headsTF[i].setFont(labelFont);
            }else {
                headsTF[i].setFont(headFont);
            }
        }

        //Puts all cats name in ComboBox
        catListData();

        exportBtn.setForeground(Color.BLUE);

    }

    private void buttonsAction(){
        backBtn.addActionListener((ActionEvent ae)->{
            new Menu();
            frame.dispose();
        });
        nextBtn.addActionListener((ActionEvent ae)-> nextPageOfData());
        previousBtn.addActionListener((ActionEvent ae)-> previousPageOfData());

        filterBtn.addActionListener((ActionEvent ae)-> dataFiltration(Objects.requireNonNull(catList.getSelectedItem()).toString()));
        exportBtn.addActionListener((ActionEvent ae)-> {
            try {
                DatabaseHelper.exportAllProducts();
            } catch (BadElementException e) {
                e.printStackTrace();
            }
        });

        for(int i = 0; i < numOfRows;i++) {
            for (int j = 0; j < 8; j++) {
                int x = i, y = j;
                productsArr[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if(y == 7){

                            if(productsArr[x][y].getBackground().equals(Color.GREEN)){
                                System.out.println("Green");
                                productsArr[x][y].setBackground(Color.RED);
                                DatabaseHelper.setWantedValue(true,productsArr[x][0].getText(),productsArr[x][5].getText());

                            }else{
                                System.out.println("RED");
                                productsArr[x][y].setBackground(Color.GREEN);
                                DatabaseHelper.setWantedValue(false,productsArr[x][0].getText(),productsArr[x][5].getText());
                            }


                        }else if(!productsArr[x][y].getText().equals("")) {
                            System.out.println("BYE");
                            frame.dispose();
                            System.out.println(x + "" + y);
                            new ChoiceFramePage(productsArr[x][0].getText(),productsArr[x][5].getText());
                        }
                    }
                });
            }
        }
    }

    private void dataFiltration(String catName){
        //int i = 0;
        deleteData();

        if(catName.equals("All")){
            new showProductsPage(frame.getLocation().getX(), frame.getLocation().getY());
            frame.dispose();
        }

        ArrayList<Product> productsArray;
        productsArray = DatabaseHelper.getProductsFromSpecificCategory(catName);
        for(int i = 0; i < productsArray.toArray().length; i++){
            for(int j = 0; j < 8; j++){
                switch (j) {
                    case 0 -> productsArr[i][j].setText(/*productsArray.get(i).getName().length() > 9 ? productsArray.get(i).getName().substring(0,9).concat("...") :*/ productsArray.get(i).getName());
                    case 1 -> productsArr[i][j].setText(String.valueOf(productsArray.get(i).getBuyPrice()));
                    case 2 -> productsArr[i][j].setText(String.valueOf(productsArray.get(i).getSoldPrice()));
                    case 3 -> productsArr[i][j].setText(String.valueOf(productsArray.get(i).getQuantity()));
                    case 4 -> productsArr[i][j].setText(String.valueOf(productsArray.get(i).getAddingToSystemDate()));
                    case 5 -> productsArr[i][j].setText(productsArray.get(i).getCategory());
                    case 6 -> productsArr[i][j].setText(String.valueOf(productsArray.get(i).getMinimumQuantity()));
                    case 7 -> {
                        if (productsArray.get(i).isWanted()) {
                            productsArr[i][j].setBackground(Color.RED);
                        } else {
                            productsArr[i][j].setBackground(Color.GREEN);
                        }
                    }
                }
            }
        }

    }

    private void nextPageOfData(){

        if(currentPageNumber < numberOfPages){
            currentPageNumber++;
            pageNumberTF.setText(currentPageNumber + " / " + numberOfPages);
        }

        dataTable(currentPageNumber);

    }

    private void previousPageOfData(){
        if(currentPageNumber > 1){
            currentPageNumber--;
            pageNumberTF.setText(currentPageNumber + " / " + numberOfPages);
        }
        dataTable(currentPageNumber);

    }

    private void catListData(){
        String sql = "SELECT * FROM categories";
        try{
            Connection conn;
            // db parameters
            String url = "jdbc:sqlite:"+ DatabaseHelper.name;
            conn = DriverManager.getConnection(url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            catList.addItem("All");
            // loop through the result set
            while (rs.next()) {
                catList.addItem(rs.getString("category"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void frameSettings(double xFrame,double yFrame){

        frame.add(backBtn);
        frame.add(nextBtn);
        frame.add(previousBtn);
        frame.add(pageNumberTF);
        for(int i = 0; i < 8;i++){
            frame.add(headsTF[i]);
        }
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8;j++) {
                frame.add(productsArr[i][j]);
            }
        }
        frame.add(catList);
        frame.add(filterBtn);
        frame.add(exportBtn);
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Products Page");
        frame.setSize(1145,650);
        int xLocate = (int) xFrame, yLocate = (int) yFrame;
        frame.setLocation(xLocate,yLocate);  //400   60
        frame.setVisible(true);
        frame.setResizable(false);
    }


}
