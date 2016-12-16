package regAuth.rest.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.appengine.api.utils.SystemProperty;

/**
 * this class is responsible for registering new users.
 * @author Leila
 *
 */
public class Registration {
	
	/**
	 * adds a new user to database
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String addUser(User user) throws Exception{
		
			Connection connection = connect();
				
				
				//create the customer ID
				String custID = createID(connection);
				
				//calculate current date
				DateFormat df = new SimpleDateFormat("yyyyMMdd");
				Calendar calobj = Calendar.getInstance();
				String registrationdate = df.format(calobj.getTime()).toString();
				
				//insert to DB if it is allowed.
				if (isAllowed(connection,user.getUsername())){
					PreparedStatement statement = connection.prepareStatement("INSERT INTO `users`"
						+ "(`custID`, `username`, `password`, `firstname`, `lastname`, `email`,"
						+ " `phone`, `address`, `city`, `country`, `postalcode`, `DOB`, `registrationdate`) "
						+ "VALUES ('"+custID+"','"+user.getUsername()+"','"+user.getPassword()+"','"+user.getFirstname()+"','"+user.getLastname()+
						"','"+user.getEmail()+"','"+user.getPhone()+"','"+user.getAddress()+"','"+user.getCity()+"','"+user.getCountry()
						+"','"+user.getPostalcode()+"','"+user.getDOB()+"','"
						+registrationdate+"')");
				
					statement.executeUpdate();
					return "you are registered now :) ";
				}
				else{
					return "the username you chose already exists :(";
				}
	}
	
	/**
	 * gets the greatest customer ID from the database and adds 1 to it and makes a new customer ID.
	 * @param connect
	 * @return
	 * @throws Exception
	 */
	public String createID(Connection connect) throws Exception{
		
		int counter = 0;
		PreparedStatement statement = connect.prepareStatement("select MAX(custID) from users");
		ResultSet result = statement.executeQuery();
		
		while(result.next()){
			
			counter = result.getInt(1)+1;
			
		}
		return Integer.toString(counter);
	}
	
	/**
	 * checks if the username already exists.
	 * @param connect
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public static boolean isAllowed(Connection connect, String username) throws Exception{
		
		PreparedStatement statement = connect.prepareStatement("SELECT * FROM `users` WHERE `username` = '"+username+"'");
		ResultSet result = statement.executeQuery();
		String permission="yes";
		
		while(result.next()){
			if(result.getString(2).equals(username))
			permission = "no";
		}
		
		if(permission.equals("yes"))
			return true;
		else
			return false;
		
	}
	
	
	/**
	 * using a username, returns information about a user.
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User getUser(String custID) throws Exception{
		
		Connection connection = connect();
		
		//select from DB
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM `users` WHERE `custID` = '"+custID+"'");
		ResultSet result = statement.executeQuery();
		
		User myuser = null;
		
		while(result.next()){
			
			myuser = new User(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5),
					result.getString(6),result.getString(7),result.getString(8),result.getString(9),result.getString(10),result.getString(11),
					result.getString(12),result.getString(13));
			
		}
		
		return myuser;
	}

	/**
	 * gets the list of all users from database.
	 * @return
	 * @throws Exception
	 */
	public List<User> getAllUsers() throws Exception{
		
		Connection connection = connect();
		
		//select from DB
		PreparedStatement statement = connection.prepareStatement("select * from users");
		ResultSet result = statement.executeQuery();
		
		List<User> userlist = new ArrayList<>();
		
		while(result.next()){
			
			User myuser = new User(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5),
					result.getString(6),result.getString(7),result.getString(8),result.getString(9),result.getString(10),result.getString(11),
					result.getString(12),result.getString(13));
			userlist.add(myuser);
			
		}
		
		return userlist;
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

	public User getUsername(String username) throws Exception{
		Connection connection = connect();
		
		//select from DB
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM `users` WHERE `username` = '"+username+"'");
		ResultSet result = statement.executeQuery();
		
		User myuser = null;
		
		while(result.next()){
			
			myuser = new User(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5),
					result.getString(6),result.getString(7),result.getString(8),result.getString(9),result.getString(10),result.getString(11),
					result.getString(12),result.getString(13));
			
		}
		
		return myuser;
	}


}
