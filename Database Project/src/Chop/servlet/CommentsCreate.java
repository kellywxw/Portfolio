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




@WebServlet("/commentscreate")
public class CommentsCreate extends HttpServlet {
	
	
	protected UsersDao usersDao;
	protected ItemsDao itemsDao;
	protected CommentsDao commentsDao;
	
	@Override
	public void init() throws ServletException {
		usersDao = UsersDao.getInstance();
		itemsDao = ItemsDao.getInstance();
		commentsDao = CommentsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/CommentsCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
               
        String stringItemId = req.getParameter("itemId");
        int itemId = Integer.parseInt(stringItemId);
        String userName = req.getParameter("username");
        String content = req.getParameter("content");
        
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Invalid UserName");
        } else {
        	Date dateNow = new Date();
    		Timestamp created = new Timestamp(dateNow.getTime());

			Users user = null;
			try {
       		user = usersDao.getUserFromUserName(userName);
			} catch (SQLException e) {
				e.printStackTrace();
   				throw new IOException(e);
			}
			
			Items item = null;
			try {
       		item = itemsDao.getItemByItemId(itemId);
			} catch (SQLException e) {
				e.printStackTrace();
   				throw new IOException(e);
			}

			Comments comment = null;
	        try {
	        	comment = new Comments(content,created,user,item);
	        	comment = commentsDao.create(comment);
	        	
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
	        
	        messages.put("success", "Successfully created comment");
        }
        
        req.getRequestDispatcher("/CommentsCreate.jsp").forward(req, resp);
    }
}
