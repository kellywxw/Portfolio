package Chop.model;

public class FriendRequest {
	protected boolean accepted_huh;
	protected Users userRequestor;
	protected Users userRequestee;
	protected int friendRequestId;
	
	public FriendRequest(boolean accepted_huh, Users userRequestor, Users userRequestee, int friendRequestId) {
		this.accepted_huh = accepted_huh;
		this.userRequestor = userRequestor;
		this.userRequestee = userRequestee;
		this.friendRequestId = friendRequestId;
	}
	
	public FriendRequest(Users userRequestor, Users userRequestee) {
		this.userRequestor = userRequestor;
		this.userRequestee = userRequestee;
	}
	
	public FriendRequest(boolean accepted_huh) {
		this.accepted_huh = accepted_huh;
	}

	public boolean isAccepted_huh() {
		return accepted_huh;
	}

	public void setAccepted_huh(boolean accepted_huh) {
		this.accepted_huh = accepted_huh;
	}

	public Users getUserRequestor() {
		return userRequestor;
	}

	public void setUserRequestor(Users userRequestor) {
		this.userRequestor = userRequestor;
	}

	public Users getUserRequestee() {
		return userRequestee;
	}

	public void setUserRequestee(Users userRequestee) {
		this.userRequestee = userRequestee;
	}	
	
	public int getFriendRequestId() {
		return friendRequestId;
	}

	public void setFriendRequestId(int friendRequestId) {
		this.friendRequestId = friendRequestId;
	}	
}
