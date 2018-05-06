package il.ac.hit.model;

import java.util.List;

/**
 * This interface defines the options of the ToDoList application,
 *  for the user and it's items.
 *  
 * @author Linoy and Liana
 *
 */
public interface IToDoListDAO {

	/**
	 * Adding an item and saves it
	 * @param item
	 * @throws TodolistException
	 */
	public void addItem(Item item) throws TodolistException;

	/**
	 * Deleting a specific item
	 * @param item
	 * @throws TodolistException
	 */
	public void deleteItem(Item item) throws TodolistException;

	/**
	 *	returns list of items of specific userId
	 * @param user
	 * @return list of items of specific userId
	 * @throws TodolistException
	 */
	public List<Item> getAllItemsByUser(User user) throws TodolistException;

	/**
	 * deleting all items of specific userId
	 * @param user
	 * @throws TodolistException
	 */
	public void deleteAllItemsOfUser(User user) throws TodolistException;

	/**
	 * updating an item
	 * @param item
	 * @return
	 * @throws TodolistException
	 */
	public boolean updateItem(Item item) throws TodolistException;

	/**
	 * checking if item is already exist
	 * @param item
	 * @return true if item is exist, false otherwise.
	 * @throws TodolistException
	 */
	public boolean isItemExist(Item item) throws TodolistException;


	/**
	 * Adding a user
	 * @param newUser
	 * @throws TodolistException
	 */
	public void addUser(User newUser) throws TodolistException;

	/**
	 * Deleting a user
	 * @param user
	 * @throws TodolistException
	 */
	public void deleteUser(User user) throws TodolistException;

	/**
	 * Deleting all users
	 * @throws TodolistException
	 */
	public void deleteAllUsers() throws TodolistException;

	/**
	 * return user
	 * @param user
	 * @return user
	 * @throws TodolistException
	 */
	public User getUser(String user) throws TodolistException;

/**
 * Validate if userName and password exists and returns the user object 
 * @param user
 * @return true if login succeeded, false otherwise.
 * @throws TodolistException
 */
	public boolean login(User user) throws TodolistException;

	/**
	 * Checking if user exists
	 * @param user
	 * @return true if user exists, false otherwise.
	 * @throws TodolistException
	 */
	public boolean isUserExist(User user) throws TodolistException;
	
	/**
	 * updating an user
	 * @param user
	 * @return true if update user succeeded, false otherwise.
	 * @throws TodolistException
	 */
	public boolean updateUser(User user) throws TodolistException;
	
}
