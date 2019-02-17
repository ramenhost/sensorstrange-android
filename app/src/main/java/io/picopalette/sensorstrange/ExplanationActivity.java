package io.picopalette.sensorstrange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExplanationActivity extends AppCompatActivity {

    private Button mExplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);

        mExplain = findViewById(R.id.explanation_next);

        mExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExplanationActivity.this, InstructionsActivity.class);
                startActivity(intent);
            }
        });
    }
}
