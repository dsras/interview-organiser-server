package com.au.glasgow.repository;


import com.au.glasgow.dto.FindInterviewersRequest;
import com.au.glasgow.entities.Role;
import com.au.glasgow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("FROM User WHERE userEmail = :email")
    User getByEmail(@Param("email") String email);

    @Query("FROM User WHERE username = :username")
    User getByUsername(@Param("username")String username);

    @Query("FROM User WHERE id = :id")
    User getById(@Param("id") Integer id);

    @Query("select r " +
           "from Role r " +
           "where r in (select u.role from UserRole u where u.user.username = :username)")
    Set<Role> getRolesByUsername(@Param("username") String username);

}
