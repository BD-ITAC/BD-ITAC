package bditac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    public Connection conn = null;

    public DB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Crawler";
            conn = DriverManager.getConnection(url, "root", "Hdbl09069088#");
            System.out.println("conn built");
        } catch (SQLException | ClassNotFoundException e) {
        }
    }

    public ResultSet runSql(String sql) throws SQLException {
        Statement sta = conn.createStatement();
        return sta.executeQuery(sql);
    }

    public boolean runSql2(String sql) throws SQLException {
        Statement sta = conn.createStatement();
        return sta.execute(sql);
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (conn != null || !conn.isClosed()) {
                conn.close();
            }
        } finally {
            super.finalize();
        }
    }
}
