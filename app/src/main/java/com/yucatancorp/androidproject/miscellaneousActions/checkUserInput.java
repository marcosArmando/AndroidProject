package com.yucatancorp.androidproject.miscellaneousActions;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.yucatancorp.androidproject.R;

public class checkUserInput {

    public static boolean checkFields(EditText v, Context context){

        String tempString = v.getText().toString();

        if (tempString.isEmpty()) {
            v.setError(context.getResources().getString(R.string.campoVacio));
            v.requestFocus();

            return false;
        }

        if ((v.getId()== R.id.ETnombre || v.getId()==R.id.newUsuario) && !Patterns.EMAIL_ADDRESS.matcher(tempString).matches()){

            v.setError(context.getResources().getString(R.string.ingreseMail));
            v.requestFocus();

            return false;
        }

        return true;
    }

/*
    public static boolean checkFields(String texto){

        return !texto.isEmpty();

    }

    public static boolean checkEmailFormat(String texto){
        return Patterns.EMAIL_ADDRESS.matcher(texto).matches();
    }

    public static boolean checkPassword(String texto) {

        return !(texto.length() < 5);

    }
*/

    public static boolean checkPassword(EditText v, Context context) {

        String passwordTemp = v.getText().toString();

        if (passwordTemp.length() < 5) {

            v.setError(context.getResources().getString(R.string.necesitaContraMayor));
            Toast.makeText(context, context.getResources().getString(R.string.contraMayor5), Toast.LENGTH_LONG)
                    .show();

            return false;
        }

        return true;
    }
}
