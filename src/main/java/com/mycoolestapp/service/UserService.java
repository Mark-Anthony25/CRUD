package com.mycoolestapp.service;

import com.mycoolestapp.entity.User;
import com.mycoolestapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
//    @Autowired
//    private PasswordEncoder passwordEncoder;

@Autowired
private UserRepository userRepository;
public User registerUser(User user){
    if(userRepository.findByEmail(user.getEmail()) != null){
        return null;
    }
    return userRepository.save(user);
}
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }
    public User updateUser(User user){
        if(userRepository.existsById(user.getId())){
            return userRepository.save(user);
        }
        else {
            throw new RuntimeException ("User not found");
        }
    }
    public User deleteUser(long id){
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            userRepository.delete(user);
        }
        return user;
    }
    public User findUserById(long id){
        return userRepository.findById(id).orElse(null);
    }
    public void emailValidation(String email){
        User existingUser = userRepository.findByEmail(email);
        if(existingUser != null){
            throw new RuntimeException ("Email already exists");
        }

    }
    public String greet(){
        return "Hello World";
    }
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


}
