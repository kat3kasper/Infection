import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;


public class UserGraph {
	
	//Graph that stores user connections
	private SimpleGraph<String,DefaultEdge> userGraph = new SimpleGraph<String,DefaultEdge>(DefaultEdge.class);
	
	// Set to keep track of users (vertices) that have been modified in the graph
	private HashSet<String> modifiedUserIds = new HashSet<String>();
	
	//User store maps userId<String> to a User<User>
	private HashMap<String, User> userDB = new HashMap<String, User>();
	
	//Group store maps groupId<Integer> to a Group<Group>
	//Used to keep track of the connected user groups within the graph
	private HashMap<Integer, Group> groupDB = new HashMap<Integer, Group>();
	
	
	/*
	 * Allows a user to be added to the system.
	 */
	public void addUser(String a) {
		if (a != null) {
			if (userGraph.addVertex(a)) {
				addUserToDB(a);
			}
		}
	}
	
	/*
	 * Allows a user to be added to the system. 
	 * User A coaches User B. A -> B
	 * User b can be null if you wish to add a single user with no relation
	 */
	public void addRelation(String a, String b) {
		if (a != null) {
			boolean isANew = userGraph.addVertex(a);
			if (isANew) {
				addUserToDB(a);
				//if new user has been added we add it to the modified list
				modifiedUserIds.add(a);
			}
			if (b != null) {
				boolean isBNew = userGraph.addVertex(b);
				if (isBNew) {
					addUserToDB(b);
				}

				//addEdge returns null if the edge from a to b already exists
				//Only need to add a to modified list if the edge didn't already exist
				if (userGraph.addEdge(a, b) != null) {
					modifiedUserIds.add(a);
				}
			}
		}		
	}
	
	private void addUserToDB(String id) {
		Group group = new Group(id);
		User user = new User(id, group.getId());
		userDB.put(user.getID(), user);
		groupDB.put(group.getId(), group);
	}
	
	/*
	 * Removes a user from the system.
	 */
	public void removeUser(String userId) {
		if ( userId != null && userGraph.containsVertex(userId)) {
			return;
		}
		
		/*
		 * Loop through all edges that will be affected by the user removal. Get the vertex 
		 * that isn't being removed and add it to the modifiedUsers set. 
		 */	
		Set<DefaultEdge> affectedEdges = userGraph.edgesOf(userId);
		for(DefaultEdge affectedEdge : affectedEdges) {
			String sourceUser = userGraph.getEdgeSource(affectedEdge);
			String targetUser = userGraph.getEdgeTarget(affectedEdge);
			if(userId.equals(sourceUser)){
				modifiedUserIds.add(targetUser);
			} else {
				modifiedUserIds.add(sourceUser);
			}
		}
		
		User user = userDB.get(userId);
		Group usersGroup = groupDB.get(user.getGroupID());
		usersGroup.removeUser(userId); //remover user from user list in group
		
		//if there are no more users in the group remove it from the database
		if(usersGroup.getNumberOfUsers() == 0) {
			groupDB.remove(usersGroup.getId());
		}
		
		userDB.remove(userId);
		userGraph.removeVertex(userId);
		modifiedUserIds.remove(userId);
	}
	
	/*
	 * Removes a relation between user a and user b
	 */
	public void removeRelation(String a, String b) {
		userGraph.removeEdge(a, b);
		modifiedUserIds.add(a);
		modifiedUserIds.add(b);
	}
	
	/*
	 * Given a specified user, returns all users connected to it.
	 */
	private HashSet<String> dfs(String userId) {
		HashSet<String> users = new HashSet<String>();
		
		/*
		 * Creates a new depth-first iterator for the specified graph. 
		 * Iteration will start at the specified start vertex and will be limited 
		 * to the connected component that includes that vertex.
		 */
		DepthFirstIterator<String,DefaultEdge> iterator = new DepthFirstIterator<String,DefaultEdge>(userGraph,userId);
		while (iterator.hasNext()) {
			users.add(iterator.next());
		}
		
		return users;
	}
	
	/*
	 * Goes through all the modifiedUsers and updates their group along with the users in that group
	 */
	private void updateGroups() {
		//list of all users that have already been correctly modified
		HashSet<String> updatedUserIds = new HashSet<String>(); 
		
		//iterate through all modified users to updated their connected count
		for(String modifiedUserId : modifiedUserIds) {
			//check to see if their connected segment has already been recounted
			if(!updatedUserIds.contains(modifiedUserId)) {
				HashSet<String> connectedUserIds = dfs(modifiedUserId);
				
				Group newGroup = new Group(connectedUserIds);
				groupDB.put(newGroup.getId(), newGroup);
				
				//Iterate through all the users in a group (connected) remove 
				//the old group associated with the user and replace with the 
				//new group that contains all the user. 
				for(String connectedUserId : connectedUserIds) {
					User connectedUser = userDB.get(connectedUserId);
					groupDB.remove(connectedUser.getGroupID());
					connectedUser.setGroupID(newGroup.getId());
				}
				updatedUserIds.addAll(connectedUserIds);
			}
		}
		modifiedUserIds.clear();
	}
	
	/*
	 * Given a specified user infects all users connected to is with the version number given.
	 * Returns the set of all affected users. 
	 */
	public HashSet<String> infection(String user, int versionNumber) {
		//if users have been modified update the groups
		if (!modifiedUserIds.isEmpty()) {
			updateGroups();
		}
		if (userDB.get(user) == null) {
			return new HashSet<String>();
		}
		HashSet<String> affectedUserIds = groupDB.get(userDB.get(user).getGroupID()).getUsers();
		for (String affectedUserId : affectedUserIds) {
			userDB.get(affectedUserId).setVersionNumber(versionNumber);
		}
		return affectedUserIds;
	}
	
	/*
	 * Infects a limited number of user. Provide a percent of users you wish to affect and a 
	 * buffer percentage that you are will to affect.
	 * 
	 * ex 10% with 2% buffer will affect anywhere from ~8% to ~12% of users. 
	 * 
	 * returns a list of users infected
	 */
	public HashSet<String> infection_limited(int versionNumber, int percentOfUsers, int bufferPercent) {
		
		int totalUsers = userDB.size();
		return infection_limited_internal(versionNumber, totalUsers*percentOfUsers/100, totalUsers*bufferPercent/100);
	}
	
	/*
	 * Infects an exactly number of users if possible.
	 * 
	 * returns a list of users infected
	 */
	public HashSet<String> infection_limited_exact(int versionNumber, int numberOfUsers) {
		return infection_limited_internal(versionNumber, numberOfUsers, 0);
	}
	
	/*
	 * internal method for infecting users
	 */
	private HashSet<String> infection_limited_internal(int versionNumber, int affectedUsers, int userBuffer) {
		
		//if users have been modified update the groups
		if (!modifiedUserIds.isEmpty()) {
			updateGroups();
		}

		HashSet<String> affectedUserIds = new HashSet<String>();
		
		//Gets a subset of users that will equal the number of affected user 
		//by figuring out the groups necessary to equal the sum+/-buffer of the given affectUsers
		Integer[] arr = groupDB.keySet().toArray(new Integer[0]);
		ArrayList<Integer> affectedGroups = 
				isSubsetSum(arr, arr.length, affectedUsers, new ArrayList<Integer>(), userBuffer);
		
		//iterate through the groups and infect users
		for (Integer affectedGroup : affectedGroups) {
			for(String user : groupDB.get(affectedGroup).getUsers()) {
				userDB.get(user).setVersionNumber(versionNumber);
				affectedUserIds.add(user);
			}
		}
		return affectedUserIds;
	}
	
	/*
	 * Returns an list of groups if there is a set where all combined users equal the given sum
	 * 
	 * set is the originally set of group ids
	 * n is the length of the set currently being checked
	 * sum is the current sum being checked for
	 * items is the list of items that is part of the sum
	 */
	public ArrayList<Integer> isSubsetSum(Integer set[], int n, int sum, ArrayList<Integer> items, int userBuffer)
	{
		//check if sum is between user buffer inclusively
	   if (sum <= userBuffer && sum >= (0 - userBuffer)) {
	     return items; 
	   }
	   if (n == 0 && sum != 0) 
	     return new ArrayList<Integer>();
	   
	   int connectedCount = groupDB.get(set[n-1]).getNumberOfUsers();
	 
	   //if last group's user count is bigger than sum and buffer ignore
	   if (connectedCount > sum + userBuffer)
	     return isSubsetSum(set, n-1, sum, items, userBuffer);
	   
	   //check if the sum can be found by excluding the last group or including the last group
	   ArrayList<Integer> l1 = isSubsetSum(set, n-1, sum, items, userBuffer);
	   if(!l1.isEmpty()) {
		   return l1;
	   }
	   
	   //else check if sum can be found by included the last group
	   ArrayList<Integer> updatedItems = new ArrayList<Integer>(items);
	   updatedItems.add(set[n-1]);
	   ArrayList<Integer> l2 = isSubsetSum(set, n-1, sum-connectedCount, updatedItems, userBuffer);
	   return l2; 
	}
	
	//Used for testing
	protected User getUser(String id) {
		return userDB.get(id);
	}
	
}
