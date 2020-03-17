package com.yucatancorp.androidproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.yucatancorp.androidproject.miscellaneousActions.checkUserInput.checkPassword;

@RunWith(MockitoJUnitRunner.class)
public class LogInTest {


    @Test
    public void checkInputFromUser() {

        assert checkPassword("ContraseñaMayorA5Caracteres");


    }


}
