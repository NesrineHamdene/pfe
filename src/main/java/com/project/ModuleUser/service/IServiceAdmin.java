package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.AdminDTO;
import com.project.ModuleUser.entities.Admin;
import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.enums.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IServiceAdmin {
    @Transactional
    Admin createAdmin(AdminDTO admin);
    @Transactional

    User updateUser(Long userId, AdminDTO userDTO);
    @Transactional
    void deleteUser(Long userId);
    void assignRole(User user, String role);
    void removeRole(User user, String role);
    void resetUserPassword(User user);
    void lockUserAccount(User user);
    void unlockUserAccount(User user);
    @Transactional
    List<User> getUsersByRole(Role role);
    @Transactional
    User updateUserFromDTO(Long userId, AdminDTO userDTO);
    @Transactional
    List<User> getUsersByRole(String role);

}
