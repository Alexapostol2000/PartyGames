package com.mps.demo.service;

import com.mps.demo.model.LoginUser;
import com.mps.demo.model.User;
import com.mps.demo.model.UserDTO;
import com.mps.demo.model.UserRole;
import com.mps.demo.repository.UserRepository;
import com.mps.demo.service.jwt.JwtUtils;
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

  @Autowired
  private JwtUtils jwtUtils;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public User processRegister(User user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    user.setTotalScore(0L);
    user.setUserRole(UserRole.ROLE_USER);
    userRepository.save(user);
    log.debug("The user {} was successfully registered", user.getName());
    return user;
  }

  public Optional<UserDTO> processLogin(LoginUser loginUser) {
    String userName = loginUser.getName();
    Optional<User> user = userRepository.findByName(userName);
    if (!user.isPresent()) {
      log.debug("The username {} was not found", loginUser.getName());
      return Optional.empty();
    }
    User userData = user.get();
    if (!passwordEncoder.matches(loginUser.getPassword(), userData.getPassword())) {
      log.debug("The password doesn't match for user {}", loginUser.getName());
      return Optional.empty();
    }
    User loggedUser = user.get();
    UserDTO userDTO = new UserDTO();
    userDTO.setName(loggedUser.getName());
    userDTO.setTotalScore(loggedUser.getTotalScore());
    userDTO.setUserRole(loggedUser.getUserRole());
    userDTO.setJwt("Bearer " + jwtUtils.generateJwtToken(loggedUser));

    return Optional.of(userDTO);
  }
}
