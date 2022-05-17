package com.accolite.intervieworganiser.service;


import com.accolite.intervieworganiser.dto.AvailabilityRequest;
import com.accolite.intervieworganiser.dto.FindInterviewersRequest;
import com.accolite.intervieworganiser.dto.LoginUser;
import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
import com.accolite.intervieworganiser.repository.UserRepository;
import com.accolite.intervieworganiser.service.AvailabilityService;
import com.accolite.intervieworganiser.service.RoleService;
import com.accolite.intervieworganiser.service.UserService;
import com.accolite.intervieworganiser.service.UserSkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;
    @Mock
    RoleService roleService;
    @Mock
    AvailabilityService availabilityService;
    @Mock
    UserSkillService userSkillService;

    UserService userService;

    final String username = "testuser@accolitedigital.com";
    final String password = "testPassword";
    final String email = "testuser@gmail.com";
    final String name = "Test User";
    final String title = "Test Business Title";

    final LocalDate date = LocalDate.of(2022, 5, 3);
    final LocalDate date2 = LocalDate.of(2022, 5, 4);
    final LocalTime startTime = LocalTime.of(10,0);
    final LocalTime endTime = LocalTime.of(11,0);

    @BeforeEach
    void initUseCase() {
        userService = new UserService(repository, roleService, availabilityService, userSkillService);
    }

    @Test
    void testSaveUser() {
        /* new user */
        User newUser = new User(username, password, email, name, title);
        List<Role> roles = new ArrayList<>();
        Role newRole = new Role();
        newRole.setName("Test Role");
        roles.add(newRole);
        newUser.setRoles(roles);
        /* return this user to mock repository layer saving user */
        when(repository.save(any(User.class))).thenReturn(newUser);
        /* ensure service saved new user correctly */
        assertThat(userService.save(newUser).getUsername()).isEqualTo(username);
    }

    @Test
    void testCheckIfUserExists(){
        /* new user */
        User newUser = new User(username, password, email, name, title);
        /* return this user to mock repository layer getting user by username */
        when(repository.getByUsername(any(String.class))).thenReturn(newUser);
        /* new login user with same username as new user */
        LoginUser user = new LoginUser();
        user.setUsername(username);
        /* ensure service correctly checks if user exists by username */
        assertThat(userService.checkIfUserExists(user)).isTrue();
    }

    @Test
    void testLoadUserByUsername(){
        /* new user */
        User newUser = new User(username, password, email, name, title);
        /* return this user to mock repository layer getting user by username */
        when(repository.getByUsername(any(String.class))).thenReturn(newUser);
        /* ensure service correctly gets user by username */
        UserDetails user = userService.loadUserByUsername(username);
        assertThat(user.getUsername()).isEqualTo(username);
    }

    @Test
    void testLoadByUsernameNullUserThrowsException() {
        /* assert creation throws null pointer when user is null */
        /* mock repository layer getting null */
        when(repository.getByUsername(any(String.class))).thenReturn(null);
        /* ensure service correctly gets user by username */
        Exception exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername(username));
        /* assert correct error message */
        String expectedMessage = "invalid username or password";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetAuthority(){
        /* new user */
        User newUser = new User(username, password, email, name, title);
        /* list of roles */
        Role userRole = new Role();
        userRole.setName("User");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_User"));
        /* return this list to mock repository layer getting roles */
        when(repository.getRolesByUsername(any(String.class))).thenReturn(roles);
        /* ensure service correctly returns list of authorities */
        assertThat(userService.getAuthority(newUser)).isEqualTo(authorities);
    }

    @Test
    void testGetAvailableInterviewers(){
        /* new user */
        User newUser = new User(username, password, email, name, title);
        /* new user availability list */
        UserAvailability availability = new UserAvailability(newUser, date, startTime, endTime);
        List<UserAvailability> availabilities = new ArrayList<>();
        availabilities.add(availability);
        /* return this list to mock repository layer getting availabilities */
        when(availabilityService.getAvailableInterviewers(any(), any())).thenReturn(availabilities);
        /* new list of skill ids */
        List<Integer> skillIds = new ArrayList<>();
        skillIds.add(1);
        /* new list of interviewers */
        List<User> interviewers = new ArrayList<>();
        interviewers.add(newUser);
        /* return this list to mock repository layer getting interviewers with skill set  */
        when(userSkillService.findBySkills(any(), any(Long.class))).thenReturn(interviewers);
        /* new findInterviewers request */
        FindInterviewersRequest request = new FindInterviewersRequest(date, date2, startTime, endTime,skillIds);
        /* ensure service correctly return list of availabilities */
        assertThat(userService.getAvailableInterviewers(request)).isEqualTo(availabilities);
    }
}
