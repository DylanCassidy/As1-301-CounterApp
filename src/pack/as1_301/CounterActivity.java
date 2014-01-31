package pack.as1_301;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CounterActivity extends Activity {
	private static final String FILENAME = "midday.sav";
	private ArrayList<Counter> counters;
	private Counter currentCounter;
	private TextView myIncTextView;
	private TextView myNameTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.counter);
		Intent intent = getIntent();
	    String temp = intent.getStringExtra(MainActivity.CURRENT_COUNTER);
	    currentCounter = deserialization(temp);
		counters = loadFromFile();
		myIncTextView = (TextView) findViewById(R.id.counter_inc);
		myNameTextView = (TextView) findViewById(R.id.counter_name_display);
	}
	 
	protected void onResume() {
		super.onResume();
		counters = loadFromFile();
		boolean stillIn = false;
    	for (int i = 0; i < counters.size(); i++) {
    		if (counters.get(i).getName().equals(currentCounter.getName())) {
    			stillIn = true;
    			currentCounter = counters.get(i);
    			myIncTextView.setText("" + currentCounter.getCount());
    			myNameTextView.setText("" + currentCounter.getName());
    			break;
    		}
    	}
    	if (!stillIn) {
    		finish();
    	}
	}
	
    public void onIncClick(View v) {
    	currentCounter.incCount();
    	myIncTextView.setText("" + currentCounter.getCount());
    	for (int i = 0; i < counters.size(); i++) {
    		if (counters.get(i).getName().equals(currentCounter.getName())) {
    			counters.set(i, currentCounter);
    			break;
    		}
    	}
    	saveInFile(counters);
    }
	
    public void onStatsClick(View v) {
    	Intent intent = new Intent(this, CounterStatsActivity.class);
    	String serialCurrentCounter = serialization(currentCounter);
    	intent.putExtra(MainActivity.CURRENT_COUNTER, serialCurrentCounter);
    	startActivity(intent);
    }
	
    public void onSettingsClick(View v) {
    	Intent intent = new Intent(this, CounterSettingsActivity.class);
    	String serialCurrentCounter = serialization(currentCounter);
    	intent.putExtra(MainActivity.CURRENT_COUNTER, serialCurrentCounter);
    	startActivity(intent);
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
    
    private Counter deserialization(String text) {
        Gson gson = new Gson();
        Counter new_counter = gson.fromJson(text, Counter.class);
        return new_counter;
    }
    
    private String serialization(Counter counter) {
        Gson gson = new Gson();
        String json = gson.toJson(counter) + "\n";
        return json;
	 }
}
