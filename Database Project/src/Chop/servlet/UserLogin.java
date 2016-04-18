package Chop.servlet;
import Chop.dal.*;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;

import Chop.model.Calendar;
import Chop.model.Persons;
import Chop.model.Users;


@WebServlet("/userlogin")
public class UserLogin extends HttpServlet {

	public UsersDao usersDao;
	public CalendarDao calendarDao;


	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
		calendarDao = CalendarDao.getInstance();
	}



	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        Users user = null;
        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	// Retrieve Users, and store as a message.
        	try {
        		 user = usersDao.getUserFromUserName(userName);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + userName);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
        	messages.put("previousUserName", userName);
        }
        req.setAttribute("users", user);
        req.getRequestDispatcher("/UserLogin.jsp").forward(req, resp);
	}
	

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		
		Users userResult =null;
		try {
			userResult = usersDao.getUserFromUserName(userName);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		
		String pw = userResult.getPassword();
		
		Users user =null;
		if (pw.equals(password)) {
			
			Cookie userCookie = new Cookie("username",userName);
			userCookie.setMaxAge(60*60*24*365);
			resp.addCookie(userCookie);
			
			try {
				user = usersDao.getUserFromUserName(userName);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", userName + " Logged-in");	
		} else {
			messages.put("success", "Login failed."); 
		}
		req.setAttribute("users", user);
		req.getRequestDispatcher("/UserLogin.jsp").forward(req, resp);
	}
}
