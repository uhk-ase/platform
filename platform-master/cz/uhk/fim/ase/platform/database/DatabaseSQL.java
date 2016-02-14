package cz.uhk.fim.ase.platform.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AnDylek
 */
public class DatabaseSQL {

    //JDBC driver and database url (hosting on wedos)
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://wm80.wedos.net/";

    //database login
    //TODO: Nebezpecne!! Nejlepe zahashovat..
    static final String USER = "a94420_db";
    static final String PASS = "deRuNsfq";

    public void createDB() {
        Connection conn = null;
        Statement st = null;
        try {

            //register JDBC driver
            Class.forName(JDBC_DRIVER);

            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Execute a query
            System.out.println("Creating statement...");
            st = conn.createStatement();
            String sql;
            st.addBatch("DROP TABLE agenti");
            st.addBatch("CREATE TABLE agents(id INT NOT NULL GENERATED ALWAYS AS IDENTITY " + "CONSTRAINT AGENTI_PK PRIMARY KEY, name VARCHAR(26), valut INTEGER()");

            st.addBatch("INSERT INTO agents (id, name, valut) VALUES ('1','ÈEZ','15000000')");
            ResultSet rs = st.executeQuery("SELECT * FROM agents ORDER BY id, valut");
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                Integer valut = rs.getInt("valut");
                System.out.println(id + " | " + name + " | " + valut + "\n");
            }
            st.executeBatch();

            rs.close();
            st.close();
            conn.close();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void WriteIntoDB(String id, String name, Integer valut) {
        try {
            // load JDBC driver into memory
            Class.forName(JDBC_DRIVER);
            // create instance of statement
            try ( // create conenction to database
                    Connection con = DriverManager.getConnection(DB_URL, USER, PASS); // create instance of statement
                    Statement st = con.createStatement()) {
                st.addBatch("INSERT INTO agents (id, name, valut) VALUES ('" + id + "','" + name + "','" + valut + "')");
                st.executeBatch();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
