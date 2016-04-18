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


@WebServlet("/eventdelete")
public class EventDelete extends HttpServlet {
	protected EventDao eventDao;
	
	@Override
	public void init() throws ServletException {
		eventDao = EventDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Event");        
        req.getRequestDispatcher("/EventDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String stringItemId = req.getParameter("itemId");
        if (stringItemId  == null || stringItemId .trim().isEmpty()) {
            messages.put("title", "Invalid ItemId");
            messages.put("disableSubmit", "true");
        } else {
        	Event event = null;;
        	int itemId = Integer.parseInt(stringItemId);
            try {
            	event = eventDao.getEventByItemId(itemId);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	
	        try {
	        	event = eventDao.delete(event);
	        	// Update the message.
		        if (event == null) {
		            messages.put("title", "Successfully deleted " +"Event "  + stringItemId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete "  + "Event " + stringItemId);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        req.setAttribute("itemId", stringItemId);
        req.getRequestDispatcher("/EventDelete.jsp").forward(req, resp);
    }
}
