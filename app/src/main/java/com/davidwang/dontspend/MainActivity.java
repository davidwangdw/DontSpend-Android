package com.davidwang.dontspend;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.davidwang.dontspend.R;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.util.Log;
import java.text.NumberFormat;

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

    // numbers

    // these are the geometric returns calculated from the bottom website for the years 1928 - 2015
    // http://pages.stern.nyu.edu/~adamodar/New_Home_Page/datafile/histretSP.html
    // inflation rates are gathered from this website for the years 1914 - 2016
    // http://www.tradingeconomics.com/united-states/inflation-cpi

    Double inflation = 0.0329;
    Double returnForSP500 = 0.0950;
    Double returnForShortTerm = 0.0345;
    Double returnForLongTerm = 0.0496;

    //for real or nominal values
    Integer dollarValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

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
                        // get values from all the fields

                        Double amount = Double.parseDouble(mEdit.getText().toString());

                        NumberFormat formatter = NumberFormat.getCurrencyInstance();

                        //Log.v("EditText", mEdit.getText().toString());

                        Double fiveYearReturn = returnsIfInvested(amount, returnForShortTerm, 5.0, 0);
                        String fiveYearReturnString = formatter.format(fiveYearReturn);

                        fiveYearCalculationText.setText(fiveYearReturn.toString());
                    }
                });



    }

    public void openSettings(View view)
    {
        Intent intent = new Intent(this, SettingsScreenActivity.class);
        startActivity(intent);
        //System.out.println("Hello");
    }

    // calculations
    public double returnsIfInvested(Double amount, Double rate, Double years, Integer dollar)
    {
        if (dollar == 0)
        {
            return amount*Math.pow(1 + rate - inflation,years);
        }
        else
        {
            return amount*Math.pow(1 + rate,years);
         }

    }

    private void initToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //mToolbar.setTitleTextColor(Color.WHITE);
        myToolbar.setTitle("Don't Spend!");
        myToolbar.showOverflowMenu();
        setSupportActionBar(myToolbar);
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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
