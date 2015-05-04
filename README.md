Contents:
<ul>
<li>Infection.jar - jar file that runs program</li>
<li>src/ - source code</li>
<li>bin/ - class files</li>
<li>users.jpg - picture of users that are used in test.java</li>
</ul>

To Run:
<ol>
<li>Need java</li>
<li>Download Infection.jar</li>
<li>$ java -cp Infection.jar Main</li>
</ol>

<p>Definition:</p>
group - refers to a set of connected users within the user graph

<p>I completed infection, infection_limited, and infection_exact. I chose to implement infection_limited by allowing the user to enter a percent of users they would like to infect along with a buffer percent that they will find acceptable.</p>

<p>This problem screams graph so my first though was to user a graph to store the users and their relations. Infection would be simple all you would have to do is start at the user and user DFS/BFS to infect all its connected users. The tricky part is the limited infections. I didn't want to have to go through the entire graph every single time a limited infection was to take place. I assumed that the graph was relatively stable and although users can easily be added and removed throught the project that wouldn't affect every group in the graph. There was a note on the instructions that limited infection for exact users could be slow, but I didn't think it had to be. If we kept track of the right data it could be relatively fast!</p>

<p>I decided to keep track of the user groups in the graph so that I could access them quickly. The group had a list of all the userIds' in that group. The easiest way to do this was build a wrapper graph around an already made graph. I chose JGraphT. It was simple enough to work with the undirected graph and had a DFS iterator (perfect!) I wanted to focused great design and algoritms for the problem not rewritting a graph. I stored a few things in the UserGraph wrapper: a list of all the users store in the graph keyed by name, a list of all groups store in the graph keyed by id, the graph itself, and a set of modified users. This is where the magic happened. </p>

<p>Keeping track of the modified users allowed me to update only certain groups in the graph at a time. Before any infection operation is done, the list of modified users is handled. A modified user is a user who's group has been modified in some way. I iterate through the modified users, create a new group for the user and its connected counterparts, and update the users to now be a part of the new group. This allows me to not have to worry about what groups they were in before and trying to combine them or split them. </p>

<p>If an infection happens and the groups have already been updated, no graph traversal needs to be done!! With a regular infection each user stores the group it is container in. So given a user to infect, all that needs to be done is to find the user's group and iterate though all users in it to infect them. Infection_limited and infection_exact are very similar in the back end with limited allowing a little more leniency, but again NO GRAPH TRAVERAL. The total number of users in each group is already easily accessible. Using these numbers it just about solving a sum problem (2^number of groups) </p>

<p>The instructions kind of hinted that maybe I should be using traversals the whole time, but with a minimal extra storage (groupid, listOfUserIds) the speed up for infections and limited infections can be incredible. </p>

<p>Reference:</p>
<a href="http://jgrapht.org/">JGrapht</a> 

