package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInActivity extends AppCompatActivity
{

    Button btnKijelentkezes;
    TextView txtTeljesNev;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        init();
        btnKijelentkezes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoggedInActivity.this,MainActivity.class);
                SaveSharedPreference.clearUserName(LoggedInActivity.this);
                startActivity(intent);
                finish();

            }
        });

    }
    private void init()
    {
        txtTeljesNev = findViewById(R.id.txtTeljesNevLogged);
        btnKijelentkezes = findViewById(R.id.btnKijelentkezes);
        txtTeljesNev.setText(getIntent().getStringExtra("felhasznaloNev"));
    }
}
