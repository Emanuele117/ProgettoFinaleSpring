package it.logicainformatica.progettoSpring.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	public Connection getConnessione(){

	Connection dbconn = null;
	
	try {
			
	Class.forName("com.mysql.jdbc.Driver");
	dbconn= DriverManager.getConnection("jdbc:mysql://localhost:3306/crm","root","");

	}
	
	catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	
	catch (SQLException e) {
		e.printStackTrace();
	}
	
	return dbconn;
	
	}
	
}
