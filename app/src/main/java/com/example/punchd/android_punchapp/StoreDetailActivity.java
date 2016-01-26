package com.example.punchd.android_punchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zwing.integration.android.IntentIntegrator;
import com.google.zwing.integration.android.IntentResult;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * An activity representing a single Store detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StoreListActivity}.
 */
public class StoreDetailActivity extends AppCompatActivity implements OnClickListener {

    private String subscanContent;
    private String subscanFormat;
    private FloatingActionButton subscanBtn;
    private TextView subTxt;// fast view to see if the camera works

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        subTxt = (TextView)findViewById(R.id.subsample);

        subscanBtn = (FloatingActionButton)findViewById(R.id.subScanCamera);
        subscanBtn.setOnClickListener(this);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(StoreDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(StoreDetailFragment.ARG_ITEM_ID));
            StoreDetailFragment fragment = new StoreDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.store_detail_container, fragment)
                    .commit();
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.subScanCamera) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult != null){
            subscanContent = scanningResult.getContents();
            subscanFormat = scanningResult.getFormatName();

            subTxt.setText(subscanFormat + subscanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),"No QR code detected!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, StoreListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
