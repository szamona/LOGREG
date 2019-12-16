package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity
{

    Button btnRegisztracio,btnVissza;
    EditText txtEmail,txtFelhasznalonev,txtJelszo,txtTeljesnev;
    private dbhelper adatbazisSegito;
    boolean boolEmail, boolFelhnev,boolJelszo,boolTeljesnev;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        btnVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegisztracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtJelszo.getText().toString().equals("")&&
                    !txtEmail.getText().toString().equals("")&&
                    !txtTeljesnev.getText().toString().equals("") &&
                    !txtFelhasznalonev.getText().toString().equals(""))
                {

                    AdatRogzites();

                }
            }
        });
        txtEmail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                boolEmail= !txtEmail.getText().toString().equals("");
                Vizsgalat();
            }
        });
        txtJelszo.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                boolJelszo= !txtJelszo.getText().toString().equals("");
                Vizsgalat();
            }
        });
            txtFelhasznalonev.addTextChangedListener(new TextWatcher()
            {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                boolFelhnev= !txtFelhasznalonev.getText().toString().equals("");
                Vizsgalat();
            }
        });
        txtTeljesnev.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                boolTeljesnev= !txtTeljesnev.getText().toString().equals("");
                Vizsgalat();
            }
        });
        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (adatbazisSegito.ellenorzesEmail(txtEmail.getText().toString()))
                    {
                       txtEmail.setTextColor(Color.RED);
                    }
                    else
                    {
                        txtEmail.setTextColor(Color.GREEN);
                    }
                }


            }
        });
        txtFelhasznalonev.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (!hasFocus)
                {
                    if (adatbazisSegito.ellenorzesFelhnev(txtFelhasznalonev.getText().toString()))
                    {
                        txtFelhasznalonev.setTextColor(Color.RED);
                    }
                    else
                    {
                        txtFelhasznalonev.setTextColor(Color.GREEN);
                    }
                }

            }
        });

    }
    private void init()
    {
        adatbazisSegito = new dbhelper(RegisterActivity.this);
        txtJelszo = findViewById(R.id.txtJelszoReg);
        txtEmail = findViewById(R.id.txtEmailReg);
        txtTeljesnev = findViewById(R.id.txtTeljesNevReg);
        txtFelhasznalonev = findViewById(R.id.txtFelhasznalonevReg);
        btnRegisztracio = findViewById(R.id.btnRegisztacioReg);
        btnRegisztracio.setEnabled(false);
        btnVissza = findViewById(R.id.btnVissza);

    }
    public void AdatRogzites()
    {
        Boolean eredmeny=false;
        boolean helyesEmail = Pattern.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", txtEmail.getText().toString());
        boolean helyesTNev= Pattern.matches("^[A-ZÖÜÓŐÚŰÁÉÍ]{1}[a-zöüóőúéáűí]+\\s[A-ZÖÜÓŐÚŰÁÉÍ]{1}[a-zöüóőúéáűí]+",txtTeljesnev.getText().toString());
        if (helyesEmail && helyesTNev){
            String Email = txtEmail.getText().toString();
            String Jelszo = txtJelszo.getText().toString();
            String TeljesNev = txtTeljesnev.getText().toString();
            String felhnev = txtFelhasznalonev.getText().toString();
            eredmeny = adatbazisSegito.adatRogzites(Email,felhnev,Jelszo,TeljesNev);
        }
        else
        {
            eredmeny= false;
            Toast.makeText(this, "Sikertelen adat rögzítés!", Toast.LENGTH_SHORT).show();
        }

        if (eredmeny)
        {
            Toast.makeText(this, "Sikeres adatrögzítés!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(this, "Adatrögzítés nem sikerült! Az e-mail vagy a felhasználónév már foglalt", Toast.LENGTH_SHORT).show();
    }


    public void Vizsgalat()
    {
        if (boolFelhnev&&boolEmail&&boolJelszo&&boolTeljesnev)
        {
            btnRegisztracio.setEnabled(true);
        }
        else
        {
            btnRegisztracio.setEnabled(false);
        }
    }
}
