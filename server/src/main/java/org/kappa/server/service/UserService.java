package org.kappa.server.service;

import org.bson.types.ObjectId;
import org.kappa.server.domain.User;
import org.kappa.server.persistence.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;


@Service
public class UserService {

  private static final String USER_ROLE = "USER";
  private final UserRepository userRepository;

  private final UserDataService userDataService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;


  public UserService(final UserRepository userRepository, final UserDataService userDataService, final BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.userDataService = userDataService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }


  public Optional<User> findUserByUserName(final String username) {
    final var user = this.userRepository.findUserByUserName(username);

    return user.map(value -> new User(value.getUsername(), value.getPassword()));

  }


  public void saveUser(final User user) {
    Assert.notNull(user, "UserRequest must not be null.");

    final var entity = new org.kappa.server.persistence.entity.User(
        new ObjectId(),
        user.username(),
        this.bCryptPasswordEncoder.encode(user.password()),
        Set.of(new SimpleGrantedAuthority(USER_ROLE))
    );

    this.userRepository.save(entity);
  }


  public Optional<User> deleteUser(final String username) {
    Assert.notNull(username, "Username must not be null.");

    final var entity = this.userRepository.findUserByUserName(username);

    if (entity.isEmpty()) {
      return Optional.empty();
    }

    this.userRepository.delete(entity.get());
    this.userDataService.deleteUserDataByUserName(username);

    return entity.map(e -> new User(e.getUsername(), "pwd"));
  }

}
