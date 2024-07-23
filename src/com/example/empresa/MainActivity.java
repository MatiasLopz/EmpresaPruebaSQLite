package com.example.empresa;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText e1, e2, e3, e4;
	RadioButton r1,r2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		e1 = (EditText) findViewById(R.id.editText1);
		e2 = (EditText) findViewById(R.id.editText2);
		e3 = (EditText) findViewById(R.id.editText3);
		e4 = (EditText) findViewById(R.id.editText4);
		
		r1 = (RadioButton) findViewById(R.id.radio0);
		r2 = (RadioButton) findViewById(R.id.radio1);
		
	}

 public void altas(View v)
 {
	 Administrador a = new Administrador(this, "empresa", null, 1);
	 SQLiteDatabase db = a.getWritableDatabase();
	 
	 String legajo = e1.getText().toString();
	 String nombre = e2.getText().toString();
	 String cargo  = e3.getText().toString();
	 String sueldo = e4.getText().toString();
	 
	 ContentValues registro = new ContentValues();
	 
	 registro.put("legajo", legajo);
	 registro.put("nombre", nombre);
	 registro.put("cargo", cargo);
	 registro.put("sueldo", sueldo);
	 
	 db.insert("empleados", null, registro);
	 
	 Toast.makeText(this, "El empleado se grabó correctamente", Toast.LENGTH_LONG).show();
	 
	 //Limpiar campos
	 e1.setText("");
	 e2.setText("");
	 e3.setText("");
	 e4.setText("");
	 
	 db.close();
 }
	
	public void consultas(View v)
	{
		Administrador a = new Administrador(this, "empresa", null, 1);
		SQLiteDatabase db = a.getWritableDatabase();
		 
		String lx = e1.getText().toString();
		 
		String sql = "Select nombre, cargo, sueldo from empleados where legajo='"+lx+"'";
		Cursor fila = db.rawQuery(sql,null);
		
    	if(fila.moveToFirst())
		{
			e2.setText(fila.getString(0));
			e3.setText(fila.getString(1));
			e4.setText(fila.getString(2));
			
		}
		else
		{
			Toast t1 = Toast.makeText(this, "No se encontró el Legajo "+ lx,Toast.LENGTH_LONG);
			
			// Para centrar el Toast en el Activity
			t1.setGravity(Gravity.CENTER, 0, 0);
			
			t1.show();
			e1.setText("");
		}
		db.close();   
	}
	
	public void limpiar(View v)
	{
		e1.setText("");
		e2.setText("");
		e3.setText("");
		e4.setText("");
	}

	public void baja(View v)
	 {
		Administrador a = new Administrador(this, "empresa", null, 1);
		SQLiteDatabase db = a.getWritableDatabase();
		
		String dato = e1.getText().toString();  
		if(dato.equals(""))
		{
			Toast t1=Toast.makeText(this, "Debe ingresar el Legajo a dar de Baja", Toast.LENGTH_LONG);
			t1.setGravity(Gravity.CENTER, 0, 0);
			t1.show();
		}
		else
		{
		int lx=Integer.parseInt(dato);
		
		int cantidad = db.delete("empleados", "legajo='"+lx+"'",null);
		db.close();
		if (cantidad>0)
			{
				Toast t1=Toast.makeText(this, "El registro para el numero de Legajo: "+lx+"\nborrado correctamente", Toast.LENGTH_LONG);
				t1.setGravity(Gravity.CENTER, 0, 0);
				t1.show();
			}else
			    {
				 Toast t1=Toast.makeText(this, "No existe un registro\npara el numero de Legajo: "+lx, Toast.LENGTH_LONG);
			     t1.setGravity(Gravity.CENTER, 0, 0); t1.show();
			    }
			e1.setText("");
			e2.setText("");
			e3.setText("");
			e4.setText("");
		}	
	 }	
	
	 public void modificacion(View v)
	 {
		Administrador a = new Administrador(this, "empresa", null, 1);
		SQLiteDatabase db = a.getWritableDatabase();

		// Tomar todos los datos a modificar
		// Tener en cuenta que el Legajo no se debe modificar
		String lx=e1.getText().toString();
	    String nx=e2.getText().toString();
		String cx=e3.getText().toString();
		String sx=e4.getText().toString();
		
		ContentValues registro = new ContentValues();
		registro.put("nombre",nx);
		registro.put("cargo",cx);
		registro.put("sueldo",sx);
		int cantidad = db.update("empleados", registro, "legajo='"+lx+"'", null);
		db.close();
	   if(cantidad>0)
	   {
			Toast t1=Toast.makeText(this, "El registro para el numero de Legajo: "+lx+"\nfue modificado correctamente", Toast.LENGTH_LONG);
			t1.setGravity(Gravity.CENTER, 0, 0); t1.show();
		}else
		    {
			 Toast t1=Toast.makeText(this, "No existe un registro\npara el numero de Legajo: "+lx, Toast.LENGTH_LONG);
			 t1.setGravity(Gravity.CENTER, 0, 0); t1.show();
		    }   
        limpiar(v);
	 }	
	
	 public void ver(View v)
	 {
		 String nombre = e2.getText().toString();
		 String cargo  = e3.getText().toString();
		 String sueldo = e4.getText().toString();
		 
		 float ss= Float.parseFloat(sueldo);
		 
		 String neto = sueldo;
		 if(r2.isChecked())
		 { 
			 ss = ss * 1.10f;
		     neto = String.valueOf(ss);
		 }
		
		 Intent i = new Intent (this,Sueldo.class);
		 i.putExtra("nombre", nombre);
		 i.putExtra("cargo", cargo);
		 i.putExtra("sueldo", sueldo);
		 i.putExtra("neto", neto);
		 
		 startActivity(i);
		 
		 
		 
		 
	 }
	 
	 
	 
	 
	 
	 public void salir(View v)	
	 {
		 Toast t1=Toast.makeText(this, "--- Fin del Programa ---", Toast.LENGTH_LONG);
		 
		 //Para colocar color al texto del Toast
		 TextView vista = (TextView) t1.getView().findViewById(android.R.id.message); 
		 vista.setTextColor(Color.GREEN);  

		 t1.setGravity(Gravity.CENTER, 0, 0); t1.show();
		 finish();
		 }
	 
}
