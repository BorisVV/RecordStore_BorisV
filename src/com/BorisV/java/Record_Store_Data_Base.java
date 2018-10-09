package com.BorisV.java;

import java.sql.*;
import java.util.Date;
import java.time.LocalDate;

public class Record_Store_Data_Base extends Consignors_Model{

    private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //New Driver add jc. Old was com.mysql.jdbc.Driver.
    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";

    // The user name in an env. var.
    private static final String USER = System.getenv("user");
    // The password is an env. var.
    private static final String PASS = System.getenv("password");
    // DB is an env. var.
    private static final String DB_NAME = System.getenv("db_name");

    protected final static String TABLE_NAME = System.getenv("table_m");


    protected final static String PK_COLUMN = "AlbumID";
    protected final static String ARTIST_NAME = "Artist";
    protected final static String ALBUM_NAME = "Title";
    protected final static String CONSIGNOR_ID = "ConsignorID";
    protected final static String PRICE = "Price";
    protected final static String DATE_ENTERED = "Date_Entered";

    // Table is an env. var.
    protected final static String CONSIGNORS_TABLE = System.getenv("table_cong");
    protected final static String CONSIGNOR_NAME = "Consignor_Name";
    protected final static String CONSIGNOR_PHONE = "Consignor_Phone";

    static Statement stmtRecStore = null;
    static Statement stmtConsignors = null;

    static ResultSet rsConsignors = null;
    static ResultSet rsRecStore = null;

    static Connection conn = null;


    private static Consignors_Model consignors_model;
    private static Record_Store_Data_Model recordStore_data_model;

    Record_Store_Data_Base(ResultSet rs2) {
        super(rs2);
    }


    public static void main(String[] args) {
        //call the method set up and creates database(if it doesn't exist.

        if (!setUp()) {
            System.exit(-1);
        }


        if (!loadTables()) {
            System.exit(-1);
        }

        All_Records_GUI allAlbumsTable = new All_Records_GUI(recordStore_data_model, consignors_model);
    }

    public static boolean setUp(){
        try {

            //Load driver class
            try {

                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException cnfe) {
                System.exit(-1);
                System.out.println("No database drivers found. Exiting" + cnfe);
                return false;
            }

            conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);

            stmtRecStore = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmtConsignors = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //Does the table exist? If not, create it.
            if (!recStoreDataTableExist()) {

                //Create a table in the database with 3 columns: Movie title, year and rating
                String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (" + PK_COLUMN + " int NOT NULL AUTO_INCREMENT, " + ARTIST_NAME +
                        " varchar(50), " + ALBUM_NAME + " varchar(50), " + CONSIGNOR_ID +
                        " int, " + PRICE + " DOUBLE, PRIMARY KEY(" + PK_COLUMN + "))";
                stmtRecStore.executeUpdate(createTableSQL);
                //This will print out a message that table was created
                System.out.println("Created albums table");

                String addDataSQL = "INSERT INTO " + TABLE_NAME + " (" + ARTIST_NAME + ", " + ALBUM_NAME + ", " + CONSIGNOR_ID + ", " +
                        PRICE + ") " + " VALUES (?, ?, ?, ?)";
                PreparedStatement ps;
                ps = conn.prepareStatement(addDataSQL);
                ps.setString(1, "Marc Anthony");
                ps.setString(2, "Si tu supieras");
                ps.setInt(3, 55);
                ps.setDouble(4, 10.99);
//                ps.setDate();


                ps.executeUpdate();
            }


            if (!consignorDataTableExist()) {

                //Create a table in the database with 3 columns: Movie title, year and rating
                String createTableConsingor = "CREATE TABLE " + CONSIGNORS_TABLE + "(" + CONSIGNOR_ID +
                        " int , " + CONSIGNOR_NAME + " varchar(50), " + CONSIGNOR_PHONE +
                        " varchar(20), PRIMARY KEY (" + CONSIGNOR_ID + "))";
                stmtConsignors.executeUpdate(createTableConsingor);
                //This will print out a message that table was created
                System.out.println("Created consignor table");

                String addDataConsig = "INSERT INTO " + CONSIGNORS_TABLE + "(" + CONSIGNOR_ID + ", "+ CONSIGNOR_NAME + ", " + CONSIGNOR_PHONE + ")" + " VALUES (?, ?, ?)";
                PreparedStatement ps2;
                ps2 = conn.prepareStatement(addDataConsig);
                ps2.setInt(1, 1);
                ps2.setString(2, "Marc Anthony");
                ps2.setString(3, "685555555");
                ps2.executeUpdate();
            }



            return true;

        } catch (SQLException se) {
            System.out.println(se);
            se.printStackTrace();
            return false;
        }
    }


    private static boolean recStoreDataTableExist() throws SQLException{

        String checkTablePresentS = "SHOW TABLES LIKE '" + TABLE_NAME + "'" ;
        ResultSet tablesRS = stmtRecStore.executeQuery(checkTablePresentS);
        if (tablesRS.next()) {
            return  true;
        }
        return false;
    }

    protected static boolean consignorDataTableExist() throws SQLException {
        String checkTablePresentConsig = "SHOW TABLES LIKE '" + CONSIGNORS_TABLE + "'";
        ResultSet tableConsig = stmtConsignors.executeQuery(checkTablePresentConsig);
        if (tableConsig.next()) {
            return true;
        }
        return false;
    }

    public static boolean loadTables() {

        try {


            if (rsRecStore != null) {
                rsRecStore.close();
            }
            if (rsConsignors != null) {
                rsConsignors.close();
            }


            String loadAlbums = "SELECT * FROM " + TABLE_NAME;
            rsRecStore = stmtRecStore.executeQuery(loadAlbums);

            if (recordStore_data_model == null) {

                recordStore_data_model = new Record_Store_Data_Model(rsRecStore);

            } else {

                recordStore_data_model.update_ResultSet(rsRecStore);
            }



            String loadConsignors = "SELECT * FROM " + CONSIGNORS_TABLE;
            rsConsignors = stmtConsignors.executeQuery(loadConsignors);

            if (consignors_model == null) {

                consignors_model = new Consignors_Model(rsConsignors);

            } else {

                consignors_model.update_ResultSet(rsConsignors);
            }

            return true;

        } catch (Exception E) {

            System.out.println("error loading tables");
            E.printStackTrace();
            return false;
        }
    }


    public static void shutdown(){
        try {
            if (rsRecStore != null) {
                rsRecStore.close();
                System.out.println("Result set closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        try {
            if (stmtRecStore != null) {
                stmtRecStore.close();
                System.out.println("Statement closed");
            }
        } catch (SQLException se){
            se.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database connection closed");
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }

}
