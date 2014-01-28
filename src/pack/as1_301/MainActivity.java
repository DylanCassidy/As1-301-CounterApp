package pack.as1_301;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView counterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    	ArrayList<Counter> counters = loadFromFile();
    	
    	ArrayAdapter adapter = new ArrayAdapter<Counter>(this, 
    	        R.layout.counter_listview, counters);
    	ListView listView = (ListView) findViewById(R.id.listview);
    }
    
    private ArrayList<Counter> loadFromFile() {
		ArrayList<Counter> tweets = new ArrayList<Counter>();
		
		
		
		return tweets;
	}
    
    private Counter deserialization(Counter counter) {
        
        Gson gson = new Gson();
        Counter new_counter = gson.fromJson(counter, Counter.class);
        return new_counter;
    }
	
}
