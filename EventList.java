import java.util.*;
class EventList {
	Random randseq;
	Node head;
	Node tail;
	int maxHeight;
	int maxHeightUsed;

	int randomHeight()
	{
		int v = 1;
		while (randseq.nextBoolean()) { v++; }
		return v;
	}


	//
	// Constructor
	//
	public EventList()
	{
		randseq = new Random(9989);
        

		Event a = new Event(Integer.MIN_VALUE, "head");
		Event b = new Event(Integer.MAX_VALUE, "tail");

		tail = new Node(b, null, null);
		head = new Node(a, tail, null);

		maxHeightUsed = 1;

		maxHeight = 1;
	}

	
	//find method
	public Node basicFind(int iyear){
		int l = maxHeight -1;
		Node x = head;
		while(l>=0){
			Node y = x.right;
			if(y.values.get(0).year == iyear){
				return y;
			}else if (y.values.get(0).year < iyear){
				x = y;
			}else{
				l--;
				x = x.down;
			}
		}
		return null;
	}

	//
	// Add an Event to the list.
	//
	public void insert(Event e)
	{
		int t = randomHeight();

		if (t > maxHeightUsed){
			maxHeightUsed = t;
			while(maxHeight < t){
				tail = tail.addUpperNode(null);
				head = head.addUpperNode(tail);
				maxHeight++;
			}
		}

		int l = maxHeight - 1;

		if(basicFind(e.year) != null) {
			Node existingNode = basicFind(e.year);
			existingNode.values.add(e);
			return;
		}

		//Otherwise, if key is not in list, must create new Node that has that key
		Node nodeToCreate = new Node(e, null, null);

		for(int i =0; i< t; i++){
			nodeToCreate  = nodeToCreate.addUpperNode(null);
		}

		Node x = head;
		while (x.down != null){
			Node y = x.right;
			if(y.values.get(0).year > e.year){
				if(l < t){
					y = nodeToCreate;
					nodeToCreate.right = x.right.right;
					nodeToCreate = nodeToCreate.down;
				}
				l--;
				x = x.down;
			}else{
				x = y;
			}
		}

		while(x.right.values.get(0).year < e.year){
			x = x.right;

		}
		x.right = new Node(e, x.right, null);

	}

	//
	// Remove all Events in the list with the specified year.
	//
	public void remove(int year)
	{
		int l = maxHeight - 1;
		Node x = head;
		while (l >= 0) {
			Node y = x.right;
			if (y.values.get(0).year == year){
				x.right = y.right;
				l--;
				x = x.down;
			}else{ 
				if (y.values.get(0).year < year){
					x = y;
				}else{
					l--;
					x = x.down;
				}
			}
		}
	}

    //find most recent
	public Node findLower(int iyear){
		int l = maxHeight -1;
		Node x = head;
		while(l>=0){
			Node y = x.right;
			if(y.values.get(0).year == iyear){
				//return Node on lowest level
				while(y.down != null) {
					y = y.down;
				}
				return y;
			}else if (y.values.get(0).year < iyear){
				x = y;
			}
			//If we are on lowest level and exact key has not been found,
			//return key of node to the left to get closest key that is smaller
			else if(x.down == null) {
				if(x.values.get(0).year == Integer.MIN_VALUE){
					return null;
				}
				return x;
			}else{
				l--;
				x = x.down;
			}
		}
		return null;
	}

	//
	// Find all events with greatest year <= input year
	//
	public Event [] findMostRecent(int year)
	{
		ArrayList<Event> ans = new ArrayList<Event>();
		if(findLower(year) != null){
			Node targetNode = findLower(year);
			ans.addAll(targetNode.values);

			int numberOfEvents = ans.size();
			Event eventsToReturn[] = new Event[numberOfEvents];
			for (int j = 0; j < numberOfEvents; j++){
				eventsToReturn[j] = ans.get(j);
			}
			return eventsToReturn;

		}
		return null;
	}


	//find range of years
	public Node findUpper(int iyear){
		int l = maxHeight -1;
		Node x = head;
		while(l>=0){
			Node y = x.right;
			if(y.values.get(0).year == iyear){
				//return Node on lowest level
				while(y.down != null) {
					y = y.down;
				}
				return y;
			}else if (y.values.get(0).year < iyear){
				x = y;
            }
			else if(x.down == null) {
				return y;
			}else{
				l--;
				x = x.down;
			}
		}
		return null;
	}


	//
	// Find all Events within the specific range of years (inclusive).
	//
	public Event [] findRange(int first, int last)
	{
		ArrayList<Event> ans = new ArrayList<Event>();
		if (findUpper(first) != null){
			Node targetNode = findUpper(first);

			while(targetNode.values.get(0).year <= last) {
				ans.addAll(targetNode.values);
				targetNode = targetNode.right;
			}
			int numberOfEvents = ans.size();
			Event eventsInRange[] = new Event[numberOfEvents];
			for (int j = 0; j < numberOfEvents; j++){
				eventsInRange[j] = ans.get(j);
			}
			return eventsInRange;
		}
		return null;
	}

}
