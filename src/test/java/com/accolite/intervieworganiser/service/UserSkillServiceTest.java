package com.accolite.intervieworganiser.service;

import com.accolite.intervieworganiser.entities.*;
import com.accolite.intervieworganiser.repository.UserSkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSkillServiceTest {

    @Mock
    UserSkillRepository userSkillRepository;

    UserSkillService userSkillService;

    final String username1 = "testuser1@accolitedigital.com";
    final String username2 = "testuser2@accolitedigital.com";
    final String password = "testPassword";
    final String email = "testuser@gmail.com";
    final String name = "Test User";
    final String title = "Test Business Title";

    @BeforeEach
    void initUseCase() {
        userSkillService = new UserSkillService(userSkillRepository);
    }

    @Test
    void testSaveUserSkill() {
        /* new user */
        User newUser = new User(username1, password, email, name, title);
        /* new skill */
        Skill newSkill = new Skill("Java", "Expert");
        /* new user-skill */
        UserSkill newUserSkill = new UserSkill(newUser,newSkill);
        /* return this user-skill to mock repository layer saving user-skill */
        when(userSkillRepository.save(any(UserSkill.class))).thenReturn(newUserSkill);
        /* ensure service saved user-skill correctly */
        assertThat(userSkillService.save(newUserSkill)).isEqualTo(newUserSkill);
    }

    @Test
    void testFindBySkills(){
        /* new users */
        User newUser1 = new User(username1, password, email, name, title);
        User newUser2 = new User(username2, password, email, name, title);
        List<User> userList = new ArrayList<>();
        userList.add(newUser1);
        userList.add(newUser2);
        /* list of skill IDs */
        List<Integer> skillIdList = new ArrayList<>();
        skillIdList.add(1);
        skillIdList.add(2);
        /* return this user to mock repository layer getting user by username */
        //when(userSkillRepository.findBySkills(anyList())).thenReturn(userList);
        /* ensure service correctly checks if user exists by username */
        //assertThat(userSkillService.findBySkills(skillIdList)).isEqualTo(userList);
    }
}
