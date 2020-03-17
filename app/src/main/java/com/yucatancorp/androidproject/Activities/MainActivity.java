package com.yucatancorp.androidproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yucatancorp.androidproject.R;
import com.yucatancorp.androidproject.miscellaneousActions.SharedPreferencesActions;

import static com.yucatancorp.androidproject.miscellaneousActions.IntentsAActivities.irPokemonActivity;
import static com.yucatancorp.androidproject.miscellaneousActions.checkUserInput.checkFields;
import static com.yucatancorp.androidproject.miscellaneousActions.checkUserInput.checkPassword;

public class MainActivity extends AppCompatActivity {

    EditText nombreUsuario, passwordUsuario;
    Button logIn;
    TextView crearUsuario;

    SharedPreferences sharedPreferences, sharedPreferencesStatus;

    SharedPreferencesActions sharedPreferencesActions;

    public final static String NOMBREUSUARIO = "nombreUsuario";
    public final static String PASSWORDUSUARIO = "passwordUsuario";
    public final static String USUARIOS = "usurios";
    public final static String STATUS = "status";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreUsuario = findViewById(R.id.ETnombre);
        passwordUsuario = findViewById(R.id.ETpassword);
        logIn = findViewById(R.id.btnLogIn);
        crearUsuario = findViewById(R.id.TVcrearUsuario);


        if (savedInstanceState != null) {

            nombreUsuario.setText(savedInstanceState.getString(NOMBREUSUARIO));
            passwordUsuario.setText(savedInstanceState.getString(PASSWORDUSUARIO));

        }

        sharedPreferences = getApplicationContext().getSharedPreferences(USUARIOS, 0);
        sharedPreferencesStatus = getApplicationContext().getSharedPreferences(STATUS, 0);

        sharedPreferencesActions = new SharedPreferencesActions(sharedPreferences, sharedPreferencesStatus);

        if (sharedPreferencesActions.checarLogIn()) {
            irPokemonActivity(MainActivity.this);
            finish();
        }

        logIn.setOnClickListener(v -> {

            if (checkFields(nombreUsuario, MainActivity.this) && checkFields(passwordUsuario, MainActivity.this)) {

                if (passwordUsuario.getText().toString().equals(sharedPreferences.getString(nombreUsuario.getText().toString(), null))){

                    sharedPreferencesActions.changeStatus(true);
                    irPokemonActivity(MainActivity.this);
                }

            } else {

                nombreUsuario.setText("");
                passwordUsuario.setText("");

                mostrarToast(MainActivity.this, getResources().getString(R.string.errorCrede));
            }

        });

        crearUsuario.setOnClickListener(v -> registrarUsuario(MainActivity.this));
    }


    public void registrarUsuario(Context context){

        final Dialog dialog = new Dialog(context);
        final Context context1 = context;

        dialog.setContentView(R.layout.nuevousuario);
        final EditText nuevoUsuario = dialog.findViewById(R.id.newUsuario);
        final EditText nuevoPassword = dialog.findViewById(R.id.newPassword);

        Button registrar = dialog.findViewById(R.id.btnNuevoUser);

        registrar.setOnClickListener(v -> {

            if (checkFields(nuevoUsuario, context1) && checkFields(nuevoPassword, context1) && checkPassword(nuevoPassword, context1)) {

                sharedPreferencesActions.registrarUsuarioNuevo(nuevoUsuario.getText().toString(), nuevoPassword.getText().toString());

                dialog.dismiss();

                mostrarToast(MainActivity.this, getResources().getString(R.string.UsuarioExito));
            }

            mostrarToast(MainActivity.this, "no pasa if");

        });

        dialog.show();

    }

    public void mostrarToast(Context context, String mensaje) {

        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(NOMBREUSUARIO, nombreUsuario.getText().toString());
        outState.putString(PASSWORDUSUARIO, passwordUsuario.getText().toString());

    }
}
