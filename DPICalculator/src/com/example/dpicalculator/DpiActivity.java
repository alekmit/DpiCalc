package com.example.dpicalculator;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static java.lang.Math.*;

public class DpiActivity extends Activity implements AdapterView.OnItemSelectedListener, TextWatcher {
	
	private Spinner spinnerX, spinnerY;
	private EditText edtDiag;
	private TextView captText;
	
	private int x, y;
	private double d;
	private int a, b, dpi;
	
	private static final double INCH = 25.4;
	private static final String FORMAT = "density = %d dpi;\nsize = %d x %d mm";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spinnerX = (Spinner) findViewById(R.id.spinner0);
		spinnerY = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.resolutions_set, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerX.setAdapter(adapter);
		spinnerY.setAdapter(adapter);
		spinnerX.setOnItemSelectedListener(this);
		spinnerY.setOnItemSelectedListener(this);
		edtDiag = (EditText) findViewById(R.id.editText0);
		edtDiag.addTextChangedListener(this);
		captText = (TextView) findViewById(R.id.text0);
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String item = (String) parent.getItemAtPosition(pos);
        int val = 0;
        try {
        	val = Integer.parseInt(item);
        } catch (NumberFormatException  e){
        	return;
        }
        if (parent == spinnerX) {
        	x = val;
        } else if (parent == spinnerY){
        	y = val;
        }
        calculate();
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private void calculate(){
		if (x == 0 || y == 0 || d == 0){
			captText.setText(null);
			return;
		}
		double k = sqrt(pow((double) y/x, 2) + 1);
		double _a = d/k;
		double _b = k * _a;
		dpi = (int) (x/_a);
		a = (int) (_a * INCH);
		b = (int) (_b * INCH);
		captText.setText(String.format(FORMAT, dpi, a, b));
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}

	@Override
	public void afterTextChanged(Editable s) {
		try {
			d = Double.parseDouble(s.toString());
		} catch (NumberFormatException e) {
			return;
		}
		calculate();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {}

}
