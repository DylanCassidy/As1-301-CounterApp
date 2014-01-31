package pack.as1_301;

import java.util.ArrayList;
import java.util.Date;

public class Counter {
	private String name;
	private int count;
	private ArrayList<Date> changes;

	// Constructor: takes a string that will be stored as the name of the counter
	public Counter(String counterName) {
		name = counterName;
		count = 0;
		changes = new ArrayList<Date>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCount() {
		return count;
	}
	
	// increments the counter's value and logs the increment
	public void incCount() {
		count++;
		Date date = new Date();
		changes.add(date);
	}
	
	// resets the counter's value and resets it's log
	public void resetCount() {
		count = 0;
		changes.clear();
	}
	
	public ArrayList<Date> getChanges() {
		return changes;
	}

	// returns the counter's name when called
	public String toString() {
		return name;
	}

}
