package pack.as1_301;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CounterStatsActivity extends Activity {
	private Counter currentCounter;
	private ArrayList<Date> dates = new ArrayList<Date>();
	private ListView counterList;
	private ArrayAdapter<Date> adapter;
	
	// sets up the class for use
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.counter_stats);
		counterList = (ListView) findViewById(R.id.counter_stats_view);
		Intent intent = getIntent();
	    String temp = intent.getStringExtra(MainActivity.CURRENT_COUNTER);
	    currentCounter = deserialization(temp);
	    dates = currentCounter.getChanges();
	}
	
	// refreshes the view with current values
	@Override
	protected void onResume() {
		super.onResume();
		dates = currentCounter.getChanges();
		adapter = new ArrayAdapter<Date>(this, R.layout.counter_listview, dates);
    	counterList.setAdapter(adapter);
	}
    
	// returns a counter when given a serialized text
    private Counter deserialization(String text) {
        Gson gson = new Gson();
        Counter new_counter = gson.fromJson(text, Counter.class);
        return new_counter;
    }
}
