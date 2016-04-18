package Chop.model;

public class Rewards {
	protected boolean allowRewards;
	protected boolean shareRewards;
	protected int points;
	protected Users user;
	// This constructor can be used for reading records from MySQL, where we have all fields,
	// including the PostId.
	public Rewards (boolean allowRewards, boolean shareRewards, int points, Users user) {
		this.allowRewards = allowRewards;
		this.shareRewards = shareRewards;
		this.points = points;
		this.user = user;
	}
	public boolean isAllowRewards() {
		return allowRewards;
	}
	public void setAllowRewards(boolean allowRewards) {
		this.allowRewards = allowRewards;
	}
	public boolean isShareRewards() {
		return shareRewards;
	}
	public void setShareRewards(boolean shareRewards) {
		this.shareRewards = shareRewards;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
}