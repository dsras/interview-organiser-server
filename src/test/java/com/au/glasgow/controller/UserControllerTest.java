package com.au.glasgow.controller;

import com.au.glasgow.config.SecurityConfig;
import com.au.glasgow.config.TokenProvider;
import com.au.glasgow.config.UnauthorizedEntryPoint;
import com.au.glasgow.entities.User;
import com.au.glasgow.repository.UserSkillRepository;
import com.au.glasgow.service.SkillService;
import com.au.glasgow.service.TokenValidationService;
import com.au.glasgow.service.UserService;
import com.au.glasgow.service.UserSkillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserController.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TokenProvider tokenProvider;

    @MockBean
    UserService userService;

    @MockBean
    UnauthorizedEntryPoint unauthorizedEntryPoint;

    SecurityConfig securityConfig = new SecurityConfig(userService,unauthorizedEntryPoint);

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    TokenValidationService tokenValidationService;

    @MockBean
    SkillService skillService;

    @MockBean
    UserSkillService userSkillService;

    @MockBean
    UserSkillRepository userSkillRepository;

    final String username = "testuser@accolitedigital.com";
    final String password = "testPassword";
    final String email = "testuser@gmail.com";
    final String name = "Test User";
    final String title = "Test Business Title";

    @Ignore
    @Test
    @WithMockUser(username = username, password = password, roles = "USER")
    public void testSaveUser() throws Exception {
        /* new user */
        User user = new User(username, password, email, name, title);
        user.setId(500);
        /* return this user to mock user service saving user */
        when(userService.save(any(User.class))).thenReturn(user);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users/register")
                                .content(asJsonString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json("{}"));
    }

//    @Test
//    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
//    public void customer_fetch_in_db_success() throws Exception {
//
//        List<Customer> customerList = Arrays.asList(
//                new Customer("sajedul", "karim", "01737186095"),
//                new Customer("shawon", "nirob", "01737186096"),
//                new Customer("aayan", "karim", "01737186097")
//        );
//
//        when(customerService.fetchAllCustomer()).thenReturn(customerList);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/customer/fetchAllCustomer"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("[{}, {}, {}]"));
//    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

