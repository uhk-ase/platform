package cz.uhk.fim.ase.platform.database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jakub "AnDylek" Pluhar <jakub.pluhar@gmail.com at andylek.eu>
 */
public class DatabaseSQL {

	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final static String DB_URL = "jdbc:mysql://192.168.46.85:3306";
    final static String DB_NAME = "vp_agents";

   //Database login
   //TODO: UNSAFE!!
    final static String USER = "root";
    final static String PASS = "Bcxa97am";

    public DatabaseSQL(){
    	final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final static String DB_URL = "jdbc:mysql://192.168.46.85:3306";
        final static String DB_NAME = "vp_agents";

       //Database login
       //TODO: UNSAFE!!
        final static String USER = "root";
        final static String PASS = "Bcxa97am";
    }
    
    public void CreateDB() {

        try {

            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser(USER);
            dataSource.setPassword(PASS);
            dataSource.setServerName(DB_URL);
            dataSource.setDatabaseName(DB_NAME);

            Connection conn;
            Statement st;

            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Succesfully connected.");

            //Execute a query
            System.out.println("Creating database...");
            st = conn.createStatement();

            //TODO: Update database tables to real model.
            //st.addBatch("CREATE TABLE agents (id INT(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,money INT(6),agentID VARCHAR(255), product VARCHAR(255), food INT(6), painkillers INT(6), tools INT(6))");
            st.addBatch("CREATE TABLE agents (id INT(6) NOT NULL AUTO_INCREMENT PRIMARY KEY, cislo INT(4)");
            st.executeBatch();
            System.out.println("Database successfully created.");         

            //Ending commands
            st.executeBatch();
            st.close();
            conn.close();
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method adds new row into database
     *
     * @param id
     * @param agentID
     * @param product
     * @param inventory_Food
     * @param inventory_PainKillers
     * @param inventory_Tools
     * @param money is money, that agent have
     */
    public static void AddRow(int id, String agentID, Float money, String product, int inventory_Food, int inventory_PainKillers, int inventory_Tools) {

        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser(USER);
            dataSource.setPassword(PASS);
            dataSource.setServerName(DB_URL);
            dataSource.setDatabaseName(DB_NAME);
            Connection conn;
            Statement st;
            Class.forName(JDBC_DRIVER);
            
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Succesfully connected.");
            
            System.out.println("Writing into database...");
            st = conn.createStatement();            
            st.addBatch("INSERT INTO agents (id, money, product, food, painkillers, tools) VALUES (" + id + "," + agentID + "," + money + "," + product + "," + inventory_Food + "," + inventory_PainKillers + "," + inventory_Tools +")");
            st.executeBatch();
            st.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void TestRow(int cislo){
    	try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser(USER);
            dataSource.setPassword(PASS);
            dataSource.setServerName(DB_URL);
            dataSource.setDatabaseName(DB_NAME);
            Connection conn;
            Statement st;
            Class.forName(JDBC_DRIVER);
            
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Succesfully connected.");
            
            System.out.println("Writing into database...");
            st = conn.createStatement();            
            st.addBatch("INSERT INTO agents (cislo) VALUES (" + cislo + ")");
            st.executeBatch();
            st.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void TestGet(){
    	try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser(USER);
            dataSource.setPassword(PASS);
            dataSource.setServerName(DB_URL);
            dataSource.setDatabaseName(DB_NAME);
            Connection conn;
            Statement st;
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Succesfully connected.");

            System.out.println("Writing into database...");
            st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("SELECT * FROM agents ORDER BY id");
            System.out.println("ID | CISLO \n");
            while (rs.next()) {                
                Integer id = rs.getInt("id");
                Integer cislo = rs.getInt("cislo");

                System.out.println(id + " | " + cislo + "\n");
            }
            
            st.executeBatch();
            st.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method prints the whole database.
     */
    public static void GetDatabase() {
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser(USER);
            dataSource.setPassword(PASS);
            dataSource.setServerName(DB_URL);
            dataSource.setDatabaseName(DB_NAME);
            Connection conn;
            Statement st;
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Succesfully connected.");

            System.out.println("Writing into database...");
            st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("SELECT * FROM agents ORDER BY id, agentID");
            System.out.println("ID | AGENT ID | MONEY | PRODUCT | FOOD | PAINKILLERS | TOOLS " + "\n");
            while (rs.next()) {                
                Integer id = rs.getInt("id");
                String agentID = rs.getString("agentID");
                Integer money = rs.getInt("money");
                String product = rs.getString("product");
                Integer food = rs.getInt("food");
                Integer painkillers = rs.getInt("painkillers");
                Integer tools = rs.getInt("tools");
                System.out.println(id + " | " + agentID + " | " + money + " | " + product + " | " + food + " | " + painkillers + " | " + tools + " | " + "\n");
            }
            
            st.executeBatch();
            st.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
