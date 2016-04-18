package Chop.servlet;

import Chop.dal.*;
import Chop.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/calendardelete")
public class CalendarDelete extends HttpServlet {
	
	protected CalendarDao calendarDao;
	
	@Override
	public void init() throws ServletException {
		calendarDao = CalendarDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Calendar");        
        req.getRequestDispatcher("/CalendarDelete.jsp").forward(req, resp);
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
            messages.put("title", "Invalid UserName");
            messages.put("disableSubmit", "true");
        } else {
        	
        	// Delete the Calendar.
        	Calendar calendar = null;;
            try {
            	calendar = calendarDao.getCalendarForUser(userName);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	
	        try {
	        	calendar = calendarDao.delete(calendar);
	        	// Update the message.
		        if (calendar == null) {
		            messages.put("title", "Successfully deleted " + userName + "'s Calendar");
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + userName + "'s Calendar");
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CalendarDelete.jsp").forward(req, resp);
    }
}
