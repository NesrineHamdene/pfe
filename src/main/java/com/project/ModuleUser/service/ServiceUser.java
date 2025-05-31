package com.project.ModuleUser.service;

import com.project.ModuleUser.dto.UserDTO;
import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.enums.Role;
import com.project.ModuleUser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceUser implements IServiceUser {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, UserDTO updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setUsername(updatedUser.getUsername());
            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setPassword(updatedUser.getPassword());
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }



//    @Override
//    public User saveUserWithPhoto(User user, MultipartFile photo) throws IOException {
//        if (photo != null && !photo.isEmpty()) {
//            // Exemple : stockage dans un dossier local /uploads
//            String uploadDir = "uploads/";
//            String originalFilename = photo.getOriginalFilename();
//
//            File destFile = new File(uploadDir + originalFilename);
//            destFile.getParentFile().mkdirs(); // crée le dossier si nécessaire
//            photo.transferTo(destFile);
//
//            user.setFileName(originalFilename); // on stocke uniquement le nom
//        }
//
//        return userRepository.save(user);

   // }


//    @Override
//    public boolean existsByUsername(String username) {
//        return userRepository.existsByUsername(username);
//    }
//
//    @Override
//    public boolean existsByEmail(String email) {
//        return userRepository.existsByEmail(email);
//    }
}
