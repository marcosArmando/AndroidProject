package com.yucatancorp.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
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

    SharedPreferences sharedPreferences, sharedPreferencesStatus;

    public final static String NOMBREUSUARIO = "nombreUsuario";
    public final static String PASSWORDUSUARIO = "passwordUsuario";
    public final static String USUARIOLOGUEADO = "usurioLogueado";
    public final static String USUARIOS = "usurios";
    public final static String STATUS = "status";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checarLogIn()) {

            irAPokemonsActivity();

        }

        if (savedInstanceState != null) {

            nombreUsuario.setText(savedInstanceState.getString(NOMBREUSUARIO));
            passwordUsuario.setText(savedInstanceState.getString(PASSWORDUSUARIO));

        }

        nombreUsuario = findViewById(R.id.ETnombre);
        passwordUsuario = findViewById(R.id.ETpassword);
        logIn = findViewById(R.id.btnLogIn);
        crearUsuario = findViewById(R.id.TVcrearUsuario);

        checarEmail = checkFields(nombreUsuario, MainActivity.this);
        checarPassword = checkFields(passwordUsuario, MainActivity.this);

        sharedPreferences = getApplicationContext().getSharedPreferences(USUARIOS, 0);
        sharedPreferencesStatus = getApplicationContext().getSharedPreferences(STATUS, 0);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checarEmail && checarPassword) {

                    if (nombreUsuario.getText().toString().equals(sharedPreferences.getString(nombreUsuario.getText().toString(), null))){

                        SharedPreferences.Editor editor = sharedPreferencesStatus.edit();
                        editor.putBoolean(STATUS, true);
                        editor.apply();
                        irAPokemonsActivity();
                    }


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

    public void irAPokemonsActivity() {
        startActivity(new Intent(MainActivity.this, PokemonsActivity.class));
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

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString(nuevoUsuario.getText().toString(), nuevoPassword.getText().toString());
                    editor.apply();

                    dialog.dismiss();

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.UsuarioExito), Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();

    }

    public boolean checarLogIn() {

        return sharedPreferencesStatus.getBoolean(USUARIOLOGUEADO, false);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(NOMBREUSUARIO, nombreUsuario.getText().toString());
        outState.putString(PASSWORDUSUARIO, passwordUsuario.getText().toString());

        super.onSaveInstanceState(outState);

    }
}
