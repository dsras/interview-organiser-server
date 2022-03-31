package com.au.glasgow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    /* custom methods */

//    @Query
//    public List<User> getAvailableUsers(AvailableUsersRequest availableUsersRequest){
//        Query query= createQuery("from Student where name = :name and university= :university");
//        query.setParameter("university", "RTGU");
//        query.setParameter("name", "John");
//    }
}
