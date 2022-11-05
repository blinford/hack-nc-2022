package hackNC;
import java.util.Scanner;

public class HackNC {
	
	Node start;

	public static void main(String[] args) {
		
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
	
	private static void loadZones() {
		
		
	}
}