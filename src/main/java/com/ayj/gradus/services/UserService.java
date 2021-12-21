package com.ayj.gradus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ayj.gradus.model.User;
import com.ayj.gradus.repositories.RoleRepository;
import com.ayj.gradus.repositories.UserRepository;

@Service
public class UserService {

  private UserRepository userRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserService(UserRepository userRepository,
      RoleRepository roleRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public User findUserByUsername(String userName) {
    return userRepository.findByUsername(userName);
  }

  public User saveUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setActive(true);
    return userRepository.save(user);
  }

}