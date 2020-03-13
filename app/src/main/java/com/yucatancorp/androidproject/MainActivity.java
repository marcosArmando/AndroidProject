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

    public final static String NOMBREUSUARIO = "nombreUsuario";
    public final static String PASSWORDUSUARIO = "passwordUsuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                    Toast.makeText(MainActivity.this, getResources().getString(R.string.UsuarioExito), Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(NOMBREUSUARIO, nombreUsuario.getText().toString());
        outState.putString(PASSWORDUSUARIO, passwordUsuario.getText().toString());

        super.onSaveInstanceState(outState);

    }
}
