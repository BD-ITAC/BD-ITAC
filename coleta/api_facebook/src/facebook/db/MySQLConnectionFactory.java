/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebook.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Fellipe Rey
 */
public class MySQLConnectionFactory {
    
//    private static final String USUARIO = "logic685_ubditac";
    private static final String USUARIO = "user_app";
//    private static final String SENHA = "BD-itac#";
    private static final String SENHA = "User_@pp";
//    private static final String URL = "jdbc:mysql://179.190.55.130:3306/logic685_bditac";
    private static final String URL = "jdbc:mysql://127.0.0.1:4000/bditac";
//    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    
    public Connection getConnection() throws Exception {
        // Registrar o driver
        Class.forName(DRIVER);
        // Capturar a conexão
        Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        // Retorna a conexao aberta
        return conn;
    }
  
}
