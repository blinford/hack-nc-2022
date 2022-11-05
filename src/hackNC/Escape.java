package hackNC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Escape {
	
	public Escape() {

		inventory = new ArrayList<Item>();
		
		loadCommands();
		loadZones();
	}
	
	// enum to keep track of commands
	private enum Command {

		HELP("help - lists commands"),
		NEXT("next - lists next locations"),
		LOOK("look - looks around at current location"),
		INV("inventory - shows contents of the inventory"),
		GET("get (item) - removes item from current room and places it in your inventory"),
		GO("go (location) - moves to specified location"),
		EXAMINE("examine (item) - examines the specified item"),
		READ("read (item) - reads the selected item");
		
		String str;
		
		Command(String str) {
			this.str = str;
		}
	}

	// the current node
	Node node;
	boolean newNode;
	
	// keeps track of inventory items
	List<Item> inventory;
	
	// the node 
	
	// runs the game
	public boolean run() {
	
		Scanner scan = new Scanner(System.in);
		String[] input;
		
		boolean running = true;
		
		node = loadStart();
		newNode = true;
		
		while(running) {
			
			if(newNode) {
				
				node.print();
				newNode = false;
			}
			
			System.out.println();
			
			if(node.next.size() == 0) {
				
				System.out.println("ENDING: "+node.endMessage+"\n");
				
				System.out.println("Keep Playing: y\\n?\n");
				
				if(scan.nextLine().equalsIgnoreCase("y"))
					return true;
				else
					return false;
			}
			
			System.out.print("> ");
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
				case INV:
					runInventory();
					break;
				case EXAMINE:
					runExamine(input);
					break;
				case READ:
					runRead(input);
					break;
				default:
					System.out.println("command not implemented");
				}
			}
		}
		
		scan.close();
		
		return false;
	}
	
	private void runNext() {
		
		if(node.next.size() == 0)
			System.out.println("no next locations found");
		
		int i = 0;
		
		for(String nxt : node.next.keySet()) {
			
			System.out.println(i+++": "+nxt);
		}
	}
	
	private void runLook() {
		
		node.print();
	}
	
	private void runHelp() {
		
		System.out.println("Commands:");
		
		for(Command c : Command.values()) {
			
			System.out.println(c.str);
		}
	}
	
	private void runGet(String[] input) {
		
		for(Item item : node.items) {
			
			if(item.name.equalsIgnoreCase(input[1])) {
				
				if(item.takeable) {
					
					node.removeItem(item);
				}
				else {
					
					System.out.println("cannot take item");
				}
				
				return;
			}
		}
		
		System.out.println("item not found");
	}

	private void runGo(String[] input) {
		
		if(input.length <= 1) {
			
			System.out.println("expected location");
			return;
		}
		
		String location = input[1];
		
		int i = 0;
		
		for(String nxt : node.next.keySet()) {
			
			if(location.equals(i+++"") || location.equalsIgnoreCase(nxt)) {
				
				node = node.next.get(nxt);
				newNode = true;
				return;
			}
		}
		
		System.out.println("location not found");
	}
	
	private void runInventory() {
		
		
	}
	
	private void runExamine(String[] input) {
		
		if(input.length == 1) {
			
			System.out.println("expected item");
			return;
		}
		
		for(Item item : node.items) {
			
			if(item.name.equalsIgnoreCase(input[1])) {
				
				item.printDescription();
				return;
			}
		}
		
		System.out.println("item not found");
	}
	
	private void runRead(String[] input) {
		
		for(Item item : node.items) {
			
			if(item.name.equalsIgnoreCase(input[1])) {
				
				if(item.readable) {
					
					item.printText();
				}
				else {
					
					System.out.println("cannot read item");
				}
				
				return;
			}
		}
		
		System.out.println("item not found");
	}
	
	Map<String, Command> commandMap;
	
	private void loadCommands() {
		
		commandMap = new HashMap<String, Command>();

		commandMap.put("help", Command.HELP);
		
		commandMap.put("inventory", Command.INV);
		commandMap.put("i", Command.INV);

		commandMap.put("look", Command.LOOK);
		commandMap.put("l", Command.LOOK);
		
		commandMap.put("next", Command.NEXT);
		commandMap.put("n", Command.NEXT);
		
		commandMap.put("get", Command.GET);
		commandMap.put("take", Command.GET);
		commandMap.put("grab", Command.GET);
		
		commandMap.put("go", Command.GO);

		commandMap.put("read", Command.READ);
		
		commandMap.put("examine", Command.EXAMINE);
	}
	
	private Node loadZones() {
		
		
		return node;
	}
	
	private Node loadStart() {
		
		Node node = new Node("Auditorium", "You are standing in a large auditorium. There is a computer in front of you.");
		
		String text = "Hello Hackers!\n"
				+ "Thank you for attending HackNC 2022!\n"
				+ "We are so glad that you all came to join us, and we would once again like to apologize for destroying the fabric of space and time itself! Our hackers this year were so talented that they broke the universe, and now spacetime is arranged like a directed graph.\n"
				+ "In other words, most space is connected to a number of other spaces, but only in one direction! This means that once you step into another area, there's no promise you'll be able to come back :)\n"
				+ "Remember to submit feedback for this year's event so that next year's can be even better (if we make it back)! If you ever find yourself stuck, shout out \"help\" to think about what to do or \"next\" to see where you might go next.";
		
		Item computer = new Item("Computer", "There is some text on the screen. It almost looks like a portal...", text);
		node.addItem(computer);
		
		node.addNext(loadComputer());
	
		return node;
	}
	
	private Node loadComputer() {
		
		Node node = new Node("Inside the Computer", "You are standing in an immense forest of binary trees. You don't see any way out, but you see a door in a nearby tree.");
		node.addNext("Door in the Tree", loadBST());
		
		return node;
	}
	
	private Node loadBST() {
		
		// generate first room and items (poster and sign)
		
		Node node = new Node("Room of Eight", "There are two doors in front of you, and there is a poster on the wall.");
		
		Item poster = new Item("Poster", "The poster has writing on it.", "\"Welcome to the Binary Search Tree! If you want to make your way out, make sure to get to the Room of Four! By the way, left is smaller and right is bigger!\"");
		node.addItem(poster);

		// generate left room
		
		Node left = new Node("Room of Three", "There are two doors in front of you.");
		node.addNext("Left Door", left);
		
		// generate right room
		
		Node right = new Node("Room of Ten", "You realize your mistake: 4 is smaller than 8.");
		node.addNext("Right Door", right);
		right.endMessage = "Lost in the Binary Search Forest";
		
		// generate left left room
		
		Node leftLeft = new Node("Room of One", "You realize your mistake: 4 is bigger than 3.");
		left.addNext("Left Door", leftLeft);
		leftLeft.endMessage = "Lost in the Binary Search Forest";
		
		// generate left right room
		
		Node leftRight = new Node("Room of Six", "There are two doors in front of you.");
		left.addNext("Right Door", leftRight);
		
		// generate the LRL room (the correct one)
		
		Node LRL = new Node("Room of Four", "You made it to room 4!");
		leftRight.addNext("Left Door", LRL);
		LRL.endMessage = "Out of the (Binary) Woods";
		
		// generate the LRR room (the wrong one)
		
		Node LRR = new Node("Room of Seven", "You realize your mistake: 4 is smaller than 6.");
		leftRight.addNext("Right Door", LRR);
		LRR.endMessage = "Lost in the Binary Search Forest";
		
		return node;
	}
}
