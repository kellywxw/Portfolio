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




@WebServlet("/eventcreate")
public class EventCreate extends HttpServlet {

	public UsersDao usersDao;
	public EventDao eventDao;
	public CategoryDao categoryDao;

	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
		eventDao = EventDao.getInstance();
		categoryDao = CategoryDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		//Just render the JSP.   
		req.getRequestDispatcher("/EventCreate.jsp").forward(req, resp);
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

			Date dateNow = new Date();
			Timestamp dateCreated = new Timestamp(dateNow.getTime());


			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String stringStartDate = req.getParameter("startdate");
			String stringEndDate = req.getParameter("enddate");
			Date startDate = new Date();
			Date endDate = new Date();
			try {
				startDate = dateFormat.parse(stringStartDate);
				endDate = dateFormat.parse(stringEndDate);
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
				Event event = new Event(description, shareitems_huh,dateCreated,user,category,startDate , endDate);	
				event = eventDao.create(event);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Successfully created an event for " + userName);
		}
		req.setAttribute("username", userName);
		req.getRequestDispatcher("/EventCreate.jsp").forward(req, resp);
	}
}
