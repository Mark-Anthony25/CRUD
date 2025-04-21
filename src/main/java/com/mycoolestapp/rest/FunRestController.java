package com.mycoolestapp.rest;

import com.mycoolestapp.Coach;
import com.mycoolestapp.entity.User;
import com.mycoolestapp.entity.history;
import com.mycoolestapp.service.HistoryService;
import com.mycoolestapp.service.UserService;
import com.mycoolestapp.dto.HistoryRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;


@Controller
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class FunRestController {
    private static final Logger logger = LoggerFactory.getLogger(FunRestController.class);
    private final UserService userService;
    private final Coach myCoach;
    private final HistoryService historyService;

    @Autowired
    public FunRestController(Coach myCoach, UserService userService, HistoryService historyService) {
        this.myCoach = myCoach;
        this.userService = userService;
        this.historyService = historyService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            logger.error("Error during user registration", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    @GetMapping("/register")
    public String redirectRegisterPage(Model model) {
        return "redirect:/api/users/register";
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            logger.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete failed: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserDirect(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            logger.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Update failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserDirect(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delete failed: " + e.getMessage());
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser == null || !userService.checkPassword(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        // Return only non-sensitive fields
        User safeUser = new User();
        safeUser.setId(existingUser.getId());
        safeUser.setEmail(existingUser.getEmail());
        safeUser.setRole(existingUser.getRole());
        return ResponseEntity.ok(safeUser);
    }

    @GetMapping("/joke-generator")
    public String showJokeGeneratorPage() {
        return "joke-generator";
    }

    @GetMapping("/joke-generator-login")
    public String showJokeGeneratorGenericPage() {
        return "joke-generator-login";
    }

    @GetMapping("/admin-dashboard")
    public String showAdminDashboard() {
        return "admin-dashboard";
    }

    @PostMapping("/history")
    public ResponseEntity<?> saveHistory(@RequestBody HistoryRequestDTO dto) {
        try {
            User user = userService.findUserById(dto.getUserId());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
            }
            history historyRecord = new history();
            historyRecord.setJokeText(dto.getJokeText());
            historyRecord.setCategory(dto.getCategory());
            try {
                historyRecord.setGeneratedAt(LocalDateTime.parse(dto.getGeneratedAt()));
            } catch (DateTimeParseException e) {
                historyRecord.setGeneratedAt(LocalDateTime.now());
            }
            historyRecord.setUser(user);

            history saved = historyService.saveHistory(historyRecord);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving history: " + e.getMessage());
        }
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<?> getHistoryById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(historyService.getLast10HistoryByUser(user));
    }
}
