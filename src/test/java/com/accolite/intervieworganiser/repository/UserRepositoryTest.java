package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.dto.FindInterviewersRequest;
import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    final String username = "testuser@accolitedigital.com";
    final String password = "testPassword";
    final String email = "testuser@gmail.com";
    final String name = "Test User";
    final String title = "Test Business Title";

    User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User(username, password, email, name, title);
    }

    @Test
    void testSaveUser() {
        /* save new user */
        repository.save(newUser);
        /* ensure repository correctly gets user by username */
        User user = repository.getByUsername(username);
        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    void testGetByEmail(){
        /* save new user */
        repository.save(newUser);
        /* ensure repository correctly gets user by email */
        User user = repository.getByEmail(email);
        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    void getByUsername(){
        /* save new user */
        repository.save(newUser);
        /* ensure repository correctly gets user by email */
        User user = repository.getByUsername(username);
        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    void getUserDetailsByUsername(){
        /* save new user */
        repository.save(newUser);
        /* ensure repository correctly gets user by email */
        User user = repository.getByUsername(username);
        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    void testGetById(){
        /* save user */
        User savedUser = repository.save(newUser);
        Integer id = savedUser.getId();
        /* ensure repository correctly gets user by id */
        User user = repository.getById(id);
        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    void testGetRolesByUsername(){
        /* list of roles assigned to user */
        Role userRole = new Role();
        userRole.setName("User");
        userRole.setDescription("User");
        Role newRole = roleRepository.save(userRole);
        List<Role> roles = new ArrayList<>();
        roles.add(newRole);
        newUser.setRoles(roles);
        Set<Role> userRoles = new LinkedHashSet<>();
        userRoles.add(newRole);
        /* save user */
        repository.save(newUser);
        /* ensure repository correctly gets roles of user */
        assertThat(repository.getRolesByUsername(username)).isEqualTo(userRoles);
    }

}