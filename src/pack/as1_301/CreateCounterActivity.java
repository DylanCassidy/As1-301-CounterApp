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
import android.view.View;
import android.widget.EditText;

public class CreateCounterActivity extends Activity {
	private static final String FILENAME = "midday.sav";
	private ArrayList<Counter> counters;
	
	// sets up the class for use
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_counter);
		counters = loadFromFile();
	}

	// called when the done button was clicked
	// checks that the EditText field is not empty and is not a name that is already in use
	// creates a counter and saves it into a file, appending it to the end
	public void callDone(View v) {
		EditText counterText = (EditText) findViewById(R.id.counter_name);
		if (counterText.getText().toString().isEmpty()) {
			return;
		}
		for (int i = 0; i < counters.size(); i++) {
    		if (counters.get(i).getName().equals(counterText.getText().toString())) {
    			return;
    		}
    	}
		Counter counter = new Counter(counterText.getText().toString());
		saveInFile(counter);
		finish();
	}
	
	// called when the cancel button was clicked
	// calls finish and returns to the previous activity
	public void callCancel(View v) {
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
	
    // saves a counter to a file, appending it to the end
	private void saveInFile(Counter counter) {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_APPEND);
			fos.write(serialization(counter).getBytes());
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
