package pack.as1_301;

import java.util.ArrayList;
import java.util.Date;

public class Counter {
	private String name;
	private int count;
	private ArrayList<Date> changes;

	public Counter(String counterName) {
		name = counterName;
		count = 0;
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
	
	public void incCount() {
		count++;
		Date date = new Date();
		changes.add(date);
	}
	
	public void resetCount() {
		count = 0;
	}
	
	public ArrayList<Date> getChanges() {
		return changes;
	}

	public String toString() {
		return name;
	}

}
