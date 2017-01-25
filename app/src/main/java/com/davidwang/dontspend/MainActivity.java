package com.davidwang.dontspend;

import android.net.Uri;
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
import android.content.Intent;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    //placeholder for testing
    Button mButton;
    EditText mEdit;
    TextView fiveYearCalculationText;
    TextView tenYearCalculationText;
    TextView twentyYearCalculationText;
    TextView thirtyYearCalculationText;

    Button settingsButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

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

        mButton = (Button) findViewById(R.id.calculateButton);
        mEdit = (EditText) findViewById(R.id.dollarInputField);
        fiveYearCalculationText = (TextView) findViewById(R.id.fiveYearCalculationResult);
        tenYearCalculationText = (TextView) findViewById(R.id.tenYearCalculationResult);
        twentyYearCalculationText = (TextView) findViewById(R.id.twentyYearCalculationResult);
        thirtyYearCalculationText = (TextView) findViewById(R.id.thirtyYearCalculationResult);
        fiveYearCalculationText.setText("");


        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //Log.v("EditText", mEdit.getText().toString());
                        fiveYearCalculationText.setText(mEdit.getText());
                    }
                });



    }

    public void openSettings(View view)
    {
        Intent intent = new Intent(this, SettingsScreenActivity.class);
        startActivity(intent);
        //System.out.println("Hello");
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
