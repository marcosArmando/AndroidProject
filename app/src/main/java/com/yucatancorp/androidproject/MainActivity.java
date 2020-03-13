package com.yucatancorp.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nombreUsuario, passwordUsuario;
    Button logIn;
    TextView crearUsuario;

    boolean checarEmail, checarPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreUsuario = findViewById(R.id.ETnombre);
        passwordUsuario = findViewById(R.id.ETpassword);
        logIn = findViewById(R.id.btnLogIn);
        crearUsuario = findViewById(R.id.TVcrearUsuario);

        checarEmail = checkFields(nombreUsuario);
        checarPassword = checkFields(passwordUsuario);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checarEmail && checarPassword) {

                    startActivity(new Intent(MainActivity.this, PokemonsActivity.class));

                } else {

                    nombreUsuario.setText("");
                    passwordUsuario.setText("");

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.errorCrede), Toast.LENGTH_LONG)
                            .show();

                }

            }
        });

        crearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario(MainActivity.this);
            }
        });
    }

    public void registrarUsuario(Context context){

        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.nuevousuario);
        final EditText nuevoUsuario = dialog.findViewById(R.id.newUsuario);
        final EditText nuevoPassword = dialog.findViewById(R.id.newPassword);

        Button registrar = dialog.findViewById(R.id.btnNuevoUser);

        final boolean checkUsuarioNuevo, checkPasswordNuevo, checkPasswordLenght;

        checkUsuarioNuevo = checkFields(nuevoUsuario);
        checkPasswordLenght = checkFields(nuevoPassword);
        checkPasswordNuevo = checkPassword(nuevoPassword, context);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkUsuarioNuevo && checkPasswordNuevo && checkPasswordLenght) {

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("usuarios", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString(nuevoUsuario.getText().toString(), nuevoPassword.getText().toString());
                    editor.apply();

                    dialog.dismiss();
                }

            }
        });

        dialog.show();

    }

    public boolean checkFields(EditText v){

        String tempString;

        tempString = v.getText().toString();

        if (tempString.isEmpty()) {
            v.setError(getResources().getString(R.string.campoVacio));
            v.requestFocus();

            return false;
        }

        if (v.getId()== R.id.ETnombre && !Patterns.EMAIL_ADDRESS.matcher(tempString).matches()){

            v.setError(getResources().getString(R.string.ingreseMail));
            v.requestFocus();

            return false;
        }

        return true;
    }

    public boolean checkPassword(EditText v, Context context) {

        String passwordTemp = v.getText().toString();

        if (passwordTemp.length() < 5) {

            v.setError(getResources().getString(R.string.necesitaContraMayor));
            Toast.makeText(context, getResources().getString(R.string.contraMayor5), Toast.LENGTH_LONG)
                    .show();

            return false;
        }

        return true;
    }
}
