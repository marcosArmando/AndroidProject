package com.yucatancorp.androidproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.yucatancorp.androidproject.miscellaneousActions.checkUserInput.checkEmailFormat;
import static com.yucatancorp.androidproject.miscellaneousActions.checkUserInput.checkFields;

@RunWith(MockitoJUnitRunner.class)
public class checkUserEmail {

    @Test
    public void checarSiHayDato() {
        assert checkFields("prueba@correo.com");
    }

    public void checarEmail() {
        assert checkEmailFormat("prueba@correo.com");
    }
}