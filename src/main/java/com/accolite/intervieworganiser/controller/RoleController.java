package com.accolite.intervieworganiser.controller;

import com.accolite.intervieworganiser.entities.Role;
import com.accolite.intervieworganiser.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /* get role by name */
    @GetMapping("/role")
    public Role getRole(@RequestParam(value="name", required = true) String name){
        return roleService.getByName(name);
    }
}
