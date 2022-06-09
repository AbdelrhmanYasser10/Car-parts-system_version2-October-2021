package Database;


import classes.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class DatabaseHelper {

    public static String name; // name of database
    public static void setName(String name) {
        DatabaseHelper.name = name; // set the name
    }

    public static void createNewDatabase() {
        String url = "jdbc:sqlite:"+ name;

        try (Connection conn = DriverManager.getConnection(url)) {

            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                createTables();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createTables(){

        String table3 = "CREATE TABLE categories (category TEXT PRIMARY KEY)";
        String table1 = "CREATE TABLE products (product_name TEXT, sold_price DOUBLE NOT NULL,buy_price DOUBLE NOT NULL,quantity INTEGER NOT NULL, adding_product_date TEXT NOT NULL, category REFERENCES categories(category),wanted BOOLEAN NOT NULL,minimum_quantity INTEGER NOT NULL, PRIMARY KEY(product_name,category))";
        String table2 = "CREATE TABLE soldProducts (product_name REFERENCES products(product_name), price DOUBLE NOT NULL,quantity integer NOT NULL, selling_date TEXT NOT NULL, category TEXT NOT NULL , returns BOOLEAN NOT NULL)";
        String table4 = "CREATE TABLE Returns (product_name REFERENCES products(product_name), price DOUBLE NOT NULL,quantity integer NOT NULL, selling_date TEXT NOT NULL, category TEXT NOT NULL)";
        try {
            Connection conn;
            // db parameters
            String url = "jdbc:sqlite:"+ name;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();

            stmt.execute(table3);
            stmt.execute(table1);
            stmt.execute(table2);
            stmt.execute(table4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String insertData(Product product){
        String sql = "INSERT INTO products(product_name,sold_price,buy_price,quantity,adding_product_date,category,minimum_quantity,wanted) VALUES(?,?,?,?,?,?,?,?)";

        try  {
            Connection conn;
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getSoldPrice());
            pstmt.setDouble(3, product.getBuyPrice());
            pstmt.setDouble(4, product.getQuantity());
            pstmt.setString(5, product.getAddingToSystemDate());
            pstmt.setString(6, product.getCategory());
            pstmt.setInt(7, product.getMinimumQuantity());
            pstmt.setBoolean(8, product.isWanted());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "This Product Already Exist";
        }
        getAllData();
        return "Inserted Successfully!";
    }
    public static void getAllData(){
            String sql = "SELECT * FROM products WHERE quantity >= 0";
            databaseOperations.data.clear();
            try{
                Connection conn;
                // db parameters
                String url = "jdbc:sqlite:"+ name;
                conn = DriverManager.getConnection(url);
                Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql);  ////// return all rows in the table
                // loop through the result set
                while (rs.next()) {
                    System.out.println(rs.getString("product_name") +  "\t" +
                            rs.getDouble("sold_price") + "\t" +rs.getDouble("buy_price")+"\t" +
                            rs.getInt("quantity") + "\t" +rs.getString("adding_product_date") + "\t"+rs.getString("category")+ "\t"+rs.getString("minimum_quantity")+ "\t"+rs.getString("wanted"));

                    /*This is how we can add data and use it in the system*/
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Product p = new Product(rs.getString("product_name"),rs.getDouble("sold_price"),rs.getDouble("buy_price"),rs.getInt("quantity"),formatter.parse(rs.getString("adding_product_date")),rs.getString("category"),rs.getInt("minimum_quantity"),rs.getBoolean("wanted"));
                    databaseOperations.data.add(p);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Collections.reverse(databaseOperations.data);
    }
    public static void deleteData(String productName , String category){
        String sql = "DELETE FROM products WHERE product_name = ? AND category = ?";

        try  {
            Connection conn;
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productName);
            pstmt.setString(2, category);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        getAllData();
        getWantedProducts();
        getAllCategories();
        getAllSalesData();
    }

    public static void getAllCategories(){
        String sql = "SELECT * FROM categories";
        databaseOperations.allCategories.clear();
        try{
            Connection conn;
            // db parameters
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);  ////// return all rows in the table
            // loop through the result set
            while (rs.next()) {
              databaseOperations.allCategories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateProductValues(Product product , String oldName,String category){
        String sql = "UPDATE products SET product_name = ?,sold_price = ?,buy_price = ? , quantity = ? , category = ? , minimum_quantity = ? WHERE product_name = ? AND category = ?";


        System.out.println(product.toString());
        try  {
            Connection conn;
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getSoldPrice());
            pstmt.setDouble(3, product.getBuyPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setString(5, product.getCategory());
            pstmt.setInt(6,product.getMinimumQuantity());
            System.out.println("UPDATED !!");
            pstmt.setString(7, oldName);
            pstmt.setString(8, category);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
        getAllData();
        getWantedProducts();
        getAllCategories();
        getAllSalesData();
    }

    public static void setWantedValue(boolean newWantedValue,String productName, String Category){
        String sql = "UPDATE products SET wanted = ? WHERE product_name = ? AND  category = ?";

        try  {
            Connection conn;
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setBoolean(1, newWantedValue);
            pstmt.setString(2, productName);
            pstmt.setString(3,Category);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        getAllData();
        getWantedProducts();
        getAllCategories();
        getAllSalesData();
    }

    public static void updateProductQuantity (Product product , int index , int newQuantity , String category){
        String sql = "UPDATE products SET quantity = ? WHERE product_name = ? AND  category = ?";

        try  {
            Connection conn;
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setInt(1, newQuantity);
            pstmt.setString(2, product.getName());
            pstmt.setString(3,category);
            // update
            pstmt.executeUpdate();
            databaseOperations.data.get(index).setQuantity(newQuantity);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        getAllData();
        getWantedProducts();
        getAllCategories();
        getAllSalesData();
    }

    public static void updateProductQuantity (String product , int newQuantity , String category){
        String sql = "UPDATE products SET quantity = ? WHERE product_name = ? AND  category = ?";

        try  {
            Connection conn;
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setInt(1, newQuantity);
            pstmt.setString(2, product);
            pstmt.setString(3,category);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        getAllData();
        getWantedProducts();
        getAllCategories();
        getAllSalesData();
    }

    public static void insertNewCategory(String category){
        String sql = "INSERT INTO categories(category) VALUES(?)";
        try  {
            Connection conn;
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        getAllCategories();
    }

    public static void insertSoldProductDetails(String productName , int quantity , String date, double price , String category,boolean returns){
        String sql = "INSERT INTO soldProducts(product_name,price,quantity,selling_date,category,returns) VALUES(?,?,?,?,?,?)";

        try  {
            Connection conn;
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, productName);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, quantity);
            pstmt.setString(4, date);
            pstmt.setString(5,category);
            pstmt.setBoolean(6,returns);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void searchForProductsInStoke(String value , String category){
        databaseOperations.searchProducts.clear();
        String sql = "SELECT\n" +
                "\tproduct_name\n" +
                "FROM\n" +
                "\tproducts\n" +
                "WHERE\n" +
                "\t(product_name LIKE '%"+value+"%') AND (category LIKE '"+category+"')";
        System.out.println(sql);
        try{
            Connection conn;
            // db parameters
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);  ////// return all rows in the table
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("product_name"));
                /*This is how we can add data and use it in the system*/
                databaseOperations.searchProducts.add(rs.getString("product_name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Product> getProductsFromSpecificCategory(String category){
        databaseOperations.listOfProductsOfASpecificCategory.clear();
        String sql = "SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\tproducts\n" +
                "WHERE\n" +
                "\tcategory LIKE '" + category+"'";
        System.out.println(sql);
        try{
            Connection conn;
            // db parameters
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);  ////// return all rows in the table
            // loop through the result set
            while (rs.next()) {
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Product p = new Product(rs.getString("product_name"),rs.getDouble("sold_price"),rs.getDouble("buy_price"),rs.getInt("quantity"),formatter.parse(rs.getString("adding_product_date")),rs.getString("category"),rs.getInt("minimum_quantity"),rs.getBoolean("wanted"));
                databaseOperations.listOfProductsOfASpecificCategory.add(p);
                System.out.println(p.getName());
            }
        } catch (SQLException | ParseException e) {
            System.out.println(e.getMessage());
        }
        Collections.reverse(databaseOperations.listOfProductsOfASpecificCategory);
        return databaseOperations.listOfProductsOfASpecificCategory;
    }

    public static void getAllSalesData(){
        String sql = "SELECT * FROM soldProducts";
        databaseOperations.salesData.clear();
        try{
            Connection conn;
            // db parameters
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);  ////// return all rows in the table
            // loop through the result set
            while (rs.next()) {

                rs.getString("product_name");
                /*This is how we can add data and use it in the system*/
                Product p = new Product();
                p.setName(rs.getString("product_name"));
                p.setTotalPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setSellingDate(rs.getString("selling_date"));
                p.setCategory(rs.getString("category"));
                p.setSoldPrice(rs.getDouble("price")/rs.getInt("quantity"));
                p.setReturns(rs.getInt("returns")==1);
                System.out.println(p.toString());
                databaseOperations.salesData.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Collections.reverse(databaseOperations.salesData);
    }
    public static void getSpecificDateSalesData(String date){
        String sql = "SELECT * FROM soldProducts WHERE selling_date LIKE '%"+date+"%'";
        System.out.println(sql);
        databaseOperations.salesData.clear();
        try{
            Connection conn;
            // db parameters
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);  ////// return all rows in the table
            // loop through the result set
            while (rs.next()) {

                rs.getString("product_name");
                /*This is how we can add data and use it in the system*/
                Product p = new Product();
                p.setName(rs.getString("product_name"));
                p.setTotalPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setSellingDate(rs.getString("selling_date"));
                p.setCategory(rs.getString("category"));
                p.setSoldPrice(rs.getDouble("price")/rs.getInt("quantity"));
                p.setReturns(rs.getInt("returns")==1);
                databaseOperations.salesData.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Collections.reverse(databaseOperations.salesData);
    }

    public static void deleteCategory(String category){
        String sql = "DELETE FROM categories WHERE category = ?";
        String sql2 = "DELETE FROM products WHERE category = ?";
        try  {
            Connection conn;
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);

            pstmt.executeUpdate();


            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, category);

            pstmt2.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        getAllCategories();
        getAllData();
    }

    public static void getWantedProducts(){
        String sql = "SELECT * FROM products WHERE wanted = 1";
        databaseOperations.wantedProducts.clear();
        try{
            Connection conn;
            // db parameters
            String url = "jdbc:sqlite:"+ name;
            conn = DriverManager.getConnection(url);
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);  ////// return all rows in the table
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("product_name") +  "\t" +
                        rs.getDouble("sold_price") + "\t" +rs.getDouble("buy_price")+"\t" +
                        rs.getInt("quantity") + "\t" +rs.getString("adding_product_date") + "\t"+rs.getString("category"));

                /*This is how we can add data and use it in the system*/
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Product p = new Product(rs.getString("product_name"),rs.getDouble("sold_price"),rs.getDouble("buy_price"),rs.getInt("quantity"),formatter.parse(rs.getString("adding_product_date")),rs.getString("category"),rs.getInt("minimum_quantity"),rs.getBoolean("wanted"));
                databaseOperations.wantedProducts.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private static void addMetaData(Document document) {
        document.addTitle("ALL PRODUCTS IN THE SYSTEM");
        document.addSubject("Using iText - PDF - LIB");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("KENZ - System");
        document.addCreator("KENZ - System");
    }

    public static void exportAllProducts()
            throws BadElementException {

        String file = "Data/AllProducts.pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            BaseFont bf = BaseFont.createFont("c:/Amiri-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(bf, 12);
            addMetaData(document);

            document.open();

            PdfPTable table = new PdfPTable(5);


            PdfPCell c1 = new PdfPCell(new Paragraph("م",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("الصنف",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("الكمية",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("السعر",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("الجملة",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            table.setHeaderRows(1);

            int counter = 0;
            for (int i = 0 ; i < databaseOperations.allCategories.toArray().length;i++){

                c1 = new PdfPCell(new Phrase("---",font));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(BaseColor.RED);
                c1.setPadding(10);
                table.addCell(c1);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(databaseOperations.allCategories.get(i),font));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(BaseColor.RED);
                c1.setPadding(10);
                c1.setArabicOptions(1);
                c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("---",font));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setPadding(10);
                c1.setBackgroundColor(BaseColor.RED);
                table.addCell(c1);
                table.addCell(c1);

                for(int j = 0 ; j < databaseOperations.data.toArray().length;j++){
                    counter++;
                    if(databaseOperations.data.get(j).getCategory().equals(databaseOperations.allCategories.get(i))){

                        c1 = new PdfPCell(new Phrase(String.valueOf(counter)));
                        c1.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                        c1.setPadding(10);
                        table.addCell(c1);


                        Phrase phrase = new Phrase();
                        Chunk c = new Chunk(databaseOperations.data.get(j).getName(),font);
                        phrase.add(c);
                        c1 = new PdfPCell(phrase);
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setBackgroundColor(BaseColor.YELLOW);
                        c1.setPadding(10);
                        c1.setArabicOptions(1);
                        c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(String.valueOf(databaseOperations.data.get(j).getQuantity())));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setPadding(10);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(String.valueOf(databaseOperations.data.get(j).getBuyPrice())));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setPadding(10);
                        table.addCell(c1);


                        c1 = new PdfPCell(new Phrase(String.valueOf(databaseOperations.data.get(j).getSoldPrice())));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setPadding(10);
                        table.addCell(c1);
                    }
                }
            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void exportAllSalesData() {

        String file = "Data/AllSalesData.pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            BaseFont bf = BaseFont.createFont("c:/Amiri-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(bf, 12);
            addMetaData(document);
            document.setMargins(0,0,10,1);

            document.open();

            PdfPTable table = new PdfPTable(6);


            PdfPCell c1 = new PdfPCell(new Paragraph("م",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("الصنف",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("الكمية المباعة",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("سعر عمليه البيع",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("تاريخ البيع",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            c1 = new PdfPCell(new Paragraph("آجل",font));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            c1.setPadding(10);
            c1.setArabicOptions(1);
            c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            table.addCell(c1);

            table.setHeaderRows(1);

            int counter = 0;
            for (int i = 0 ; i < databaseOperations.allCategories.toArray().length;i++){

                c1 = new PdfPCell(new Phrase("---",font));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(BaseColor.RED);
                c1.setPadding(10);
                table.addCell(c1);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase(databaseOperations.allCategories.get(i),font));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(BaseColor.RED);
                c1.setPadding(10);
                c1.setArabicOptions(1);
                c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("---",font));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setPadding(10);
                c1.setBackgroundColor(BaseColor.RED);
                table.addCell(c1);
                table.addCell(c1);
                table.addCell(c1);

                for(int j = 0 ; j < databaseOperations.data.toArray().length;j++){
                    counter++;
                    if(databaseOperations.salesData.get(j).getCategory().equals(databaseOperations.allCategories.get(i))){

                        c1 = new PdfPCell(new Phrase(String.valueOf(counter)));
                        c1.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                        c1.setPadding(10);
                        table.addCell(c1);


                        Phrase phrase = new Phrase();
                        Chunk c = new Chunk(String.valueOf(databaseOperations.salesData.get(j).getName()),font);
                        phrase.add(c);
                        c1 = new PdfPCell(phrase);
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setPadding(10);
                        c1.setExtraParagraphSpace(30);
                        c1.setArabicOptions(1);
                        c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(String.valueOf(databaseOperations.salesData.get(j).getQuantity())));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setPadding(10);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(String.valueOf(databaseOperations.salesData.get(j).getTotalPrice())));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setPadding(10);
                        table.addCell(c1);

                        phrase = new Phrase();
                        c = new Chunk(String.valueOf(databaseOperations.salesData.get(j).getSellingDate()),font);
                        phrase.add(c);
                        c1 = new PdfPCell(phrase);
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setPadding(10);
                        c1.setExtraParagraphSpace(30);
                        c1.setArabicOptions(1);
                        c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                        table.addCell(c1);

                        phrase = new Phrase();
                        c = new Chunk((databaseOperations.salesData.get(j).isReturns() ? "نعم":"لا"),font);
                        phrase.add(c);
                        c1 = new PdfPCell(phrase);
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setPadding(10);
                        c1.setArabicOptions(1);
                        c1.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                        table.addCell(c1);

                    }
                }
            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
