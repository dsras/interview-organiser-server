package com.accolite.intervieworganiser.repository;

import com.accolite.intervieworganiser.entities.Role;
import com.google.j2objc.annotations.Weak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("FROM Role WHERE name = :name")
    Role getByName(@Param("name") String name);

    @Query(
        value = "Insert Into user_roles (id, user_id, role_id) " +
                "values (:id, :user_id, 2);",
        nativeQuery = true
    )
    void setNewUserRole(
            @Param("id") Integer id,
            @Param("user_id") Integer user_id
    );

    @Query(
            value = "SELECT MAX(id) " +
            "FROM user_roles",
            nativeQuery = true
    )
    Integer getMaxIdInUserRoles();
}
