package pack.as1_301;

public class Counter {
	private String name;
	private int count;
	private static int idTotal = 0;
	private int id;

	public Counter(String counterName) {
		// TODO Auto-generated constructor stub
		name = counterName;
		count = 0;
		idTotal++;
		id = idTotal;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCount() {
		return count;
	}
	
	public void incCount() {
		count++;
	}
	
	public String toString() {
		return name;
	}

}
