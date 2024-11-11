package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/findByEmailAndPassword")
    public ResponseEntity<?> findUserIdByEmailAndPassword(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<Integer> userId = userService.findUserIdByEmailAndPassword(email, password);

        if (userId.isPresent()) {
            return ResponseEntity.ok(userId.get()); // Renvoie l'ID de l'utilisateur
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Vérifier si l'utilisateur existe déjà par email
        if (userService.getUserByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email already exists");
        }
        // Vérifier si l'utilisateur existe déjà par username
        if (userService.getUserByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists");
        }
        // Si tout est bon, on crée l'utilisateur
        userService.createUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        boolean isAuthenticated = userService.authenticateUser(email, password);
        if (isAuthenticated) {
            return ResponseEntity.ok("Authentication successful");
        } else {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }
}
