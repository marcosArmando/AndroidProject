package com.yucatancorp.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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

        boolean checarEmail = checkFields(nombreUsuario);
        boolean checarPassword = checkFields(passwordUsuario);


    }

    public boolean checkFields(View v){

        EditText temp = (EditText) v;
        String tempString;

        tempString = String.valueOf(temp.getText());

        if (tempString.isEmpty()) {
            temp.setError(getResources().getString(R.string.campoVacio));
            temp.requestFocus();

            return false;
        }

        if (temp.getId()== R.id.ETnombre && !Patterns.EMAIL_ADDRESS.matcher(tempString).matches()){

            temp.setError(getResources().getString(R.string.ingreseMail));
            temp.requestFocus();

            return false;
        }

        return true;
    }

}
