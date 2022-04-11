package com.au.glasgow.serviceImpl;

import com.au.glasgow.dto.LoginUser;
import com.au.glasgow.entities.Role;
import com.au.glasgow.entities.User;
import com.au.glasgow.repository.UserRepository;
import com.au.glasgow.requestModels.AvailableUsersRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public User getById(Integer id) {
        return userRepository.getById(id);
    }

    public List<User> getByIds(List<Integer> ids){
        return userRepository.findAllById(ids);
    }

    /* custom methods */

    /* getAvailableUsers
    takes an AvailableUsersRequest object
    finds skills by ID
    finds interviewers that
    (1) have these skills
    (2) have availability on this date between these times
    (3) do not have interviews booked on this date between these times */
    public List<User> getAvailableUsers(AvailableUsersRequest availableUsersRequest){
        return userRepository.getAvailableUser(availableUsersRequest);
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public boolean checkIfUserExists(LoginUser loginUser){
        User user = findOne(loginUser.getUsername());
        if (null == user) {
            return false;
        }
        return true;
    };

    public User findOne(String username) {
        return userRepository.getByUsername(username);
    }

    /* get permissions for user by retrieving roles from database*/
    public Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        userRepository.getRolesByUsername(user.getUsername()).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        Iterator<SimpleGrantedAuthority> it = authorities.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getUserpassword(),
                getAuthority(user));
    }

    public User save(User user) {
        User nUser = new User();
        BeanUtils.copyProperties(user, nUser);
        nUser.setUserpassword(encoder().encode(user.getUserpassword()));
        Role role = roleService.getByName("USER");
        List<Role> roleSet = new ArrayList<>();
        roleSet.add(role);
        if (user.getRoles() != null) {
            for (Role roles : user.getRoles()) {
                roleSet.add(roleService.getByName(roles.getRoleName()));
            }
            nUser.setRoles(roleSet);
        }
        return userRepository.save(nUser);
    }

    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
