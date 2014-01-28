package pack.as1_301;

public class Counter {
	private String name;
	private int count;

	public Counter(String counterName) {
		// TODO Auto-generated constructor stub
		name = counterName;
		count = 0;
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

}
