package com.example.examen;

import com.example.examen.helpers.PreferencesHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private Boolean isUpdatingProfile = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		SharedPreferences sharedPref = getSharedPreferences("app-data",Context.MODE_PRIVATE);
		Boolean isLoggedIn = sharedPref.getBoolean(PreferencesHelper.IS_LOGGED_IN_KEY, false);
		
		if(isLoggedIn)
		{
			String name = sharedPref.getString(PreferencesHelper.NAME_KEY, "");
			String email = sharedPref.getString(PreferencesHelper.EMAIL_KEY, "");
			String password = sharedPref.getString(PreferencesHelper.PASSWORD_KEY, "");
			
			EditText nameField = (EditText) this.findViewById(R.id.nameField);
			nameField.setText(name);
			
			EditText emailField = (EditText) this.findViewById(R.id.emailField);
			emailField.setText(email);
			
			EditText passwordField = (EditText) this.findViewById(R.id.passwordField);
			passwordField.setText(password);
			
			EditText passwordConfirmationField = (EditText) this.findViewById(R.id.passwordConfirmationField);
			passwordConfirmationField.setText(password);
			
			Button submitButton = (Button) this.findViewById(R.id.registerButton);
			submitButton.setText("Update");
			
			this.setTitle("Profile");
			
			this.isUpdatingProfile = true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	
	public void onRegisterButtonClicked(View view) {
	
		EditText nameField = (EditText) this.findViewById(R.id.nameField);
		String name = nameField.getText().toString();
		
		EditText emailField = (EditText) this.findViewById(R.id.emailField);
		String email = emailField.getText().toString();
		
		EditText passField = (EditText) this.findViewById(R.id.passwordField);
		String pass = passField.getText().toString();
		
		EditText confirmPassField = (EditText) this.findViewById(R.id.passwordConfirmationField);
		String passConfirmation = confirmPassField.getText().toString();
		
		if(pass.equals(passConfirmation))
		{
			SharedPreferences sharedPref = getSharedPreferences("app-data",Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
						
			editor.putString(PreferencesHelper.NAME_KEY, name);
			editor.putString(PreferencesHelper.EMAIL_KEY, email);
			editor.putString(PreferencesHelper.PASSWORD_KEY, pass);
			editor.putBoolean(PreferencesHelper.IS_REGISTERES_KEY, true);
			editor.putBoolean(PreferencesHelper.IS_LOGGED_IN_KEY, false);
			
			editor.commit();
			
			if(this.isUpdatingProfile)
			{
				this.isUpdatingProfile = false;
				
				Toast.makeText(this, "Profile updated!", Toast.LENGTH_LONG).show();
				
				Intent app = new Intent(this, ApplicationActivity.class);
				this.startActivity(app);
			}
			else
			{
				Toast.makeText(this, "Thanks for registering!", Toast.LENGTH_LONG).show();
				
				Intent login = new Intent(this, LoginActivity.class);
				this.startActivity(login);
			}
		}
		else
		{
			Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
		}
	}
}
