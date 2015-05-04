import java.util.Collection;
import java.util.HashSet;

/*
 * Used to represent a set of connected users. Group is specified as an id and a set of userIds
 */

public class Group {
	//used to create group each time a group is created
	private static int idCounter = 0;
	
	private int id;
	private HashSet<String> users = new HashSet<String>();
	
	public Group(String userId) {
		this.id = idCounter++;
		users.add(userId);
	}
	
	public Group(Collection<String> userIds) {
		this.id = idCounter++;
		users.addAll(userIds);
	}
	
	public int getNumberOfUsers(){
		return users.size();
	}
	
	public HashSet<String> getUsers() {
		return users;
	}
	
	public int getId() {
		return id;
	}
	
	public void removeUser(String userId) {
		users.remove(userId);
	}
	
	@Override
	public String toString() {
		return "[id: " + id + " numberOfUsers: " + getNumberOfUsers() + " users: " + users + ']';
	}
}
