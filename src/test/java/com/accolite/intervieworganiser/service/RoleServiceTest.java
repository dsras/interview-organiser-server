package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RoleServiceTest {

    @Autowired
    RoleRepository repository;

    final String description = "TESTER";
    final String name = "TEST";

    Role newRole;

    @BeforeEach
    void setUp() { newRole = new Role(description, name); }

    @Test
    void testGetByName(){
        /* save new user */
        repository.save(newRole);
        /* ensure service correctly gets role by name */
        Role role = repository.getByName(name);
        assertThat(role.getName()).isEqualTo(name);
    }
}
