import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;


public class test {
	static UserGraph ug = new UserGraph();
	static int versionNumber = 0;
	
	@BeforeClass
	public static void oneTimeSetUp() {
		loadData(ug);
	}

	@Test
	public void testInfection() {
		HashSet<String> expectedInfected = new HashSet<String>();
		expectedInfected.add("bb");
		expectedInfected.add("cc");
		expectedInfected.add("dd");
		expectedInfected.add("ee");
		expectedInfected.add("ff");
		expectedInfected.add("ii");
		expectedInfected.add("mm");
		expectedInfected.add("jj");
		expectedInfected.add("kk");
		expectedInfected.add("ll");
		expectedInfected.add("nn");
		
		int previousVersion = ug.getUser("bb").getVersionNumber();
		assertEquals(expectedInfected, ug.infection("ff", ++versionNumber));
		assertNotEquals(previousVersion, ug.getUser("bb").getVersionNumber());
		
	}
	
	@Test 
	public void testInfectection_limited() {
		HashSet<String> affectedUsers = ug.infection_limited(++versionNumber, 45, 5);
		assertTrue(40 <= affectedUsers.size() && affectedUsers.size() <= 50);
		
	}
	
	@Test 
	public void testInfection_exact() {
		//there is a group size 17 so return 
		assertEquals(17, ug.infection_limited_exact(++versionNumber, 17).size());
		
		//Only 1 set of groups can return 12 users infected...5 & 7
		HashSet<String> users = ug.infection_limited_exact(++versionNumber, 12);
		for (String user : users) {
			assertEquals(versionNumber, ug.getUser(user).getVersionNumber());
		}
		assertEquals(12, users.size());
		
		//return 0 because there are only 100 users 
		assertEquals(0, ug.infection_limited_exact(++versionNumber , 101).size());
	}
	
	public static void loadData(UserGraph ug) {
		//total of 100 users 
		
		// 1
		ug.addUser("a");
		
		// 2 
		ug.addRelation("b", "c");
		
		// 3
		ug.addRelation("d", "e");
		ug.addRelation("d", "f");
		
		//5
		ug.addRelation("g", "h");
		ug.addRelation("h", "i");
		ug.addRelation("i", "j");
		ug.addRelation("i", "k");
		
		//7
		ug.addRelation("l", "m");
		ug.addRelation("l", "n");
		ug.addRelation("l", "o");
		ug.addRelation("l", "p");
		ug.addRelation("l", "q");
		ug.addRelation("l", "r");
		
		//9
		ug.addRelation("s", "t");
		ug.addRelation("s", "u");
		ug.addRelation("v", "u");
		ug.addRelation("v", "y");
		ug.addRelation("aa", "y");
		ug.addRelation("u", "x");
		ug.addRelation("u", "w");
		ug.addRelation("x", "z");
		
		//11
		ug.addRelation("bb", "cc");
		ug.addRelation("dd", "ee");
		ug.addRelation("cc", "ff");
		ug.addRelation("ee", "ff");
		ug.addRelation("ff", "ii");
		ug.addRelation("ff", "mm");
		ug.addRelation("ii", "jj");
		ug.addRelation("ii", "kk");
		ug.addRelation("ii", "ll");
		ug.addRelation("mm", "nn");
		
		//13
		ug.addRelation("oo", "pp");
		ug.addRelation("pp", "qq");
		ug.addRelation("pp", "rr");
		ug.addRelation("pp", "ss");
		ug.addRelation("qq", "tt");
		ug.addRelation("qq", "uu");
		ug.addRelation("qq", "vv");
		ug.addRelation("rr", "ww");
		ug.addRelation("rr", "xx");
		ug.addRelation("rr", "yy");
		ug.addRelation("ss", "zz");
		ug.addRelation("ss", "a3");

		//17
		ug.addRelation("b3", "c3");
		ug.addRelation("b3", "d3");
		ug.addRelation("c3", "e3");
		ug.addRelation("c3", "f3");
		ug.addRelation("d3", "g3");
		ug.addRelation("d3", "h3");
		ug.addRelation("e3", "i3");
		ug.addRelation("e3", "j3");
		ug.addRelation("f3", "k3");
		ug.addRelation("f3", "l3");
		ug.addRelation("g3", "m3");
		ug.addRelation("g3", "n3");
		ug.addRelation("h3", "o3");
		ug.addRelation("h3", "p3");
		ug.addRelation("l3", "q3");
		ug.addRelation("m3", "q3");
		ug.addRelation("n3", "r3");
		
		//19
		ug.addRelation("k4", "s3");
		ug.addRelation("k4", "t3");
		ug.addRelation("k4", "u3");
		ug.addRelation("k4", "v3");
		ug.addRelation("s3", "w3");
		ug.addRelation("s3", "x3");
		ug.addRelation("t3", "x3");
		ug.addRelation("t3", "y3");
		ug.addRelation("u3", "y3");
		ug.addRelation("u3", "z3");
		ug.addRelation("v3", "z3");
		ug.addRelation("v3", "a4");
		ug.addRelation("w3", "b4");
		ug.addRelation("x3", "b4");
		ug.addRelation("x3", "c4");
		ug.addRelation("y3", "c4");
		ug.addRelation("y3", "d4");
		ug.addRelation("z3", "d4");
		ug.addRelation("z3", "e4");
		ug.addRelation("a4", "e4");
		ug.addRelation("b4", "f4");
		ug.addRelation("b4", "g4");
		ug.addRelation("c4", "g4");
		ug.addRelation("c4", "h4");
		ug.addRelation("d4", "h4");
		ug.addRelation("d4", "i4");
		ug.addRelation("e4", "i4");
		ug.addRelation("e4", "j4");
		
		//13
		ug.addRelation("a5", "b5");
		ug.addRelation("a5", "c5");
		ug.addRelation("a5", "d5");
		ug.addRelation("a5", "e5");
		ug.addRelation("a5", "f5");
		ug.addRelation("a5", "g5");
		ug.addRelation("a5", "h5");
		ug.addRelation("a5", "i5");
		ug.addRelation("f5", "j5");
		ug.addRelation("f5", "k5");
		ug.addRelation("f5", "l5");
		ug.addRelation("f5", "m5");
	}

}
