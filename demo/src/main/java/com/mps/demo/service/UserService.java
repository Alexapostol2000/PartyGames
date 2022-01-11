package com.mps.demo.service;

import com.mps.demo.model.LoginUser;
import com.mps.demo.model.User;
import com.mps.demo.repository.UserRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public User processRegister(User user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    user.setTotalScore(0L);
    userRepository.save(user);
    log.debug("The user {} was successfully registered", user.getName());
    return user;
  }

  public Optional<User> processLogin(LoginUser loginUser) {
    String userName = loginUser.getName();
    Optional<User> user = userRepository.findByName(userName);
    if (user.isEmpty()) {
      log.debug("The username {} was not found", loginUser.getName());
      return Optional.empty();
    }
    User userData = user.get();
    if (!passwordEncoder.matches(loginUser.getPassword(), userData.getPassword())) {
      log.debug("The password doesn't match for user {}", loginUser.getName());
      return Optional.empty();
    }
    return user;
  }
}
