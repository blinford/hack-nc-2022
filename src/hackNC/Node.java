package hackNC;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Node {

	Map<String, Node> next;
	List<Item> items;
	
	String name;
	private String[] description;
	
	String endMessage = "";
	
	public Node() {
		
		next = new LinkedHashMap<String, Node>();
		items = new ArrayList<Item>();
	}
	
	public Node(String name, String desc) {
		
		this();
		
		this.name = name;
		
		String[] description = {desc};
		this.description = description;
	}
	
	public void print() {
		
		System.out.println(name);
		
		for(int i = 0; i < description.length; i++) {
			
			System.out.println(description[i]);
			
			if(i+1 != description.length)
				System.out.println();
		}
	}
	
	public void addNext(Node node) {
		
		next.put(node.name, node);
	}
	
	public void addNext(String name, Node node) {
		
		next.put(name, node);
	}
	
	public void addItem(Item item) {
		
		items.add(item);
	}
	
	public void removeItem(Item item) {
		
		items.remove(item);
	}
}