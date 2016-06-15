package org.spark.model.repository;

import java.sql.*;

public class MySQLDAO {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	//static final String DB_URL = "jdbc:mysql://localhost/ita?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	static final String DB_URL = "jdbc:mysql://db.bditac.com/bditac?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	// Database credentials
	static final String USER = "user_app";//"root";//user_app
	static final String PASS = "User_@pp";//"magica";//User_@pp

	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	public void connect() throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
	}

	public void close() throws SQLException {

		if (rs != null)
			this.rs.close();

		if (stmt != null)
			this.stmt.close();

		if (conn != null)
			this.conn.close();
	}

	public ResultSet find(String sql) throws SQLException {
		stmt = conn.createStatement();
		this.rs = stmt.executeQuery(sql);
		return this.rs;
	}

	public static void main(String[] args) {
		MySQLDAO my = null;
		try {
			my = new MySQLDAO();
			my.connect();

			String sql = "SELECT * FROM crise";
			ResultSet rs = my.find(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String name = rs.getString("nome");

				// Display values
				System.out.print("ID: " + id);
				System.out.print(", Name: " + name);
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			if(my != null)
				try {
					my.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}
