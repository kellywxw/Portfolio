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




@WebServlet("/usercreate")
public class UserCreate extends HttpServlet {
	
	protected UsersDao usersDao;
	
	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
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
        	// Create the User.
        	String firstName = req.getParameter("firstname");
        	String lastName = req.getParameter("lastname");
        	String password = req.getParameter("password");
        	String email = req.getParameter("email");
        	String profilePicture = req.getParameter("profilePicture");
        	String about = req.getParameter("about");
        	String newAge = req.getParameter("age");
			int age = Integer.parseInt(newAge);
        	String gender = req.getParameter("gender");
        	Date dateNow = new Date();
    		Timestamp lastLogin = new Timestamp(dateNow.getTime());
	        try {
	        	Users users = new Users(userName,password, firstName, lastName,email,profilePicture,about,age,lastLogin,gender);
	        	users = usersDao.create(users);
	        	messages.put("success", "Successfully created " + userName);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UserCreate.jsp").forward(req, resp);
    }
}
