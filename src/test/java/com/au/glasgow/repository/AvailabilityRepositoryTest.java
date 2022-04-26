package com.au.glasgow.repository;

import com.au.glasgow.entities.User;
import com.au.glasgow.entities.UserAvailability;
import com.au.glasgow.repository.AvailabilityRepository;
import com.au.glasgow.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AvailabilityRepositoryTest {

    @Autowired
    private AvailabilityRepository repository;

    @Autowired
    private UserRepository userRepository;

    final String username = "testuser@accolitedigital.com";
    final String password = "testPassword";
    final String email = "testuser@gmail.com";
    final String name = "Test User";
    final String title = "Test Business Title";

    final LocalDate date = LocalDate.of(2022, 5, 3);
    final LocalTime startTime = LocalTime.of(10,0);
    final LocalTime endTime = LocalTime.of(11,0);

    @Test
    void testSaveAvailability() {
        /* new user */
        userRepository.save(new User(username, password, email, name, title));
        User user = userRepository.getByUsername(username);
        /* new availability */
        UserAvailability newAvailability = new UserAvailability(user, date, startTime, endTime);
        repository.save(newAvailability);
        /* ensure repository correctly retrieves availability by user username */
        List<UserAvailability> availability = repository.getByUsername(username);
        assertThat(availability.get(0)).isEqualTo(newAvailability);
    }

}
