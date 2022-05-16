package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Provides handling of role-related requests.
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * Gets role from the database by name.
     *
     * @param name the name of the role
     * @return the role retrieved from the database
     */
    @GetMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public Role getRole(@RequestParam(value="name", required = true) String name){
        return roleService.getByName(name);
    }
}
