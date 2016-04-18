package Chop.servlet;

import Chop.dal.*;
import Chop.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/taskcreate")
public class TaskCreate extends HttpServlet {

	public UsersDao usersDao;
	public TaskDao taskDao;
	public CategoryDao categoryDao;

	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
		taskDao = TaskDao.getInstance();
		categoryDao = CategoryDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		//Just render the JSP.   
		req.getRequestDispatcher("/TaskCreate.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		// Retrieve and validate name.
		String userName = req.getParameter("username");
		if (userName == null || userName.trim().isEmpty()) {
			messages.put("success", "Invalid UserName");
		} else {

			String description = req.getParameter("description");
			String stringShareitems_huh = req.getParameter("shareitems_huh");
			boolean shareitems_huh = Boolean.parseBoolean(stringShareitems_huh);
			
			String accomplished = req.getParameter("accomplished_huh");
			boolean accomplished_huh = Boolean.parseBoolean(accomplished);

			Date dateNow = new Date();
			Timestamp dateCreated = new Timestamp(dateNow.getTime());


			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String stringDueDate = req.getParameter("duedate");
			Date dueDate = new Date();
			try {
				dueDate = dateFormat.parse(stringDueDate);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			

			Users user = null;
			try {
				user = usersDao.getUserFromUserName(userName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}

			String categoryName = req.getParameter("category");
			int categoryId = 0;
			Category category = null;
			try {
				categoryId = categoryDao.getCategoryIDByUserNameCategoryName(userName,categoryName);
				category = categoryDao.getCategoryByCategoryId(categoryId);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}


			try {
				Task task = new Task(description, shareitems_huh,dateCreated,user,category,accomplished_huh,dueDate);	
				task = taskDao.create(task);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Successfully created a task for " + userName);
		}
		req.setAttribute("username", userName);
		req.getRequestDispatcher("/TaskCreate.jsp").forward(req, resp);
	}
}
