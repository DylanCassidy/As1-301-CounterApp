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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_counter);
		counters = loadFromFile();
	}

	public void callDone(View v) {
		EditText counterText = (EditText) findViewById(R.id.counter_name);
		Counter counter = new Counter(counterText.getText().toString());
		if (counter.getName().isEmpty()) {
			return;
		}
		for (int i = 0; i < counters.size(); i++) {
    		if (counters.get(i).getName().equals(counter.getName())) {
    			return;
    		}
    	}
		saveInFile(counter);
		finish();
	}
	
	public void callCancel(View v) {
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
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return counters;
	}
	
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
