package net.sourcewalker.ndkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import net.sourcewalker.ndkdemo.calc.Calculation;
import net.sourcewalker.ndkdemo.calc.JavaCalculation;

import org.apache.commons.lang3.ArrayUtils;

public class MainActivity extends Activity implements View.OnClickListener {

    private Spinner mPrimeLimit;
    private CheckBox mUseNative;
    private TextView mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] limitRes = getResources().getIntArray(R.array.limit_array);
        Integer[] limits = ArrayUtils.toObject(limitRes);
        ArrayAdapter<Integer> limitAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, limits);
        limitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mPrimeLimit = (Spinner) findViewById(R.id.limit);
        mPrimeLimit.setAdapter(limitAdapter);
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
        Integer limit = (Integer) mPrimeLimit.getSelectedItem();
        if (limit == null) {
            mResults.setText("error: no limit set");
        }
        Calculation calc;
        if (mUseNative.isChecked()) {
            mResults.setText("error: native not implemented");
            return;
        } else {
            calc = new JavaCalculation(limit.intValue());
        }
        calc.start(new StatusHandler());
    }

    private final class StatusHandler extends Handler {

        private long start;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MSG_START:
                    mResults.setText("- start -\n");
                    start = msg.getWhen();
                    break;
                case Constants.MSG_END:
                    mResults.append("- end -\n");
                    mResults.append("time: " + (msg.getWhen() - start + "ms"));
                    break;
                case Constants.MSG_STATUS:
                    mResults.append(msg.obj.toString() + "\n");
                    break;
            }
        }

    }

}
