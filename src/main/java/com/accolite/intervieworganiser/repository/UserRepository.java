package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.entities.User;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("FROM User WHERE email = :email")
    User getByEmail(@Param("email") String email);

    @Query("FROM User WHERE username = :username")
    User getByUsername(@Param("username") String username);

    @Query("FROM User WHERE id = :id")
    User getById(@Param("id") Integer id);

    @Query(
        "select r "
            +
            "from Role r "
            +
            "where r in (select u.role from UserRole u where u.user.username = :username)"
    )
    Set<Role> getRolesByUsername(@Param("username") String username);

    @Query("FROM User u " + "WHERE u.username = :username")
    User getUserDetailsByUsername(@Param("username") String username);
}
