package il.ac.hit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import il.ac.hit.model.HibernateToDoListDAO;
import il.ac.hit.model.IToDoListDAO;
import il.ac.hit.model.User;
import il.ac.hit.model.Item;
import il.ac.hit.model.TodolistException;

/**
 * Servlet implementation class contoller
 */
// @WebServlet("/controller/*")
public class ApplicationRouter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RequestDispatcher dispatcher = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * default Ctor
	 */
	public ApplicationRouter() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String path = request.getPathInfo();

		out.print(path);
		switch (path) {
		case "/login": {
			loginController(request, response);
			break;
		}
		case "/signup": {
			registerController(request, response);
			break;
		}
		case "/addItem": {
			addItemController(request, response);
			break;
		}
		case "/delete": {
			deleteController(request, response);
			break;
		}
		case "/logout": {
			logoutController(request, response);
			break;
		}
		}
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Sign out from the app
	 **/
	private void logoutController(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		request.getRequestDispatcher("/welcome.jsp").forward(request, response);
	}

	/**
	 * Adding an item and saves it in the list for the current user
	 * 
	 * @param item
	 * @throws ServletException
	 *             , IOException
	 */

	private void addItemController(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		String itemDesc = request.getParameter("myInput");
		List<Item> userItems = null;
		if (itemDesc.isEmpty() || itemDesc.isEmpty() || itemDesc.equals("")) {
			request.setAttribute("message", "emptyString");
			request.getRequestDispatcher("/myList.jsp").forward(request, response);
			return;
		} else {
			try {
				Item item = new Item(itemDesc, user.getUserId());
				HibernateToDoListDAO dao = HibernateToDoListDAO.getInstance();
				dao.addItem(item);
				userItems = dao.getAllItemsByUser(user);
				request.getSession().setAttribute("userItems", userItems);
				request.getRequestDispatcher("/myList.jsp").forward(request, response);

			} catch (TodolistException e) {
				PrintWriter out = response.getWriter();
				out.print(e.getCause().getMessage());
			}

		}
	}

	/**
	 * Deleting a specific item from the list of the current user
	 * 
	 * @param item
	 * @throws ServletException
	 *             , IOException
	 */
	private void deleteController(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String itemID = request.getParameter("itemId");
		long longItemID = Long.parseLong(itemID);
		User user = (User) request.getSession().getAttribute("user");
		long UserId = user.getUserId();
		HibernateToDoListDAO dao = HibernateToDoListDAO.getInstance();
		List<Item> userItems = null;
		try {
			userItems = dao.getAllItemsByUser(user);
			for (Item item : userItems) {
				if (item.getItemId() == longItemID) {
					dao.deleteItem(item);
					userItems = dao.getAllItemsByUser(user);
					request.getSession().setAttribute("userItems", userItems);
					request.getRequestDispatcher("/myList.jsp").forward(request, response);
				}
			}
		} catch (TodolistException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Check if a user is registered to the system If so, enter to his to do list
	 * Otherwise go to the registration page
	 * 
	 * @param user
	 * @throws ServletException
	 *             , IOException
	 */
	private void loginController(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("user");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");
		if (userName.isEmpty() || pass.isEmpty() || userName == null || pass == null || email.isEmpty()
				|| email == null) {
			request.setAttribute("LoginFailed", "true");

			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		} else {
			try {
				User usr = new User(userName, pass, email);
				HibernateToDoListDAO dao = HibernateToDoListDAO.getInstance();

				if (dao.login(usr) == false) {// user is not in register
					request.setAttribute("NotRegister", "No");
					dispatcher = request.getRequestDispatcher("/register.jsp");
					return;
				} else {
					User user = dao.getUser(userName);
					request.getSession().setAttribute("user", user);
					List<Item> userItems = dao.getAllItemsByUser(user);
					request.getSession().setAttribute("userItems", userItems);
					request.getRequestDispatcher("/myList.jsp").forward(request, response);

				}
			} catch (TodolistException e) {
				PrintWriter out = response.getWriter();
				out.print(e.getCause().getMessage());
			}

		}
	}

	/**
	 * Registration of the user to the system
	 * 
	 * @param user
	 * @throws ServletException
	 *             , IOException
	 */
	private void registerController(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");
		String passVerification = request.getParameter("passVerification");
		if (user.isEmpty() || pass.isEmpty() || user == null || pass == null || email.isEmpty() || email == null) {
			request.setAttribute("LoginFailed", "true");

			request.getRequestDispatcher("/register.jsp").forward(request, response);

		} else if (passVerification.isEmpty() || passVerification == null || !pass.equals(passVerification)) {
			request.setAttribute("PassNotCorrect", "true");

			request.getRequestDispatcher("/register.jsp").forward(request, response);
		} else {
			try {
				User usr = new User(user, pass, email);
				HibernateToDoListDAO dao = HibernateToDoListDAO.getInstance();

				if (dao.login(usr) == false) {// user is not in register
					dao.addUser(usr);
					request.getSession().setAttribute("userId", usr.getUserId());
					request.getSession().setAttribute("user", usr);
					List<Item> userItems = dao.getAllItemsByUser(usr);
					request.getSession().setAttribute("userItems", userItems);
					Mailer.send(email, user);
					request.getRequestDispatcher("/myList.jsp").forward(request, response);
					return;
				} else if (dao.login(usr) == true) {
					request.setAttribute("alreadyRegister", "true");
					request.getRequestDispatcher("/register.jsp").forward(request, response);
				}
			} catch (TodolistException e) {
				PrintWriter out = response.getWriter();
				out.print(e.getCause().getMessage());
			}
		}
		// private void sendMail(String to) throws javax.mail.MessagingException {
		// String subject = "welcome!";
		// String text = "Welcome for To Do List website, here you can access you to do
		// list from anywhere.";
		//
		// try {
		// java.util.Properties props = System.getProperties();
		// props.put("mail.smtp.auth", "true");
		// props.put("mail.smtp.starttls.enable", "true");
		// props.put("mail.smtp.host", "smtp.gmail.com");
		// props.put("mail.smtp.port", "587");
		// props.put("mail.smtp.starttls.enable", "true");
		//
		// Session mailSession = Session.getDefaultInstance(props);
		// Message emailMessage = new MimeMessage(mailSession);
		//
		// emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		// emailMessage.setFrom(new InternetAddress("todolistweb@gmail.com"));
		// emailMessage.setSubject(subject);
		// emailMessage.setText(text);
		//
		// Transport transport = mailSession.getTransport("smtp");
		// transport.connect("smtp.gmail.com","todolistweb@gmail.com","linoytodo1234" );
		//
		// } catch (AddressException ex) {
		// ex.printStackTrace();
		// }
		//
		//
	}

}
