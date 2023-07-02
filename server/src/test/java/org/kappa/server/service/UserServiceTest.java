package org.kappa.server.service;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kappa.server.persistence.entity.User;
import org.kappa.server.persistence.repository.UserRepository;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserDataService userDataService;
  @Mock
  private BCryptPasswordEncoder bCryptPasswordEncoder;


  @BeforeEach
  void beforeEach() {
    this.userService = new UserService(this.userRepository, this.userDataService, this.bCryptPasswordEncoder);
  }


  @Test
  void testFindUserByUserName_GivenRepositoryReturnsUser_ThenDomainUserShouldBeReturned() {
    // Given
    final var username = "foo";
    final var password = "foobar";
    final var entity = new User(new ObjectId(), username, "foobar", Set.of(new SimpleGrantedAuthority("USER")));

    when(this.userRepository.findUserByUserName(username)).thenReturn(Optional.of(entity));

    // When
    final var user = this.userService.findUserByUserName(username);

    // Then
    assertTrue(user.isPresent());
    assertEquals(new org.kappa.server.domain.User(username, password), user.get());
  }


  @Test
  void testFindUserByUserName_GivenRepositoryReturnsNoUser_ThenEmptyOptionalShouldBeReturned() {
    // Given
    final var username = "foo";

    when(this.userRepository.findUserByUserName(username)).thenReturn(Optional.empty());

    // When
    final var user = this.userService.findUserByUserName(username);

    // Then
    assertTrue(user.isEmpty());
  }


  @Test
  void testSaveUser_GivenDomainUser_ThenRepositorySaveShouldBeCalledWithUserEntity() {
    // Given
    final var user = new org.kappa.server.domain.User("foo", "foobar");

    // When
    this.userService.saveUser(user);

    // Then
    verify(this.userRepository, times(1)).save(any(User.class));

  }


  @Test
  void deleteUser() {
  }
}