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


@WebServlet("/taskdelete")
public class TaskDelete extends HttpServlet {
	protected TaskDao taskDao;
	
	@Override
	public void init() throws ServletException {
		taskDao = TaskDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Task");        
        req.getRequestDispatcher("/TaskDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String stringItemId = req.getParameter("itemId");
        if (stringItemId  == null || stringItemId .trim().isEmpty()) {
            messages.put("title", "Invalid ItemId");
            messages.put("disableSubmit", "true");
        } else {
        	Task task = null;;
        	int itemId = Integer.parseInt(stringItemId);
            try {
            	task = taskDao.getTaskByItemId(itemId);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	
	        try {
	        	task = taskDao.delete(task);
	        	// Update the message.
		        if (task == null) {
		            messages.put("title", "Successfully deleted " + "Task " + stringItemId );
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + "Task " + stringItemId);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        req.setAttribute("itemId", stringItemId);
        req.getRequestDispatcher("/TaskDelete.jsp").forward(req, resp);
    }
}
