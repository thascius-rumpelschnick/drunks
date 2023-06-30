package org.kappa.server.persistence.repository;

import org.kappa.server.persistence.entity.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface UserDataRepository extends MongoRepository<UserData, String> {

  @Query("{username:'?0'}")
  Optional<UserData> findUserDataByUserName(String username);
}
