package hackNC;

import java.util.ArrayList;
import java.util.List;

public class Node {

	List<Node> next;
	
	String name, desc;
	
	public Node() {
		
		next = new ArrayList<Node>();
	}
	
	public Node(String name, String desc) {
		
		this();
		this.name = name;
		this.desc = desc;
	}
}
