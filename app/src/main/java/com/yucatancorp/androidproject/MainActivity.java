package com.yucatancorp.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText nombreUsuario, passwordUsuario;
    Button logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreUsuario = findViewById(R.id.ETnombre);
        passwordUsuario = findViewById(R.id.ETpassword);
        logIn = findViewById(R.id.btnLogIn);

    }
}
