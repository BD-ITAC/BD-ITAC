package org.sparkexample.model.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sparkexample.model.pojo.CrisePojo;

public class CriseRepository extends MySQLDAO {

	public List<CrisePojo> find() {
		try {
			connect();

			String sql = "SELECT * FROM crise";
			ResultSet rs = find(sql);

			List<CrisePojo> crises = new ArrayList<CrisePojo>();
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String name = rs.getString("nome");
				
				crises.add(new CrisePojo(id, name));
			}
			
			return crises;
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			return null;
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			return null;
		} finally {
			try {
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {

		CriseRepository cr = new CriseRepository();
		List<CrisePojo> list = cr.find();
		
		for(CrisePojo crise : list){
			// Display values
			System.out.println("ID: " + crise.getId());
			System.out.println("Name: " + crise.getName());
		}
	}
}
