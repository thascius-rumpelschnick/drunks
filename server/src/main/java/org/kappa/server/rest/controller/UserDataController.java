package org.kappa.server.rest.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.kappa.server.domain.UserData;
import org.kappa.server.service.UserDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user-data")
public class UserDataController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDataController.class);

  private final UserDataService userDataService;


  public UserDataController(final UserDataService userDataService) {
    this.userDataService = userDataService;
  }


  @GetMapping
  public ResponseEntity<UserData> getUserData(@NotBlank(message = "Username must not be null.") @RequestParam final String username) {
    final var userData = this.userDataService.findUserDataByUserName(username);

    return userData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
  }


  @PatchMapping
  public ResponseEntity<UserData> saveUserData(@NotNull(message = "UserData must not be null.") @RequestBody final UserData userData) {
    final var savedUserData = this.userDataService.saveUserData(userData);

    return savedUserData.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
  }


  @ExceptionHandler
  public ResponseEntity<String> handleException(final Exception exception) {
    LOGGER.error("Exception: {}.", exception.getMessage(), exception);

    return ResponseEntity.internalServerError().body(exception.getMessage());
  }

}
