package net.sourcewalker.ndkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private Spinner mPrimeLimit;
    private CheckBox mUseNative;
    private TextView mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrimeLimit = (Spinner) findViewById(R.id.limit);
        mUseNative = (CheckBox) findViewById(R.id.check_native);
        findViewById(R.id.start).setOnClickListener(this);
        mResults = (TextView) findViewById(R.id.results);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start) {
            startCalculation();
        }
    }

    private void startCalculation() {

    }

}
