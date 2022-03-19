package com.example.jpamanytomany.service;

import com.example.jpamanytomany.Repository.RoleRepository;
import com.example.jpamanytomany.Repository.UserReposiory;
import com.example.jpamanytomany.entities.Role;
import com.example.jpamanytomany.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements  UserService{
    private RoleRepository roleRepository ;
    private UserReposiory userReposiory ;
    @Override
    public List<User> findAllUsers() {
        return userReposiory.findAll() ;
    }
    @Override
    public User addNewUser(User user) {
      // user.setUsername(UUID.randomUUID().toString());
        return userReposiory.save(user);
    }
    @Override
    public User findUserByUserName(String username) {
        return userReposiory.findByUsername(username);
    }
    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role) ;
    }
    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
    @Override
    public Role findRoleByRoleName(String rolename) {
        return roleRepository.findByRoleName(rolename);
    }
    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = findUserByUserName(username) ;
        Role role = findRoleByRoleName(roleName) ;
        if(user.getRoles()!=null){
            user.getRoles().add(role) ;
            role.getUsers().add(user) ;
        }
    }
    @Override
    public User authenticate(String username, String password) {
       User user = userReposiory.findByUsername(username) ;
       if(user!=null){
           if(user.getPassword().equals(password))
               return user;
       }
       throw new RuntimeException("Bad credental");
    }
}
