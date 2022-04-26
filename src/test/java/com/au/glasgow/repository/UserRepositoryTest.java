package com.au.glasgow.repository;

import com.au.glasgow.entities.Role;
import com.au.glasgow.entities.User;
import com.au.glasgow.repository.RoleRepository;
import com.au.glasgow.repository.UserRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Test
    void testSaveUser() {
        /* save new user */
        repository.save(new User(username, password, email, name, title));
        /* ensure repository correctly gets user by username */
        User user = repository.getByUsername(username);
        assertThat(user.getUserName()).isEqualTo(name);
    }

    @Test
    void testGetByEmail(){
        /* save new user */
        repository.save(new User(username, password, email, name, title));
        /* ensure repository correctly gets user by email */
        User user = repository.getByEmail(email);
        assertThat(user.getUserName()).isEqualTo(name);
    }

    @Test
    void testGetById(){
        /* new user */
        User newUser = new User(username, password, email, name, title);
        /* save user */
        User savedUser = repository.save(newUser);
        Integer id = savedUser.getId();
        /* ensure repository correctly gets user by id */
        User user = repository.getById(id);
        assertThat(user.getUserName()).isEqualTo(name);
    }

    @Test
    void testGetRolesByUsername(){
        /* new user */
        User newUser = new User(username, password, email, name, title);
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
