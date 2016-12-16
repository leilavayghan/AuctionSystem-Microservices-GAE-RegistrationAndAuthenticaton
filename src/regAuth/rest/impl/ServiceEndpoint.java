package regAuth.rest.impl;


import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/RegAuth")
public class ServiceEndpoint {
	
	Registration registration = new Registration();
	Authentication authentication = new Authentication();
	
	/**
	 * create a new user.
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public String DBinsert(User user) throws Exception{
		
		return registration.addUser(user);
		
	}
	
	/**
	 * retrieve a list of users.
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/register/info")
	@Produces(MediaType.APPLICATION_XML)
	public List<User> findAllUsers() throws Exception{
		
		return registration.getAllUsers();
	
	}
	
	/**
	 * retrieves information about a specific user.
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/register/info/{custID}")
	@Produces(MediaType.APPLICATION_XML)
	public User findUser(@PathParam ("custID") String custID) throws Exception{
		
		return registration.getUser(custID);
		
	}
	
	/**
	 * retrieves information about a specific user by username.
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/register/info/login/{username}")
	@Produces(MediaType.APPLICATION_XML)
	public User findUsername(@PathParam ("username") String username) throws Exception{
		
		return registration.getUsername(username);
		
	}
	
	/**
	 * authenticates the user when the username and password are provided by user.
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/authenticate/{username}&{password}")
	@Produces(MediaType.APPLICATION_XML)
	public String findUser(@PathParam ("username") String username, @PathParam ("password") String password) throws Exception{
		
		return authentication.authenticate(username, password);
		
	}

}
