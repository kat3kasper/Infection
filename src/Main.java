
import java.util.HashSet;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		UserGraph userGraph = new UserGraph();
		
		System.out.println("Welcome to the infection Program");
		System.out.println("--------------------------------");
		System.out.println("Press a letter from the menu to run a task then press enter.");
		System.out.println();
		System.out.println("Menu: ");
		System.out.println("1 - Add a user");
		System.out.println("2 - Add a relation");
		System.out.println("3 - Remove a user");
		System.out.println("4 - Remove a relation");
		System.out.println("5 - Start an infection");
		System.out.println("6 - Start an limited infection");
		System.out.println("7 - Start an exact limited infection");
		System.out.println("");
		System.out.println("exit - to exit(only works when entering a task)");
		System.out.println("________________________________");
		
		
		Scanner scanner = new Scanner( System.in );	
		String input = scanner.nextLine();
		HashSet<String> users;
		while (!input.equals("exit")) {
			switch (input) {
			case "1":
				System.out.println("Enter a user: ");
				input = scanner.nextLine();
				userGraph.addUser(input);
				break;
			case "2":
				System.out.println("Enter the 1st user: ");
				String a = scanner.nextLine();
				System.out.println("Enter the 2nd user: ");
				String b = scanner.nextLine();
				userGraph.addRelation(a, b);
				break;
			case "3":
				System.out.println("Enter a user: ");
				input = scanner.nextLine();
				userGraph.removeUser(input);
				break;
			case "4":
				System.out.println("Enter the 1st user: ");
				a = scanner.nextLine();
				System.out.println("Enter the 2nd user: ");
				b = scanner.nextLine();
				userGraph.removeRelation(a, b);
				break;
			case "5":
				System.out.println("Enter the user you wish to infection:");
				a = scanner.nextLine();
				System.out.println("Enter the version number you wish to infect them with");
				int version = scanner.nextInt();
				users = userGraph.infection(a, version);
				System.out.println("Infected Users: "+ users);
				System.out.println("Number of infected Users: " + users.size());
				break;
			case "6":
				System.out.println("Enter the version number: ");
				version = scanner.nextInt();
				System.out.println("Enter the percent of users you would like to infect: ");
				int percent = scanner.nextInt();
				System.out.println("Enter the percent you wish to use as a buffer: ");
				int buffer  = scanner.nextInt();
				users = userGraph.infection_limited(version, percent, buffer);
				System.out.println("Infected Users: "+ users);
				System.out.println("Number of infected Users: " + users.size());
				break;
			case "7":
				System.out.println("Enter the version number: ");
				version = scanner.nextInt();
				System.out.println("Enter the number of users you would like to infect: ");
				int number = scanner.nextInt();
				users = userGraph.infection_limited_exact(version, number);
				System.out.println("Infected Users: "+ users);
				System.out.println("Number of infected Users: " + users.size());
				break;		
			}
			System.out.println("Press a option from the menu or 'exit' : ");
			input = scanner.nextLine();
		}
		scanner.close();
		
	}

}
