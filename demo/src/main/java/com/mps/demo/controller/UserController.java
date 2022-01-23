package com.mps.demo.controller;

import com.mps.demo.model.LoginUser;
import com.mps.demo.model.User;
import com.mps.demo.model.UserDTO;
import com.mps.demo.service.UserService;
import com.mps.demo.service.jwt.JwtUtils;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody User user) {
    User registeredUser = userService.processRegister(user);
    return ResponseEntity.ok(registeredUser);
  }

  @PostMapping("/login")
  public ResponseEntity<Optional<UserDTO>> login(@RequestBody LoginUser loginUser) {
    return ResponseEntity.ok(userService.processLogin(loginUser));
  }

  @GetMapping("/score/user")
  public ResponseEntity getScore(@RequestHeader("Authorization") String jwt) {
    return userService.getScore(jwt);
  }

}
