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


@WebServlet("/commentsdelete")
public class CommentsDelete extends HttpServlet {
	protected EventDao eventDao;
	protected CommentsDao commentsDao;
	
	@Override
	public void init() throws ServletException {
		commentsDao = CommentsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete Comment");        
        req.getRequestDispatcher("/CommentsDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String stringCommentId = req.getParameter("commentId");
        if (stringCommentId  == null || stringCommentId .trim().isEmpty()) {
            messages.put("title", "Invalid CommentId");
            messages.put("disableSubmit", "true");
        } else {
        	Comments comment = null;
        	int commentId = Integer.parseInt(stringCommentId);
            try {
            	comment = commentsDao.getCommentByCommentId(commentId);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	
	        try {
	        	comment = commentsDao.delete(comment);
	        	// Update the message.
		        if (comment == null) {
		            messages.put("title", "Successfully deleted " +"Comment "  + stringCommentId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete "  + "Comment " + stringCommentId);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        req.getRequestDispatcher("/CommentsDelete.jsp").forward(req, resp);
    }
}
