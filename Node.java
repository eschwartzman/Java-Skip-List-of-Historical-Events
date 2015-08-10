import java.util.ArrayList;

public class Node {

	public ArrayList<Event> values;
	public Node right;
	public Node down;

	public Node(Event val, Node iright, Node idown){
		this.values = new ArrayList<Event>();
		this.values.add(val);
		this.right = iright;
		this.down = idown;
	}

	public Node addUpperNode(Node iright){
		return new Node(values.get(0), iright, this);
	}

}
