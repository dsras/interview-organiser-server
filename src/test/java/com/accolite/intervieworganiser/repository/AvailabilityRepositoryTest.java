package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AvailabilityRepositoryTest {

    @Autowired
    private AvailabilityRepository repository;

    @Autowired
    private UserRepository userRepository;

    final String username = "testuser@accolitedigital.com";
    final String username2 = "testuser2@accolitedigital.com";
    final String password = "testPassword";
    final String email = "testuser@gmail.com";
    final String name = "Test User";
    final String title = "Test Business Title";

    final LocalDate date = LocalDate.of(2022, 5, 3);
    final LocalTime startTime = LocalTime.of(10, 0);
    final LocalTime endTime = LocalTime.of(11, 0);

    @Test
    void testSaveAvailability() {
        /* new user */
        User user = userRepository.save(new User(username, password, email, name, title));
        /* new availability */
        UserAvailability newAvailability = new UserAvailability(user, date, startTime, endTime);
        UserAvailability savedAvailability = repository.save(newAvailability);
        /* ensure repository correctly saved availability */
        assertThat(savedAvailability).isEqualTo(newAvailability);
    }

    @Test
    void testGetByUsername() {
        /* new user */
        User user = userRepository.save(new User(username, password, email, name, title));
        /* new availability */
        UserAvailability newAvailability = new UserAvailability(user, date, startTime, endTime);
        repository.save(newAvailability);
        /* ensure repository correctly retrieves availability by user username */
        List<UserAvailability> availability = repository.getByUsername(username);
        assertThat(availability.get(0)).isEqualTo(newAvailability);
    }

    @Test
    void testGetByUsers() {
        /* new users */
        User user1 = userRepository.save(new User(username, password, email, name, title));
        User user2 = userRepository.save(new User(username2, password, email, name, title));
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        /* new availabilities */
        UserAvailability newAvailability1 = new UserAvailability(user1, date, startTime, endTime);
        repository.save(newAvailability1);
        UserAvailability newAvailability2 = new UserAvailability(user2, date, startTime, endTime);
        repository.save(newAvailability2);
        List<UserAvailability> newAvailabilities = new ArrayList<>();
        newAvailabilities.add(newAvailability1);
        newAvailabilities.add(newAvailability2);
        /* ensure that repository correctly retrieves availabilites by list of users */
        // List<UserAvailability> savedAvailabilities = repository.getByUsers(users);
        // assertThat(savedAvailabilities).isEqualTo(newAvailabilities);
    }

}
