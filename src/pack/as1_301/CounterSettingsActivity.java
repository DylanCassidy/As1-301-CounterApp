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

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_settings);
        Intent intent = getIntent();
	    String temp = intent.getStringExtra(MainActivity.CURRENT_COUNTER);
	    currentCounter = deserialization(temp);
		counters = loadFromFile();
    }
	
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
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (IOException e) {
                // TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
