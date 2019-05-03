package com.teachapp.teachapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RequestActivity extends AppCompatActivity {

    TextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Button btnSendRequest = (Button) findViewById(R.id.request_tutorial);
        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendRequestTutorial();
            }
        });
    }

    private void SendRequestTutorial(){

        Intent intent = new Intent(RequestActivity.this,RequestActivity.class);
        startActivity(intent);
        finish();
    }
}
