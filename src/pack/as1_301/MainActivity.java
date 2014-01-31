package pack.as1_301;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
	private static final String FILENAME = "midday.sav";
	public final static String CURRENT_COUNTER = "pack.as1_301.As1-301-CounterApp.COUNTER";
	private ArrayList<Counter> counters;
	private ListView counterList;
	private ArrayAdapter<Counter> adapter;

	// sets up the class for use
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counterList = (ListView) findViewById(R.id.counter_list);
        counterList.setOnItemClickListener(this);
        counters = loadFromFile();
    }
    
    // refreshes the view with current values
    @Override
    protected void onResume() {
    	super.onResume();
    	counters = loadFromFile();
    	adapter = new ArrayAdapter<Counter>(this, R.layout.counter_listview, counters);
    	counterList.setAdapter(adapter);
    }
    
    // called when the create counter (+) button was clicked
    // launches the CreateCounterActivity activity
    public void callCreate(View v) {
    	Intent intent = new Intent(this, CreateCounterActivity.class);
    	startActivity(intent);
	}
    
    // called when the reorder button was clicked
    // reorganizes the counter list based on highest counter value, then saves the counter list
    public void callReorder(View v) {
    	@SuppressWarnings("unchecked")
		ArrayList<Counter> templist = (ArrayList<Counter>) counters.clone();
    	ArrayList<Counter> orderedCounters = new ArrayList<Counter>();
    	Counter largest = null;
    	for (int i = 0; i < counters.size(); i++) {
    		int max = Integer.MIN_VALUE;
    		for (int k = 0; k < templist.size(); k++) {
        		if (templist.get(k).getCount() > max) {
        			max = templist.get(k).getCount();
        			largest = templist.get(k);
        		}
        	}
    		orderedCounters.add(largest);
    		templist.remove(largest);
    	}
    	counters = orderedCounters;
    	saveInFile(counters);
    	adapter = new ArrayAdapter<Counter>(this, R.layout.counter_listview, counters);
    	counterList.setAdapter(adapter);
    }
    
    // A Counter in the listview has been pushed, change to CounterActivity and provide the pushed counter to that activity
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	Counter counter = counters.get(position);
    	Intent intent = new Intent(this, CounterActivity.class);
    	String currentCounter = serialization(counter);
    	intent.putExtra(CURRENT_COUNTER, currentCounter);
    	startActivity(intent);
    }
    
    // loads the counter list from a file
    private ArrayList<Counter> loadFromFile() {
    	ArrayList<Counter> counters = new ArrayList<Counter>();
        try {
                FileInputStream fis = openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                String line = in.readLine();
                
                while (line != null) {
                	Counter counter = deserialization(line);
                	counters.add(counter);
                    line = in.readLine();
                }

        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return counters;
	}
    
    // saves the counter list to a file, overwriting what was there
    private void saveInFile(ArrayList<Counter> counters) {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			
			for (int i = 0; i < counters.size(); i++) {
				Counter counter = counters.get(i);
				fos.write(serialization(counter).getBytes());
            }
			
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    // returns a counter when given a serialized text
    private Counter deserialization(String text) {
        Gson gson = new Gson();
        Counter new_counter = gson.fromJson(text, Counter.class);
        return new_counter;
    }
    
    // returns a string when given a counter
    private String serialization(Counter counter) {
        Gson gson = new Gson();
        String json = gson.toJson(counter) + "\n";
        return json;
	 }
	
}
