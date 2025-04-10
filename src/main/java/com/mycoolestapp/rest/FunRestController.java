package com.mycoolestapp.rest;

import com.mycoolestapp.Coach;
import com.mycoolestapp.entity.User;
import com.mycoolestapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class FunRestController {
    private final UserService userService;
    private final Coach myCoach;

    @Autowired
    public FunRestController(Coach myCoach, UserService userService) {
        this.myCoach = myCoach;
        this.userService = userService;
    }

    @GetMapping("/mark")
    public String hello(){
        return "hello world!!!";
    }

    @GetMapping("/greet")
    public String greet(){
        return userService.greet();
    }


    @GetMapping("/work")
    public String work(){
        return "work";
    }
    @GetMapping("/lucky")
    public String lucky(){
        return "today is lucky" + getTraining();
    }

    @GetMapping("/basketball")
    public Coach getMyCoach() {
        return myCoach;
    }
//    @Autowired
//    public void setMyCoach(Coach myCoach) {
//        this.myCoach = myCoach;
//    }
    @GetMapping("/training")
    public String getTraining(){
        return myCoach.getTimeWorkout().toString();
    }


    //back-end for register form
//    @Autowired
//    public void UserService(UserService userService) {
//        this.userService = userService;
//    }
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userService.emailValidation(user.getEmail());
        return userService.registerUser(user);
    }

    @RequestMapping("/getusers")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PutMapping ("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
        return userService.updateUser(user);
    }
//    @PatchMapping("update/{id}")
//    public User patchUser(@PathVariable Long id, @RequestBody User user) {
//        user.setId(id);
//        userService.updateUser(user);
//        return userService.updateUser(user);
//    }
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User existingUser = userService.findUserByEmail(user.getEmail());
        if(existingUser != null || !encoder.matches(user.getPassword(), existingUser.getPassword())) {

        }
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }


}
