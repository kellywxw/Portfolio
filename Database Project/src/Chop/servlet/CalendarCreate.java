package Chop.servlet;

import Chop.dal.*;
import Chop.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/calendarcreate")
public class CalendarCreate extends HttpServlet {
	
	protected CalendarDao calendarDao;
	protected UsersDao usersDao;
	protected CategoryDao categoryDao;
	
	@Override
	public void init() throws ServletException {
		calendarDao = CalendarDao.getInstance();
		usersDao = UsersDao.getInstance();
		categoryDao = CategoryDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/CalendarCreate.jsp").forward(req, resp);
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
        	Date dateNow = new Date();
    		Timestamp dateCreated = new Timestamp(dateNow.getTime());
        	String calendarBackground = req.getParameter("calendarbackground");
			String isPublic = req.getParameter("ispublic_huh");
			boolean isPublic_huh = Boolean.parseBoolean(isPublic);
			String workCategoryName = "work";
			String shareWorkCategory = req.getParameter("shareWorkCategory_huh");
			boolean shareWorkCategory_huh = Boolean.parseBoolean(shareWorkCategory);
			String schoolCategoryName = "school";
			String shareSchoolCategory = req.getParameter("shareSchoolCategory_huh");
			boolean shareSchoolCategory_huh = Boolean.parseBoolean(shareSchoolCategory);
			String socialCategoryName = "social";
			String shareSocialCategory = req.getParameter("shareSocialCategory_huh");
			boolean shareSocialCategory_huh = Boolean.parseBoolean(shareSocialCategory);
			String otherCategoryName = "other";
			String shareOtherCategory = req.getParameter("shareOtherCategory_huh");
			boolean shareOtherCategory_huh = Boolean.parseBoolean(shareOtherCategory);
			
			Users user = null;
			try {
       		user = usersDao.getUserFromUserName(userName);
			} catch (SQLException e) {
				e.printStackTrace();
   				throw new IOException(e);
			}

	        try {
	        	Calendar calendar = new Calendar(dateCreated,calendarBackground,isPublic_huh,user);
	        	calendar = calendarDao.create(calendar);
	        	
	        	Category workCategory = new Category(workCategoryName ,shareWorkCategory_huh,user);
	        	workCategory = categoryDao.create(workCategory);
	        	
	        	Category schoolCategory = new Category(schoolCategoryName ,shareSchoolCategory_huh,user);
	        	schoolCategory = categoryDao.create(schoolCategory);
	        	
	        	Category socialCategory = new Category(socialCategoryName ,shareSocialCategory_huh,user);
	        	socialCategory = categoryDao.create(socialCategory);
	        	
	        	Category otherCategory = new Category(otherCategoryName ,shareOtherCategory_huh,user);
	        	otherCategory = categoryDao.create(otherCategory);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
	        
	        messages.put("success", "Successfully created " + userName + "'s Calendar");
        }
        
        req.getRequestDispatcher("/CalendarCreate.jsp").forward(req, resp);
    }
}
