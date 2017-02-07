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

import static android.text.TextUtils.isEmpty;

/**
 * Created by davidwang on 2/4/17.
 */

public class RetirementInformationPageActivity extends AppCompatActivity{

    Button calculateButton;
    TextView summaryText;
    EditText monthlyExpensesEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirement_information_age);

        summaryText = (TextView) findViewById(R.id.retirementAmountText);
        summaryText.setText(" ");

        monthlyExpensesEdit = (EditText) findViewById(R.id.monthlyExpensesEdit);

        calculateButton = (Button) findViewById(R.id.calculateButton);

        // alert code
        final AlertDialog alertDialog = new AlertDialog.Builder(RetirementInformationPageActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please fill the field with a number less than 100,000");
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

                        if (isEmpty(monthlyExpensesEdit.getText()) || Double.parseDouble(monthlyExpensesEdit.getText().toString()) > 100000) {
                            //System.out.println("This is empty");
                            alertDialog.show();
                        } else {
                            // currency formatter
                            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                            Double networthDouble = Double.parseDouble(monthlyExpensesEdit.getText().toString());

                            Double retirementAmount = networthDouble * 4.0;

                            String retirementAmountString = formatter.format(retirementAmount);
                            summaryText.setText(retirementAmountString);



                        }

                    }

                }




        );

    }

    public void openSettings(View view)
    {
        Intent intent = new Intent(this, SettingsScreenActivity.class);
        startActivity(intent);
    }
}
