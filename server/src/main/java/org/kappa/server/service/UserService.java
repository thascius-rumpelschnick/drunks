package org.kappa.server.service;

import org.kappa.server.entity.User;
import org.kappa.server.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

  private final UserRepository userRepository;


  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  public User findUserByUserName(String username) {
    return this.userRepository.findUserByUserName(username);
  }

}
