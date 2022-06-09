package pages;

import Database.*;
import com.itextpdf.text.BadElementException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReturnsPage extends JPanel {

    int numberOfPages;
    int currentPageNumber = 1;
    int numOfRows = 0, length;

    JFrame frame = new JFrame();
    Font labelFont = new Font("Arial", Font.PLAIN, 15);
    Font headFont = new Font("Arial", Font.BOLD, 12);

    JTextField[][] productsArr = new JTextField[10][7];
    JTextField[] headsTF = new JTextField[7];
    JButton backBtn = new JButton("Back");
    JButton nextBtn = new JButton("->");
    JButton previousBtn = new JButton("<-");
    JTextField pageNumberTF = new JTextField();
    JButton exportBtn = new JButton("Export");

    public ReturnsPage() {
        length = databaseOperations.returns.toArray().length;

        numberOfPages = length / 10;
        if (length % 10 != 0) {
            numberOfPages++;
        }


        dataTableDesign();
        dataTable(1);   //initial page

        Design();
        buttonsAction();
        frameSettings();
    }

    private void dataTableDesign() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 7; j++) {
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
                    case 6 -> productsArr[i][j].setBounds(1037 , 51 * (i + 1), 160,48);

                }

            }

        }
    }

    private void dataTable(int currentPageNumber) {

        int productNumber = (currentPageNumber - 1) * 10 - 1;

        if (length >= currentPageNumber * 10) {
            numOfRows = 10;
        } else {
            numOfRows = length - (currentPageNumber - 1) * 10;
        }

        deleteData();
        for (int i = 0; i < numOfRows; i++) {
            productNumber++;
            for (int j = 0; j < 7; j++) {

                switch (j) {
                    case 0 -> productsArr[i][j].setText(/*databaseOperations.salesData.get(productNumber).getName().length()>9? databaseOperations.salesData.get(productNumber).getName().substring(0,9).concat("..."):*/databaseOperations.returns.get(productNumber).getName());
                    case 1 -> productsArr[i][j].setText(String.valueOf(databaseOperations.returns.get(productNumber).getQuantity()));
                    case 2 -> productsArr[i][j].setText(String.valueOf(databaseOperations.returns.get(productNumber).getSoldPrice()));
                    case 3 -> productsArr[i][j].setText(String.valueOf(databaseOperations.returns.get(productNumber).getTotalPrice()));
                    case 4 -> productsArr[i][j].setText(String.valueOf(databaseOperations.returns.get(productNumber).getSellingDate()));
                    case 5 -> productsArr[i][j].setText(String.valueOf(databaseOperations.returns.get(productNumber).getCategory()));
                    case 6 -> {
                        if (databaseOperations.returns.get(productNumber).isReturns()) {
                            productsArr[i][j].setBackground(Color.red);
                        } else {
                            productsArr[i][j].setBackground(Color.green);
                        }
                    }
                }

            }

        }


    }

    private void deleteData() {      //when its less than 10 rows
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                productsArr[i][j].setText("");
            }

        }
    }

    private void Design() {

        backBtn.setBounds(70, 572, 80, 20);
        nextBtn.setBounds(355 + 150, 572, 50, 20);
        previousBtn.setBounds(255 + 150, 572, 50, 20);
        pageNumberTF.setBounds(310 + 150, 572, 40, 20);
        pageNumberTF.setText(currentPageNumber + " / " + numberOfPages);
        pageNumberTF.setEditable(false);
        pageNumberTF.setHorizontalAlignment(0);

        exportBtn.setBounds(180, 572, 75, 20);

        int border = 0;
        nextBtn.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
        previousBtn.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
        exportBtn.setForeground(Color.BLUE);

        for (int i = 0; i < 7; i++) {
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
                    headsTF[i].setBounds(420 + 300, 0, 165, 50);
                }
                case 5 -> {
                    headsTF[i] = new JTextField("Category");
                    headsTF[i].setBounds(585 + 300, 0, 160, 50);
                }
                case 6 -> {
                    headsTF[i] = new JTextField("Returns");
                    headsTF[i].setBounds(709 + 300, 0, 160, 50);
                }
            }
            headsTF[i].setBackground(Color.gray);
            headsTF[i].setHorizontalAlignment(0);
            headsTF[i].setEditable(false);
            //Fonts
            if (i != 1 && i != 2 && i != 3) {
                headsTF[i].setFont(labelFont);
            } else {
                headsTF[i].setFont(headFont);
            }
        }

    }

    private void buttonsAction() {
        backBtn.addActionListener((ActionEvent ae) -> {
            new Menu();
            frame.dispose();
        });
        nextBtn.addActionListener((ActionEvent ae) -> nextPageOfData());
        previousBtn.addActionListener((ActionEvent ae) -> previousPageOfData());

        exportBtn.addActionListener((ActionEvent ae) -> {
            try {
                exportProcess();
            } catch (BadElementException e) {
                e.printStackTrace();
            }
        });

        for(int i = 0; i < numOfRows;i++) {
            for (int j = 0; j < 7; j++) {
                int x = i, y = j;
                productsArr[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if(y == 6){

                            if(productsArr[x][y].getBackground().equals(Color.RED)){
                                System.out.println("Green");
                                if(new ConfirmRemovePopUp(frame,1,"Remove").getResult()==0) {
                                    productsArr[x][y].setBackground(Color.RED);
                                    DatabaseHelper.setReturns(false, productsArr[x][0].getText(), productsArr[x][5].getText(),productsArr[x][4].getText());
                                    DatabaseHelper.getAllSalesData();
                                    DatabaseHelper.getAllData();
                                    DatabaseHelper.getAllReturnsData();
                                    DatabaseHelper.getWantedProducts();
                                    frame.dispose();
                                    new ReturnsPage();
                                }
                            }else{
                                if(new ConfirmRemovePopUp(frame,1,"Add").getResult()==0) {
                                    productsArr[x][y].setBackground(Color.GREEN);
                                    DatabaseHelper.setReturns(true,productsArr[x][0].getText(),productsArr[x][5].getText(),productsArr[x][4].getText());
                                    DatabaseHelper.getAllReturnsData();
                                    DatabaseHelper.getAllSalesData();
                                    frame.dispose();
                                    new ReturnsPage();
                                }
                            }


                        }else if(!productsArr[x][y].getText().equals("")) {
                            System.out.println("BYE");
                            frame.dispose();
                            System.out.println(x + "" + y);
                            new ChoiceFramePage(productsArr[x][0].getText(),productsArr[x][6].getText());
                        }
                    }
                });
            }
        }
    }


    private void exportProcess() throws BadElementException {
        if(new ConfirmPopUp(frame, "", 5).getResult() == 0) {
            DatabaseHelper.exportAllReturnsData(getTodayDateString());
        }
    }
    private String getTodayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }
    private void nextPageOfData() {

        if (currentPageNumber < numberOfPages) {
            currentPageNumber++;
            pageNumberTF.setText(currentPageNumber + " / " + numberOfPages);
        }

        dataTable(currentPageNumber);

    }

    private void previousPageOfData() {
        if (currentPageNumber > 1) {
            currentPageNumber--;
            pageNumberTF.setText(currentPageNumber + " / " + numberOfPages);
        }
        dataTable(currentPageNumber);

    }



    private void frameSettings() {

        frame.add(backBtn);
        frame.add(nextBtn);
        frame.add(previousBtn);
        frame.add(pageNumberTF);
        for (int i = 0; i < 7; i++) {
            frame.add(headsTF[i]);
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 7; j++) {
                frame.add(productsArr[i][j]);
            }
        }
        frame.add(exportBtn);
        frame.add(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Sales Report");
        frame.setSize(1145,650);
        frame.setLocation(400, 60);
        frame.setVisible(true);
        frame.setResizable(false);
    }


}
