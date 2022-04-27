package com.accolite.intervieworganiser.persistence;

import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.repository.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void testSaveNewUser() {
        final Integer id = 100;
        final String username = "testuser@accolitedigital.com";
        final String password = "testpassword";
        final String email = "testuser@gmail.com";
        final String name = "Test User";
        final String title = "Test Business Title";

        entityManager.persist(new User(id, username, password, email, name, title));
        User user = repository.getById(id);
        assertThat(user.getName()).isEqualTo(name);
    }
}
