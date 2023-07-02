package org.kappa.server.service;

import org.bson.types.ObjectId;
import org.kappa.server.domain.UserData;
import org.kappa.server.persistence.repository.UserDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;


@Service
public class UserDataService {

  private final UserDataRepository userDataRepository;


  public UserDataService(final UserDataRepository userDataRepository) {
    this.userDataRepository = userDataRepository;
  }


  public Optional<UserData> findUserDataByUserName(final String username) {
    Assert.notNull(username, "UserRequest must not be null.");

    final var entity = this.userDataRepository.findUserDataByUserName(username);

    return entity.map(e -> new UserData(e.username(), e.highScore(), e.level()));
  }


  public Optional<UserData> saveUserData(final UserData userData) {
    Assert.notNull(userData, "UserData must not be null.");

    var entity = this.userDataRepository.findUserDataByUserName(userData.username());

    final var id = entity.isPresent() ? entity.get().id() : new ObjectId();

    entity = Optional.of(
        this.userDataRepository.save(
            new org.kappa.server.persistence.entity.UserData(id, userData.username(), userData.highScore(), userData.level())
        )
    );

    return entity.map(e -> new UserData(e.username(), e.highScore(), e.level()));
  }


  public Optional<UserData> deleteUserDataByUserName(final String username) {
    Assert.notNull(username, "UserRequest must not be null.");

    final var entity = this.userDataRepository.findUserDataByUserName(username);

    if (entity.isEmpty()) {
      return Optional.empty();
    }

    this.userDataRepository.delete(entity.get());

    return entity.map(e -> new UserData(e.username(), e.highScore(), e.level()));
  }

}
