package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.config.TokenProvider;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.repository.UserSkillRepository;
import com.accolite.intervieworganiser.service.SkillService;
import com.accolite.intervieworganiser.service.TokenValidationService;
import com.accolite.intervieworganiser.service.UserService;
import com.accolite.intervieworganiser.service.UserSkillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = UserController.class)
@AutoConfigureMockMvc
// @WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    TokenProvider tokenProvider;

    @MockBean
    TokenValidationService tokenValidationService;

    @MockBean
    SkillService skillService;

    @MockBean
    UserSkillService userSkillService;

    @MockBean
    UserSkillRepository userSkillRepository;

    // @Autowired
    // WebApplicationContext webApplicationContext;

    @Test
    @WithMockUser(username = "tester", roles = { "RECRUITER", "ADMIN" })
    void testFindUserReturnsCorrectResponse() throws Exception {
        /* build mock request */
        RequestBuilder request = MockMvcRequestBuilders.get("/users/findUser?username=tester")
            .accept(MediaType.TEXT_PLAIN)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8");
        User expectedUser = new User(
                "tester",
                "password",
                "email",
                "name",
                "title"
        );

        /* mock user service getUserDetailsByUsername */
        when(userService.getUserDetailsByUsername("tester")).thenReturn(expectedUser);

        /* mock perform request and get response as User object */
        MvcResult result = mvc.perform(request).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        User response = objectMapper.readValue(contentAsString, User.class);

        /* assert response is expected user */
        assertEquals(expectedUser, response);
    }

    // @Test
    // void testSaveUserReturnsCorrectResponse() throws Exception {
    //
    // User newInterviewer = Constants.interviewer;
    // User newRecruiter = Constants.recruiter;
    //
    // when(userService.save(newInterviewer)).thenReturn(newInterviewer);
    // when(userService.save(newRecruiter)).thenReturn(newRecruiter);
    //
    // ObjectMapper mapper = new ObjectMapper();
    // mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    // ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
    // String newInterviewerJson = writer.writeValueAsString(newInterviewer);
    //
    // mvc.perform(post("/users/register")
    // .content(newInterviewerJson))
    // .andExpect(status().isCreated())
    // .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
    // .andExpect(jsonPath("$", hasSize(1)))
    // .andExpect(jsonPath("$[0].id", is(1)))
    // .andExpect(jsonPath("$[0].description", is("Lorem ipsum")))
    // .andExpect(jsonPath("$[0].title", is("Foo")));
    //
    //
    // verify(userServiceMock, times(1)).save(newInterviewer);
    // verifyNoMoreInteractions(userServiceMock);
    // }
}
