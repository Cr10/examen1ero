package com.example.examen;


import com.example.examen_db.MyAppDataSource;
import com.example.examen_db.model.Person;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ListaNueva extends Activity {

	
	private MyAppDataSource ds;
	private Person personToUpdate;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_nueva);
		setupActionBar();
		
		ds = new MyAppDataSource(this);
	    ds.open();
	    
	    Intent i = this.getIntent();
	    
	    if(i.hasExtra(ApplicationActivity.EXTRA_PERSON))
	    {
	    	Person p = (Person) i.getSerializableExtra(ApplicationActivity.EXTRA_PERSON);
	    	
	    	EditText firstNameField = (EditText) this.findViewById(R.id.crearlista);
			firstNameField.setText(p.getFirstName());
			
			
					
			this.setTitle("Update Person");
			
			this.personToUpdate = p;
	    }
	    else
	    {
	    	Button saveButton = (Button) this.findViewById(R.id.crearlista);
	    	saveButton.setText("Create");
	    	
	    	Button deleteButton = (Button) this.findViewById(R.id.button3);
	    	deleteButton.setVisibility(Button.VISIBLE);
	    	
	    	this.setTitle("Create Person");
	    	
	    	this.personToUpdate = null;
	    }
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
		
		
		
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_nueva, menu);
		return true;
	}

	public void onSaveListaClicked(View view) {
		EditText firstNameField = (EditText) this.findViewById(R.id.Guardarlista);
		String firstName = firstNameField.getText().toString();
		
		
		if(firstName.isEmpty())
		{
			Toast.makeText(this, "Complete the form before saving", Toast.LENGTH_LONG).show();
			return;
		}
		
		Person p = null;
		
		if(this.personToUpdate != null)
		{
			p = ds.updatePerson(this.personToUpdate, firstName);
		}
		else
		{
			p = ds.createPerson(firstName);
		}
		
		Intent i = new Intent();
		i.putExtra(ApplicationActivity.EXTRA_PERSON, p);
		i.putExtra(ApplicationActivity.EXTRA_REMOVE, false);
		this.setResult(RESULT_OK, i);
		
		this.finish();
	}
	
	public void onDeleteButtonClicked(View view) {
		
		Person p = ds.deletePerson(this.personToUpdate);
		
		Intent i = new Intent();
		i.putExtra(ApplicationActivity.EXTRA_PERSON, p);
		i.putExtra(ApplicationActivity.EXTRA_REMOVE, true);
		this.setResult(RESULT_OK, i);
		
		this.finish();
	}
	
	@Override
	protected void onResume() {
		ds.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		ds.close();
		super.onPause();
	}
	
	
	
}
