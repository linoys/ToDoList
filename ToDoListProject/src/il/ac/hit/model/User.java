package il.ac.hit.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Linoy and Liana
 *
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long userId ;
	private String userName;
	private String password;
	private String email;
	
	
	/**
	 * ctor User
	 */
	public User () {}
	/**
	 * Constructor for user object
	 * @param userId
	 * @param userName
	 * @param password
	 * @param email
	 */
	public User (String userName , String password, String email) {
		setUserId(userId) ;
		setUserName(userName) ;
		setPassword(password); 
		setEmail(email);
	}
	
	/**
	 * get User id
	 * @return
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * set user id
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	/**
	 * get User name
	 * @return
	 */
	public String getUserName ()
	{
		return userName ; 
	}
	/**
	 * ser User name
	 * @param userName
	 * @return
	 */
	public boolean setUserName (String userName)
	{
		if (userName == null || userName.isEmpty())
			return false ; 
		this.userName=userName ; 
		return true;
	}
	

	/**
	 * get the user password
	 * @return
	 */
	public String getPassword ()
	{
		return password ; 
	}
	/**
	 * set user password
	 * @param password
	 * @return
	 */
	public boolean setPassword (String password)
	{
		if (password == null || password.isEmpty())
			return false ; 
		this.password=password ; 
		return true;	
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * Override to string method
	 */
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + "]";
	}
    
	
}
