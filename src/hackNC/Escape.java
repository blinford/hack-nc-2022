package hackNC;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Escape {
	
	// enum to keep track of commands
	private enum Command {

		HELP("help - lists commands"),
		NEXT("next - lists next locations"),
		LOOK("look - looks around at current location"),
		INV("inventory - shows contents of the inventory"),
		GET("get (item) - removes item from current room and places it in your inventory"),
		GO("go (location) - moves to specified location");
		
		String str;
		
		Command(String str) {
			this.str = str;
		}
	}

	// the current node
	Node node;
	boolean newNode;
	
	// runs the game
	public void run() {
		
		init();
	
		Scanner scan = new Scanner(System.in);
		String[] input;
		
		boolean running = true;
		newNode = true;
		
		while(running) {
			
			if(newNode) {
				
				System.out.println(node.desc);
				newNode = false;
			}
			
			input = scan.nextLine().split(" ", 2);
			
			if(commandMap.get(input[0]) == null) {
				
				System.out.println("command not found");
			}
			else {
				
				switch(commandMap.get(input[0])) {
				
				case NEXT:
					runNext();
					break;
				case LOOK:
					runLook();
					break;
				case HELP:
					runHelp();
					break;
				case GET:
					runGet(input);
					break;
				case GO:
					runGo(input);
					break;
				default:
					System.out.println("command not implemented");
				}
			}
		}
		
		scan.close();
	}
	
	private void runNext() {
		
		if(node.next.size() == 0)
			System.out.println("no next");
		
		for(int i = 0; i < node.next.size(); i++) {
			
			System.out.println(i+": "+node.next.get(i).name);
		}
	}
	
	private void runLook() {
		
		System.out.println(node.desc);
	}
	
	private void runHelp() {
		
		System.out.println("Commands:");
		
		for(Command c : Command.values()) {
			
			System.out.println(c.str);
		}
	}
	
	private void runGet(String[] input) {
		
		
	}

	private void runGo(String[] input) {
		
		if(input.length >= 2) {
			
			String location = input[1];
			
			for(int i = 0; i < node.next.size(); i++) {
				
				Node next = node.next.get(i);
				
				if(location.equals(i+"") || location.equals(next.name)) {
					
					node = next;
					newNode = true;
					return;
				}
			}
		}
		
		System.out.println("location not found");
	}
	private void init() {
		
		loadCommands();
		loadZones();
	}
	
	Map<String, Command> commandMap;
	
	private void loadCommands() {
		
		commandMap = new HashMap<String, Command>();

		commandMap.put("help", Command.HELP);
		
		commandMap.put("inventory", Command.INV);
		commandMap.put("i", Command.INV);
		
		commandMap.put("next", Command.NEXT);
		commandMap.put("n", Command.NEXT);
		
		commandMap.put("get", Command.GET);
		commandMap.put("take", Command.GET);
		commandMap.put("grab", Command.GET);
		
		commandMap.put("go", Command.GO);
	}
	
	private void loadZones() {
		
		node = new Node("Starting Room", "This is the starting room.");
		
		node.next.add(new Node("Winner's Room", "You Won!"));
		node.next.add(new Node("Loser's Room", "You Lost!"));
	}
}
