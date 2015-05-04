/*
 * Used to represent a User in the system. Each user has an id, the version number they are using, 
 * and the group they belong to. Relations are kept track of by the User Graph.
 */
public class User {
	
	private String id;
	private int versionNumber;
	private int groupID;
	
	public User(String id, int groupID) {
		versionNumber = 0;
		this.id = id;
		this.groupID = groupID;
		
	}
	
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}
	
	public int getVersionNumber() {
		return versionNumber;
	}
	
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
	public int getGroupID() {
		return groupID;
	}
	
	public String getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return "[id: " + id + " groupid: " + groupID + " versionNumber: " + versionNumber + "]";
	}
	
}
