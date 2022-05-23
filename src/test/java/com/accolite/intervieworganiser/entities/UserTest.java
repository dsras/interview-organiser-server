package com.accolite.intervieworganiser.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Test
    void testGetAndSetAllAttributes(){
        int id = 1;
        String username = "tester@accolitedigital.com";
        String name = "Tester";
        String password = "myPassword123";
        String email = "tester@email.com";
        String title = "official tester";
        Long mobile = 123456789L;
        String account = "Test Account";
        String unit = "Testing";
        String designation = "tester";
        LocalDate joinDate = LocalDate.of(2022, 3, 1);
        String location = "Glasgow";
        int experience = 1;
        Role role = new Role(); role.setName("TESTER");
        List<Role> roles = new ArrayList<>(); roles.add(role);

        User user = new User(username, password, email, name, title);
        user.setAccount(account);
        user.setDateOfJoining(joinDate);
        user.setBusinessUnit(unit);
        user.setLocation(location);
        user.setMobile(mobile);
        user.setPriorExperience(experience);
        user.setDesignation(designation);
        user.setRoles(roles);
        user.setId(id);

        assertEquals(name, user.getName());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(location, user.getLocation());
        assertEquals(designation, user.getDesignation());
        assertEquals(experience, user.getPriorExperience());
        assertEquals(mobile, user.getMobile());
        assertEquals(unit, user.getBusinessUnit());
        assertEquals(title, user.getBusinessTitle());
        assertEquals(joinDate, user.getDateOfJoining());
    }
}
