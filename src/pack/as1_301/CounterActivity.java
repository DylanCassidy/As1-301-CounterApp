package pack.as1_301;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class CounterActivity extends Activity {
	private static final String FILENAME = "midday.sav";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.counter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter, menu);
		return true;
	}
	
	public void callDone(View v) {
		EditText counterText = (EditText) findViewById(R.id.counter_name);
		Counter counter = new Counter(counterText.getText().toString());
		if (counter.getName().isEmpty()) {
			return;
		}
		saveInFile(counter);
		finish();
	}
	
	public void callCancel(View v) {
		finish();
	}
	
	private void saveInFile(Counter counter) {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_APPEND);
			fos.write(serialization(counter).getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String serialization(Counter counter) {
         Gson gson = new Gson();
         String json = gson.toJson(counter) + "\n";
         return json;
	 }

}
