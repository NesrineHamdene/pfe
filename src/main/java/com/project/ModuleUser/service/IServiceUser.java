package com.project.ModuleUser.service;

import com.project.ModuleUser.entities.User;
import org.springframework.stereotype.Service;


public interface IServiceUser {
    User findByUsername(String username);
    User createUser(User user);

}
