package com.davidwang.dontspend;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
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

import static android.text.TextUtils.isEmpty;
import static java.lang.Math.log;

public class retirementActivity extends AppCompatActivity implements OnItemSelectedListener {

    Button calculateButton;
    EditText networthEdit;
    EditText amountSavedPerYearEdit;
    EditText targetRetirementAmountEdit;
    EditText spendAmountEdit;
    TextView summaryText;
    TextView yearsUntilRetirementText;
    Button retirementInfoButton;

    Double inflation = 0.0329;
    Double returnForSP500 = 0.0950;
    Double yearsUntilRetirementDouble = 0.0;

    Double yearsUntilRetirementDifference = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirement);

        //startActivity(intent);


        calculateButton = (Button) findViewById(R.id.calculateButton);
        networthEdit = (EditText) findViewById(R.id.currentNetWorthText);
        amountSavedPerYearEdit = (EditText) findViewById(R.id.amountSavedPerYearText);
        targetRetirementAmountEdit = (EditText) findViewById(R.id.targetRetirementText);
        spendAmountEdit = (EditText) findViewById(R.id.spendText);
        summaryText = (TextView) findViewById(R.id.summaryText);
        yearsUntilRetirementText = (TextView) findViewById(R.id.yearsUntilRetirement);

        initToolbar();

        retirementInfoButton = (Button) findViewById(R.id.retirementInfoButton);

        networthEdit.requestFocus();


        yearsUntilRetirementText.setText(" ");
        summaryText.setText(" ");

        // spinner element
        /*final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener((OnItemSelectedListener) this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.investments_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener((OnItemSelectedListener) this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.frequency_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);*/

        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        // Initializing a String Array
        String[] investments_array = new String[]{
                "Once",
                "Per Month"
        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,investments_array
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner2.setAdapter(spinnerArrayAdapter);

        // alert code
        final AlertDialog alertDialog = new AlertDialog.Builder(retirementActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please fill all fields with a number between 1 and 9,000,000, and make sure your target retirement amount is more than your current net worth. If you just want to see how long it'll take you to retire on your current savings rate, enter 0 as your spend amount.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        calculateButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        // get values from all the fields

                        //probably an easier way to do this, fix later
                        if (isEmpty(networthEdit.getText()) || Double.parseDouble(networthEdit.getText().toString()) > 9000000.0 || isEmpty(amountSavedPerYearEdit.getText()) || Double.parseDouble(amountSavedPerYearEdit.getText().toString()) > 9000000.0 || isEmpty(targetRetirementAmountEdit.getText())|| Double.parseDouble(targetRetirementAmountEdit.getText().toString()) > 9000000.0 || Double.parseDouble(spendAmountEdit.getText().toString()) > 9000000.0) {

                            //System.out.println("This is empty");
                            alertDialog.show();
                            return;
                         }

                        else

                        {
                            //hide keyboard

                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                            // currency formatter
                            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                            Double networthDouble = Double.parseDouble(networthEdit.getText().toString());
                            Double amountSavedPerYearDouble = Double.parseDouble(amountSavedPerYearEdit.getText().toString());
                            Double targetRetirementAmountDouble = Double.parseDouble(targetRetirementAmountEdit.getText().toString());
                            Double spendAmountDouble = Double.parseDouble(spendAmountEdit.getText().toString());

                            yearsUntilRetirementDouble = yearsUntilRetirementFunc(networthDouble, returnForSP500 - inflation, targetRetirementAmountDouble, 12.0*amountSavedPerYearDouble);

                            if (spinner2.getSelectedItemPosition() == 0) { //once

                                Double newYearsUntilRetirement = yearsUntilRetirementFunc(networthDouble + spendAmountDouble, returnForSP500 - inflation, targetRetirementAmountDouble, 12.0*amountSavedPerYearDouble);
                                yearsUntilRetirementDifference = yearsUntilRetirementDouble - newYearsUntilRetirement;

                            } else { //per month
                                    Double newYearsUntilRetirement = yearsUntilRetirementFunc(networthDouble, returnForSP500 - inflation, targetRetirementAmountDouble, 12.0*(amountSavedPerYearDouble + spendAmountDouble));
                                    yearsUntilRetirementDifference = yearsUntilRetirementDouble - newYearsUntilRetirement;
                            }

                            yearsUntilRetirementText.setText(String.format("%.2f", yearsUntilRetirementDouble));


                            summaryText.setText("If you saved $" + spendAmountDouble.toString() + " instead of spending it, you would be able to retire " + String.format("%.2f", yearsUntilRetirementDifference) + " years, or " + String.format("%.2f", yearsUntilRetirementDifference * 365) + " days earlier!");
                            return;


                        }

                    }

                }




        );

    }



    public double yearsUntilRetirementFunc(double netWorth, double rate, double targetWorth, double payment) {

        return log((targetWorth * (rate-inflation) + payment) / (payment + netWorth * (rate-inflation)))/log(1 + (rate-inflation));
    }

    private void initToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //mToolbar.setTitleTextColor(Color.WHITE);
        myToolbar.setTitle("Don't Spend!");
        myToolbar.showOverflowMenu();
        setSupportActionBar(myToolbar);
    }

    public void openSettings()
    {
        Intent intent = new Intent(this, SettingsScreenActivity.class);
        // next line will instead of opening new activity bring the one to front
        intent .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openMoreInfo(View view)
    {
        System.out.println("in open more info");
        Intent intent = new Intent(this, RetirementInformationPageActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        //System.out.println(position);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
