package org.kappa.server.repository;

import org.kappa.server.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


// import org.springframework.security.core.userdetails.User;
public interface UserRepository extends MongoRepository<User, String> {

  @Query("{username:'?0'}")
  User findUserByUserName(String username);

}
