package com.au.glasgow.repository;

import com.au.glasgow.entities.Role;
import com.au.glasgow.entities.User;
import com.au.glasgow.requestModels.AvailableUsersRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {

    /* custom methods */
    @Query("FROM User WHERE email = :email")
    User getByEmail(@Param("email") String email);

    @Query("FROM User WHERE username = :username")
    User getByUsername(@Param("username")String username);

    @Query("SELECT a.user from UserAvailability a")
//            "UserAvailability a, Interview i " +
//            "WHERE u = a.user AND u.id = i.interviewerId")
    List<User> getAvailableUser(AvailableUsersRequest availableUsersRequest);

//    @Query("SELECT r from Role r WHERE r.id = (SELECT ur.id from UserRole ur WHERE ur.user.username = ?1)")
    Set<Role> getRolesByUsername(String username);

}
