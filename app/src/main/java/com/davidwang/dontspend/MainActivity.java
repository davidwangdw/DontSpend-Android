package com.davidwang.dontspend;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView.OnItemSelectedListener;
import java.text.NumberFormat;



public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    //placeholder for testing
    Button mButton;
    EditText mEdit;
    TextView fiveYearCalculationText;
    TextView tenYearCalculationText;
    TextView twentyYearCalculationText;
    TextView thirtyYearCalculationText;

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

        //set color
        View view = this.getWindow().getDecorView();
        //view.setBackgroundColor(Color.rgb(93, 100, 118));

        initToolbar();

        // spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.investments_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // alert code
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please input a number between 1 and 100,000");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        mButton = (Button) findViewById(R.id.calculateButton);
        mEdit = (EditText) findViewById(R.id.dollarInputField);

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(300);

        fiveYearCalculationText = (TextView) findViewById(R.id.fiveYearCalculationResult);
        tenYearCalculationText = (TextView) findViewById(R.id.tenYearCalculationResult);
        twentyYearCalculationText = (TextView) findViewById(R.id.twentyYearCalculationResult);
        thirtyYearCalculationText = (TextView) findViewById(R.id.thirtyYearCalculationResult);

        fiveYearCalculationText.setVisibility(TextView.INVISIBLE);
        tenYearCalculationText.setVisibility(TextView.INVISIBLE);
        twentyYearCalculationText.setVisibility(TextView.INVISIBLE);
        thirtyYearCalculationText.setVisibility(TextView.INVISIBLE);


        mButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        // get values from all the fields

                        if (isEmpty(mEdit) || Double.parseDouble(mEdit.getText().toString()) > 100000.0)
                        {
                            //System.out.println("This is empty");
                            alertDialog.show();
                        }
                        else {
                            //System.out.println(spinner.getSelectedItemPosition());
                            // currency formatter
                            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                            Double amount = Double.parseDouble(mEdit.getText().toString());

                            if (spinner.getSelectedItemPosition() == 0)
                            {

                                fiveYearCalculationText.startAnimation(in);
                                fiveYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double fiveYearReturn = returnsIfInvested(amount, returnForShortTerm, 5.0, 0);
                                String fiveYearReturnString = formatter.format(fiveYearReturn);
                                fiveYearCalculationText.setText(fiveYearReturnString + " in 5 years");

                                tenYearCalculationText.startAnimation(in);
                                tenYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double tenYearReturn = returnsIfInvested(amount, returnForShortTerm, 10.0, 0);
                                String tenYearReturnString = formatter.format(tenYearReturn);
                                tenYearCalculationText.setText(tenYearReturnString + " in 10 years");

                                twentyYearCalculationText.startAnimation(in);
                                twentyYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double twentyYearReturn = returnsIfInvested(amount, returnForShortTerm, 20.0, 0);
                                String twentyYearReturnString = formatter.format(twentyYearReturn);
                                twentyYearCalculationText.setText(twentyYearReturnString + " in 20 years");

                                thirtyYearCalculationText.startAnimation(in);
                                thirtyYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double thirtyYearReturn = returnsIfInvested(amount, returnForShortTerm, 30.0, 0);
                                String thirtyYearReturnString = formatter.format(thirtyYearReturn);
                                thirtyYearCalculationText.setText(thirtyYearReturnString + " in 30 years");
                            }
                            else if (spinner.getSelectedItemPosition() == 1)
                            {
                                fiveYearCalculationText.startAnimation(in);
                                fiveYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double fiveYearReturn = returnsIfInvested(amount, returnForLongTerm, 5.0, 0);
                                String fiveYearReturnString = formatter.format(fiveYearReturn);
                                fiveYearCalculationText.setText(fiveYearReturnString + " in 5 years");

                                tenYearCalculationText.startAnimation(in);
                                tenYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double tenYearReturn = returnsIfInvested(amount, returnForLongTerm, 10.0, 0);
                                String tenYearReturnString = formatter.format(tenYearReturn);
                                tenYearCalculationText.setText(tenYearReturnString + " in 10 years");

                                twentyYearCalculationText.startAnimation(in);
                                twentyYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double twentyYearReturn = returnsIfInvested(amount, returnForLongTerm, 20.0, 0);
                                String twentyYearReturnString = formatter.format(twentyYearReturn);
                                twentyYearCalculationText.setText(twentyYearReturnString + " in 20 years");

                                thirtyYearCalculationText.startAnimation(in);
                                thirtyYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double thirtyYearReturn = returnsIfInvested(amount, returnForLongTerm, 30.0, 0);
                                String thirtyYearReturnString = formatter.format(thirtyYearReturn);
                                thirtyYearCalculationText.setText(thirtyYearReturnString + " in 30 years");
                            }
                            else if (spinner.getSelectedItemPosition() == 2)
                            {
                                fiveYearCalculationText.startAnimation(in);
                                fiveYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double fiveYearReturn = returnsIfInvested(amount, returnForSP500, 5.0, 0);
                                String fiveYearReturnString = formatter.format(fiveYearReturn);
                                fiveYearCalculationText.setText(fiveYearReturnString + " in 5 years");

                                tenYearCalculationText.startAnimation(in);
                                tenYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double tenYearReturn = returnsIfInvested(amount, returnForSP500, 10.0, 0);
                                String tenYearReturnString = formatter.format(tenYearReturn);
                                tenYearCalculationText.setText(tenYearReturnString + " in 10 years");

                                twentyYearCalculationText.startAnimation(in);
                                twentyYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double twentyYearReturn = returnsIfInvested(amount, returnForSP500, 20.0, 0);
                                String twentyYearReturnString = formatter.format(twentyYearReturn);
                                twentyYearCalculationText.setText(twentyYearReturnString + " in 20 years");

                                thirtyYearCalculationText.startAnimation(in);
                                thirtyYearCalculationText.setVisibility(TextView.VISIBLE);
                                Double thirtyYearReturn = returnsIfInvested(amount, returnForSP500, 30.0, 0);
                                String thirtyYearReturnString = formatter.format(thirtyYearReturn);
                                thirtyYearCalculationText.setText(thirtyYearReturnString + " in 30 years");
                            }


                        }

                    }
                });

    }


    public void openSettings(View view)
    {
        Intent intent = new Intent(this, SettingsScreenActivity.class);
        startActivity(intent);
    }

    public void openSettings()
    {
        Intent intent = new Intent(this, SettingsScreenActivity.class);
        // next line will instead of opening new activity bring the one to front
        intent .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        // next line will instead of opening new activity bring the one to front
        intent .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openRetirement()
    {
        Intent intent = new Intent(this, retirementActivity.class);
        // next line will instead of opening new activity bring the one to front
        intent .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
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

    //to check for empty text
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
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

        //System.out.println(position);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // for toolbar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                return true;

            case R.id.main_activity:
                openMain();
                return true;

            case R.id.retirement_activity:
                openRetirement();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
