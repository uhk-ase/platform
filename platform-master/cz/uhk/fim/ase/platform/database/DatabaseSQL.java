package cz.uhk.fim.ase.platform.database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jakub "AnDylek" Pluhar
 */
public class DatabaseSQL {

    //JDBC driver and database url (free hosting)
    //TODO: Add school DB creditals!!
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "db4free.net";
    static final String DB_NAME = "vp_agents";

    //Database login
    //TODO: Nebezpecne!! Nejlepe zahashovat..
    static final String USER = "andylek";
    static final String PASS = "toor123";

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

            //Execute a query
            System.out.println("Creating database...");
            st = conn.createStatement();

            st.addBatch("DROP TABLE agents");
            //TODO: Update database tables to real model.
            st.addBatch("CREATE TABLE agents (id INT(6) NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(26), valut INTEGER(6))");
            st.executeBatch();
            System.out.println("Database sucessfully created.");

            // !! TEST !! _ !! TEST !! _ !! TEST !! _ !! TEST !! _ !! TEST !! _ !! TEST !! _ !! TEST !!
            System.out.println("Executing database statements...");
            st.addBatch("INSERT INTO agents (name, valut) VALUES ('ÈEZ','15000000')");
            st.addBatch("INSERT INTO agents (name, valut) VALUES ('agent82','30000')");
            st.addBatch("INSERT INTO agents (name, valut) VALUES ('agent54','100000')");
            st.executeBatch();

            ResultSet rs = st.executeQuery("SELECT * FROM agents ORDER BY id, valut");
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                Integer valut = rs.getInt("valut");
                System.out.println(id + " | " + name + " | " + valut + "\n");
            }

            System.out.println("Test of database ended succesfully! Hurray!");

            //Ending commands
            st.executeBatch();
            rs.close();
            st.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void AddRow(int customID, String production, String strategy, int sellPrice, int money) {
        //TODO: Update this method.
    }
}
