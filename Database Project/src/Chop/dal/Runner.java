package Chop.dal;

import Chop.model.*;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * main() runner, used for the app demo.
 * 
 * Instructions: 1. Create a new MySQL schema and then run the CREATE TABLE
 * statements from lecture: http://goo.gl/86a11H. 2. Update ConnectionManager
 * with the correct user, password, and schema.
 */
public class Runner {

	public static void main(String[] args) throws SQLException {
		// DAO instances.
		PersonsDao personsDao = PersonsDao.getInstance();
		AdministratorsDao administratorsDao = AdministratorsDao.getInstance();
		UsersDao usersDao = UsersDao.getInstance();
		CalendarDao calendarDao = CalendarDao.getInstance();
		CategoryDao categoryDao = CategoryDao.getInstance();
		AnalyticsFeedbackDao analyticsfeedbackDao = AnalyticsFeedbackDao.getInstance();
		RewardsDao rewardsDao = RewardsDao.getInstance();
		ItemsDao itemsDao = ItemsDao.getInstance();
		EventDao eventDao = EventDao.getInstance();
		TaskDao taskDao = TaskDao.getInstance();
		CommentsDao commentsDao = CommentsDao.getInstance();
		FriendRequestDao friendRequestDao = FriendRequestDao.getInstance();
		
		Date dateNow = new Date();
		Timestamp timeNow = new Timestamp(dateNow.getTime());
		Blob nullBlob = null;

		// PERSONS
		// CREATE PERSONS
		Persons alex = new Persons("alex.kon.09@gmail.com", "Password", "Alexander", "Kon",
				"alex.kon.09@gmail.com");
		Persons Ghost_1 = new Persons("Ghost_1", "boo", "ghost", "Wong",
				"wong.all@husky.neu.edu");
		Persons deletePerson = new Persons("delete_this", "boo", "ghost", "Wong",
				"wong.all@husky.neu.edu");
		alex = personsDao.create(alex);
		Ghost_1 = personsDao.create(Ghost_1);
		deletePerson = personsDao.create(deletePerson);

		// UPDATE PERSONS
		Ghost_1 = personsDao.updatePerson(Ghost_1, "newpassword", "newfirst", "newlast", "ghost@gmail.com");
		System.out.format("Updating %s : password: %s first: %s last: %s email: %s \n",
				Ghost_1.getUserName(), Ghost_1.getPassword(), Ghost_1.getFirstName(), Ghost_1.getLastName(), Ghost_1.getEmail());
		
		// GET PERSONS
		Persons p1 = personsDao.getPersonByUserName("alex.kon.09@gmail.com");
		System.out.format(
				"Reading Person: username:%s firstname:%s lastname:%s \n",
				p1.getUserName(), p1.getFirstName(), p1.getLastName());

		// DELETE PERSONS
//		System.out.format("Deleting Person: %s\n", deletePerson.userName);
		personsDao.delete(deletePerson);
		
		// ADMINS
		System.out.println();
		// CREATE ADMINS
		Administrators kaleigh = new Administrators("kaleigh.ohara@gmail.com", "Password", "Kaleigh", "OHara",
				"kaleigh.ohara@gmail.com", dateNow);
		Administrators kelly = new Administrators("xinwei.wang@hotmail.com", "Password", "Kelly", "Wang",
				"xinwei.wang@hotmail.com", dateNow);
		Administrators allen = new Administrators("wong.all@husky.neu.edu", "Password", "Allen", "Wong",
				"wong.all@husky.neu.edu", dateNow);
		Administrators deleteAdmin = new Administrators("delete_me", "Password", "Allen", "Wong",
				"wong.all@husky.neu.edu", dateNow);
		kaleigh = administratorsDao.create(kaleigh);
		kelly = administratorsDao.create(kelly);
		allen = administratorsDao.create(allen);
		deleteAdmin = administratorsDao.create(deleteAdmin);
		
		// UPDATE ADMINS
		allen = administratorsDao.updatePerson(allen, "newpassword", "newAllen", "newWong", "allen@gmail.com");
		System.out.format("Updating %s : password: %s first: %s last: %s email: %s \n",
				allen.getUserName(), allen.getPassword(), allen.getFirstName(), allen.getLastName(), allen.getEmail());
		
		// GET ADMINS
		Administrators a1 = administratorsDao.getAdminByUserName("kaleigh.ohara@gmail.com");
		System.out.format(
				"Reading Admin: username:%s firstname:%s lastname:%s \n",
				a1.getUserName(), a1.getFirstName(), a1.getLastName());
		
		// DELETE ADMINS
//		System.out.format("Deleting Admin: %s\n", deleteAdmin.userName);
		administratorsDao.delete(deleteAdmin);
		

		// USERS
		System.out.println();
		// CREATE USERS
		Users brian = new Users("bn_gillespie@hotmail.com", "Password", "Brian", "G",
				"bn_gillespie@hotmail.com", "picture", "Hi, my name is Brian Gillespie", 24, timeNow, "male");
		Users Ghost_2 = new Users("Ghost_2", "boo", "ghost", "ghost",
				"ghost@husky.neu.edu", "ghost-picture", "Hi, my name is Ghost_2", 999, timeNow, "giraffe");
		Users Ghost_3 = new Users("Ghost_3", "passboo", "ghost3", "ghost3",
				"ghost@husky.neu.edu", "ghost-picture", "Hi, my name is Ghost_2", 9999, timeNow, "hippo");
		Users deleteUser = new Users("deleteUser", "del", "del", "del",
				"del@husky.neu.edu", "ghost-picture", "Hi, my name is gone", 999, timeNow, "bye");
		brian = usersDao.create(brian);
		Ghost_2 = usersDao.create(Ghost_2);
		Ghost_3 = usersDao.create(Ghost_3);
		deleteUser = usersDao.create(deleteUser);
		
		// UPDATE USERS
		Ghost_2 = usersDao.updateUsers(Ghost_2, "newPic", "I am BOO", 1000, "elephant");
		System.out.format("Updating %s : profilepic: %s description: %s age: %s gender: %s \n",
				Ghost_2.getUserName(), Ghost_2.getProfilePicture(), Ghost_2.getAbout(), Ghost_2.getAge(), Ghost_2.getGender());
		
		Ghost_3 = usersDao.updateAboutUser(Ghost_3, "all about me");
		System.out.format("Updating About : user: %s about: %s \n ",
				Ghost_3.getUserName(), Ghost_3.getAbout());
		
		// GET USERS
		Users u1 = usersDao.getUserFromUserName("bn_gillespie@hotmail.com");
		System.out.format(
				"Reading User: username:%s firstname:%s lastname:%s \n",
				u1.getUserName(), u1.getFirstName(), u1.getLastName());
		
		// DELETE USERS
//		System.out.format("Deleting User: %s\n", deleteUser.userName);
		usersDao.delete(deleteUser);
		
		// CALENDAR
		System.out.println();
		// CREATE CALENDAR
		Calendar brian_calendar = new Calendar(dateNow, "background", false, brian); 
		Calendar Ghost2_calendar = new Calendar(dateNow, "background", false, Ghost_2); 
		Calendar delete_calendar = new Calendar(dateNow, "background", false, Ghost_3);
		brian_calendar = calendarDao.create(brian_calendar);
		Ghost2_calendar = calendarDao.create(Ghost2_calendar);
		delete_calendar = calendarDao.create(delete_calendar);
		
		// UPDATE CALENDAR
		brian_calendar = calendarDao.updateCalendarIsPublic(brian_calendar, true);
		System.out.format("Updating %s : is-Public?: %s \n",
				brian_calendar.getUsers().getUserName(), brian_calendar.getIsPublic_huh());
		
		brian_calendar = calendarDao.updateCalendarBackground(brian_calendar, "newbackground");
		System.out.format("Updating %s : is-Public?: %s \n",
				brian_calendar.getUsers().getUserName(), brian_calendar.getCalendarBackground());
				
		// GET CALENDAR
		Calendar c1 = calendarDao.getCalendarForUser("Ghost_2");
		System.out.format(
				"Reading Calendar: username:%s datecreated:%s \n",
				c1.getUsers().getUserName(), c1.getDateCreated());
				
		// DELETE CALENDAR
		System.out.format("Deleting Calendar: %s\n", delete_calendar.getUsers().getUserName());
		calendarDao.delete(delete_calendar);
		
		// CATEGORY
		System.out.println();
		// CREATE CATEGORY
		Category brian_school = new Category("school", false, brian);
		Category brian_work = new Category("work", false, brian);
		Category brian_others = new Category("other", false, brian);
		Category brian_social_delete = new Category("social", false, brian);
		brian_school = categoryDao.create(brian_school);
		brian_work = categoryDao.create(brian_work);
		brian_others = categoryDao.create(brian_others);
		brian_social_delete = categoryDao.create(brian_social_delete);
		
		// UPDATE CATEGORY
		brian_school = categoryDao.updateShareCategory(brian_school, true);
		System.out.format("Updating %s : Category is-Public?: %s \n",
				brian_school.getUsers().getUserName(), brian_school.getShareCategory_huh());
		
		// GET CATEGORY
		Category cat1 = categoryDao.getCategoryByCategoryId(1);
		System.out.format(
				"Reading Category: username:%s Category Name:%s Category Share Status:%s\n",
				cat1.getUsers().getUserName(), cat1.getCategoryName(), cat1.getShareCategory_huh());
		
		List<Category> listCat1 = categoryDao.getCategoriesForUser("bn_gillespie@hotmail.com");
		System.out.println(listCat1);
		
		// DELETE CATEGORY
		System.out.format("Deleting Category: %s\n", brian_social_delete.getCategoryName());
		categoryDao.delete(brian_social_delete);
		
		// AnalyticsFeedback
		System.out.println();
		// CREATE AnalyticsFeedback
		AnalyticsFeedback analyticsfeedback = new AnalyticsFeedback("feedback", true, 0.61, 30,"chart", brian);
		AnalyticsFeedback deleteAnalyticsfeedback = new AnalyticsFeedback("feedback1", true, 0.61, 30,"chart", Ghost_2);

		analyticsfeedback = analyticsfeedbackDao.create(analyticsfeedback);
		deleteAnalyticsfeedback = analyticsfeedbackDao.create(deleteAnalyticsfeedback);
				
		// UPDATE AnalyticsFeedback
		analyticsfeedback = analyticsfeedbackDao.updateAnalyticsFeedback(analyticsfeedback,"feedback2", false, 0.71, 40, "newchart");
		System.out.format("Updating AnalyticsFeedback %s : title: %s allowAnalytics: %s completionPercentage: %s usage: %s \n chart: %s \n ",
				analyticsfeedback.getUser().getUserName(),analyticsfeedback.getTitle(), analyticsfeedback.isAllowAnalytics(), analyticsfeedback.getCompletionPercentage(), analyticsfeedback.getHowMuchUsed(), analyticsfeedback.getChart());
				
		// GET AnalyticsFeedback
		AnalyticsFeedback ana1 = analyticsfeedbackDao.getAnalyticsFeedbackByUserName("bn_gillespie@hotmail.com");
		System.out.format("Reading AnalyticsFeedback: title:%s allowAnalytics :%s Completion Percentage:%s HowMuchUsed:%s Chart:%s UserName:%s \n",
				ana1.getTitle(), ana1.isAllowAnalytics(), ana1.getCompletionPercentage(), ana1.getHowMuchUsed(),ana1.getChart(),ana1.getUser().getUserName());
		
		// DELETE AnalyticsFeedback
//		System.out.format("Deleting Analyticsfeedback: %s\n", deleteAnalyticsfeedback.user);
		analyticsfeedbackDao.delete(deleteAnalyticsfeedback);
				
		// REWARDS
		System.out.println();
		// CREATE REWARDS
		Rewards rewards = new  Rewards(true, true, 20, brian);
		Rewards deleteRewards = new Rewards(true, true, 20, Ghost_2);

		rewards = rewardsDao.create(rewards);
		deleteRewards = rewardsDao.create(deleteRewards);
				
		// UPDATE REWARDS
		rewards = rewardsDao.updateRewards(rewards,false, true, 20);
		System.out.format("Updating Rewards %s : allowRewards: %s shareRewards: %s point: %s \n ",
				rewards.getUser().getUserName(),rewards.isAllowRewards(), rewards.isShareRewards(),rewards.getPoints());
		
		// GET Rewards
		Rewards reward = rewardsDao.getRewardsByUserName("bn_gillespie@hotmail.com");
		System.out.format("Reading Rewards:  allowRewards :%s shareRewards:%s points:%s UserName:%s \n",
				reward.isAllowRewards(), reward.isShareRewards(), reward.getPoints(),reward.getUser().getUserName());
		
		// DELETE REWARDS
//		System.out.format("Deleting Rewards: %s\n", deleteRewards.user);
		rewardsDao.delete(deleteRewards);
		
		// ITEMS
		System.out.println();
		// CREATE ITEMS
		Items item1 = new Items("PDP", true, dateNow, brian, brian_school);
		Items item2 = new Items("Database", true, dateNow, brian, brian_school);
		Items item3 = new Items("Indoor maps", true, dateNow, brian, brian_work);
		Items item4 = new Items("Google meeting", true, dateNow, brian, brian_work);
		Items item5 = new Items("date night", true, dateNow, brian, brian_others);
		Items item_delete = new Items("delete this", true, dateNow, brian, brian_others);
		item1 = itemsDao.create(item1);
		item2 = itemsDao.create(item2);
		item3 = itemsDao.create(item3);
		item4 = itemsDao.create(item4);
		item5 = itemsDao.create(item5);
		item_delete = itemsDao.create(item_delete);
		
		// UPDATE ITEMS
		item5 = itemsDao.updateShareItem(item5, false);
		System.out.format("Updating ItemShared %s : Item is-Public?: %s \n",
				item5.getItemId(), item5.getShareItems_huh());
		
		item4 = itemsDao.updateItemDescription(item4, "sleep");
		System.out.format("Updating ItemDescription: Item %s : ItemDescription: %s \n",
				item4.getItemId(), item4.getDescription());
		
		// GET ITEMS
		Items i1 = itemsDao.getItemByItemId(1);
		System.out.format(
				"Reading Item: username:%s Description Name:%s Category :%s\n",
				i1.getUser().getUserName(), i1.getDescription(), i1.getCategory().getCategoryName());
		
		List<Items> listItem1 = itemsDao.getItemsByUserName("bn_gillespie@hotmail.com");
		System.out.println(listItem1);
		
		// DELETE ITEMS
		System.out.format("Deleting Items : %s, Description: %s \n ", 
				item_delete.getItemId(), item_delete.getDescription());
		itemsDao.delete(item_delete);
		
		// EVENTS
		System.out.println();
		// CREATE EVENTS
		Event event1 = new Event("web-dev", true, new Date(112, 1, 1), brian, brian_school, new Date(112, 2, 5), new Date(12, 2, 6));
		Event event2 = new Event("kick-ball", true, new Date(112, 1, 19), brian, brian_others, new Date(112, 2, 5), new Date(12, 2, 6));
		Event event3 = new Event("Drag Show", true, new Date(112, 1, 19), brian, brian_others, new Date(112, 2, 5), new Date(12, 2, 6));
		Event event4 = new Event("registration", true, new Date(112, 1, 19), brian, brian_work, new Date(112, 2, 5), new Date(12, 2, 6));
		Event event5 = new Event("Lunch with Bob", true, new Date(112, 1, 19), brian, brian_school, new Date(112, 2, 5), new Date(12, 2, 6));
		Event deleteEvent = new Event("Delete This", true, new Date(112, 1, 21), brian, brian_school, new Date(112, 2, 5), new Date(12, 2, 6));
		event1 = eventDao.create(event1);
		event2 = eventDao.create(event2);
		event3 = eventDao.create(event3);
		event4 = eventDao.create(event4);
		event5 = eventDao.create(event5);
		deleteEvent = eventDao.create(deleteEvent);
		
		// UPDATE EVENTS
		event3 = eventDao.updateStartDate(event3, dateNow);
		System.out.format("Updating Event StartDate: Event %s : StartDate: %s \n",
				event3.getItemId(), event3.getStartDate());
		
		event4 = eventDao.updateEndDate(event4, dateNow);
		System.out.format("Updating Event EndDate: Event %s : EndDate: %s \n",
				event4.getItemId(), event4.getEndDate());
		
		// GET EVENTS
		Event ev1 = eventDao.getEventByItemId(7);
		System.out.format(
				"Reading Event: username:%s Description Name:%s Start Date :%s End Date :%s \n",
				ev1.getUser().getUserName(), ev1.getDescription(), ev1.getStartDate(), ev1.getEndDate());
		
		List<Event> listEvent1 = eventDao.getEventsByUserName("bn_gillespie@hotmail.com");
		System.out.println(listEvent1);
		
		// DELETE EVENTS
		System.out.format("Deleting Event : %s, Description: %s \n ", 
				deleteEvent.getItemId(), deleteEvent.getDescription());
		eventDao.delete(deleteEvent);
		
		// TASK
		System.out.println();
		// CREATE TASK
		Task task1 = new Task("HW1", true, new Date(112, 1, 1), brian, brian_school, false, new Date(112, 2, 5));
		Task task2 = new Task("HW2", true, new Date(112, 1, 19), brian, brian_school, false, new Date(112, 2, 5));
		Task task3 = new Task("HW3", true, new Date(112, 1, 19), brian, brian_school, false, new Date(112, 2, 5));
		Task task4 = new Task("HW4", true, new Date(112, 1, 19), brian, brian_school, false, new Date(112, 2, 5));
		Task task5 = new Task("HW5", true, new Date(112, 1, 19), brian, brian_school, false, new Date(112, 2, 5));
		Task deleteTask = new Task("Delete This", true, new Date(112, 1, 21), brian, brian_school, false, new Date(112, 2, 5));
		task1 = taskDao.create(task1);
		task2 = taskDao.create(task2);
		task3 = taskDao.create(task3);
		task4 = taskDao.create(task4);
		task4 = taskDao.create(task4);
		deleteTask = taskDao.create(deleteTask);
		
		// UPDATE TASK
		task4 = taskDao.updateAccomplishedTask(task4, false);
		System.out.format("Updating Accomplished Task %s : Task is-Accomplished?: %s \n",
				task4.getItemId(), task4.getAccomplished_huh());
		
		task3 = taskDao.updateDueDate(task3, dateNow);
		System.out.format("Updating Task DueDate: Task %s : DueDate: %s \n",
				task3.getItemId(), task3.getDueDate());
		
		// GET TASK
		Task t1 = taskDao.getTaskByItemId(15);
		System.out.format(
				"Reading Task: username:%s Description Name:%s Accomplished? :%s Due Date :%s \n",
				t1.getUser().getUserName(), t1.getDescription(), t1.getAccomplished_huh(), t1.getDueDate());
		
		List<Task> listTask1 = taskDao.getTasksByUserName("bn_gillespie@hotmail.com");
		System.out.println(listTask1);
		
		// DELETE TASK
		System.out.format("Deleting Task : %s, Description: %s \n ", 
				deleteTask.getItemId(), deleteTask.getDescription());
		taskDao.delete(deleteTask);
		
		// COMMENTS
		System.out.println();
		// CREATE COMMENTS
		Comments comment1 = new Comments("boo", dateNow, Ghost_2, item1);
		Comments comment2 = new Comments("boo", dateNow, Ghost_2, item2);
		Comments comment3 = new Comments("boo", dateNow, Ghost_2, item3);
		Comments commentDelete = new Comments("Delete This", dateNow, Ghost_2, item1);
		comment1 = commentsDao.create(comment1);
		comment2 = commentsDao.create(comment2);
		comment3 = commentsDao.create(comment3);
		commentDelete = commentsDao.create(commentDelete);
		
		// UPDATE COMMENTS
		comment1 = commentsDao.updateContent(comment1, "boo boo boo!!!");
		System.out.format("Updating Comment : commentor: %s item: %s updated Comment: %s \n ",
				comment1.getUser().getUserName(), comment1.getItem().getDescription(), comment1.getContent());
		
		// GET COMMENTS
		Comments com1 = commentsDao.getCommentByCommentId(1);
		System.out.format(
				"Reading Comment: username:%s Content:%s Item :%s\n",
				com1.getUser().getUserName(), com1.getContent(), com1.getItem());
		
		List<Comments> listCommentsGhost2 = commentsDao.getCommentsForUser("Ghost_2");
		System.out.println(listCommentsGhost2);
		
		List<Comments> listCommentsItems1 = commentsDao.getCommentsForItem(1);
		System.out.println(listCommentsItems1);
		
		
		// DELETE COMMENTS
		System.out.format("Deleting Comment, commentor: %s, Description: %s \n ", 
				commentDelete.getUser().getUserName(), commentDelete.getContent());
		commentsDao.delete(commentDelete);
		
		// FRIENDREQUEST
		System.out.println();
		// CREATE FRIENDREQUEST
		FriendRequest fr1 = new FriendRequest(brian, Ghost_2);
		fr1 = friendRequestDao.create(fr1);
		
		// UPDATE FRIENDREQUEST
		fr1 = friendRequestDao.updateAccepted(fr1, true);
		System.out.format("Brian and Ghost_2 are now friends : Requestor: %s Requestee: %s Friends?: %s \n ",
				fr1.getUserRequestor().getUserName(), fr1.getUserRequestee().getUserName(), fr1.isAccepted_huh());
		
		// GET FRIENDREQUEST
		
		// DELETE FRIENDREQUEST

	}

}
