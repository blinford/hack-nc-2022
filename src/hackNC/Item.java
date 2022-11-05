package hackNC;

public class Item {

	String name;
	
	String[] description, text;
	
	boolean takeable = false, readable = false;

	public Item(String name, String desc) {
		
		this.name = name;
		
		String[] description = {desc};
		this.description = description;
	}
	
	public Item(String name, String desc, String txt) {
		
		this.name = name;
		
		String[] description = {desc};
		this.description = description;

		String[] text = {txt};
		this.text = text;
		readable = true;
	}

	public Item(String name, String desc, String[] text) {
		
		this.name = name;
		
		String[] description = {desc};
		this.description = description;
		
		this.text = text;
		readable = true;
	}
	
	public void printDescription() {
		
		for(int i = 0; i < description.length; i++) {
			
			System.out.println(description[i]);
			
			if(i+1 != description.length)
				System.out.println();
		}
	}
	
	public void printText() {
		
		for(int i = 0; i < text.length; i++) {
			
			System.out.println(text[i]);
			
			if(i+1 != text.length)
				System.out.println();
		}
	}
}
