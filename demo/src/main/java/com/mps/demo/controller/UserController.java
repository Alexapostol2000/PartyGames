package com.mps.demo.controller;

import com.mps.demo.model.LoginUser;
import com.mps.demo.model.User;
import com.mps.demo.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

  @Autowired
  private UserService userService;
  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody User user) {
    User registeredUser = userService.processRegister(user);
    return ResponseEntity.ok(registeredUser);
  }
  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/login")
  public ResponseEntity<Optional<User>> login(@RequestBody LoginUser loginUser) {
    Optional<User> loggedUser = userService.processLogin(loginUser);
    return ResponseEntity.ok(loggedUser);
  }

}
