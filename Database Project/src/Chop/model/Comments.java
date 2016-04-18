package Chop.model;

import java.util.Date;


public class Comments {
	protected int commentId;
	protected String content;
	protected Date created;
	protected Users user;
	protected Items item;
	
	public Comments(int commentId, String content, Date created, Users user, Items item) {
		this.commentId = commentId;
		this.content = content;
		this.created = created;
		this.user = user;
		this.item = item;		
	}
	
	public Comments(int commentId) {
		this.commentId = commentId;
	}
	
	public Comments(String content, Date created, Users user,Items item) {
		this.content = content;
		this.created = created;
		this.user = user;
		this.item = item;		
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Items getItem() {
		return item;
	}

	public void setItem(Items item) {
		this.item = item;
	}

}
