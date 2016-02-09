package cz.uhk.fim.ase.platform.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSQL {

    public void createDB() {
        try {
            // load JDBC driver into memory
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            // create instance of statement
            try ( // create conenction to database
                    Connection con = DriverManager.getConnection("JDBC:derby:AgentiDB;create=true"); // create instance of statement
                    Statement st = con.createStatement()) {
                
                st.addBatch("DROP TABLE agenti");
                st.addBatch("CREATE TABLE agents(id INT NOT NULL GENERATED ALWAYS AS IDENTITY " + "CONSTRAINT AGENTI_PK PRIMARY KEY, name VARCHAR(26), valut INTEGER()");
                
                //TEST
                st.addBatch("INSERT INTO agents (id, name, valut) VALUES ('1','ÈEZ','15000000')");
                ResultSet rs = st.executeQuery("SELECT * FROM agents ORDER BY id, valut");
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    Integer valut = rs.getInt("valut");
                    System.out.println(id + " | " + name + " | " + valut + "\n");
                }
                
                st.executeBatch();
            }

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void WriteIntoDB(String id, String name, Integer valut) {
        try {
            // load JDBC driver into memory
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            // create instance of statement
            try ( // create conenction to database
                    Connection con = DriverManager.getConnection("JDBC:derby:AgentiDB"); // create instance of statement
                    Statement st = con.createStatement()) {
                st.addBatch("INSERT INTO agents (id, name, valut) VALUES ('" + id + "','" + name + "','" + valut + "')");
                st.executeBatch();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
