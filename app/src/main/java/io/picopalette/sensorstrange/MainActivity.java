package io.picopalette.sensorstrange;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import io.picopalette.sensorstrange.helpers.Logger;
import io.picopalette.sensorstrange.views.KeyLogTextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";
    private KeyLogTextView mTextMessage;
    private AppCompatButton mControlButton;
    private EditText mSesEditText;
    private boolean isSensorLogging = false;
    private SensorManager mSensorManager;
    private Sensor mAccSensor;
    private Sensor mGyroSensor;
    private Logger sLogger;
    private Logger kLogger;
    private static final float NS2MS = 1.0f / 1000000.0f;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this,ExplanationActivity.class);
        startActivity(intent);
//        mTextMessage = findViewById(R.id.message);
//        //mControlButton = findViewById(R.id.controlButton);
//        mSesEditText = findViewById(R.id.sesName);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        try {
//            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//            mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//            mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//        } catch (NullPointerException e) {
//            Log.d(TAG, e.getMessage());
//        }
//
//        mControlButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!isSensorLogging) {
//                    startSensorLogging();
//                } else {
//                    stopSensorLogging();
//                }
//            }
//        });
//
//        mTextMessage.setText(getResources().getString(R.string.message));
//        mTextMessage.setVisibility(View.GONE);
    }

    private void startSensorLogging() {
        Log.i(TAG, "Starting sensor log");
        isSensorLogging = true;
        mControlButton.setText(getResources().getString(R.string.stop));
        mTextMessage.setVisibility(View.VISIBLE);
        mTextMessage.requestFocus();

        mSensorManager.registerListener(this, mAccSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mGyroSensor, SensorManager.SENSOR_DELAY_GAME);

        String ses = mSesEditText.getText().toString();
        sLogger = new Logger(getApplicationContext(), ses, Logger.LOG_SENSOR);
        kLogger = new Logger(getApplicationContext(), ses, Logger.LOG_KEY);
        mTextMessage.setLogger(kLogger);
        mSesEditText.setEnabled(false);
    }



    private void stopSensorLogging() {
        Log.i(TAG, "Stopping sensor log");
        isSensorLogging = false;
        mControlButton.setText(getResources().getString(R.string.start));
        mTextMessage.setVisibility(View.GONE);
        mTextMessage.hideKeyboard();
        mSensorManager.unregisterListener(this);
        mSesEditText.setText("");
        mSesEditText.setEnabled(true);
        sLogger.close();
        kLogger.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSensorLogging) {
            stopSensorLogging();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String log =  (long)(event.timestamp*NS2MS) + ","+ event.sensor.getName().substring(7) + "," + event.values[0] + "," + event.values[1] + "," + event.values[2];
        //Log.d(TAG, log);
        sLogger.appendLog(log);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
