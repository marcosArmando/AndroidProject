package com.yucatancorp.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.yucatancorp.androidproject.checkUserInput.checkFields;
import static com.yucatancorp.androidproject.checkUserInput.checkPassword;

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

        checarEmail = checkFields(nombreUsuario, MainActivity.this);
        checarPassword = checkFields(passwordUsuario, MainActivity.this);

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

        checkUsuarioNuevo = checkFields(nuevoUsuario, context);
        checkPasswordLenght = checkFields(nuevoPassword, context);
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


}
