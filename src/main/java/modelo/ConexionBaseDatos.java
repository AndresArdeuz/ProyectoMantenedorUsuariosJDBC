package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {

    private static String url = "jdbc:mysql://localhost:3306/usuarios";
    private static String username = "root";
    private static String password = "defendof1991";
    private static Connection connection;

    public static Connection getInstance() throws SQLException {
        return DriverManager.getConnection(url,username,password);}
}
