package pack.as1_301;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CounterStatsActivity extends Activity {
	private static final String FILENAME = "midday.sav";
	private ArrayList<Counter> counters;
	private Counter currentCounter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.counter_stats);
		Intent intent = getIntent();
	    String temp = intent.getStringExtra(MainActivity.CURRENT_COUNTER);
	    if (temp != null) {
	    	currentCounter = deserialization(temp);
	    }
	    else {
	    	currentCounter = null;
	    }
		counters = loadFromFile();
	}
	
	protected void onResume() {
		if (currentCounter == null) {
			ArrayList<ArrayList<Date>> alldates = new ArrayList<ArrayList<Date>>();
			for (int i = 0; i < counters.size(); i++) {
				alldates.add(counters.get(i).getChanges());
			}
		} 
		else {
			ArrayList<Date> dates = currentCounter.getChanges();
			dates.clear(); // Fake use of variable
		}
	}
	
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
    
    private Counter deserialization(String text) {
        Gson gson = new Gson();
        Counter new_counter = gson.fromJson(text, Counter.class);
        return new_counter;
    }
}
