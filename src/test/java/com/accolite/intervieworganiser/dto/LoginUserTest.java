package com.accolite.intervieworganiser.dto;

import org.junit.jupiter.api.Test;
import utility.Constants;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginUserTest {

    String username = Constants.interviewerUsername;
    String password = Constants.password;
    String type = "Social";

    @Test
    void testCreationCorrectAttributes(){
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        loginUser.setType(type);
        assertEquals(username, loginUser.getUsername());
        assertEquals(password, loginUser.getPassword());
        assertEquals(type, loginUser.getType());
    }

}
