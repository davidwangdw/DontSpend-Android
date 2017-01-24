package com.davidwang.dontspend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.util.Log;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //placeholder for testing
    Button mButton;
    EditText mEdit;
    TextView tText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.investments_array)

        // spinner click listener
        spinner.setOnItemSelectedListener(this);

        // creating adapter for spinner
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, R.array.investments_array);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.investments_array, android.R.layout.simple_spinner_item);
        // drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(adapter);

        mButton = (Button)findViewById(R.id.calculateButton);
        mEdit   = (EditText)findViewById(R.id.dollarInputField);
        tText = (TextView) findViewById(R.id.fiveYearCalculationResult);
        tText.setText("");

        //t.setText("Step One: blast egg");

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        //Log.v("EditText", mEdit.getText().toString());
                        tText.setText(mEdit.getText());
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        System.out.println(position);

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}
