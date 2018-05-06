package il.ac.hit.model;
//import static org.junit.Assert.*;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

public class TestHibernateToDoListDAO {


	/**
	 * Testing Model
	 */
	/**
	 * User methods test
	 */
	@Test
	public void testUsers() {
		HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();

		User u1 = new User("Linoy", "lin123","linoy15s@gmail.com");
		User u2 = new User("Liana", "2GetM3","linoy15s@gmail.com");
		/**
		 * Test adding users
		 */
		try {
			model.addUser(u1);
			model.addUser(u2);
			System.out.println("test add users : Successfully created users: \n\t[\n\t\t" + u1.getUserName());
		} catch (TodolistException e) {
			System.err.println(
					"test add users : Failed to create user. There will be nothing to get - failing this test...");
		}
		/**
		 * Test get user details
		 */
		try {
			System.out.println("Test get user: successfully get the user: /n" + model.getUser("Linoy"));
			assertTrue(true);
		} catch (TodolistException e) {
			System.err.println("test Get User : Failed to retrieve the user.");
		}
		/**
		 * Test update user
		 */
		try {
			u2.setPassword("liana11");
			System.out.println(model.updateUser(u2));
			System.out.println("Test update user: successfully updated user: /n" + model.getUser("Liana"));
			assertTrue(true);
		} catch (TodolistException e) {
			System.err.println("test Update Users : Failed to update user.");
			assertTrue(false);
		}
		/**
		 * Test login user to the DB
		 */
		try {
			User u3 = new User("Liana", "2GetM3","linoy15s@gmail.com");
			boolean returnedUser = model.login(u3);
			if (returnedUser == false) {
				System.out.println(
						"test login User :  Unable to login: success authentication- user not authorized to login");
				// password for user is different from password for user in database
				assertTrue(true);
			}
		} catch (TodolistException e) {
			System.err.println("test login User : user not authorized to login suceeded to login : Security error"
					+ e.getCause().getCause());
			e.printStackTrace();
		}

	}
	/**
	 * Item test
	 * @throws TodolistException
	 */
	@Test
	public void testItems() throws TodolistException {
		HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();
		User u1 = new User("linoya","12","linoy15s@gmail.com");
		User u2 = new User("shira","1324","linoy15s@gmail.com");
		model.addUser(u1);
		model.addUser(u2);
		Item item1 = new Item("do dishes",u1.getUserId());
		Item item2 = new Item("feed the dog",u2.getUserId());
		Item item3 = new Item("eat breakfast",u1.getUserId());
		Item item4 = new Item("learn to the test tomorrow",u2.getUserId());
		/**
		 * Test adding items
		 */
		try {
			model.addItem(item1);
			model.addItem(item2);
			model.addItem(item3);
			model.addItem(item4);
			System.out.println("test add item: successfully added the items.");
			assertTrue(true);
		} catch (TodolistException e) {
			System.err.println("test add item: Failed to add the item: item already exist in the database");
			e.getCause().printStackTrace();
		}
		/**
		 * Test getting user's items
		 */
		try {
			List<Item> itemsForU1 = model.getAllItemsByUser(u1);
			for(Item i:itemsForU1) {
				System.out.println(i.getDescription()+"/n");
			}
			assertTrue(true);
		} catch (TodolistException e) {
			System.err.println("test get all items for user: Failed to get the item.");
			assertTrue(false);
			e.getCause().printStackTrace();
		}
		/**
		 * Testing deleting item
		 */
		try {
			model.deleteItem(item3);
			System.out.println("Test delete item: successfully deleted item.");
			assertTrue(true);
		} catch (TodolistException e) {
			System.err.println("Test delete item: Failed to delete the item.");
			assertTrue(false);
			e.printStackTrace();
		}
		/**
		 * Tesing update item
		 */
		try {
			item2.setDescription("done");
			model.updateItem(item2);
			System.out.println("Test update item: successfully updated item.");
			model.deleteUser(u1);
			System.out.println("Test delete user and all it's items: successfully deleted user.");
			assertTrue(true);
		} catch (TodolistException e) {
			System.err.println("test update item : Failed to update the item.");
			assertTrue(false);
			e.printStackTrace();
		}
	}

	/**
	 * Clean database - Delete users and items tables
	 */
	@AfterClass
	public static void cleanDataBase() {
		try {
			HibernateToDoListDAO model = HibernateToDoListDAO.getInstance();
			model.deleteAllUsers();
			System.out.println("test Delet eUsers : Successfully Deleted Users");
			assertTrue(true);
		} catch (TodolistException ex) {
			System.err.println("test Delete Users : Unable to delete users." + ex.getCause().getCause());
			ex.printStackTrace();
			assertTrue(false);
		}
	}
		

}