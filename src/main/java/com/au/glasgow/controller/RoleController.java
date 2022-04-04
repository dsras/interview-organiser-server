package com.au.glasgow.controller;

import com.au.glasgow.entities.Role;
import com.au.glasgow.serviceImpl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /* test method */
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome";
    }


    /* get role by name */
    /* ISSUE: how to get role when name has spaces in it? %20, +
     */
    @GetMapping("/role")
    public Role getRole(@RequestParam(value="name", required = true) String name){
        return roleService.getByName(name);
    }

    /* create new role
    * JSON format for POST request:
    * {
    *   "roleName" : "Software Engineer",
    *   "description" : "Full stack developer"
    * }
    * */
    @PostMapping("/new")
    public Role newRole(@RequestBody Role newRole) {
        return roleService.save(newRole);
    }
}
