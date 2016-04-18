package Chop.servlet;

import Chop.dal.*;
import Chop.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/commentsupdate")
public class CommentsUpdate extends HttpServlet {
	
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

        String stringCommentId = req.getParameter("commentId");
        
        if (stringCommentId == null || stringCommentId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid CommentId.");
        } else {
        	try {
    			int commentId = Integer.parseInt(stringCommentId);
        		Comments comment = commentsDao.getCommentByCommentId(commentId);
        		if(comment == null) {
        			messages.put("success", "Comment does not exist.");
        		}
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CommentsUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String commentId = req.getParameter("commentId");
        if (commentId == null || commentId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid CommentId.");
        } else {
        	try {
        		int commentID = Integer.parseInt(commentId);
        		Comments comment = commentsDao.getCommentByCommentId(commentID);
        		if(comment == null) {
        			messages.put("success", "Comment " + commentId + " does not exist. No update to perform.");
        		} else {
        			String newContent = req.getParameter("content");
        			if (newContent == null || newContent.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid Content");
        	        } else {
        	        	comment = commentsDao.updateContent(comment,newContent);
        	        	messages.put("success", "Successfully updated Comment " + commentId);
        	        }
        		}
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        req.getRequestDispatcher("/CommentsUpdate.jsp").forward(req, resp);
    }
}
