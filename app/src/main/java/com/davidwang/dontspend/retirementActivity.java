package com.davidwang.dontspend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class retirementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirement);

        initToolbar();
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


}
