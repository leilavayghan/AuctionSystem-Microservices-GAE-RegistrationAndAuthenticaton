package regAuth.rest.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.appengine.api.utils.SystemProperty;

public class Authentication {

	public String authenticate(String username, String password) throws Exception{
		
		Connection connection = connect();
		
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM `users` WHERE `username` = '"+username+"' and `password` = '"+password+"'");
		ResultSet result = statement.executeQuery();
		String permission="no";
		
		while(result.next()){
		
			if(result.getString(2).equals(username)&&result.getString(3).equals(password))
			permission = "yes";
		}
		
		if(permission.equals("yes"))
			return "you are logged in";
		else
			return "wrong username or password";
		
	}
	
	public Connection connect() throws Exception{
		String url;
		Connection connect;
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			  // Load the class that provides the new "jdbc:google:mysql://" prefix.
			  Class.forName("com.mysql.jdbc.GoogleDriver");
			  url = "jdbc:google:mysql://regauthservice:registrationservice/people?user=myuser&password=leila";
			  connect = DriverManager.getConnection(url);
			} else {
			  // Local MySQL instance to use during development.
			  Class.forName("com.mysql.jdbc.Driver");
			  url = "jdbc:mysql://127.0.0.1:3306/people?user=root";
			  connect = DriverManager.getConnection(url);
			}
		return connect;
	}
}
