package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnBejelentkezes, btnRegisztracio;
    EditText txtFelhasznalonev, txtJelszo;
    private dbhelper adatbazisSegito;
    CheckBox chBoxJegyezzMeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnRegisztracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnBejelentkezes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtFelhasznalonev.getText().toString().equals("") &&
                    !txtJelszo.getText().toString().equals(""))
                {
                    Cursor felhasznalo = adatbazisSegito.felhasznaloEllenorzes(txtFelhasznalonev.getText().toString(), txtJelszo.getText().toString());

                    if (felhasznalo.getCount() == 1 && felhasznalo.moveToFirst())
                    {
                        Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
                        intent.putExtra("felhasznaloNev", felhasznalo.getString(0));

                        if (chBoxJegyezzMeg.isChecked())
                        {
                            SaveSharedPreference.setUserName(MainActivity.this, felhasznalo.getString(0));
                        }
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        RosszAdatBevitel();

                    }
                }
                else
                {
                    UresenHagyottMezok();
                }

            }
        });
        if (SaveSharedPreference.getUserName(MainActivity.this).length() != 0) {
            Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
            intent.putExtra("felhasznaloNev", SaveSharedPreference.getUserName(MainActivity.this));
            startActivity(intent);
            finish();
        }
    }

    private void init()
    {

        adatbazisSegito = new dbhelper(MainActivity.this);
        txtJelszo = findViewById(R.id.txtJelszo);
        txtFelhasznalonev = findViewById(R.id.txtEmail);
        btnRegisztracio = findViewById(R.id.btnRegisztracio);
        btnBejelentkezes = findViewById(R.id.btnBejelentkezes);
        chBoxJegyezzMeg = findViewById(R.id.chBoxJegyezzMeg);
    }

    private void RosszAdatBevitel()
    {
        Toast.makeText(this, "Hibás felhasználónév vagy jelszó!", Toast.LENGTH_SHORT).show();
    }

    private void UresenHagyottMezok() {
        Toast.makeText(this, "Nem adott meg minden adatot!", Toast.LENGTH_SHORT).show();
    }

}