package com.au.glasgow.service;

import com.au.glasgow.dto.FindInterviewersRequest;
import com.au.glasgow.dto.LoginUser;
import com.au.glasgow.entities.Role;
import com.au.glasgow.entities.User;
import com.au.glasgow.entities.UserAvailability;
import com.au.glasgow.repository.UserRepository;
import com.au.glasgow.service.AvailabilityService;
import com.au.glasgow.service.RoleService;
import com.au.glasgow.service.UserService;
import com.au.glasgow.service.UserSkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    UserService service;

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
        service = new UserService(repository, roleService, availabilityService, userSkillService);
    }

    @Test
    void testSaveUser() {
        /* new user */
        User newUser = new User(username, password, email, name, title);
        /* return this user to mock repository layer saving user */
        when(repository.save(any(User.class))).thenReturn(newUser);
        /* ensure service saved new user correctly */
        assertThat(service.save(newUser).getUsername()).isEqualTo(username);
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
        assertThat(service.checkIfUserExists(user)).isTrue();
    }

    @Test
    void testLoadUserByUsername(){
        /* new user */
        User newUser = new User(username, password, email, name, title);
        /* return this user to mock repository layer getting user by username */
        when(repository.getByUsername(any(String.class))).thenReturn(newUser);
        /* ensure service correctly gets user by username */
        UserDetails user = service.loadUserByUsername(username);
        assertThat(user.getUsername()).isEqualTo(username);
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
        assertThat(service.getAuthority(newUser)).isEqualTo(authorities);
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
        assertThat(service.getAvailableInterviewers(request)).isEqualTo(availabilities);
    }
}
