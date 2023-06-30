package org.kappa.server.rest.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.kappa.server.domain.User;
import org.kappa.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user")
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;


  public UserController(final UserService userService) {
    this.userService = userService;
  }


  @PostMapping("/register")
  public ResponseEntity<String> saveUser(@Valid final User user) {
    if (this.userService.findUserByUserName(user.username()).isPresent()) {
      return ResponseEntity.badRequest().body("User with username: " + user.username() + " is already registered.");
    }

    this.userService.saveUser(user);

    return ResponseEntity.ok("User created");
  }


  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteUser(@NotBlank(message = "Username must not be null.") @RequestParam final String username) {
    final var user = this.userService.deleteUser(username);

    if (user.isEmpty()) {
      return ResponseEntity.badRequest().body("User with username: " + username + " could not be deleted.");
    }

    return ResponseEntity.ok("User deleted.");
  }


  @ExceptionHandler
  public ResponseEntity<String> handleException(final Exception exception) {
    LOGGER.error("Exception: {}.", exception.getMessage(), exception);

    return ResponseEntity.internalServerError().body(exception.getMessage());
  }

}
