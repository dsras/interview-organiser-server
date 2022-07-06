package com.accolite.intervieworganiser.entities;

import org.junit.jupiter.api.Test;
import utility.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRoleTest {

    @Test
    void testGettersAndSetters() {
        UserRole userRole = new UserRole();
        User user = Constants.interviewer;
        userRole.setUser(user);

        assertEquals(user, userRole.getUser());
    }

}
