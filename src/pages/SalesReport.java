package pages;


import Database.*;
import com.itextpdf.text.BadElementException;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class SalesReport extends JPanel {

    int numberOfPages;
    int currentPageNumber = 1;
    int numOfRows = 0, length;

    JFrame frame = new JFrame();
    Font labelFont = new Font("Arial",Font.PLAIN,15);
    Font headFont = new Font("Arial",Font.BOLD,12);

    JTextField[][] productsArr = new JTextField[10][6];
    JTextField[] headsTF = new JTextField[6];
    JButton backBtn = new JButton("Back");
    JButton nextBtn = new JButton("->");
    JButton previousBtn = new JButton("<-");
    JTextField pageNumberTF = new JTextField();
    JComboBox<String> dateList = new JComboBox<>();
    JButton filterBtn = new JButton("Search");
    JButton exportBtn = new JButton("Export");

    public SalesReport(){
        length = databaseOperations.salesData.toArray().length;

        numberOfPages = length / 10;
        if(length%10 != 0){
            numberOfPages++;
        }


        dataTableDesign();
        dataTable(1);   //initial page

        Design();
        buttonsAction();
        frameSettings();
    }

    private void dataTableDesign(){

        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 6;j++) {
                productsArr[i][j] = new JTextField("");
                productsArr[i][j].setHorizontalAlignment(0);
                productsArr[i][j].setEditable(false);
                //Fonts
                productsArr[i][j].setFont(labelFont);
                switch (j) {
                    case 0 -> productsArr[i][j].setBounds(0, 51 * (i + 1), 149 + 300, 48);
                    case 1 -> productsArr[i][j].setBounds(150 + 300, 51 * (i + 1), 92, 48);
                    case 2 -> productsArr[i][j].setBounds(240 + 300, 51 * (i + 1), 92, 48);
                    case 3 -> productsArr[i][j].setBounds(330 + 300, 51 * (i + 1), 92, 48);
                    case 4 -> productsArr[i][j].setBounds(420 + 300, 51 * (i + 1), 165, 48);
                    case 5 -> productsArr[i][j].setBounds(585 + 300, 51 * (i + 1), 160, 48);
                }

            }

        }
    }

    private void dataTable(int currentPageNumber){

        int productNumber = (currentPageNumber -1) *10 - 1;

        if(length >= currentPageNumber*10){
            numOfRows = 10;
        }else{
            numOfRows = length - (currentPageNumber - 1) * 10;
        }

        deleteData();
        for(int i = 0; i < numOfRows;i++){
            productNumber++;
            for(int j = 0; j < 6;j++) {

                switch (j) {
                    case 0 -> productsArr[i][j].setText(/*databaseOperations.salesData.get(productNumber).getName().length()>9? databaseOperations.salesData.get(productNumber).getName().substring(0,9).concat("..."):*/databaseOperations.salesData.get(productNumber).getName());
                    case 1 -> productsArr[i][j].setText(String.valueOf(databaseOperations.salesData.get(productNumber).getQuantity()));
                    case 2 -> productsArr[i][j].setText(String.valueOf(databaseOperations.salesData.get(productNumber).getSoldPrice()));
                    case 3 -> productsArr[i][j].setText(String.valueOf(databaseOperations.salesData.get(productNumber).getTotalPrice()));
                    case 4 -> productsArr[i][j].setText(String.valueOf(databaseOperations.salesData.get(productNumber).getSellingDate()));
                    case 5 -> productsArr[i][j].setText(String.valueOf(databaseOperations.salesData.get(productNumber).getCategory()));
                }
                int x = i, y = j;
                int finalProductNumber = productNumber;
                /*productsArr[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if(databaseOperations.salesData.get(finalProductNumber).getName().length()<9) {
                            if (productsArr[x][y].getText().equals(databaseOperations.salesData.get(finalProductNumber).getName())) {
                                new ProductNamePopUp(frame, databaseOperations.salesData.get(finalProductNumber).getName());
                            }
                        }
                        else{
                            if(productsArr[x][y].getText().equals(databaseOperations.salesData.get(finalProductNumber).getName().substring(0, 9).concat("..."))){
                                new ProductNamePopUp(frame, databaseOperations.salesData.get(finalProductNumber).getName());
                            }
                        }
                    }
                });*/
            }

        }


    }

    private void deleteData(){      //when its less than 10 rows
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 6;j++) {
                productsArr[i][j].setText("");
            }

        }
    }

    private void Design(){

        backBtn.setBounds(70,572,80,20);
        nextBtn.setBounds(355 + 150,572,50,20);
        previousBtn.setBounds(255 + 150,572,50,20);
        pageNumberTF.setBounds(310 + 150,572,40,20);
        pageNumberTF.setText(currentPageNumber + " / " + numberOfPages);
        pageNumberTF.setEditable(false);
        pageNumberTF.setHorizontalAlignment(0);

        dateList.setBounds(470 + 200,572,150,20);
        filterBtn.setBounds(640 + 250,572,90,20);
        exportBtn.setBounds(180,572,75,20);

        int border = 0;
        nextBtn.setBorder(BorderFactory.createEmptyBorder(border,border,border,border));
        previousBtn.setBorder(BorderFactory.createEmptyBorder(border,border,border,border));
        exportBtn.setForeground(Color.BLUE);

        for(int i = 0; i < 6; i++) {
            switch (i) {
                case 0 -> {
                    headsTF[i] = new JTextField("Name");
                    headsTF[i].setBounds(0, 0, 151 + 300, 50);
                }
                case 1 -> {
                    headsTF[i] = new JTextField("Quantity");
                    headsTF[i].setBounds(150 + 300, 0, 92, 50);
                }
                case 2 -> {
                    headsTF[i] = new JTextField("Selling Price");
                    headsTF[i].setBounds(240 + 300, 0, 92, 50);
                }
                case 3 -> {
                    headsTF[i] = new JTextField("Price");
                    headsTF[i].setBounds(330 + 300, 0, 92, 50);
                }
                case 4 -> {
                    headsTF[i] = new JTextField("Selling Date");
                    headsTF[i].setBounds(420 + 300 , 0, 165, 50);
                }
                case 5 -> {
                    headsTF[i] = new JTextField("Category");
                    headsTF[i].setBounds(585 + 300, 0, 160, 50);
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
        dateListData();

        //Fonts

    }

    private void buttonsAction(){
        backBtn.addActionListener((ActionEvent ae)->{
            new Menu();
            frame.dispose();
        });
        nextBtn.addActionListener((ActionEvent ae)-> nextPageOfData());
        previousBtn.addActionListener((ActionEvent ae)-> previousPageOfData());

        filterBtn.addActionListener((ActionEvent ae)-> dataFiltration(Objects.requireNonNull(dateList.getSelectedItem()).toString()));
        exportBtn.addActionListener((ActionEvent ae)-> {
            try {
                exportProcess(Objects.requireNonNull(dateList.getSelectedItem()).toString());
            } catch (BadElementException e) {
                e.printStackTrace();
            }
        });


    }

    private void dataFiltration(String catName){
        //int i = 0;
        deleteData();

        switch (catName) {
            case "All" -> DatabaseHelper.getAllSalesData();
            case "Today"->DatabaseHelper.getSpecificDateSalesData(getTodayDateString());
            case "Yesterday"->DatabaseHelper.getSpecificDateSalesData(getYesterdayDateString());
            case "Before Yesterday"->DatabaseHelper.getSpecificDateSalesData(getBeforeYesterdayDateString());
        }
        for(int i = 0; i < databaseOperations.salesData.toArray().length; i++){
            for(int j = 0; j < 6; j++){
                switch (j) {
                    case 0 -> productsArr[i][j].setText(/*databaseOperations.salesData.get(i).getName().length()>9?databaseOperations.salesData.get(i).getName().substring(0,9).concat("..."):*/databaseOperations.salesData.get(i).getName());
                    case 1 -> productsArr[i][j].setText(String.valueOf(databaseOperations.salesData.get(i).getQuantity()));
                    case 2 -> productsArr[i][j].setText(String.valueOf(databaseOperations.salesData.get(i).getSoldPrice()));
                    case 3 -> productsArr[i][j].setText(String.valueOf(databaseOperations.salesData.get(i).getTotalPrice()));
                    case 4 -> productsArr[i][j].setText(String.valueOf(databaseOperations.salesData.get(i).getSellingDate()));
                    case 5 -> productsArr[i][j].setText(databaseOperations.salesData.get(i).getCategory());
                }
            }
        }

    }

    private void exportProcess(String catName) throws BadElementException {
        switch (catName) {
            case "All" -> {
                if (new ConfirmPopUp(frame, " ", 2).getResult() == 0) {
                    DatabaseHelper.getAllSalesData();
                    DatabaseHelper.exportAllSalesData();
                } else {
                    System.out.println("Canceld");
                }
            }
            case "Today"->DatabaseHelper.getSpecificDateSalesData(getTodayDateString());
            case "Yesterday"->DatabaseHelper.getSpecificDateSalesData(getYesterdayDateString());
            case "Before Yesterday"->DatabaseHelper.getSpecificDateSalesData(getBeforeYesterdayDateString());
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

    private void dateListData(){
        dateList.addItem("All");
        dateList.addItem("Today");
        dateList.addItem("Yesterday");
        dateList.addItem("Before Yesterday");
    }

    private void frameSettings(){

        frame.add(backBtn);
        frame.add(nextBtn);
        frame.add(previousBtn);
        frame.add(pageNumberTF);
        for(int i = 0; i < 6;i++){
            frame.add(headsTF[i]);
        }
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 6;j++) {
                frame.add(productsArr[i][j]);
            }
        }
        frame.add(dateList);
        frame.add(filterBtn);
        frame.add(exportBtn);
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Sales Report");
        frame.setSize(760 + 300,650);
        frame.setLocation(400,60);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private String getTodayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }
    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(yesterday());
    }

    private String getBeforeYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(beforeYesterday());
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private Date beforeYesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return cal.getTime();
    }

}
