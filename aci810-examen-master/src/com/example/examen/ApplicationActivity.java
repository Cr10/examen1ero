package com.example.examen;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.examen_db.listeners.ListViewItemClickListener;
import com.example.examen_db.model.Person;
import com.example.examen_db.MyAppDataSource;
import com.example.examen.helpers.PreferencesHelper;

public class ApplicationActivity extends Activity {
	
	
	public static final int REQUEST_CODE_ADD_PERSON = 1;
	public static final int REQUEST_CODE_UPDATE_PERSON = 2;
	
	public static final String EXTRA_PERSON = "person";
	public static final String EXTRA_REMOVE = "remove";

	private MyAppDataSource ds;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_application);
		

		SharedPreferences sharedPref = getSharedPreferences("app-data",Context.MODE_PRIVATE);
		String name = sharedPref.getString(PreferencesHelper.NAME_KEY, "");
		
		if(name.length() > 0)
		{
			TextView nameTextView = (TextView) this.findViewById(R.id.nameTextViewField);
			nameTextView.setText("Welcome " + name + "!	");
		}
		
		ds = new MyAppDataSource(this);
	    ds.open();
	    
	    List<Person> values = ds.getPeople();
	    
	    // use the SimpleCursorAdapter to show the elements in a ListView
	    ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(
	    		this,
	    		android.R.layout.simple_list_item_1, 
	    		values
	    	);
	    
	    this.setListAdapter(adapter);
	    
	    ListView lv = (ListView) this.findViewById(android.R.id.list);
	    lv.setOnItemClickListener(new ListViewItemClickListener(this));
		
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(data.hasExtra(EXTRA_PERSON))
		{
			List<Person> values = ds.getPeople();
		    
		    // use the SimpleCursorAdapter to show the elements in a ListView
		    ArrayAdapter<Person> adapter = new ArrayAdapter<Person>(
		    		this,
		    		android.R.layout.simple_list_item_1, 
		    		values
		    	);
		    
		    ListView lv = (ListView) this.findViewById(android.R.id.list);
		    lv.setAdapter(adapter);
		    
			if(requestCode == REQUEST_CODE_ADD_PERSON) {
				// do something here when a person is added
			}
			else if(requestCode == REQUEST_CODE_UPDATE_PERSON)
			{
				Boolean remove = data.getBooleanExtra(EXTRA_REMOVE, false);
				
				if(remove) {
					// do something here when a person is removed
				}
				else {
					// do something here when a person is updated
				}
			}
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.application, menu);
		return true;
	}
	
	public void onProfileButtonClicked(View view) {
		Intent register = new Intent(this, RegisterActivity.class);
		this.startActivity(register);
	}
	
	public void onLogoutButtonClicked(View view) {
		
		SharedPreferences sharedPref = getSharedPreferences("app-data",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean(PreferencesHelper.IS_LOGGED_IN_KEY, false);
		editor.commit();
		
		Intent login = new Intent(this, LoginActivity.class);
		this.startActivity(login);
	}
}
