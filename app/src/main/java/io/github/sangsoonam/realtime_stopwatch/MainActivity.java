package io.github.sangsoonam.realtime_stopwatch;

import android.os.Bundle;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mStartStopButton;
    private boolean mStarted;
    private long mStartTime;

    private final Runnable mUpdateTextView = new Runnable() {
        @Override
        public void run() {
            Trace.beginSection("UPDATE");
            mTextView.setText(String.valueOf(System.currentTimeMillis() - mStartTime));
            mTextView.post(mUpdateTextView);
            Trace.endSection();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartStopButton = (Button) findViewById(R.id.btn_start_stop);
        mTextView = (TextView) findViewById(R.id.txt_time);
        mStartStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStarted = !mStarted;
                mStartStopButton.setText(mStarted ? R.string.btn_stop: R.string.btn_start);

                if (mStarted) {
                    mStartTime = System.currentTimeMillis();
                    mTextView.post(mUpdateTextView);
                } else {
                    mTextView.removeCallbacks(mUpdateTextView);
                }
            }
        });
    }
}
