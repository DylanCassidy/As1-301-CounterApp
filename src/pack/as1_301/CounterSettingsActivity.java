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
import android.widget.EditText;

public class CounterSettingsActivity extends Activity {
	private static final String FILENAME = "midday.sav";
	private ArrayList<Counter> counters;
	private Counter currentCounter;

	// sets up the class for use
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_settings);
        Intent intent = getIntent();
	    String temp = intent.getStringExtra(MainActivity.CURRENT_COUNTER);
	    currentCounter = deserialization(temp);
		counters = loadFromFile();
    }
	
	// called when the rename button was clicked
	// checks that the EditText field is not empty and is not a name that is already in use
	// renames the current counter and updates the counter list, then saves the counter list to a file
	public void callRename(View v) {
		EditText counterText = (EditText) findViewById(R.id.counter_new_name);
		if (counterText.getText().toString().isEmpty()) {
			return;
		}
		for (int i = 0; i < counters.size(); i++) {
    		if (counters.get(i).getName().equals(counterText.getText().toString())) {
    			return;
    		}
    	}
		for (int i = 0; i < counters.size(); i++) {
    		if (counters.get(i).getName().equals(currentCounter.getName())) {
    			currentCounter.setName(counterText.getText().toString());
    			counters.set(i, currentCounter);
    			break;
    		}
    	}
		saveInFile(counters);
		finish();
	}
	
	// called when the reset button was clicked
	// checks that the current counter's value is not already equal to zero
	// resets the current counter and updates the counter list, then saves the counter list to a file
	public void callReset(View v) {
		if (currentCounter.getCount() == 0) {
			return;
		}
		currentCounter.resetCount();
		for (int i = 0; i < counters.size(); i++) {
    		if (counters.get(i).getName().equals(currentCounter.getName())) {
    			counters.set(i, currentCounter);
    			break;
    		}
    	}
		saveInFile(counters);
		finish();
	}

	// called when the delete button was clicked
	// deletes the current counter and updates the counter list, then saves the counter list to a file
	public void callDelete(View v) {
		for (int i = 0; i < counters.size(); i++) {
    		if (counters.get(i).getName().equals(currentCounter.getName())) {
    			counters.remove(i);
    			break;
    		}
    	}
		saveInFile(counters);
		finish();
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
