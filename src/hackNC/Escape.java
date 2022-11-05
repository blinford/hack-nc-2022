package hackNC;

import java.util.Scanner;

public class Escape {

	Node start;
	
	public void run() {
	
		Scanner scan = new Scanner(System.in);
		
		String input;
		
		boolean running = true;
		
		while(running) {
			
			input = scan.nextLine();
			
			if(input.equals("exit")) {
				
				running = false;
			}
		}
		
		scan.close();
	}
	
	private void loadZones() {
		
		start = new Node();
		
		Node node = new Node();
		start.next.add(node);
		node.description = "whatever";
	}
}
