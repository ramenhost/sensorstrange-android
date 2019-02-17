package io.picopalette.sensorstrange;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.picopalette.sensorstrange.helpers.Logger;
import io.picopalette.sensorstrange.views.KeyLogTextView;

public class InstructionsActivity extends AppCompatActivity {

    private Button mInstruction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        mInstruction = findViewById(R.id.instruction_next);

        mInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionsActivity.this, LoggingActivity.class);
                startActivity(intent);
            }
        });
    }


}
