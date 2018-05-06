package il.ac.hit.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
/**
 * 
 * @author linoy and liana
 *
 */
public class HibernateToDoListDAO implements IToDoListDAO {

	private static HibernateToDoListDAO instance = null;
	private SessionFactory sessionFactory = null;

	private HibernateToDoListDAO() {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Failed to create sessionFactory object." + e + "\n" + e.getCause().getCause());
			throw new ExceptionInInitializerError(e);
		}
	}
	/**
	 * implementing singelton pattern
	 * @return
	 */
	public static HibernateToDoListDAO getInstance() {
		if (instance == null) {
			instance = new HibernateToDoListDAO();
		}
		return instance;
	}

	@PersistenceContext
	public EntityManager em;
	/**
	 * Adding item to the list of the user
	 */
	@Override
	public void addItem(Item item) throws TodolistException {
		Transaction transaction = null;
		if (isItemExist(item) == false) {
			Session session = sessionFactory.openSession();
			try {
				transaction = session.beginTransaction();
				session.saveOrUpdate(item);
				transaction.commit();
			}

			catch (HibernateException e) {
				if (transaction != null && transaction.isActive())
					transaction.rollback();
				throw new TodolistException("Failed to add item", e);
			}

			finally {
				if (session != null && session.isOpen())
					session.close();
			}
		}

	}
	/**
	 * update usre details
	 * @return true if succeded, false otherwise. 
	 */
	@Override
	public boolean updateUser(User user) throws TodolistException {
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		if (isUserExist(user)) {
			try {
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				session.update(user);
				transaction.commit();
				result = true;
			} catch (HibernateException exception) {
				transaction.rollback();
				throw new TodolistException("Unable to get users list from the database");
			} finally {
				if (session != null && session.isOpen())
					session.close();
			}
		}

		return result;
	}
	/**
	 * check if item exist in the DB
	 * @return true if succeded, false otherwise. 
	 */
	@Override
	public boolean isItemExist(Item item) throws TodolistException {
		boolean exist = false;
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("select itemId from Item where itemId = '" + item.getItemId() + "' ");
			Object object = query.uniqueResult();
			if (object != null)
				exist = true;
			else
				exist = false;
		} catch (HibernateException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw new TodolistException("Failed to get item", e);
		}

		finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return exist;
	}
	/**
	 * delete item for specific user
	 */
	@Override
	public void deleteItem(Item item) throws TodolistException {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			session.delete(item);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			System.out.println(e.getCause().getCause());
			throw new TodolistException("Failed to delete item", e);
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
	}
	/**
	 * deleting all utems for user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteAllItemsOfUser(User user) throws TodolistException {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<Item> results = getAllItemsByUser(user);
			for(Item i: results) {
			deleteItem(i);	
			}
		} catch (QueryException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw new TodolistException("Unable to delete users list from the database");
		} catch (HibernateException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw new TodolistException("Unable to delete users list from the database");
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
	}
	/**
	 * Delete all items for a spesific user
	 * @return list of the items. 
	 */
	@Override
	public List<Item> getAllItemsByUser(User user) throws TodolistException {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("FROM Item WHERE userId =:userId").setParameter("userId",user.getUserId());
//			TypedQuery<Item> query = em.createQuery("FROM Item WHERE userId = :userId", Item.class);
			@SuppressWarnings("unchecked")
			List<Item> results = query.list();
			return results;
		} catch (QueryException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw new TodolistException("Unable to get users list from the database");
		} catch (HibernateException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw new TodolistException("Unable to get users list from the database");
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
	}
	/**
	 * updating item
	 * @return true if succeded, false otherwise. 
	 */
	
	@Override
	public boolean updateItem(Item item) throws TodolistException {
		boolean result = false;
		Session session = null;
		Transaction transaction = null;
		if (isItemExist(item)) {
			try {
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				session.update(item);
				transaction.commit();
				result = true;
			} catch (HibernateException exception) {
				transaction.rollback();
				throw new TodolistException("Unable to get users list from the database");
			} finally {
				if (session != null && session.isOpen())
					session.close();
			}
		}

		return result;
	}

	/**
	 * users methods
	 * 
	 **/
	/**
	 * Adding user to the DB
	 */
	@Override
	public void addUser(User newUser) throws TodolistException {
		Transaction transaction = null;
		Session session = null;

		if (isUserExist(newUser) == false) {
			try {
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				session.save(newUser);
				transaction.commit();
			}

			catch (HibernateException e) {
				if (transaction != null && transaction.isActive())
					transaction.rollback();
				throw new TodolistException("Failed to add user" + e.getCause().getCause());
			}

			finally {
				if (session != null && session.isOpen())
					session.close();
			}
		}

	}
	/**Deleting user
	 * 
	 */
	@Override
	public void deleteUser(User user) throws TodolistException {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			deleteAllItemsOfUser(user);
			Query query = session.createQuery("delete from User where userName =:userId").setParameter("userId", user.getUserName());
			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw new TodolistException("Failed to delete user\n" + e.getMessage());
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

	}
	/**
	 * Check if user exist
	 */
	@Override
	public boolean isUserExist(User user) throws TodolistException {
		boolean exist = false;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("select userName from User where userName = '" + user.getUserName() + "' ");
			Object object = query.uniqueResult();
			if (object != null)
				exist = true;
			else
				exist = false;
		} catch (HibernateException e) {
			throw new TodolistException("Failed to delete user", e.getCause().getCause());
		}

		finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return exist;

	}
	/**
	 * delete all users
	 */
	@Override
	public void deleteAllUsers() throws TodolistException {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			session.createQuery("delete from Item").executeUpdate();//delete item table
			session.createQuery("delete from User").executeUpdate();//delete users table
			transaction.commit();
		}

		catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			throw new TodolistException("Failed to delete all users " + e.getMessage());

		}

		finally {
			if (session != null && session.isOpen())
				session.close();
		}
	}
	/**
	 * Get user details
	 */
	
	@Override
	public User getUser(String user) throws TodolistException {
		Session session = sessionFactory.openSession();
		User returnedUser = null;

		try {
			Query query = session.createQuery("from User where userName =:user").setParameter("user", user);
			returnedUser = (User) query.uniqueResult();
			

		} catch (HibernateException ex) {
			System.err.println("error occured" + ex.getMessage());

			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new TodolistException("Unable to get user from the database", ex.getCause().getCause());

		} finally {
			if (session != null && session.isOpen())
				session.close();
		}
		return returnedUser;
	}
	/**
	 * login to the DB: check user authenication 
	 */
	@Override
	public boolean login(User user) throws TodolistException {
		System.out.println("user login to hibernate ...");
		User validationUser = getUser(user.getUserName());
		if (validationUser != null && validationUser.getPassword().equals(user.getPassword()) && 
				validationUser.getUserName().equals(user.getUserName()) 
				&& validationUser.getEmail().equals(user.getEmail()) ) {
			return true;
		}

		return false;
	}

}
