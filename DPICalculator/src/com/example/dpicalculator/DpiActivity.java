package com.example.dpicalculator;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Math.*;

public class DpiActivity extends Activity {
	
	private AutoCompleteTextView autocompleteX, autocompleteY;
	private EditText edtDiag;
	private TextView captText;

	private double x, y, d;
	private int a, b, ppi;
	
	private static final double INCH = 25.4;
	private static final String FORMAT = "density = %d ppi\nsize = %d x %d mm";
	
	private class TextHandler implements TextWatcher {
		
		private View v;
		
		TextHandler(View v){
			this.v = v;
		}

		@Override
		public void afterTextChanged(Editable s) {
			double val = 0;
			try {
	        	val = Double.parseDouble(s.toString());
	        } catch (NumberFormatException  e){
	        	return;
	        }
			if (v == autocompleteX){
				x = val;
			} else if (v == autocompleteY){
				y = val;
			}
			else if (v == edtDiag){
				d = val;
			} 
			calculate();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		autocompleteX = (AutoCompleteTextView) findViewById(R.id.autocompleteX);
		autocompleteY = (AutoCompleteTextView) findViewById(R.id.autocompleteY);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.resolutions_set, android.R.layout.simple_list_item_1);
		autocompleteX.setAdapter(adapter);
		autocompleteY.setAdapter(adapter);
		autocompleteX.addTextChangedListener(new TextHandler(autocompleteX));
		autocompleteY.addTextChangedListener(new TextHandler(autocompleteY));
		edtDiag = (EditText) findViewById(R.id.editText0);
		edtDiag.addTextChangedListener(new TextHandler(edtDiag));
		captText = (TextView) findViewById(R.id.text0);
	}
	
	private void calculate(){
		if (x == 0 || y == 0 || d == 0){
			captText.setText(null);
			return;
		}
		double k = y/x;
		double k1 = sqrt(pow(k, 2) + 1);
		double _a = d/k1;
		double _b = k * _a;
		ppi = (int) (x/_a);
		a = (int) (_a * INCH);
		b = (int) (_b * INCH);
		captText.setText(String.format(FORMAT, ppi, a, b));
	}


}
