package com.au.glasgow.repository;

import com.au.glasgow.entities.User;
import com.au.glasgow.requestModels.AvailableUsersRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    /* custom methods */
    @Query("FROM User WHERE email = :email")
    User getByEmail(@Param("email") String email);

    @Query("FROM User WHERE username = :username")
    User getByUsername(@Param("username")String username);

//    @Query("From User u, UserAvailability a, Interview i " +
//            "WHERE u = a.user AND ")
//    ResponseEntity<List<User>> getAvailableUser(AvailableUsersRequest availableUsersRequest);
//    @Query
//    public List<User> getAvailableUsers(AvailableUsersRequest availableUsersRequest){
//        Query query= createQuery("from Student where name = :name and university= :university");
//        query.setParameter("university", "RTGU");
//        query.setParameter("name", "John");
//    }
}
