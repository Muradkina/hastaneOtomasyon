package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnetion {

    Connection connection = null;

    public DBConnetion() {
    }

    public Connection baglanDB()  {
            try {
              Class.forName("org.postgresql.Driver");
                this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hastane1",
                        "postgres", "123456");
                return  connection;
            } catch (SQLException  e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        return connection;
    }
}
