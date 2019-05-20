package fr.pizzeria.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import fr.pizzeria.utils.PropertyLoader;

public class PiloteMySQL {
	
	Connection connection;

	public PiloteMySQL() {
		chargerDriver();
	}
	
	
	public void chargerDriver() {
		try {
			PropertyLoader.loadProperties();
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void seConnecter() {
		String url = PropertyLoader.getProperty("url");
		String user = PropertyLoader.getProperty("user");
		String psw = PropertyLoader.getProperty("psw");
		
		try {
			this.connection = (Connection) DriverManager.getConnection(url, user, psw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Statement creerStatement() {
		Statement statement = null;
		try {
			statement = (Statement) connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}
	
	public PreparedStatement creerPreparedStatement(String requette) {
		PreparedStatement pSt = null;
		try {
			pSt = (PreparedStatement) connection.prepareStatement(requette);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pSt;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			connection = null;
		}
	}
	
}
