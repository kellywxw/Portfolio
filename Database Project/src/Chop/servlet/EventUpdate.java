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


@WebServlet("/eventupdate")
public class EventUpdate extends HttpServlet {
	
	protected ItemsDao itemsDao;
	
	@Override
	public void init() throws ServletException {
		itemsDao = ItemsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String itemId = req.getParameter("itemId");
        
        if (itemId == null || itemId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid ItemId.");
        } else {
        	try {
    			int itemID = Integer.parseInt(itemId);
        		Items item = itemsDao.getItemByItemId(itemID);
        		if(item == null) {
        			messages.put("success", "Event does not exist.");
        		}
        		req.setAttribute("item", item);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/EventUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String itemId = req.getParameter("itemId");
        if (itemId == null || itemId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid ItemId.");
        } else {
        	try {
        		int itemID = Integer.parseInt(itemId);
        		Items item = itemsDao.getItemByItemId(itemID);
        		if(item == null) {
        			messages.put("success", "Event " + itemId + " does not exist. No update to perform.");
        		} else {
        			String newDescription = req.getParameter("description");
        			if (newDescription == null || newDescription.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid Description");
        	        } else {
        	        	item = itemsDao.updateItemDescription(item,newDescription);
        	        	messages.put("success", "Successfully updated Event " + itemId);
        	        }
        		}
        		req.setAttribute("item", item);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        req.setAttribute("itemId", itemId);
        req.getRequestDispatcher("/EventUpdate.jsp").forward(req, resp);
    }
}
