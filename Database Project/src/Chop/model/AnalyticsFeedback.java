package Chop.model;

public class AnalyticsFeedback {
	protected String title;
	protected boolean allowAnalytics;
	protected double completionPercentage;
	protected int howMuchUsed;
	protected String chart;
	protected Users user;
	
	// This constructor can be used for reading records from MySQL, where we have all fields,
	// including the PostId.
	public AnalyticsFeedback (String title, boolean allowAnalytics, double completionPercentage, int howMuchUsed,
			String chart, Users user) {
		this.title = title;
		this.allowAnalytics = allowAnalytics;
		this.completionPercentage = completionPercentage;
		this.howMuchUsed = howMuchUsed;
		this.chart = chart;
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isAllowAnalytics() {
		return allowAnalytics;
	}

	public void setAllowAnalytics(boolean allowAnalytics) {
		this.allowAnalytics = allowAnalytics;
	}

	public double getCompletionPercentage() {
		return completionPercentage;
	}

	public void setCompletionPercentage(double completionPercentage) {
		this.completionPercentage = completionPercentage;
	}

	public int getHowMuchUsed() {
		return howMuchUsed;
	}

	public void setHowMuchUsed(int howMuchUsed) {
		this.howMuchUsed = howMuchUsed;
	}

	public String getChart() {
		return chart;
	}

	public void setChart(String chart) {
		this.chart = chart;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}