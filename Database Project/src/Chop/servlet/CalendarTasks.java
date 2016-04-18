package Chop.servlet;
import Chop.dal.*;
import Chop.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calendartasks")
public class CalendarTasks extends HttpServlet {
	
	public TaskDao taskDao;
	
	@Override
	public void init() throws ServletException {
		taskDao = taskDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        String userName = req.getParameter("username");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("title", "Invalid username.");
        } else {
        	messages.put("title", "Tasks for " + userName);
        }    
        
        List<Task> tasks = new ArrayList<Task>();
        try {
        	tasks = taskDao.getTasksByUserName(userName);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("task", tasks);
        req.setAttribute("username", userName);
        req.getRequestDispatcher("/CalendarTasks.jsp").forward(req, resp);
	}
}
        
