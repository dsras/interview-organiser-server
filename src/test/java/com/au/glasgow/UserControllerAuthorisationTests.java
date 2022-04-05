package com.au.glasgow;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class UserControllerAuthorisationTests {

    @Test
    @WithMockUser(username="ahmed",roles={"ADMIN"})
    public void shouldGetAllRoundsByUserId() throws Exception {
    }

}
