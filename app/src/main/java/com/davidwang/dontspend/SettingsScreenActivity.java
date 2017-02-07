package com.davidwang.dontspend;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
import android.app.ActionBar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static android.text.TextUtils.isEmpty;

public class SettingsScreenActivity extends AppCompatActivity {

    EditText inflationRateEdit;
    EditText shortTermRateEdit;
    EditText longTermRateEdit;
    EditText SP500RateEdit;

    Button githubLinkButton;
    Button changeButton;
    Button defaultButton;




    static Double inflationRate = 3.29/100.0;
    static Double shortTermRate = 3.45/100.0;
    static Double longTermRate = 4.96/100.0;
    static Double SP500Rate  = 9.50/100.0;
    static Integer dollarValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        inflationRateEdit = (EditText) findViewById(R.id.inflationRateField);
        shortTermRateEdit = (EditText) findViewById(R.id.shortTermRateField);
        longTermRateEdit = (EditText) findViewById(R.id.longTermRateField);
        SP500RateEdit = (EditText) findViewById(R.id.SP500Rate);

        githubLinkButton = (Button) findViewById(R.id.githubButton);
        changeButton = (Button) findViewById(R.id.changeButton);
        defaultButton = (Button) findViewById(R.id.resetDefaultButton);

        inflationRateEdit.requestFocus();

        final Spinner spinner = (Spinner) findViewById(R.id.dollarValueSpinner);

        // Initializing a String Array
        String[] dollarValueArray = new String[]{
                "Real",
                "Nominal"
        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,dollarValueArray
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        final AlertDialog alertDialog = new AlertDialog.Builder(SettingsScreenActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please fill all fields with a number between 0 and 100");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        defaultButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        inflationRate = 3.29/100.0;
                        shortTermRate = 3.45/100.0;
                        longTermRate = 4.96/100.0;
                        SP500Rate  = 9.50/100.0;

                        inflationRateEdit.setText(String.format("%.2f", inflationRate*100));
                        shortTermRateEdit.setText(String.format("%.2f", shortTermRate*100));
                        longTermRateEdit.setText(String.format("%.2f", longTermRate*100));
                        SP500RateEdit.setText(String.format("%.2f", SP500Rate*100));

                    }
                }
        );

        changeButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        if (isEmpty(shortTermRateEdit.getText()) || Double.parseDouble(shortTermRateEdit.getText().toString()) > 100.0 || isEmpty(longTermRateEdit.getText()) || Double.parseDouble(longTermRateEdit.getText().toString()) > 100.0 || isEmpty(SP500RateEdit.getText()) || Double.parseDouble(SP500RateEdit.getText().toString()) > 100.0 || isEmpty(inflationRateEdit.getText()) || Double.parseDouble(inflationRateEdit.getText().toString()) > 100.0)
                        {
                            alertDialog.show();
                        }
                        else
                        {
                            shortTermRate = Double.parseDouble(shortTermRateEdit.getText().toString());
                            longTermRate = Double.parseDouble(longTermRateEdit.getText().toString());
                            SP500Rate = Double.parseDouble(SP500RateEdit.getText().toString());
                            inflationRate = Double.parseDouble(inflationRateEdit.getText().toString());

                            if (spinner.getSelectedItemPosition() == 0)
                            {
                                dollarValue = 0;
                            }
                            else {
                                dollarValue = 1;
                            }

                        }
                    }
                }
        );

        githubLinkButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/davidwangdw/DontSpend-Android"));
                        startActivity(intent);

                    }
                }
        );


    }
}
