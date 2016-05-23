package com.sevenheaven.segmentcontrol.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sevenheaven.segmentcontrol.SegmentControl;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    private SegmentControl mSegmentHorzontal;
    private SegmentControl mSegmentVertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSegmentHorzontal = (SegmentControl) findViewById(R.id.segment_control);
        mSegmentVertical = (SegmentControl) findViewById(R.id.segment_control2);
        mSegmentHorzontal.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                Log.i(TAG, "onSegmentControlClick: index = " + index);
            }
        });
        mSegmentVertical.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                Log.i(TAG, "onSegmentControlClick: index" + index);
            }
        });
        mSegmentVertical.setText("AAA", "BBB", "CCC");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
