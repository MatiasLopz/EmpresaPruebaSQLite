package com.example.empresa;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Sueldo extends Activity {

	TextView ta, tb, tc, td;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sueldo);
		
		
		ta = (TextView) findViewById(R.id.textViewB);
		tb = (TextView) findViewById(R.id.textViewD);
		tc = (TextView) findViewById(R.id.textViewG);
		td = (TextView) findViewById(R.id.textViewJ);
		
		Bundle b = getIntent().getExtras();
		
		ta.setText(b.getString("nombre"));
		tb.setText(b.getString("cargo"));
		tc.setText("$ " + b.getString("sueldo"));
		td.setText("$ " + b.getString("neto"));
		
	}

  public void retornar(View v)
  {
	  finish();
  }

}
