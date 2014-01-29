package pack.as1_301;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity /*implements AdapterView.OnItemClickListener, View.OnClickListener*/ {
	private static final String FILENAME = "midday.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clearFile();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
    }
    
    public void callCreate(View v) {
		// Create a counter
    	Intent intent = new Intent(this, CounterActivity.class);
    	startActivity(intent);
	}
    
    @Override
    protected void onResume() {
    	super.onResume();
    	ListView counterList = (ListView) findViewById(R.id.counterList);
    	//ListView counterList = new ListView(this);
    	ArrayList<Counter> counters = loadFromFile();
    	
    	ArrayAdapter<Counter> adapter = new ArrayAdapter<Counter>(this, 
    	        R.layout.counter_listview, counters)/* {
    	            @Override
    	            public View getView(int position, View convertView, ViewGroup parent) {
    	                View row =  super.getView(position, convertView, parent);

    	                View left = row.findViewById(R.id.counter_name);
    	                left.setTag(position);
    	                left.setOnClickListener(MainActivity.this);
    	                View right = row.findViewById(R.id.counter_options);
    	                right.setTag(position);
    	                right.setOnClickListener(MainActivity.this);
    	                
    	                return row;
    	            }
    	        }*/;
    	counterList.setAdapter(adapter);
    	
    	//counterList.setOnItemClickListener(this);
    }
    
    /*@Override
    public void onClick(View v) {
        switch(v.getId()) {
        case R.id.counter_name:
            Toast.makeText(this, "Left Accessory "+v.getTag(), Toast.LENGTH_SHORT).show();
            break;
        case R.id.counter_options:
            Toast.makeText(this, "Right Accessory "+v.getTag(), Toast.LENGTH_SHORT).show();
            break;
        default:
            break;
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Toast.makeText(this, "Item Click "+position, Toast.LENGTH_SHORT).show();
    }*/
    
    private ArrayList<Counter> loadFromFile() {
    	ArrayList<Counter> counters = new ArrayList<Counter>();
        try {
                FileInputStream fis = openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                String line = in.readLine();
                
                Counter counter = deserialization(line);
                
                while (counter != null) {
                	counters.add(counter);
                    line = in.readLine();
                    counter = deserialization(line);
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
    
    private void clearFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
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
	
}
